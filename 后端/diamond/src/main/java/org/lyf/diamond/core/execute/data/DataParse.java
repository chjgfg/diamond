package org.lyf.diamond.core.execute.data;

import org.lyf.diamond.core.entity.auxiliary.Dictionary;
import org.lyf.diamond.core.entity.auxiliary.Relation;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.file.table.TableFile;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.Arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @program:IntelliJ IDEA
 * @discription:操作数据格式的工具类
 * @author: GG-lyf
 * @create:2022-11-22.1.7 17:11:06
 */
@SuppressWarnings("all")
public class DataParse {

  /**
   * 去除括号和逗号
   * (id,name,now,age,salary) -> [id, name, now, age, salary]
   *
   * @param str
   * @return
   */
  public static List<String> removeComma(String str) {
    return Arrays.asList(str.replace("(", "").replace(")", "").split(","));
  }

  /**
   * 切割数据
   * ('csdcscs',22.5) , ('csdcsd',22.5) , ('csdcsc',22.5)  -> [csdcscs, 22.5 ,  csdcsd, 22.5 ,  csdcsc, 22.5]
   * (2,'dvdvdvd',2021-08-10 00:51:14,2,22.5) -> [2,dvdvdvd,2021-08-10 00:51:14,2,22.5]
   *
   * @param str
   * @return
   */
  public static List<String> splitData(String str) {
    String replace = str.trim().replace("'", "").replace("(", "").replace(")", "");
    if (str.contains(" , ")) {
      return Arrays.asList(replace.split(" , "));
    } else {
      return Arrays.asList(replace.split(","));
    }
  }

  /**
   * 判断多对值中每一对值的长度是否等于键的长度
   *
   * @param keys
   * @param values
   * @return
   */
  public static boolean judgeMultipleLengths(Data dict, String values) {
    List<String> values_list = splitData(values);
//    System.out.println(values_list);
    boolean result = false;
    for (int i = 0; i < values_list.size(); i++) {
      int size1 = removeComma(values_list.get(i)).size();
      if (dict.getFields().size() != size1) {
        result = true;
      }
    }
    return result;
  }


  /**
   * 获取是主键的那个字段对象
   *
   * @param path
   * @return
   */
  public static Field getKey(String path) {
    List<String> read = TableDataFile.read(path);
    List<Field> fields = new ArrayList<>();
    for (String s : read) {
      String[] s1 = s.split(" ");
      if (s1[2] == "*") {
        return new Field(s1[0], s1[1], s1[2]);
      }
    }
    return null;
  }

  /**
   * 将数据字典的东西拼成 (id,name,now,age,salary) 这种形式
   *
   * @param path
   * @return
   */
  public static String setDictToParentheses(Data dict) {
    List<Field> fields = dict.getFields();
    String s = "(";
    for (int i = 0; i < fields.size(); i++) {
      String s1 = fields.get(i).getName();
      s = s + s1;
      if (i < fields.size() - 1) {
        s = s + ",";
        continue;
      }
      s = s + ")";
    }
    return s;
  }

  /**
   * 数据插入
   *
   * @param data
   * @param path
   */
  public static void input(Data data, String path) {
    String input = "";
    for (Field field : data.getFields()) {
      if (field.getValue() == "") {
        input = input + " ";
        continue;
      }
      input = input + field.getValue() + " ";
    }
    TableDataFile.write(path + ".data", input, true);
  }

  /**
   * 切割从数据文件中读出的数据集
   *
   * @param path
   * @return
   */
  public static List<List<String>> splitDatas(String path) {
//    System.out.println(path);
    List<String> read = TableDataFile.read(path);
//    System.out.println(read);
    List<List<String>> ll = new ArrayList<List<String>>();
    for (String s : read) {
      List<String> lines = new ArrayList<String>();
      String[] s1 = s.split(" ");
      lines.addAll(Arrays.asList(s1));
      ll.add(lines);
    }
    return ll;
  }

  /**
   * 把字典列表和数据列表中东西都拼接为一个大对象
   *
   * @param path
   * @return
   */
  public static List<Data> getData(String path) {
    List<List<String>> lists = splitDatas(path + ".data");
//    System.out.println(lists);
    if (lists == null) {
      return null;
    }
    List<Data> datas = new ArrayList<Data>();
    for (List<String> s : lists) {
      Data dict = TableFile.getDict(path + ".dict");
      if (dict == null) {
        return null;
      }
      List<Field> fields = dict.getFields();
      for (int i = 0; i < s.size(); i++) {
        fields.get(i).setValue(s.get(i));
      }
      datas.add(new Data(fields));
    }
    return datas;
  }

  /**
   * 切割还带时间的那种
   * time in (2021-08-10 00:51:14,2021-08-10 10:51:14)  ->  [time, in, 2021-08-10 00:51:14, 2021-08-10 10:51:14]
   * id <= 1  ->  [id, <=, 1]
   *
   * @param value
   * @return
   */
  public static List<String> splitValues(String value) {
    List<String> values = new ArrayList<String>();
    if (value.contains("(")) {
      if (value.contains("-")) {
        String[] split = value.split("\\(");
        String[] split1 = split[0].split(" ");
        String[] split2 = split[1].replace(")", "").replace("'", "").replace(";", "").split(",");
        values.addAll(Arrays.asList(split1));
        values.addAll(Arrays.asList(split2));
      } else {
        String[] split = value.split(" ");
        String[] split1 = split[2].replace("(", "").replace(")", "").replace("'", "").replace(";", "").split(",");
        values.add(split[0]);
        values.add(split[1]);
        values.addAll(Arrays.asList(split1));
      }
    } else {
//      System.out.println(value);
      String[] s = value.replace("'", "").split(" ");
      if (s[2].contains("-") && s.length == 4) {
        s[2] = s[2] + " " + s[3];
        for (int i = 0; i < s.length - 1; i++) {
          values.add(s[i]);
        }
      } else {
        for (int i = 0; i < s.length; i++) {
          values.add(s[i]);
        }
      }
    }
    return values;
  }

  /**
   * 找到条件对应的那个对象
   *
   * @param path
   * @param name
   * @return
   */
  public static Field includeDict(Data dict, String name) {
    for (Field field : dict.getFields()) {
      if (field.getName().equals(name)) {
//        System.out.println(dict);
        return field;
      }
    }
    return null;
  }


  /**
   * 拼接满足条件的Field对象
   *
   * @param field
   * @param conditions
   * @return
   */
  public static List<Field> assemblyFields(Field field, String conditions) {
    List<Field> fields = new ArrayList<Field>();
    List<String> list = (List<String>) splitValues(conditions);
//    System.out.println(list);
    for (int i = 2; i < list.size(); i++) {
      Field nfield = field.clone();
      if (field.getType().equals(Dictionary.date)) {
        String s = TimeUtils.dateToTimestamp(list.get(i));
        nfield.setValue(s);
      } else {
        nfield.setValue(list.get(i));
      }
      fields.add(nfield);
    }
    return fields;
  }


  /**
   * 切割and的条件
   * e_id = 1 and e_id = 2 or e_id = 3 and e_id = 4 or e_id = 5  ->  [[], [e_id = 5]]
   * e_id = 1 and e_id = 2  ->  null
   * e_id = 4 or e_id = 5 or e_id = 3 and e_id = 2  ->  [[], [e_id = 4, e_id =5]]
   * e_id = 1 or e_id = 3 and e_id = 2 and e_id = 4 or e_id = 5  ->  [[], [e_id = 1, e_id =5]]
   * e_id = 1 and e_id = 2 or e_id = 3 and e_id = 4  ->  [[], []]
   *
   * @param keys
   * @return
   */
  public static List<List<String>> and(String keys) {
    List<List<String>> list = new ArrayList<List<String>>();
    List<String> orList = new ArrayList<String>();
    List<String> andList = new ArrayList<String>();
    String[] split = keys.split(Relation.and_);
    if (split.length >= 2) {
      if (split[0].contains(Relation.or)) {
        String[] split1 = split[0].split(Relation.or_);
        for (int i = 0; i < split1.length - 1; i++) {
          orList.add(split1[i]);
        }
      } else {
        orList.add(split[0]);
      }
      if (split[split.length - 1].contains(Relation.or)) {
        String[] split1 = split[split.length - 1].split(Relation.or_);
        orList.add(split1[split1.length - 1]);
      }
      list.addAll(Collections.singleton(andList));
      list.addAll(Collections.singleton(orList));
    }
    return list;
  }

  /**
   * 切割or的条件
   * e_id = 4 or e_id =5 or e_id = 3 and e_id = 2  ->  [[e_id = 4, e_id =5]]
   * e_id = 1 or e_id = 4 and e_id =5 or e_id = 3 and e_id = 2  ->
   * e_id = 1 and e_id = 2 or e_id = 3 and e_id = 4  ->  [[]]
   *
   * @param keys
   * @return
   */
  public static List<List<String>> or(String keys) {
    List<List<String>> list = new ArrayList<List<String>>();
    List<String> orList = new ArrayList<String>();
    List<String> andList = new ArrayList<String>();
    String[] split = keys.split(Relation.or_);
    if (split.length >= 2) {
      for (int i = 0; i < split.length; i++) {
        if (i == split.length - 1) {
          if (split[i].contains(Relation.or)) {
            orList.addAll(Arrays.asList(split[i].split(Relation.or_)));
          } else if (!split[i].contains(Relation.and)) {
            orList.add(split[i]);
          }
        } else {
          if (!split[i].contains(Relation.and)) {
            orList.add(split[i]);
          }
        }
      }
    } else {
      orList.addAll(Arrays.asList(split));
    }
    list.addAll(Collections.singleton(orList));
    return list;
  }

  /**
   * 解析带in的那种语句
   *
   * @param s
   * @return
   */
  public static List<String> in(String s) {
    List<String> inList = new ArrayList<>();
    String[] split = s.split(Relation.in_);
    String s2 = split[0];
    List<String> list1 = splitData(split[1]);
    for (String s1 : list1) {
      inList.add(s2 + " = " + s1);
    }
    return inList;
  }


  /**
   * 在and和or的条件下封装的in方法
   * e_id = 1 and e_id = 2 or e_id = 3 and e_id = 4 or e_id = 5 or e_id = 5 or time in (1,2,3,4,5)  ->  [[e_id = 5, e_id = 5, time in (1,2,3,4,5)]]
   * e_id = 1 or e_id = 3 and e_id = 2 and e_id = 4 or e_id = 5 or time in (1,2,3,4,5)  ->  [[e_id = 1, e_id = 5, time in (1,2,3,4,5)]]
   * e_id = 4 or e_id = 5 and e_id = 3 or time in (1,2,3,4,5)  ->  [[e_id = 4, time in (1,2,3,4,5)]]
   * <p>
   * 由于封装后的条件是一个二维数组,在直接删除的时候会出现并发修改异常,因此使用lambda表达式来进行数据的删除和对空元素的删除
   *
   * @param keys
   */
  public static List<List<String>> andAndOrAndin(String keys) {
    List<List<String>> list = new ArrayList<>();
    String[] and = keys.split(Relation.and_);
    String[] or = keys.split(Relation.or_);
    List<String> inList = new ArrayList<>();
//    System.out.println(keys);
//    System.out.println(keys.contains(Relation.in));
    if (keys.contains(Relation.in)) {
      if (keys.contains(Relation.and)) {
        if (keys.contains(Relation.or)) {
          if (and.length >= or.length) {
            list = and(keys);
          } else {
            list = or(keys);
          }
        } else {
          list = and(keys);
        }
      } else {
        if (keys.contains(Relation.or)) {
          list = or(keys);
        } else {
          //解析in
          inList = in(keys);
        }
      }
    } else {
      if (keys.contains(Relation.and)) {
        if (keys.contains(Relation.or)) {
          if (and.length >= or.length) {
            list = and(keys);
          } else {
            list = or(keys);
          }
        } else {
          list = and(keys);
        }
      } else {
        if (keys.contains(Relation.or)) {
          list = or(keys);
        } else {
          ArrayList<String> nomal = new ArrayList<>();
          nomal.add(keys);
          list.add(nomal);
          return list;
        }
      }
    }
//    System.out.println(list);
    Iterator<List<String>> iterator = list.iterator();
    while (iterator.hasNext()) {
      for (String s : iterator.next()) {
        if (s.contains(Relation.in)) {
          inList.addAll(in(s));
//          System.out.println(inList);
        }
      }
    }
    list.addAll(Collections.singleton(inList));//封装所有判断后的条件
//    System.out.println(list);
    list.stream().forEach(vo -> {
      vo.removeIf(vi -> vi.contains(Relation.in));
    });
    list.removeIf(vo -> vo.size() == 0);
//    System.out.println(list);
    return list;
  }


  /**
   * 解析条件
   * time in (1,2,3,4,5) or e_id = 4 or e_id = 5 or e_id = 3  ->  [[time = 1, time = 2, time = 3, time = 4, time = 5, e_id = 4, e_id = 5, e_id = 3]]
   * id = 4 or time in (2021-08-10 00:51:14,2021-08-10 10:51:14)  ->  [[id = 4], [time = 2021-08-10 00:51:14, time = 2021-08-10 10:51:14]]
   *
   * @param keys
   */
  public static List<List<String>> regroupKeys(String keys) {
    List<List<String>> in = andAndOrAndin(keys);
//    System.out.println(in);
    List<List<String>> list = new ArrayList<List<String>>();
//    System.out.println(in);
    for (int i = 0; i < in.size(); i++) {
      for (int i1 = 0; i1 < in.get(i).size(); i1++) {
        List<String> keyList = new ArrayList<String>();
        String s = in.get(i).get(i1);
        if (s.contains("-") && s.contains(":")) {
          String[] split = s.split(" " + Relation.e + " ");
          keyList.add(split[0]);
          keyList.add("=");
          keyList.add(split[1]);
        } else {
          String[] split = s.replace("'", "").split(" ");
          keyList.addAll(Arrays.asList(split));
        }
        list.add(keyList);
      }
    }
//    System.out.println(list);
    return list;
  }


  /**
   * 找这个表中是否存在这个字段
   *
   * @param n_table
   * @param value
   * @return
   */
  public static boolean checkField(String n_table, String value) {
    Data dict = TableFile.getDict(UseDatabase.getPath() + "\\" + n_table + "\\" + n_table + ".dict");
    for (Field field : dict.getFields()) {
      if (field.getName().equals(value)) {
        //如果找到就返回true
        return true;
      }
    }
    //找到数据的最后直接返回false,说明未找到
    return false;
  }




  public static void main(String[] args) {
    String s1 = "time in (2021-08-10 00:51:14,2021-08-10 10:51:12);";
    String s2 = "name in ('张三','李四','王五','韩阳','赵六');";
    String s3 = "time = 2021-08-10 10:51:14";
    String s4 = "id in (1,2,3,4,5,6,7,8,9,10)";
    String s5 = "money in (10.2,11.2,12.2)";
//    String[] split = s1.split("\\(");
//    String[] split1 = split[0].split(" ");
//    String[] split2 = split[1].replace(");", "").replace("'", "").split(",");
//    for (String s : split1) {
//      System.out.println(s);
//    }
//    for (String s : split2) {
//      System.out.println(s);
//    }
    System.out.println(splitValues(s5));
  }


}
