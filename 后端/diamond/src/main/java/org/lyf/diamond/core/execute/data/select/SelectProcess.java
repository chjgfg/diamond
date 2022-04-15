package org.lyf.diamond.core.execute.data.select;

import org.lyf.diamond.core.entity.auxiliary.Dictionary;
import org.lyf.diamond.core.entity.auxiliary.Relation;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.execute.data.DataParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.utile.CharsetUtils;
import org.lyf.diamond.core.utile.CloneUtils;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.*;

/**
 * @program:IntelliJ IDEA
 * @discription:查找数据处理类
 * @author: GG-lyf
 * @create:2022-16-22.1.18 15:16:53
 */
//@SuppressWarnings("all")
public class SelectProcess {

  /**
   * 找出一个表中的数据,之后组装成一个集合
   *
   * @param t
   * @return
   */
  public static List<Data> findData(String t) {
    t = t.trim();
    String path = UseDatabase.getPath() + "\\" + t + "\\" + t;
    List<Data> data = DataParse.getData(path);
    if (data == null) {
      return null;
    }
    return data;
  }


//  /**
//   * [[left, person, user, id, person, id], [right, dept, user, id, dept, id], [innor, people, user, id, people, id]]  ->  [[[left], [user, id], [person, id]], [[right], [user, id], [dept, id]], [[innor], [user, id], [people, id]]]
//   *
//   * @param n_tables
//   * @return
//   */
//  private static List<List<List<String>>> getLists(List<List<String>> n_tables) {
//    List<List<List<String>>> list = new ArrayList<>();
//    for (int i = 0; i < n_tables.size(); i++) {
//      List<String> qq = new ArrayList<>();
//      qq.add(n_tables.get(i).get(0));
//      List<String> ww = new ArrayList<>();
//      ww.add(n_tables.get(i).get(2));
//      ww.add(n_tables.get(i).get(3));
//      ww.add("");
//      List<String> ee = new ArrayList<>();
//      ee.add(n_tables.get(i).get(4));
//      ee.add(n_tables.get(i).get(5));
//      ee.add("");
//      List<List<String>> rr = new ArrayList<>();
//      rr.add(qq);
//      rr.add(ww);
//      rr.add(ee);
//      list.add(rr);
//    }
//    return list;
//  }

  /**
   * 切割表名并组装
   *
   * @param tables
   * @return
   */
  public static List<String> splitTables(String tables) {
    List<String> tableList = new ArrayList<String>();
    if (tables.contains(" , ")) {
      String[] split = tables.split(" , ");
      for (String s : split) {
        tableList.add(s.trim());
      }
    } else {
      tableList.add(tables.trim());
    }
    return tableList;
  }


  /**
   * 切割查找的目的并组装
   *
   * @param condition
   * @return
   */
  public static List<String> splitPurpose(String condition) {
    List<String> lists = new ArrayList<String>();
    if (condition.contains("*") && !condition.contains("(")) {
      lists.add(condition);
    } else if (condition.contains("(") && condition.contains(")")) {
      String[] split = condition.split("\\(");
      String replace = split[1].replace(")", "");
      lists.add(split[0]);
      if (replace.contains(".")) {
        String[] split1 = replace.split("\\.");
        lists.addAll(Arrays.asList(split1));
      } else {
        lists.add(replace);
      }
    } else if (condition.contains(".")) {
      String[] split1 = condition.split("\\.");
      lists.addAll(Arrays.asList(split1));
    }
    return lists;
  }

  /**
   * 解析找到的条件想要的答案,封装成数组
   * table4.id , count(table4.id)  ->  [[table4, id], [count, table4, id]]
   * count(*)  ->  [[count, *]]
   *
   * @param condition
   * @return
   */
  public static List<List<String>> purpose(String condition) {
    List<List<String>> lists = new ArrayList<List<String>>();
    if (condition.contains(" , ")) {
      String[] split = condition.split(" , ");
      for (String s : split) {
        lists.add(splitPurpose(s));
      }
    } else {
      lists.add(splitPurpose(condition));
    }
    return lists;
  }


  /**
   * 切割的一个连接
   * left join tab2 on tab1.size = tab2.size  ->  [[left, tab2, tab1.size, tab2.size]]
   *
   * @param link
   * @return
   */
  private static List<String> oneLink(String link) {
    List<String> splitLink = new ArrayList<String>();
    String[] split = link.trim().split(Relation.on_);
    String join = split[0];
    String conditions = split[1];
    if (split[0].startsWith(Relation.left_join)) {
      join = join.replace(Relation.left_join_, "");
      splitLink.add("left");
    } else if (split[0].startsWith(Relation.right_join)) {
      join = join.replace(Relation.right_join_, "");
      splitLink.add("right");
    } else if (split[0].startsWith(Relation.inner_join)) {
      join = join.replace(Relation.inner_join_, "");
      splitLink.add("inner");
    }
    //都不匹配要报错
    splitLink.add(join);
    String[] split1 = conditions.split(" = ");
    String s = split1[0];
    String s1 = split1[1];
    if (s.contains(".")) {
      splitLink.addAll(Arrays.asList(s.split("\\.")));
    } else {
      splitLink.add(s);
    }
    if (s1.contains(".")) {
      splitLink.addAll(Arrays.asList(s1.split("\\.")));
    } else {
      splitLink.add(s1);
    }
    return splitLink;
  }

  /**
   * [[left, uu, uu, bdfbd, dw, id], [right, uuk, uuk, id, uu, vsdvsd], [inner, uuk, uuk, dvsvdsv, uu, id]]
   * ||
   * [[left, uu, bdfbd, dw, id], [right, uuk, id, uu, vsdvsd], [inner, uuk, dvsvdsv, uu, id]]
   *
   * @param n_tables
   * @return
   */
  public static List<List<String>> setLink(List<List<String>> n_tables) {
    n_tables.forEach(vo -> {
      String a = "", b = "", c = "", d = "";
      if (vo.get(0).equals(Relation.left)) {
        if (vo.get(1).equals(vo.get(2))) {
          a = vo.get(2);
          b = vo.get(3);
          c = vo.get(4);
          d = vo.get(5);
          vo.set(2, c);
          vo.set(3, d);
          vo.set(4, a);
          vo.set(5, b);
        }
      } else if (vo.get(0).equals(Relation.right)) {
        if (vo.get(1).equals(vo.get(4))) {
          a = vo.get(4);
          b = vo.get(5);
          c = vo.get(2);
          d = vo.get(3);
          vo.set(2, a);
          vo.set(3, b);
          vo.set(4, c);
          vo.set(5, d);
        }
      }
    });
    n_tables.forEach(vo -> {
      vo.remove(1);
    });
    return n_tables;
  }

  /**
   * 切割很多被 ,分割  的连接
   * left join person on user.size = people.size , right join dept on user.size = people.size , innor join people on user.size = people.size  ->  [[left,  person, user, size, people, size], [right, dept, user, size, people, size], [innor, people, user, size, people, size]]
   *
   * @param link
   */
  public static List<List<String>> splitLink(String link) {
    List<List<String>> list = new ArrayList<List<String>>();
    if (link == null) {
      return list;
    }
    if (link.contains(" , ")) {
      //多个连接
      String[] split = link.split(" , ");
      for (int i = 0; i < split.length; i++) {
        list.add(oneLink(split[i]));
      }
    } else {//一个连接
      list.add(oneLink(link));
    }
    return setLink(list);
  }

  /**
   * 找符合条件的数据的下标
   *
   * @param list
   * @param data1
   * @return
   */
  private static int findIndex(List<String> list, List<Data> data1, int a, int b) {
    List<Field> fields = data1.get(0).getFields();
    for (int i = 0; i < fields.size(); i++) {
      if (fields.get(i).getConcern().equals(list.get(a)) && fields.get(i).getName().equals(list.get(b))) {
        return i;
      }
    }
    return 0;
  }

  /**
   * 找inner的那种
   *
   * @param list
   * @param data1
   * @return
   */
  private static int findInnerIndex(List<String> list, List<Data> data1) {
    List<Field> fields = data1.get(0).getFields();
    for (int i = 0; i < fields.size(); i++) {
      if (fields.get(i).getConcern().equals(list.get(1))) {
        if (fields.get(i).getName().equals(list.get(2))) {
          return i;
        }
      } else if (fields.get(i).getConcern().equals(list.get(3))) {
        if (fields.get(i).getName().equals(list.get(4))) {
          return i;
        }
      }
    }
    return 0;
  }


  /**
   * 把符合条件的数据的key标成1
   * 不用笛卡尔积
   *
   * @param n_tables
   */
  public static List<Data> setTable(List<List<String>> n_tables) {
    //把查出来的数据全部都翻到一个集合中去
    Map<String, List<Data>> tabless = new HashMap();
    //放方向
    List<List<String>> trundation = new ArrayList<>();
    //交验和找
    for (List<String> n_table : n_tables) {
      if (n_table.get(1).equals(n_table.get(3))) {
        //左表和右表名字一样
        System.out.println(Return.not_unique_table_or_alias);
        return new ArrayList<>();
      }
      if (!DataParse.checkField(n_table.get(1), n_table.get(2))) {
        //找第一个表中的字段未找到
        System.out.println(Return.field_is_not_exist);
        return new ArrayList<>();
      }
      if (!DataParse.checkField(n_table.get(3), n_table.get(4))) {
        //找第二个表中的字段未找到
        System.out.println(Return.field_is_not_exist);
        return new ArrayList<>();
      }
      //把数据封装起来
      List<Data> data1 = findData(n_table.get(1));
      data1.forEach(vo -> {
        vo.setName(n_table.get(1));
        vo.setValue(n_table.get(2));
        vo.getFields().forEach(pp -> {
          pp.setConcern(n_table.get(1));
          pp.setSymbol(n_table.get(2));
        });
      });
      tabless.put(n_table.get(1), data1);
      List<Data> data2 = findData(n_table.get(3));
      data2.forEach(vo -> {
        vo.setName(n_table.get(3));
        vo.setValue(n_table.get(4));
        vo.getFields().forEach(pp -> {
          pp.setConcern(n_table.get(3));
          pp.setSymbol(n_table.get(4));
        });
      });
      tabless.put(n_table.get(3), data2);
      //把表名和条件封装起来
      trundation.add(Arrays.asList(n_table.get(1), n_table.get(2)));
      trundation.add(Arrays.asList(n_table.get(3), n_table.get(4)));
    }
    //去重,有的表示重复的,因此可以去重
    List<List<Data>> tables = new ArrayList<>();
    for (String s : tabless.keySet()) {
      tables.add(tabless.get(s));
    }
    return processTables(tables, n_tables);
  }


  /**
   * 循环遍历条件,找个空的集合来装所有以后的数据
   * 遍历集合中的m每一个条件,使用这个条件找到这个表,不管坐标还是右表
   * 在这个表中找到需要匹配的字段的下标
   * 在两个表中是由国内下标找到这两个字段,之后开始对比,对比成功之后就把这个右边表(注意不是右表)的字段添加进左边表中的字段列表中
   */
  private static List<Data> processTables(List<List<Data>> tables, List<List<String>> trundation) {
    //装新数据
    List<Data> d = null;
    for (int i = 0; i < trundation.size(); i++) {
      //得到每一个条件
      List<String> list = trundation.get(i);
//      System.out.println("\r\n");
//      System.out.println(list);
      if (d == null) {
        for (int j = 0; j < tables.size(); j++) {
          if (tables.get(j).get(0).getName().equals(list.get(1))) {
            d = tables.get(j);
            break;
          }
        }
      }
      //拿到d中所有的表名
      Set<String> ss = new HashSet();
      d.get(0).getFields().forEach(vo -> {
        ss.add(vo.getConcern());
      });
      //第二个拿到数据
      List<Data> data2 = null;
      for (int j = 0; j < tables.size(); j++) {
        String m = "";
        for (String s : ss) {
          if (s.equals(list.get(1))) {
            m = list.get(3);
          } else {
            m = list.get(1);
          }
          break;
        }
        if (tables.get(j).get(0).getName().equals(m)) {
          data2 = tables.get(j);
          break;
        }
      }
      //找到这个表中的哪个字段
      int index1 = findIndex(list, d, 1, 2);
      int index2 = findIndex(list, data2, 3, 4);
      if (i == 0) {
        //判断是否为inner
        if (list.get(0).equals(Relation.inner)) {
//          System.out.println(index1 + " " + index2);
          for (Data data : d) {
            for (Data data3 : data2) {
              if (data.getFields().get(index1).getValue().equals(data3.getFields().get(index2).getValue())) {
                data.getFields().addAll(data3.getFields());
                data.setKey(1);
              }
            }
          }
          d.removeIf(v -> v.getKey() == 0);
          d.forEach(vo -> vo.setKey(0));
//        } else if (list.get(0).equals(Relation.left)) {
        } else {
          for (Data data : d) {
            for (Data data3 : data2) {
              if (data.getFields().get(index1).getValue().equals(data3.getFields().get(index2).getValue())) {
                data.getFields().addAll(data3.getFields());
                data.setKey(0);
                break;
              } else {
                data.setKey(1);
              }
            }
          }
          List<Field> fields = CloneUtils.deepCloneField(data2.get(0));
          d.forEach(vo -> {
            if (vo.getKey() == 1) {
              vo.getFields().addAll(fields);
            }
            vo.setKey(0);
          });
        }
      } else {
        //不是第一次就判断是否为往d里面添加还是在data2里面添加
        //left在d,里面添加
        if (list.get(0).equals(Relation.left)) {
//          int index1 = findIndex(list, d, 1, 2);
//          int index2 = findIndex(list, data2, 3, 4);
          for (Data data : d) {
            for (Data data3 : data2) {
              if (data.getFields().get(index1).getValue().equals(data3.getFields().get(index2).getValue())) {
                data.getFields().addAll(data3.getFields());
//              } else {
//                data.getFields().addAll(CloneUtils.deepCloneField(data2.get(0)));
//              }
                data.setKey(0);
                break;
              } else {
                data.setKey(1);
              }
            }
          }
          List<Field> fields = CloneUtils.deepCloneField(data2.get(0));
          d.forEach(vo -> {
            if (vo.getKey() == 1) {
              vo.getFields().addAll(fields);
            }
            vo.setKey(0);
          });
        } else if (list.get(0).equals(Relation.right)) {
          //right在data2里面添加,因为每次都得循环d,故还得把d清空,把data2的数据放进d中
          index1 = findIndex(list, d, 3, 4);
          index2 = findIndex(list, data2, 1, 2);
          for (Data data3 : data2) {
            for (Data data : d) {
              if (data.getFields().get(index1).getValue().equals(data3.getFields().get(index2).getValue())) {
                data3.getFields().addAll(data.getFields());
//              } else {
//                data3.getFields().addAll(CloneUtils.deepCloneField(d.get(0)));
//              }
                data3.setKey(0);
                break;
              } else {
                data3.setKey(1);
              }
            }
          }
          List<Field> fields = CloneUtils.deepCloneField(d.get(0));
          data2.forEach(vo -> {
            if (vo.getKey() == 1) {
              vo.getFields().addAll(fields);
            }
            vo.setKey(0);
          });
          d.clear();
          d.addAll(data2);
        } else if (list.get(0).equals(Relation.inner)) {
          index1 = findInnerIndex(list, d);
          index2 = findInnerIndex(list, data2);
          for (Data data : d) {
            for (Data data3 : data2) {
              if (data.getFields().get(index1).getValue().equals(data3.getFields().get(index2).getValue())) {
                data.getFields().addAll(data3.getFields());
                data.setKey(1);
                break;
              }
            }
          }
          d.removeIf(v -> v.getKey() == 0);
          d.forEach(vo -> vo.setKey(0));
        }
      }
    }
    return d;
  }


  /**
   * 自己拼成的笛卡尔积
   *
   * @param tables
   */
  public static List<Data> falseCartesianProduct(List<String> tables) {
    List<Data> data = null;
    for (int i = 0; i < tables.size(); i++) {
      List<Data> data2 = null;
      String s = tables.get(i);
      if (i == 0) {
        data = findData(s);
        for (Data datum : data) {
          datum.setName(s);
          datum.getFields().forEach(vo -> {
            vo.setConcern(s);
          });
        }
      } else {
        data2 = findData(tables.get(i));
        for (Data datum : data2) {
          datum.setName(s);
          datum.getFields().forEach(vo -> {
            vo.setConcern(s);
          });
        }

        List<Data> ll = new ArrayList<>();
        for (Data datum : data) {
          for (Data data1 : data2) {
            List<Field> fields = CloneUtils.deepCloneByFalseCartesianProduct(datum).getFields();
            fields.addAll( CloneUtils.deepCloneByFalseCartesianProduct(data1).getFields());
            ll.add(new Data(datum.getKey(), datum.getName(), datum.getValue(), fields));
          }
        }
        data.clear();
        data.addAll(ll);
        data.forEach(vo -> vo.setKey(0));
      }
    }
    return data;
  }


  /**
   * 通过条件解析数据,得到最后结果
   *
   * @param purpose1
   * @param newData
   * @param conditionsLists
   * @return
   */
  public static List<Data> getLastData(List<List<String>> purpose1, List<Data> newData, List<List<String>> conditionsLists) {
    if (conditionsLists == null) {
      return newData;
    } else {
      //拿到第一个中的所有字段的表名
//      Set<String> s = new HashSet<>();
//      newData.get(0).forEach(vo -> {
//        s.add(vo.getConcern());
//      });
      //遍历所有的条件
      for (int i = 0; i < conditionsLists.size(); i++) {
        List<String> list = conditionsLists.get(i);
        if (list.get(0).equals(Relation.group_by)) {
          //先执行 group by 进行 group by条件之后的分组,之后再通过找的条件使用having进行过滤
          //[group by, uu.id, having, sum(uu.id), >, 3]
          String[] split = list.get(1).split("\\.");
          List<String> s = new ArrayList<>(Arrays.asList(split));
//          System.out.println(list);
//          System.out.println(s);
          int index = findIndex(s, newData, 0, 1);
          List<List<Data>> sss = new ArrayList<>();
          //通过下标进行分组
          for (int m = 0; m < newData.size(); m++) {
            List<Data> sssss = new ArrayList<>();
            newData.get(m).setKey(1);
            sssss.add(newData.get(m));
            for (int j = m; j < newData.size(); j++) {
              if (newData.get(m).getFields().get(index).getValue().equals(newData.get(j).getFields().get(index).getValue()) && newData.get(j).getKey() == 0) {
                newData.get(j).setKey(1);
                sssss.add(newData.get(j));
              }
            }
            sss.add(sssss);
          }
          newData.clear();
          for (List<Data> data : sss) {
            newData.addAll(data);
          }

//          if (list.get(2).equals(Relation.having)) {
////            list[0]+"("+list[1]+"."+list[2]+")";
//            String[] split1 = list.get(3).split("(");
//            String fun = split1[0];
//            String[] split2 = split1[1].split("\\.");
//            String table = split2[0];
//            String field = split2[1].replace(")", "");
//            for (int w = 0; w < purpose1.size(); w++) {
//              if (fun.equals(purpose1.get(w).get(0)) && table.equals(purpose1.get(w).get(1)) && field.equals(purpose1.get(w).get(2))) {
//
//
//              }
//            }
//
//          }


        } else if (list.get(0).equals(Relation.order_by)) {
          //[order by, uu.id, desc]
          String[] split = list.get(1).split("\\.");
          List<String> l = new ArrayList<>(Arrays.asList(split));
          int index0 = findIndex(l, newData, 0, 1);
          newData.forEach(vo -> {
            vo.setValue(vo.getFields().get(index0).getValue());
          });
          newData.sort(Comparator.comparing(Data::getValue));
          if (list.get(2).equals(Relation.desc)) {
            List<Data> newData1 = new ArrayList<>();
            for (int i1 = newData.size() - 1; i1 >= 0; i1--) {
              newData1.add(newData.get(i1));
            }
            newData.clear();
            newData.addAll(newData1);
          }
        } else if (list.get(0).equals(Relation.limit)) {
          //[limit, uu, id 2 , 2] 从哪取,取几个
          System.out.println(list);
          //[id 2, 2]
          String[] s = list.get(2).split(" \\, ");
          //[id, 2]
          String[] split = s[0].split(" ");
          List<String> l = new ArrayList<>(Arrays.asList(list.get(1), split[0]));
//          System.out.println(l);
          int index0 = findIndex(l, newData, 0, 1);
          int to = Integer.parseInt(s[1]);
          boolean flag = false;
          for (Data newDatum : newData) {
            Field field = newDatum.getFields().get(index0);
            if (field.getType().equals(Dictionary.inte) || field.getType().equals(Dictionary.date) || field.getType().equals(Dictionary.bool)) {
              int l1 = Integer.parseInt(field.getValue());
              int index = Integer.parseInt(split[1]);
              if (index <= l1) {
                if (to == 0) {
                  break;
                }
                to--;
                newDatum.setKey(1);
                continue;
              }
            } else if (field.getType().equals(Dictionary.doub)) {
              double l1 = Double.parseDouble(field.getValue());
              double index = Double.parseDouble(split[1]);
              if (index <= l1) {
                if (to == 0) {
                  break;
                }
                to--;
                newDatum.setKey(1);
                continue;
              }
            } else if (field.getType().equals(Dictionary.varchar)) {
              if (field.getValue().equals(split[1])) {
                to--;
                flag = true;
                newDatum.setKey(1);
                continue;
              }
              if (to > 0 && flag) {
                to--;
                newDatum.setKey(1);
                continue;
              }
            }
          }
          newData.removeIf(vo -> vo.getKey() == 0);
        } else {
          //[uu, id, <>, 1]
//          System.out.println(newData);
          int index = findIndex(list, newData, 0, 1);
          for (Data newDatum : newData) {
            Field field = newDatum.getFields().get(index);
            if ("".equals(field.getValue())) {
              continue;
            }
            //先将时间转换
            if (field.getType().equals(Dictionary.date)) {
              field.setValue(TimeUtils.dateToTimestamp(field.getValue()));
            }
            if (field.getType().equals(Dictionary.inte) || field.getType().equals(Dictionary.date) || field.getType().equals(Dictionary.bool)) {
              int l1 = Integer.parseInt(field.getValue());
              int l2 = Integer.parseInt(list.get(3));
              if (list.get(2).equals(Relation.l)) {
                if (l1 < l2) {
                  newDatum.setKey(1);
                }
              } else if (list.get(2).equals(Relation.g)) {
                if (l1 > l2) {
                  newDatum.setKey(1);
                }
              } else if (list.get(2).equals(Relation.e)) {
                if (l1 == l2) {
                  newDatum.setKey(1);
                }
              } else if (list.get(2).equals(Relation.ge)) {
                if (l1 >= l2) {
                  newDatum.setKey(1);
                }
              } else if (list.get(2).equals(Relation.le)) {
                if (l1 <= l2) {
                  newDatum.setKey(1);
                }
              } else if (list.get(2).equals(Relation.ne) || list.get(2).equals(Relation.gl)) {
                if (l1 != l2) {
                  newDatum.setKey(1);
                }
              }
              continue;
            } else if (field.getType().equals(Dictionary.doub)) {
              double v1 = Double.parseDouble(field.getValue());
              double v2 = Double.parseDouble(list.get(3));
              if (list.get(2).equals(Relation.l)) {
                if (v1 < v2) {
                  newDatum.setKey(1);
                }
              } else if (list.get(2).equals(Relation.g)) {
                if (v1 > v2) {
                  newDatum.setKey(1);
                }
              } else if (list.get(2).equals(Relation.e)) {
                if (v1 == v2) {
                  newDatum.setKey(1);
                }
              } else if (list.get(2).equals(Relation.ge)) {
                if (v1 >= v2) {
                  newDatum.setKey(1);
                }
              } else if (list.get(2).equals(Relation.le)) {
                if (v1 <= v2) {
                  newDatum.setKey(1);
                }
              } else if (list.get(2).equals(Relation.ne) || list.get(2).equals(Relation.gl)) {
                if (v1 != v2) {
                  newDatum.setKey(1);
                }
              }
              continue;
            } else if (field.getType().equals(Dictionary.varchar)) {
              if (field.getValue().equals(list.get(3))) {
                newDatum.setKey(1);
              }
              continue;
            }
          }
          newData.removeIf(vo -> vo.getKey() == 0);
        }
      }
      return newData;
    }

  }


//  public static void main(String[] args) {
////  [[left, uu, dw, id, uu, id], [right, uuk, uuk, id, uu, id]]
//
//    List<List<String>> l = new ArrayList<>();
//    ArrayList<String> left = new ArrayList<>(Arrays.asList("left", "uu", "dw", "id", "uu", "bdfbd"));
//    ArrayList<String> left1 = new ArrayList<>(Arrays.asList("left", "uu", "uu", "id", "dw", "bdfbd"));
//    ArrayList<String> right = new ArrayList<>(Arrays.asList("right", "uuk", "uuk", "id", "uu", "vsdvsd"));
//    ArrayList<String> right1 = new ArrayList<>(Arrays.asList("right", "uuk", "uu", "id", "uuk", "vsdvsd"));
//    ArrayList<String> inner = new ArrayList<>(Arrays.asList("inner", "uuk", "uu", "id", "uuk", "dvsvdsv"));
//    l.add(left);
//    l.add(right);
//    l.add(inner);
//    l.add(left1);
//    l.add(right1);
//    System.out.println(setLink(l));
//  }
//

}
