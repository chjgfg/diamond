package org.lyf.diamond.core.execute.data.select;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Dictionary;
import org.lyf.diamond.core.entity.auxiliary.Relation;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.execute.log.LogParse;
import org.lyf.diamond.core.file.data.DataFile;
import org.lyf.diamond.core.utile.CloneUtils;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program:IntelliJ IDEA
 * @discription:从数据库中查看指定条件的数据
 * @author: GG-lyf
 * @create:2022-19-22.1.3 17:19:47
 */
@SuppressWarnings("all")
public class Select {

  /**
   * 1.找 后面要返回的条件,表名(可能有多个)或连接(左内右),where后面的数据
   * 2.处理返回的条件,把要返回的条件通过OperationUtils类进行计算
   * 3.处理表名,有多表的自然连接,把他们分割开来,判端是否存在.左内右连接是是通过三个关键字来判断的,之后是分割,处理,组装,找出表进行组装匹配
   * 4.where后面的条件有可能也是 连接查询, 有 and or in 等 有 group by . oeder by 等 最后 可能有limit
   *
   * @param ms
   * @param cmd
   * @return
   */
  public List<Data> select(Matcher ms, String cmd) {
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      return null;
    }


    String table_name = ms.group(12);//表名

    String three = ms.group(32);// select version() select user() select database();

//    System.out.println("three" + three);
    List<Data> data = new ArrayList<>();
    if (table_name != null) {

      String p = cmd.split(" ")[0];
      String[] splits = UseDatabase.getPath().split("\\\\");
      String cheak = UserParse.cheak(Authority.getName(), splits[splits.length - 1], table_name.contains(";") ? table_name.replace(";", "") : "", p);
      if (!cheak.equals(p)) {
        List<Data> list = new ArrayList<>();
        list.add(new Data());
        return list;
      }


      String purpose = ms.group(2);//*
      String link = ms.group(14);//left join tab2 on tab1.size = tab2.size , right join tab2 on tab1.size = tab2.size , innor join tab2 on tab1.size = tab2.size
      String where = ms.group(29);//where
      String conditions = ms.group(30);//	id <> 1 having dd group by dd ;


      table_name = table_name.replace(";", "");
      List<String> tables = SelectProcess.splitTables(table_name);
//      System.out.println(tables);
      for (String s : tables) {
        if (!DataFile.isTable(s)) {
//          System.out.println("table " + s + " is not exist");
          return null;
        }
      }

      List<Data> new_data = new ArrayList<>();
      List<List<String>> conditionsLists = new ArrayList<>();
      //拆分的条件
      List<List<String>> purpose1 = SelectProcess.purpose(purpose);
      if (tables.size() == 1) {
        if (link != null) {
          if (link.contains(Relation.inner_join_) || link.contains(Relation.left_join_) || link.contains(Relation.right_join_)) {
            //[[left,  person, user, size, people, size], [right, dept, user, size, people, size], [innor, people, user, size, people, size]]
            List<List<String>> n_tables = SelectProcess.splitLink(link);
//          System.out.println(n_tables);
            //插入日志
            insertTolog(cmd, n_tables);
            //得到拼接好的表
            new_data = SelectProcess.setTable(n_tables);
          }
          if (where != null) {
            conditionsLists = SelectParse.splitCondition(conditions);
          } else {
            //啥都不用做
            toLog(cmd, tables.get(0));
          }
        } else {
          if (where != null) {
            conditionsLists = SelectParse.splitCondition(conditions);
//          } else {
            //找数据
          } else {
            toLog(cmd, tables.get(0));
          }
          new_data = SelectProcess.falseCartesianProduct(tables);
        }
      } else if (tables.size() > 1) {
        //默认不解析加逗号的多表,只解析链接
        if (link != null) {
          if (link.contains(Relation.inner_join_) || link.contains(Relation.left_join_) || link.contains(Relation.right_join_)) {
            //[[left,  person, user, size, people, size], [right, dept, user, size, people, size], [innor, people, user, size, people, size]]
            List<List<String>> n_tables = SelectProcess.splitLink(link);
//          System.out.println(n_tables);
            //插入日志
            insertTolog(cmd, n_tables);
            //得到拼接好的表
            new_data = SelectProcess.setTable(n_tables);
          }
          if (where != null) {
            conditionsLists = SelectParse.splitCondition(conditions);
          } else {
            //啥都不用做
//            insertTolog(cmd, n_tables);
          }
        } else {
          if (where != null) {
            //id <> 1 having dd group by dd ;
            int i = 0;
            if (conditions.contains(Relation.and_)) {
              i = conditions.indexOf(Relation.and);
            } else if (conditions.contains(Relation.or_)) {
              i = conditions.indexOf(Relation.or);
            }
            //拿到and或or的第一个字串之后判断这个字串中是否存在两个 . 和 一个 =
            //存在 dw.uu_id = uu.id 这种结构的时候就切割组装起来,把他和后面的条件分开,让后面的条件单独进入操作,它组装成 inner 那个再进入link操作
//        System.out.println(conditions);
            if (i != 0) {
              String substring = conditions.substring(0, i).trim();
              new_data = getData(substring);
              conditions = conditions.trim().substring(i + 3, conditions.length()).replace(";", "").trim();
//          System.out.println(conditions);
              conditionsLists = SelectParse.splitCondition(conditions);
//          System.out.println(conditionsLists);
            } else if (check(conditions)) {
              //这个就完全没有条件,就  dw.uu_id = uu.id; 这种使用正则匹配
//          System.out.println(conditions);
              new_data = getData(conditions);
            } else {
              conditionsLists = SelectParse.splitCondition(conditions);
              new_data = SelectProcess.falseCartesianProduct(tables);
            }
          } else {
            //找笛卡尔积
            new_data = SelectProcess.falseCartesianProduct(tables);
          }
        }
      }

//      System.out.println(conditionsLists);
//      new_data.forEach(System.out::println);
      //得到数据,条件和想要的结果显示之后开始找数据
      List<Data> lastData = SelectProcess.getLastData(purpose1, new_data, conditionsLists);
//      System.out.println();
      lastData.forEach(System.out::println);


      List<Data> newData = new ArrayList<>();
      lastData.stream().forEach(vo->{
        newData.add(CloneUtils.deepCloneByFalseCartesianProduct(vo));
      });
      lastData.clear();
      lastData.addAll(newData);

      lastData.forEach(System.out::println);

      lastData.stream().forEach(vo -> vo.getFields().stream().forEach(ll -> {
        if (ll.getType().equals(Dictionary.date)) {
          if (ll.getValue() != null) {
            ll.setValue(TimeUtils.timestampToDate(ll.getValue()));
          }
        }
      }));


      return lastData;
//use kk;
//select * from dw;

//select * from dw , uu;
//select * from dw , uu where dw.id > 1;

//select * from dw where limit uu.id 1 , 2;
//select * from dw where limit dw.name 'vsvds' , 2;

//select * from uu where uu.id > 3;

//select * from uu where uu.id > 150 order by uu.id desc;

//select * from uu where order by uu.id desc;

//select * from dw , uu where dw.uu_id = uu.id and uu.id > 3;
//select * from dw , uu where dw.uu_id = uu.id;

//select * from dw left join uu on dw.uu_id = uu.id , right join uuk on uuk.uu_id = uu.id where uu.id > 1 group by uu.id having sum(uu.id) > 3 order by uu.id desc uu.id limit 1 , 2;
//select * from dw left join uu on dw.uu_id = uu.id , right join uuk on uuk.uu_id = uu.id where uu.id > 1 group by uu.id having sum(uu.id) > 3 order by uu.id desc uu.id;
//select * from dw left join uu on dw.uu_id = uu.id , right join uuk on uuk.uu_id = uu.id where uu.id <> 1 group by uu.id having dd order by dd desc;
//select * from dw left join uu on dw.uu_id = uu.id , right join uuk on uuk.uu_id = uu.id where uu.id <> 1 group by uu.id having sum(uu.id) > 3 order by dd desc;
//select * from dw left join uu on dw.uu_id = uu.id , left join uuk on uuk.uu_id = uu.id where dw.id <= 2;
//select * from dw left join uu on dw.uu_id = uu.id , inner join uuk on uuk.uu_id = uu.id where uu.id < 2;

//select * from uu right join uuk on uuk.uu_id = uu.id , left join dw on dw.uu_id = uu.id where uu.id > 2;
//select * from uu right join uuk on uuk.uu_id = uu.id , right join dw on dw.uu_id = uu.id where uu.id > 2;
//select * from uu right join uuk on uuk.uu_id = uu.id , inner join dw on dw.uu_id = uu.id where uu.id > 2;

//select * from dw inner join uu on dw.uu_id = uu.id , left join uuk on uuk.uu_id = uu.id where uu.id > 2;
//select * from dw inner join uu on dw.uu_id = uu.id , right join uuk on uuk.uu_id = uu.id where uu.id > 2;
//select * from dw inner join uu on dw.uu_id = uu.id , inner join uuk on uuk.uu_id = uu.id where uu.id > 2;

    } else if (three != null) {// select version(); 和 select user(); 和 select database();
      List<Field> fields = new ArrayList<>();
      if ("database".equals(three)) {
        String[] split = UseDatabase.getPath().split("\\\\");
        String s = split[split.length - 1];
        fields.add(new Field("", "", ""));
        data.add(new Data(1, s, s, fields));
      } else if ("user".equals(three)) {
        fields.add(new Field("", "", ""));
        data.add(new Data(1, Authority.getName(), Authority.getName(), fields));
      } else if ("version".equals(three)) {
        fields.add(new Field("", "", ""));
        data.add(new Data(1, "0.0.1-SNAPSHOT", "0.0.1-SNAPSHOT", fields));
      }

    }
//    System.out.println(data);

    return data;

  }

  private void toLog(String cmd, Object s2) {
    String path = UseDatabase.getPath() + "\\" + s2 + "\\" + s2;
    String condatin = Authority.getName() + " -> " + TimeUtils.nationalToDate(new Date()) + " -> " + cmd;
    LogParse.insert(path + ".log", condatin);
  }

  private List<Data> getData(String conditions) {
    List<Data> new_data;
    List<List<String>> ll = new ArrayList<>();
    List<String> l = new ArrayList<>();
    l.add(Relation.inner);
    String[] split = conditions.replace(";", "").split(Relation.e);
    for (String s : split) {
      l.addAll(Arrays.asList(s.trim().split("\\.")));
    }
    ll.add(l);
//    System.out.println(ll);
    new_data = SelectProcess.setTable(ll);
    return new_data;
  }

  private void insertTolog(String cmd, List<List<String>> n_tables) {
    Set set = new HashSet();
    n_tables.forEach(vo -> {
      set.add(vo.get(1));
      set.add(vo.get(3));
    });
    set.forEach(vo -> {
      toLog(cmd, vo);
    });
  }


  private static boolean check(String s) {
    Pattern other = Pattern.compile("([^\\s]+\\s+\\=+\\s+[^\\s]+(\\;))");
    Matcher mcd = other.matcher(s);
    return mcd.find();
  }

/**
 from
 join
 on
 where
 group by(开始使用select中的别名，后面的语句中都可以使用)
 avg,sum.... 复合函数
 having
 select
 distinct
 order by
 use user1;

 select * from user left join people on user.u_d_id = people.id;
 select * from user right join people on user.u_d_id = people.id;
 select * from user right join people on user.u_d_id = people.id , left join dept on user.id = dept.id;
 select * from user right join people on user.u_d_id = people.id , left join dept on people.id = dept.id;

 select * from people;
 select * from user , people left join person on user.id = person.id , right join dept on user.id = dept.id where user.id <> 1 and person.id = 1;
 select * from user left join person on user.id = person.id , right join dept on user.id = dept.id , innor join people on user.id = people.id where user.id <> 1;
 select * from user left join person on person.id = user.id , right join dept on dept.id = user.u_d_id , innor join people on people.id = user.id where user.id <> 1;
 select * from user right join dept on dept.id = user.u_d_id where user.id <> 1;
 select * from user right join dept on dept.id = user.id where user.id <> 1;
 select * from user right join dept on dept.id = user.id;
 select * from user right join dept on dept.id = user.u_d_id;
 select user.id from user , people;
 select * from user left join people on user.u_d_id = people.id where user.id <> 1 group by user.id;



 select user.id from user , people where user.id  =  people.id;
 select * from user , people where user.id = people.id;
 select * from user , people where user.id = people.id and user.id >= 1 ;
 select * from user , people where user.id >= 1;
 select * from user where user.id != 1;
 select * from user where user.name = 'vdvd';
 select * from user where name = 'vdvd';
 select * from user , people where user.name in ('vdvd','cscscsc');
 select user.id , people.name from user , people where user.id = people.id and user.id > 1 ;
 select user.id from user , people where user.id = people.id like %ll%;
 select user.id from user , people where user.id = people.id limit 1,10;
 select user.id from user , people limit 1,10;
 select user.id from user , people limit 1;
 select user.id from user left join people on user.id = people.id where id <> 1 limit 1 ;
 select user.id from user left join people on user.id = people.id , left join dept on dept.id = people.id where id <> 1 limit 1 ;
 select user.id from user, people left join dept on user.size = people.size where id <> 1 limit 1 ;
 select user.id from user , people left join people on user.size = people.size where id <> 1 limit 1 ;
 select count(*) from user , people left join people on user.size = people.size where id <> 1 limit 1 ;
 select count(*) from user , people left join people on user.size = people.size where id <> 1 having dd group by dd ;
 select count(*) , max(user.id) from user , people left join people on user.size = people.size where id <> 1 having dd group by dd ;
 select count(vdvdvdvfd) , max(fdvd) , sum(fdvd) , min(fdvd) , avg(fdvd) , version() from user left join people on user.size = people.size where people.name = AAA;
 select * from user left join people on user.size = people.size , right join dept on user.size = people.size where people.name = AAA and dd = dd order by dd desc;
 select count(*) , max(user.id) from user , people left join people on user.size = people.size , right join people on user.size = people.size where id <> 1 having dd group by dd order by dd desc dd - 2 > 3;
 select count(vdvdvdvfd) , max(fdvd) , sum(fdvd) , min(fdvd) , avg(fdvd) , version() from user  group by e_name having count(*) > 1;
 select version();
 select user();
 select database();
 select * from user where id = (select * from dd where id = 1);

 通过一个顺口溜总结下顺序：我(W)哥(G)是(SH)偶(O)像。按照执行顺序的关键词首字母分别是W（where）->G（Group）->S（Select）->H（Having）->O（Order），对应汉语首字母可以编成容易记忆的顺口溜：我(W)哥(G)是(SH)偶(O)像
 1.查询中用到的关键词主要包含六个，并且他们的顺序依次为 select--from--where--group by--having--order by
 其中select和from是必须的，其他关键词是可选的，这六个关键词的执行顺序 与sql语句的书写顺序并不是一样的，而是按照下面的顺序来执行
 from:需要从哪个数据表检索数据
 where:过滤表中数据的条件
 group by:如何将上面过滤出的数据分组
 having:对上面已经分组的数据进行过滤的条件
 select:查看结果集中的哪个列，或列的计算结果
 order by :按照什么样的顺序来查看返回的数据
 2.from后面的表关联，是自右向左解析 而where条件的解析顺序是自下而上的。
 <p>
 在写sql的时候，group by，order by 和limit之间的先后顺序是有约定的，一定要按照group by，order by 、limit的先后顺序书写，否则会报错！
 <p>
 use user1;
 <p>
 select * from user where id = (select * from dd where id = 1);
 select * from people;
 select * from user , people left join people on user.size = people.size , right join people on user.size = people.size , innor join people on user.size = people.size where id <> 1;
 select user.id from user , people;
 select user.id from user , people where user.id=people.id;
 select * from user , people where user.id=people.id;
 select * from user , people where user.id=people.id and user.id => 1 ;
 select user.id , people.name from user , people where user.id=people.id and user.id > 1 ;
 select user.id from user , people where user.id = people.id like %ll%;
 select user.id from user , people where user.id = people.id limit 1,10;
 select user.id from user , people limit 1,10;
 select user.id from user , people limit 1;
 select user.id from user , people left join people on user.size = people.size where id <> 1 limit 1 ;
 select count(*) from user , people left join people on user.size = people.size where id <> 1 limit 1 ;
 select count(*) from user , people left join people on user.size = people.size where id <> 1 having dd group by dd ;
 select count(*) , max(user.id) from user , people left join people on user.size = people.size where id <> 1 having dd group by dd ;
 select count(vdvdvdvfd) , max(fdvd) , sum(fdvd) , min(fdvd) , avg(fdvd) , version() from user left join people on user.size = people.size where people.name = AAA;
 select * from user left join people on user.size = people.size , right join people on user.size = people.size where people.name = AAA and dd = dd order by dd desc;
 select count(*) , max(user.id) from user , people left join people on user.size = people.size , right join people on user.size = people.size where id <> 1 having dd group by dd order by dd desc dd - 2 > 3;
 select count(vdvdvdvfd) , max(fdvd) , sum(fdvd) , min(fdvd) , avg(fdvd) , version() from user  group by e_name having count(*) > 1;
 select version();
 select user();
 select database();


 /**
 *
 通过一个顺口溜总结下顺序：我(W)哥(G)是(SH)偶(O)像。按照执行顺序的关键词首字母分别是W（where）->G（Group）->S（Select）->H（Having）->O（Order），对应汉语首字母可以编成容易记忆的顺口溜：我(W)哥(G)是(SH)偶(O)像
 1.查询中用到的关键词主要包含六个，并且他们的顺序依次为 select--from--where--group by--having--order by
 其中select和from是必须的，其他关键词是可选的，这六个关键词的执行顺序 与sql语句的书写顺序并不是一样的，而是按照下面的顺序来执行
 from:需要从哪个数据表检索数据
 where:过滤表中数据的条件
 group by:如何将上面过滤出的数据分组
 having:对上面已经分组的数据进行过滤的条件
 select:查看结果集中的哪个列，或列的计算结果
 order by :按照什么样的顺序来查看返回的数据
 2.from后面的表关联，是自右向左解析 而where条件的解析顺序是自下而上的。

 在写sql的时候，group by，order by 和limit之间的先后顺序是有约定的，一定要按照group by，order by 、limit的先后顺序书写，否则会报错！
 */


}
