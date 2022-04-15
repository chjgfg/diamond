package org.lyf.diamond.core.execute.data.select;

import org.lyf.diamond.core.entity.auxiliary.Relation;
import org.lyf.diamond.core.execute.data.DataParse;
import org.lyf.diamond.core.utile.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program:IntelliJ IDEA
 * @discription:查找工具类操作
 * @author: GG-lyf
 * @create:2022-15-22.1.8 00:15:01
 */
//@SuppressWarnings("all")
public class SelectParse {

//
//  /**
//   * 切割很多被 ,分割  的连接
//   * left join person on user.size = people.size , right join dept on user.size = people.size , innor join people on user.size = people.size  ->  [[left,  person, user, size, people, size], [right, dept, user, size, people, size], [innor, people, user, size, people, size]]
//   *
//   * @param link
//   */
//  public static List<List<String>> splitLink(String link) {
//    List<List<String>> list = new ArrayList<List<String>>();
//    if (link == null) {
//      return list;
//    }
//    if (link.contains(" , ")) {//多个连接
//      String[] split = link.split(" , ");
//      for (int i = 0; i < split.length; i++) {
//        list.add(oneLink(split[i]));
//      }
//    } else {//一个连接
//      list.add(oneLink(link));
//    }
//    return list;
//  }
//
//  /**
//   * 切割的一个连接
//   * left join tab2 on tab1.size = tab2.size  ->  [[left, tab2, tab1.size, tab2.size]]
//   *
//   * @param link
//   * @return
//   */
//  private static List<String> oneLink(String link) {
//    List<String> splitLink = new ArrayList<String>();
//    String[] split = link.trim().split(Relation.on_);
//    String join = split[0];
//    String conditions = split[1];
//    if (split[0].startsWith(Relation.left_join)) {
//      join = join.replace(Relation.left_join_, "");
//      splitLink.add("left");
//    } else if (split[0].startsWith(Relation.right_join)) {
//      join = join.replace(Relation.right_join_, "");
//      splitLink.add("right");
//    } else if (split[0].startsWith(Relation.inner_join)) {
//      join = join.replace(Relation.inner_join_, "");
//      splitLink.add("innor");
//    }
//    //都不匹配要报错
//    splitLink.add(join);
//    String[] split1 = conditions.split(" = ");
//    String s = split1[0];
//    String s1 = split1[1];
//    if (s.contains(".")) {
//      splitLink.addAll(Arrays.asList(s.split("\\.")));
//    } else {
//      splitLink.add(s);
//    }
//    if (s1.contains(".")) {
//      splitLink.addAll(Arrays.asList(s1.split("\\.")));
//    } else {
//      splitLink.add(s1);
//    }
//    return splitLink;
//  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * 排序,将orde by的后面排序的东西拿出来,并且把是正序还是逆序也拿出来
   * group by dddd having dddd order by ddd desc  ->  [order by, ddd, desc]
   *
   * @param condition
   * @return
   */
  public static List<String> order(String condition) {
    List<String> order_by = new ArrayList<String>();
    if (condition != null) {
      order_by.add(Relation.order_by);
      String[] order_bies = condition.substring(condition.indexOf("order by"), condition.length()).split(Relation.order_by);
      String[] s1 = order_bies[1].trim().split(" ");
      order_by.addAll(Arrays.asList(s1));
    } else {
      order_by.add("");
    }
    return order_by;
  }

  /**
   * 分组,将分组的数据切割,并且组装
   * group by dddd having dddd  ->  [group by, dddd, having, dddd]
   *
   * @param condition
   * @return
   */
  public static List<String> group(String condition) {
    List<String> group_by = new ArrayList<String>();
    if (condition != null) {
      //判断是否有having
      int have = condition.indexOf(Relation.having);
      group_by.add(Relation.group_by);
      //没有having
      if (have == -1) {
        String[] split = condition.split(Relation.group_by);
        if (split.length != 0) {
          group_by.add(split[1].trim());
        } else {
          group_by.add(null);
        }
      } else {
        String[] split = condition.split(Relation.having);
        group_by.add(split[0].replace(Relation.group_by, "").trim());
        group_by.add(Relation.having);
        group_by.addAll(Arrays.asList(split[1].trim().split(" ")));
      }
    } else {
      group_by.add("");
    }
    return group_by;
  }

  /**
   * 限制,将限制条件拼接组装
   * limit 1,1  ->  [limit, 1,1]
   *
   * @param condition
   * @return
   */
  public static List<String> limit(String condition) {
    List<String> limit = new ArrayList<String>();
    if (condition != null) {
      limit.add(Relation.limit);
      String[] split = condition.split(Relation.limit);
      limit.add(split[1].trim());
    } else {
      limit.add("");
    }
    return limit;
  }

  /**
   * 其他,这时的条件只有1个
   *
   * @param condition
   * @return
   */
  public static List<String> other(String condition) {
    List<String> others = new ArrayList<String>();
    if (condition != null) {
      String[] split = condition.split(" ");
      others.addAll(Arrays.asList(split));
    } else {
      others.add("");
    }
    return others;
  }

  /**
   * 封装in的数据,在修改和删除那里的不能用,只能自己再封装了
   *
   * @param condition
   * @return
   */
  public static List<List<String>> in(String condition) {
    int right = condition.indexOf(")");
    String substring = condition.substring(0, right);
    List<List<String>> in_and_others = new ArrayList<List<String>>();
    List<String> others = new ArrayList<String>();
    String[] s = substring.split(" ");
    String[] split = s[2].replace("(", "").split(",");
    for (String s1 : split) {
      List<String> in = new ArrayList<String>();
      in.add(s[0]);
      in.add(Relation.e);
      in.add(s1);
      in_and_others.add(in);
    }
    List<List<String>> lists = orderAndGroupAndLimitAndLike(condition.substring(right, condition.length()).replace(")", "").trim());
    in_and_others.addAll(lists);
    return in_and_others;
  }

  /**
   * 把where后面的条件切割一下封装起来
   * group by dddd having dddd order by ddd desc  ->  [, group by dddd having dddd, null, order by ddd desc]
   * emp1 = 1 group by e_name having count(*) > 1 order by dddd desc limit 1,1  ->  [emp1 = 1, group by e_name having count(*) > 1, order by dddd desc, limit 1,1]
   *
   * @param nn
   * @param g
   * @param o
   * @param l
   * @return
   */
  private static List<String> findCondition(String nn, int g, int o, int l) {
    nn = nn.replace(";", "");
    List<String> conditions = new ArrayList<String>();
    String other_to_group = null, group_to_order = null, order_to_limit = null, limit_to_end = null;
    if (g != -1) {
      if (o != -1) {
        if (l != -1) {
          //有group by order by limit
          other_to_group = nn.substring(0, g).trim();
          group_to_order = nn.substring(g, o).trim();
          order_to_limit = nn.substring(o, l).trim();
          limit_to_end = nn.substring(l, nn.length()).trim();
        } else {
          //有group by order by
          other_to_group = nn.substring(0, g).trim();
          group_to_order = nn.substring(g, o).trim();
          order_to_limit = nn.substring(o, nn.length()).trim();
        }
      } else {
        //有group by limit
        if (l != -1) {
          other_to_group = nn.substring(0, g).trim();
          group_to_order = nn.substring(g, l).trim();
          limit_to_end = nn.substring(l, nn.length()).trim();
        } else {//有group by
          other_to_group = nn.substring(0, g).trim();
          group_to_order = nn.substring(g, nn.length()).trim();
        }
      }
    } else {
      if (o != -1) {
        //有order by limit
        if (l != -1) {
          other_to_group = nn.substring(0, o).trim();
          order_to_limit = nn.substring(o, l).trim();
          limit_to_end = nn.substring(l, nn.length()).trim();
        } else {//有order by
          other_to_group = nn.substring(0, o).trim();
          order_to_limit = nn.substring(o, nn.length()).trim();
        }
      } else {
        //有limit
        if (l != -1) {
          other_to_group = nn.substring(0, l).trim();
          limit_to_end = nn.substring(l, nn.length()).trim();
        } else {
          //无
          other_to_group = nn;
        }
      }
    }
    conditions.add(other_to_group);
    conditions.add(group_to_order);
    conditions.add(order_to_limit);
    conditions.add(limit_to_end);
    return conditions;
  }

  /**
   * 封装所有的数据
   *
   * @param nn
   * @return
   */
  public static List<List<String>> orderAndGroupAndLimitAndLike(String nn) {
    int g = nn.indexOf(Relation.group_by);
    int o = nn.indexOf(Relation.order_by);
    int l = nn.indexOf(Relation.limit);
    List<List<String>> limit = new ArrayList<List<String>>();
    List<String> condition = findCondition(nn, g, o, l);
    //other
    limit.add(other(condition.get(0)));
    //group
    limit.add(group(condition.get(1)));
    //order
    limit.add(order(condition.get(2)));
    //limit
    limit.add(limit(condition.get(3)));
    limit.removeIf(vo -> vo.size() == 1);
    return limit;
  }

  public static List<List<String>> splitOther(List<List<String>> lists) {
    List<List<String>> ll = new ArrayList<List<String>>();
    lists.forEach(vo -> {
      vo.forEach(vv -> {
        if (vv.contains(Relation.group_by) || vv.contains(Relation.order_by) || vv.contains(Relation.limit)) {
          ll.addAll(orderAndGroupAndLimitAndLike(vv));
        } else {
          ll.add(Stream.of(vv.split(" ")).collect(Collectors.toList()));
        }
      });
    });
    return ll;
  }


  public static List<List<String>> andAndOrAndin(String keys) {
    List<List<String>> list = new ArrayList<>();
    String[] and = keys.split(Relation.and_);
    String[] or = keys.split(Relation.or_);
    List<String> inList = new ArrayList<>();
    if (keys.contains(Relation.in)) {
      if (keys.contains(Relation.and)) {
        if (keys.contains(Relation.or)) {
          if (and.length >= or.length) {
            list = DataParse.and(keys);
          } else {
            list = DataParse.or(keys);
          }
        } else {
          list = DataParse.and(keys);
        }
      } else {
        if (keys.contains(Relation.or)) {
          list = DataParse.or(keys);
        } else {
          //解析in
          inList = DataParse.in(keys);
        }
      }
    } else {
      if (keys.contains(Relation.and)) {
        if (keys.contains(Relation.or)) {
          if (and.length >= or.length) {
            list = DataParse.and(keys);
          } else {
            list = DataParse.or(keys);
          }
        } else {
          list = DataParse.and(keys);
        }
      } else {
        if (keys.contains(Relation.or)) {
          list = DataParse.or(keys);
        } else {
          ArrayList<String> nomal = new ArrayList<>();
          nomal.add(keys);
          list.add(nomal);
          return list;
        }
      }
    }
    Iterator<List<String>> iterator = list.iterator();
    while (iterator.hasNext()) {
      for (String s : iterator.next()) {
        if (s.contains(Relation.in)) {
          inList.addAll(DataParse.in(s));
        }
      }
    }
    //封装所有判断后的条件
    list.addAll(Collections.singleton(inList));
    list.removeIf(vo -> vo.size() == 0);
    return list;
  }


  /**
   * 封装所有条件数据的入口
   * id <> 1 or e_id in (1,2,3,4,5,6) order by dd desc  ->  [[id, <>, 1], [e_id, =, 1], [e_id, =, 2], [e_id, =, 3], [e_id, =, 4], [e_id, =, 5], [e_id, =, 6], [order by, dd, desc]]
   * id <> 1 or e_id in (1,2,3,4,5,6) group by dd having dd  order by dd desc  ->  [[id, <>, 1], [e_id, =, 1], [e_id, =, 2], [e_id, =, 3], [e_id, =, 4], [e_id, =, 5]]
   *
   * @param condition
   */
  public static List<List<String>> splitCondition(String condition) {
    List<List<String>> lists = new ArrayList<List<String>>();
    if (condition == null) {
      return lists;
    }
    boolean and = condition.contains(Relation.and);
    boolean or = condition.contains(Relation.or_);
    boolean in = condition.contains(Relation.in_);
    //有and或or或and和or都有
    if (and || or || (and && or)) {
      if (in) {
        lists = andAndOrAndin(condition);
        lists = splitOther(lists);
      } else {
        int al = condition.split(Relation.and_).length;
        int ol = condition.split(Relation.or_).length;
        if (ol >= al) {
          lists = DataParse.or(condition);
        } else {
          lists = DataParse.and(condition);
        }
        lists = splitOther(lists);
      }
    } else {
      if (in) {
        lists = in(condition);
      } else {
        lists = orderAndGroupAndLimitAndLike(condition);
      }
    }
    LinkedHashSet<List<String>> split = new LinkedHashSet<List<String>>();
    split.addAll(lists);
    Iterator<List<String>> iterator = split.iterator();
    List<List<String>> n_lists = new ArrayList<List<String>>();
    while (iterator.hasNext()) {
      List<String> next = iterator.next();
      next.removeIf(oo -> oo.isEmpty());

      if (next.get(0).contains(".")) {
        String[] split1 = next.get(0).split("\\.");
        next.addAll(Arrays.asList(split1));
        next.remove(next.get(0));
        String mm = "";
        //逆序 [<>, 1, user, id]  ->  [user, id, <>, 1]
        for (int i = 0; i < next.size() / 2; i++) {
          mm = next.get(i);
          next.set(i, next.get(i + 2));
          next.set(i + 2, mm);
        }
      }
      String k = next.get(next.size() - 1);
      //判断是否为数字字符串,不是才让他切割 user.id  =  people.id  ->  [user, id, =, people, id]
      if (k.contains(".")) {
        if (!NumberUtils.match(k)) {
          String[] split1 = k.split("\\.");
          for (String s : split1) {
            if (s.contains("'")) {
              s = s.replace("'", "");
            }
            next.add(s);
          }
          next.remove(next.get(next.size() - 3));
        }
      } else {
        if (k.contains("'")) {
          k = k.replace("'", "");
        }
        next.add(k);
        next.remove(next.get(next.size() - 2));
      }
      if (next.get(1).equals(Relation.in)) {
        iterator.remove();
        continue;
      }
      n_lists.addAll(Collections.singleton(next));
    }
    return n_lists;
  }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//  /**
//   * 切割查找的目的并组装
//   *
//   * @param condition
//   * @return
//   */
//  public static List<String> splitPurpose(String condition) {
//    List<String> lists = new ArrayList<String>();
//    if (condition.contains("*") && !condition.contains("(")) {
//      lists.add(condition);
//    } else if (condition.contains("(") && condition.contains(")")) {
//      String[] split = condition.split("\\(");
//      String replace = split[1].replace(")", "");
//      lists.add(split[0]);
//      if (replace.contains(".")) {
//        String[] split1 = replace.split("\\.");
//        lists.addAll(Arrays.asList(split1));
//      } else {
//        lists.add(replace);
//      }
//    } else if (condition.contains(".")) {
//      String[] split1 = condition.split("\\.");
//      lists.addAll(Arrays.asList(split1));
//    }
//    return lists;
//  }

//  /**
//   * 解析找到的条件想要的答案,封装成数组
//   * table4.id , count(table4.id)  ->  [[table4, id], [count, table4, id]]
//   * count(*)  ->  [[count, *]]
//   *
//   * @param condition
//   * @return
//   */
//  public static List<List<String>> purpose(String condition) {
//    List<List<String>> lists = new ArrayList<List<String>>();
//    if (condition.contains(" , ")) {
//      String[] split = condition.split(" , ");
//      for (String s : split) {
////        System.out.println(s);
//        lists.add(splitPurpose(s));
//      }
//    } else {
//      lists.add(splitPurpose(condition));
//    }
//    return lists;
//  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//  /**
//   * 切割表名并组装
//   *
//   * @param tables
//   * @return
//   */
//  public static List<String> splitTables(String tables) {
//    List<String> tableList = new ArrayList<String>();
//    if (tables.contains(",")) {
//      String[] split = tables.split(",");
//      for (String s : split) {
//        tableList.add(s.trim());
//      }
//    } else {
//      tableList.add(tables.trim());
//    }
//    return tableList;
//  }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static void main(String[] args) {
    String path = "D:\\idea\\idea-workspace\\diamon\\core\\src\\main\\resources\\db\\data\\user1\\user\\user";
    String link1 = "left join tab2 on tab1.size = tab2.size";
    String link2 = "left join tab2 on tab1.size = tab2.size , right join tab2 on tab1.size = tab2.size , innor join tab2 on tab1.size = tab2.size";
//    splitLink(link1);
//    splitLink(link2);


    String str1 = "thinking in java";
    String str2 = "java";
    String c = "e_id = 5 or e_id = 1 order by e_id desc";
    String bb = "e_id like 5 or e_id like 1 order by e_id desc";
    String dd = "emp1 group by e_name having count(*) > 1";
    String e = "id <> 1 and e_id in (1,2,3,4,5,6)";
    String a = "e_id > 7 and e_id in (1,2,3,4,5,6) or e_id = 10 order by e_name desc";
    String s = "e_id = 7 or e_id = 1 and e_id = 1 or e_id = 1  and e_id = 1 or e_id = 10 order by e_name desc";
    String ww = "id = (select * from dd where id = 1) limit 1,1";
    String rr = "limit 1,1 and id = (select * from dd where id = 1)";
    String yy = "e_id = 5 or e_id = 1 order by e_id desc limit 1,1";
    String hh = "emp1 group by e_name having count(*) > 1 limit 1,1";
    String oo = "user.id <> 1 group by dd having dd ";
    String aaa = "user.id  =  people.id";
//    System.out.println(instr(str1, str2));
//    System.out.println(pad("sss", 5, "123456", "r"));
//    String 
    splitCondition(aaa);
//    System.out.println(DataParse.andAndOrAndin(hh));
//    System.out.println(DataParse.or(dd));
//    splitCondition(bb);

    String bd = "";

    String b = "group by";
    String vf = "group by dddd";
    String ds = "group by dddd having dddd";
    String aa = "group by dddd having dddd order by ddd desc";
    String yn = "group by dddd having dddd order by ddd desc limit 1,1";
    String vdf = "group by dddd having dddd limit 1,1";
    String aw = "group by dddd having dddd limit 1";

    String v = "order by ddd desc";
    String n = "order by ddd desc limit 1,1";

    String k = "limit 1,1";

    String nn = "emp1 = 1";
    String bg = "emp1 like %1% group by e_name having count(*) > 1";
    String op = "emp1 like %1% group by e_name having count(*) > 1 order by dddd desc limit 1,1";
    String jh = "emp1 like %1% group by e_name having count(*) > 1 order by dddd desc";
    String ddp = "id <> 1 or e_id in (1,2,3,4,5,6) group by dd having dd  order by dd desc";
//    System.out.println(order(s));

    String ss = "e_id in (1,2,3,4,5,6) order by e_name desc";
    String sd = "order by e_name desc e_id in (1,2,3,4,5,6) ";//写法错误


    String ll = "e_id in (1,2,3,4,5,6) group by e_name";
//    splitCondition(a);


    String bf = "*";
    String nf = "table4.id";
    String hj = "table4.id , table5.name";
    String rg = "count(*)";
    String we = "table4.id , count(*)";
    String lo = "table4.id , count(table4.id)";

//    purpose(bf);
//    purpose(nf);
//    purpose(hj);
//    purpose(rg);
//    purpose(we);
//    purpose(lo);

  }
}
