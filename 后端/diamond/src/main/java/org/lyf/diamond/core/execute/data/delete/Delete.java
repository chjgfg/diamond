package org.lyf.diamond.core.execute.data.delete;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.execute.log.LogParse;
import org.lyf.diamond.core.file.data.DataFile;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.Date;
import java.util.regex.Matcher;


/**
 * @program:IntelliJ IDEA
 * @discription:从数据库删除数据
 * @author: GG-lyf
 * @create:2022-17-22.1.3 17:17:47
 */
@SuppressWarnings("all")
public class Delete {

  public String delete(Matcher md, String cmd) {
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      return Return.please_point_database;
    }

    String table_name = md.group(1);//表名

    String p = cmd.split(" ")[0];
    String[] split = UseDatabase.getPath().split("\\\\");
    String cheak = UserParse.cheak(Authority.getName(), split[split.length - 1], table_name, p);
    if (!cheak.equals(p)) {
      return cheak;
    }

    String where = md.group(3);//where
    String conditions = md.group(4);//条件

    boolean table = DataFile.isTable(table_name);
    if (table) {
      String path = UseDatabase.getPath() + "\\" + table_name + "\\" + table_name;
      String condatin = Authority.getName() + " -> " + TimeUtils.nationalToDate(new Date()) + " -> " + cmd;
      LogParse.insert(path + ".log", condatin);
      if (where != null) {
//        System.out.println(conditions);
        return DeleteParse.process(path, conditions);
      } else {
        return DeleteParse.deleteAll(path + ".data");
      }
    } else {
//      System.out.println("table is not exist");
      return Return.table_is_not_exist;
    }
  }

  /**
   * use user1;
   * <p>
   delete from user where time = 2021-08-10 10:51:14;
   delete from user where time = 2021-08-10 00:51:14;
   delete from user;
   delete from user where id = 1;
   delete from user where id <= 5;
   delete from user where id >= 10;
   delete from user where id > 1;
   delete from user where id < 1;
   delete from user where name = 'sdsdvvskl';
   delete from user where name = 'ssssssssssssssssssssssssss';
   delete from user where money = 22.5;
   delete from peoper where sex = 22.5;
   delete from user where money = 23.2;
   delete from user where now = 2021-08-10 00:51:14;
   delete from user where time in (2021-08-10 00:51:14,2021-08-10 10:51:12);
   delete from user where id in (1,2,3,4,5,6,7,8,9,10);
   delete from user where id in (1,2);
   delete from user where money in (10.2,11.2,12.2);
   delete from user where salary in (22.5,23.2);
   delete from user where name in ('张三','李四','李四1');
   delete from user where name in ('张三','李四','钱卫');
   delete from user where name in ('张三','李四','钱卫','孙荣','王五','韩阳','赵六');
   delete from user where name in ('张三','李四','王五','韩阳','赵六');

   delete from user where sex <= 5 and sex <= 2 or sex = 11;
   delete from user where id in (1,2,3,4,5,5,5555) and sex <= 22.2 or sex = 11;
   delete from user where name = '222222' and sex <= 22.2 or ss = 11 or name = '2121212';
   delete from user name in ('vsds','cdvs','ssc') and sex <= 22.2 or ss = 11;
   delete from user id in (10,2,11,3) and sex <= 22.2 or sex = 11;
   delete from user where time in (2021-08-10 00:51:14,2021-08-10 10:51:14) and sex <= 22.2 or sex = 11;
   delete from user where time = 2021-08-10 00:51:14 and sex <= 22.2 or sex = 11;
   delete from user where time in (2021-08-10 00:51:14,2021-08-10 10:51:14);
   delete from user where id >= 1;
   delete from user where sex != 5;
   delete from user where id > 1;
   use user2;
   delete from iiii where id > 1;




   delete\s+from\s+(\w+)(\s(where)\s+((\s+(and|or)\s+)*((\w+)\s?((([<=>]|(\>\=)|(\<\=))\s?(([^\s]+)|(\s+([1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\s+(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d))))?|(\s?in\s?\(([^\)]+,?)\))?))*))?\s?;
   */


//  public boolean delete_join(Matcher mdj) {
//    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
//      return false;
//    }
//
//    return false;
//  }


  /**
   *
   * use user1;
   *
   * delete table4 , table5 from table4 , table5;
   * delete table4 , table5 from table4 , table5 where table4.id = table5.id;
   * delete table4 , table5 from table4 , table5 where table4.id = table5.id and table4.id > 1 ;
   * delete table4 , table5 from table4 , table5 where table4.id = table5.id like %ll%;
   * delete table4 , table5 from table4 , table5 where table4.id = table5.id limit 1,10;
   * delete table4 , table5 from table4 ,table5 where id <> 1 limit 1 ;
   * delete table4 , table5 from table4 , table5 where id <> 1 limit 1 ;
   * delete table4 , table5 from table4 , table5 where id <> 1 having dd group by dd ;
   * delete table4 , table5 from table4 , table5 where id <> 1 having dd group by dd ;
   * delete table4 , table5 from table4 left join tab2 on tab1.size = tab2.size , right join tab2 on tab1.size = tab2.size where id <> 1 having dd group by dd;
   * delete table4 , table5 from table4 left join tab2 on tab1.size = tab2.size;
   *
   */
}
