package org.lyf.diamond.core.execute.data.update;

import org.lyf.diamond.core.entity.auxiliary.Dictionary;
import org.lyf.diamond.core.entity.auxiliary.Relation;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.execute.data.DataParse;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.utile.CharsetUtils;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program:IntelliJ IDEA
 * @discription:修改数据操作
 * @author: GG-lyf
 * @create:2022-14-22.1.8 00:14:28
 */
@SuppressWarnings("all")
public class UpdateParse {

  /**
   * 切割数据
   * name = 10 , age = 11 , age = 12 , age = 13  ->  [[name, =, 10], [age, =, 11], [age, =, 12], [age, =, 13]]
   * name = 10 , age = 11 , now = 2021-08-10 00:51:14 , salary = 13.2  ->  [[name, =, 10], [age, =, 11], [now, =, 2021-08-10 00:51:14], [salary, =, 13.2]]
   *
   * @param values
   */
  public static List<List<String>> splitValues(String values) {
    values = values.replace("'", "");
    List<List<String>> list = new ArrayList<List<String>>();
    if (values.contains(" , ")) {
      String[] split = values.split(" , ");
      for (int i = 0; i < split.length; i++) {
        List<String> ll = new ArrayList<String>();
        if (split[i].contains("-")) {
          String[] s = split[i].split(" ");
          s[2] = s[2] + " " + s[3];
          for (int j = 0; j < s.length - 1; j++) {
            ll.add(s[j]);
          }
        } else if (split[i].contains(Relation.add) || split[i].contains(Relation.sub) || split[i].contains(Relation.mul) || split[i].contains(Relation.div) || split[i].contains(Relation.mod)) {
          String[] split1 = split[i].split(" = ");
          String[] s = split1[1].split(" ");
          ll.addAll(Arrays.asList(s));
        } else {
          String[] s = split[i].split(" ");
          ll.addAll(Arrays.asList(s));
        }
        list.add(ll);
      }
    } else {
      String[] split = values.split(" = ");
      List<String> ll = new ArrayList<String>();
      ll.add(split[0]);
      if (split[1].contains(Relation.add) || split[1].contains(Relation.sub) || split[1].contains(Relation.mul) || split[1].contains(Relation.div) || split[1].contains(Relation.mod)) {
        String[] s = split[1].split(" ");
        ll.add(s[1]);
        ll.add(s[2]);
      } else {
        ll.add("=");
        ll.add(split[1]);
      }
      list.add(ll);
    }
//    System.out.println(list);
    return list;
  }

  /**
   * 将找到的那个大的data对象用 t 标记一下
   * Data{key=0, fields=[Field{name='id', concern='null', value='1', type='int', is_key='*', symbol='t'}, Field{name='name', concern='null', value='11', type='varchar', is_key='~', symbol='null'}, Field{name='time', concern='null', value='1641651254000', type='datetime', is_key='~', symbol='null'}, Field{name='sex', concern='null', value='1', type='int', is_key='~', symbol='null'}]}
   *
   * @param data
   * @param list
   */
  public static void get(Data data, List<String> list) {
    String name = list.get(0);
    String concern = list.get(1);
    String value = list.get(2);
//    System.out.println(list);
    data.getFields().stream().forEach(field -> {
      String n_value = null;
      if (field.getName().equals(name)) {//先比较改哪个值
//        System.out.println(field.getType());
        if (field.getType().equals(Dictionary.varchar)) {//字符串类型
          if (concern.equals(Relation.e)) {//得出关系
            if (field.getValue().equals(n_value)) {//
              data.getFields().get(0).setSymbol("t");
            }
          }
        } else {
          if (field.getType().equals(Dictionary.date)) {
            n_value = TimeUtils.dateToTimestamp(value);
          } else {
            n_value = value;
          }
          if (concern.equals(Relation.e)) {//得出关系
            if (CharsetUtils.compareTo(field.getValue(), n_value) == 0) {//
              data.getFields().get(0).setSymbol("t");
            }
          } else if (concern.equals(Relation.g)) {
            if (CharsetUtils.compareTo(field.getValue(), n_value) > 0) {
              data.getFields().get(0).setSymbol("t");
            }
          } else if (concern.equals(Relation.l)) {
            if (CharsetUtils.compareTo(field.getValue(), n_value) < 0) {
              data.getFields().get(0).setSymbol("t");
            }
          } else if (concern.equals(Relation.le)) {
            if (CharsetUtils.compareTo(field.getValue(), n_value) <= 0) {
              data.getFields().get(0).setSymbol("t");
            }
          } else if (concern.equals(Relation.ge)) {
            if (CharsetUtils.compareTo(field.getValue(), n_value) >= 0) {
              data.getFields().get(0).setSymbol("t");
            }
          } else if (concern.equals(Relation.gl) || concern.equals(Relation.ne)) {
            if (CharsetUtils.compareTo(field.getValue(), n_value) != 0) {
              data.getFields().get(0).setSymbol("t");
            }
          }
        }

      }
    });
//    System.out.println(data);
  }

  /**
   * 设置值
   * 把找到的并且标记的那个东西设置成想要的值
   * Data{key=0, fields=[Field{name='id', concern='null', value='2', type='int', is_key='*', symbol='t'}, Field{name='name', concern='null', value='knknknkn', type='varchar', is_key='~', symbol='null'}, Field{name='time', concern='null', value='1641651254000', type='datetime', is_key='~', symbol='null'}, Field{name='sex', concern='null', value='2', type='int', is_key='~', symbol='null'}]}
   *
   * @param data
   * @param list
   */
  public static void set(Data data, List<String> list) {
    if (!"t".equals(data.getFields().get(0).getSymbol())) {
      return;
    }
    String name = list.get(0);
    String concern = list.get(1);
    String value = list.get(2);
//    System.out.println(value);
    data.getFields().stream().forEach(field -> {
      String n_value = null;
      if (field.getName().equals(name)) {//先比较改哪个值
        if (field.getType().equals(Dictionary.date)) {
          n_value = TimeUtils.dateToTimestamp(value);
        } else {
          n_value = value;
        }
        if (concern.equals(Relation.e)) {//得出关系
          field.setValue(n_value);
        } else if (concern.equals(Relation.add)) {
          String val = null;
          if (field.getType().equals(Dictionary.inte) || field.getType().equals(Dictionary.date)) {
            val = Integer.parseInt(field.getValue()) + Integer.parseInt(n_value) + "";
          } else if (field.getType().equals(Dictionary.doub)) {
            val = Double.parseDouble(field.getValue()) + Double.parseDouble(n_value) + "";
          }
          field.setValue(val);
        } else if (concern.equals(Relation.sub)) {
          String val = null;
          if (field.getType().equals(Dictionary.inte) || field.getType().equals(Dictionary.date)) {
            val = Integer.parseInt(field.getValue()) - Integer.parseInt(n_value) + "";
          } else if (field.getType().equals(Dictionary.doub)) {
            val = Double.parseDouble(field.getValue()) - Double.parseDouble(n_value) + "";
          }
          field.setValue(val);
        } else if (concern.equals(Relation.mul)) {
          String val = null;
          if (field.getType().equals(Dictionary.inte) || field.getType().equals(Dictionary.date)) {
            val = Integer.parseInt(field.getValue()) * Integer.parseInt(n_value) + "";
          } else if (field.getType().equals(Dictionary.doub)) {
            val = Double.parseDouble(field.getValue()) * Double.parseDouble(n_value) + "";
          }
          field.setValue(val);
        } else if (concern.equals(Relation.div)) {
          String val = null;
          if (field.getType().equals(Dictionary.inte) || field.getType().equals(Dictionary.date)) {
            val = Integer.parseInt(field.getValue()) / Integer.parseInt(n_value) + "";
          } else if (field.getType().equals(Dictionary.doub)) {
            val = Double.parseDouble(field.getValue()) / Double.parseDouble(n_value) + "";
          }
          field.setValue(val);
        } else if (concern.equals(Relation.mod)) {
          String val = null;
          if (field.getType().equals(Dictionary.inte) || field.getType().equals(Dictionary.date)) {
            val = Integer.parseInt(field.getValue()) % Integer.parseInt(n_value) + "";
          } else if (field.getType().equals(Dictionary.doub)) {
            val = Double.parseDouble(field.getValue()) % Double.parseDouble(n_value) + "";
          }
          field.setValue(val);
        }
      }
    });
//    System.out.println(data);
  }

  /**
   * 校验,当数据长度和自增的变量一样时才算是哟这个字段名
   *
   * @param data1
   * @param listss
   * @param lists
   * @return
   */
  public static boolean check(Data data1, List<List<String>> listss, List<List<String>> lists) {
    int oo = 0, kk = 0;
    for (Field field : data1.getFields()) {
      for (List<String> list : lists) {
        if (list.get(0).equals(field.getName())) {
          oo++;
        }
      }
      for (List<String> sss : listss) {
        if (sss.get(0).equals(field.getName())) {
          kk++;
        }
      }
    }
    
    if (oo == lists.size() && kk == listss.size()) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * 先找到要改哪条数据的那个字段,之后在这条数据的第一个字段哪里用 t 标记,之后再通过想要的数据进行修改
   *
   * @param path
   * @param keys
   * @param values
   */
  public static String combination(String path, String keys, String values) {
    List<List<String>> listss = DataParse.regroupKeys(keys);
    List<Data> data = DataParse.getData(path);
    if (data == null) {
      return Return.table_is_not_exist;
    }
    data.stream().forEach(datum -> {
      listss.stream().forEach(list -> {
        get(datum, list);
      });
    });
    List<List<String>> lists = splitValues(values);
    if (!check(data.get(0), listss, lists)) {
      return Return.data_mismatch2;
    }
    data.stream().forEach(datum -> {
      lists.stream().forEach(list -> {
        set(datum, list);
      });
    });
    TableDataFile.clearn(path + ".data");
    data.stream().forEach(vo -> {
      DataParse.input(vo, path);
    });
    return Return.update_ok;
  }


  public static void main(String[] args) {
    String s1 = "name = 10 , age = 11 , age = 12 , age = 13";
    String s4 = "name = 10 , age = 11 , now = 2021-08-10 00:51:14 , salary = 13.2";
    String s2 = "id = 1";
    String s5 = "money = money + 10.5 , name = 'knknknkn' , id = 1 , time = 2021-08-10 10:51:14";
    String s6 = "name = '历史' , salary = salary * 10.0 , now = 2021-08-13 10:51:14";
    String s15 = "id = id + 1 , name = 'knknknkn' , sex = sex * 2";
    String s16 = "time = 2021-08-10 10:51:14";

    String path = "D:\\idea\\idea-workspace\\diamon\\core\\src\\main\\resources\\db\\data\\user1\\user\\user";
//    combination(path, s3, s6);
//    List<Data> lists = DataParse.getData(path);
//    for (Data list : lists) {
//      System.out.println(list);
//    }

//    List<Data> data = DataParse.getData(path);


    String s7 = "e_id = 1 and e_id = 2 or e_id = 3 and e_id = 4 or e_id = 5";
    String s12 = "e_id = 1 or e_id = 3 and e_id = 2 and e_id = 4 or e_id =5";
    String s13 = "e_id = 1 and e_id = 2 or e_id = 3 and e_id = 4 or e_id =5";
    String s8 = "e_id = 4 or e_id =5 or e_id = 3";
    String s10 = "e_id = 1 and e_id = 2 or e_id = 3 and e_id = 4";
    String s11 = "e_id = 1 and e_id = 2";
    String s9 = "e_id = 4 or e_id =5 or e_id = 3 and e_id = 2";
    String s14 = "e_id = 1 or e_id = 4 and e_id =5 or e_id = 3 and e_id = 2";

    String z1 = "e_id = 1 and e_id = 2 or e_id = 3 and e_id = 4 or e_id = 5 or e_id = 5 or time in (1,2,3,4,5)";
    String z2 = "e_id = 1 or e_id = 3 and e_id = 2 and e_id = 4 or e_id = 5 or time in (1,2,3,4,5)";
    String z3 = "time in (1,2,3,4,5) or e_id = 1 and e_id = 2 or e_id = 3 and e_id = 4 or e_id = 5";
    String z4 = "time in (1,2,3,4,5) and e_id = 4 or e_id = 5 or e_id = 3";
    String z5 = "e_id = 1 and e_id = 2 or e_id = 3 and e_id = 4 and time in (1,2,3,4,5)";
    String z6 = "e_id = 1 and e_id = 2 and time in (1,2,3,4,5)";
    String z7 = "e_id = 4 or e_id = 5 or e_id = 3 and e_id = 2 and time in (1,2,3,4,5)";
    String z8 = "e_id = 1 or e_id = 4 and e_id = 5 or e_id = 3 and e_id = 2 or time in (1,2,3,4,5)";
    String z9 = "e_id = 4 or e_id = 5 or e_id = 3 and time in (1,2,3,4,5)";
    String z10 = "id <= 4 or id > 5";
    String z11 = "e_id >= 4 or e_id <= 5 and e_id = 3 or time in (2021-08-10 00:51:14,2021-08-10 10:51:14)";
    String z12 = "e_id = 4 or time in (2021-08-10 00:51:14,2021-08-10 10:51:14)";
    String z13 = "id = 4 and time in (2021-08-10 00:51:14,2021-08-10 10:51:14)";
    String z14 = "e_id in (1,2) and e_id in (3,4)";
    String z15 = "e_id in (1,2) or e_id in (3,4)";
    String z16 = "id = 4 or time in (2021-08-10 00:51:14,2021-08-10 10:51:14)";


    String n1 = "id <= 3";
    String n14 = "id = 10";
    String n13 = "id in (4,8,6,10)";
    String n2 = "time in (2022-01-08 22:14:14,2021-08-10 10:51:14)";
    String n9 = "name = '22222' , time = 2022-01-09 22:14:14";
    String n10 = "name = 'knknknkn'";
    String n15 = "name = 'mm'";
    String n11 = "time = 2022-01-10 22:14:14";
    String n12 = "time = 2022-01-09 22:14:14";
    String n3 = "name in ('11','22','33','44')";
    String n4 = "id <= 10 or id <= 8 and id >= 3 and id in (11,12)";
    String n5 = "e_id <= 10 and e_id <= 8 or e_id <= 3 or e_id in (11,12)";
    String n6 = "e_id <= 10 and e_id <= 8 and e_id >= 3 and e_id in (11,12)";
    String n7 = "id >= 5 or id in (1,2)";
    String n8 = "sex = sex + 5";

//    List<List<String>> listss = DataParse.regroupKeys(n4);
//    listss.stream().forEach(System.out::println);
    combination(path, n10, n15);

  }
}
