package org.lyf.diamond.core.execute.table;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.data.DataParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.file.table.TableFile;
import org.lyf.diamond.core.utile.FieldUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Stream;

/**
 * 四个方法写的太难受了
 * 特别是判空,写的太烦人了
 */
@SuppressWarnings("all")
public class AlterTable {
  public String alter(Matcher mat, String cmd) {
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      return Return.please_point_database;
//      return false;
    }

    String table_name = mat.group(1);

    String p = cmd.split(" ")[0];
    String[] split = UseDatabase.getPath().split("\\\\");
    String cheak = UserParse.cheak(Authority.getName(), split[split.length - 1], table_name, p);
    if (!cheak.equals(p)) {
      return cheak;
    }


    String operate = mat.group(2);
    String o = mat.group(4);
    String n = mat.group(5);
    String type = mat.group(6);
    String key = mat.group(7);
    String path = UseDatabase.getPath() + "\\" + table_name + "\\" + table_name + ".dict";
//    System.out.println(path);
    Data dict = TableFile.getDict(path);
    if (dict == null) {
      return Return.table_is_not_exist;
    }
    if (existKey(key(dict), key != null) == 0) {//有主键就不让他插
//      System.out.println("key is exists!");
      return Return.key_is_exist;
    } else {//没有主键就让他插
      if ("add".equals(operate)) {//添加字段
        String s1 = null;
        if (!FieldUtils.isField(n)) {//判断添加字段的数据类型
//          System.out.println("data type is error!");
          return Return.data_type_is_error;
        }
        if (existKey(key(dict), key != null) == 2) {
          s1 = o + " " + n + " *";
        } else {
          s1 = o + " " + n + " ~";
        }
        TableDataFile.write(path, s1, true);
        return Return.add_ok;
      } else if ("change".equals(operate)) {//变更字段
        if (!FieldUtils.isField(type)) {//判断变更字段的数据类型
//          System.out.println("data type is error!");
          return Return.data_type_is_error;
        }
        Data data = changes(dict, o, n, type, key);
        if (data == null) {
//          System.out.println("field is not exist!");
          return Return.field_is_not_exist;
        }
        TableDataFile.clearn(path);
        for (Field field : data.getFields()) {
          String s1 = field.getName() + " " + field.getType() + " " + field.getIs_key();
          TableDataFile.write(path, s1, true);
        }
        return Return.change_ok;
      } else if ("drop".equals(operate)) {//删除字段
        Data data = drop(dict, o);
        if (data == null) {
//          System.out.println("field is not exist!");
          return Return.field_is_not_exist;
        }
        String replace = path.replace(".dict", "");
        if (dropData(replace, o) == false) {
//          System.out.println(Return.drop_error);
          return Return.drop_error;
        }
        TableDataFile.clearn(path);
        for (Field field : data.getFields()) {
          String s1 = field.getName() + " " + field.getType() + " " + field.getIs_key();
          TableDataFile.write(path, s1, true);
        }
//        System.out.println(Return.drop_ok);
        return Return.drop_ok;
      } else if ("modify".equals(operate)) {//修改字段
        if (!FieldUtils.isField(n)) {//判断修改字段的数据类型
//          System.out.println(Return.data_type_is_error);
          return Return.data_type_is_error;
        }
        Data data = modify(dict, o, n, key);
        if (data == null) {
//          System.out.println("field is not exist!");
          return Return.field_is_not_exist;
        }
        TableDataFile.clearn(path);
        for (Field field : data.getFields()) {
          String s1 = field.getName() + " " + field.getType() + " " + field.getIs_key();
          TableDataFile.write(path, s1, true);
        }
//        System.out.println(Return.modify_ok);
        return Return.modify_ok;
      }
    }
    return Return.unknown_error;
  }


  /**
   * 找是否存在主键
   *
   * @param dict
   * @return
   */
  private static boolean key(Data dict) {
    Stream<Field> fieldStream = dict.getFields().stream().filter(s -> "*".equals(s.getIs_key()));
    long count = fieldStream.count();
    return count != 0 ? true : false;
  }

  /**
   * 判断是否可以插入带主键的值
   * 0:不能插入
   * 2:只能插入带星号的
   * 1,3:只能不带星号插
   *
   * @param e
   * @param f
   * @return
   */
  private static int existKey(boolean e, boolean f) {
    if (e) {
      if (f) {
        return 0;//不能插
      } else {
        return 1;//只能不带星号插
      }
    } else {
      if (f) {
        return 2;//只能带星号插
      } else {
        return 3;//只能不带星号插
      }
    }
  }

  /**
   * 修改一个字段的值和属性
   *
   * @param dict
   * @param o
   * @param n
   * @param t
   * @param key
   * @return
   */
  private static Data changes(Data dict, String o, String n, String t, String key) {
    Stream<Field> fieldStream = dict.getFields().stream().filter(s -> o.equals(s.getName()));
    Optional<Field> first = fieldStream.findFirst();
//    System.out.println(first.isPresent());
    if (!first.isPresent()) {
      return null;
    }//use user1;  alter table user modify fffffint int;
    Field field = dict.getFields().stream().filter(s -> o.equals(s.getName())).findFirst().get();
//    dict.getFields().remove(field);
    field.setName(n);
    field.setType(t);
//    dict.getFields().stream().forEach(System.out::println);
    if (key == null) {
      field.setIs_key("~");
    } else {
      field.setIs_key("*");
    }
    return dict;
  }

  /**
   * 删除一个字段
   *
   * @param dict
   * @param o
   * @return
   */
  private static Data drop(Data dict, String o) {
    Stream<Field> fieldStream = dict.getFields().stream().filter(s -> o.equals(s.getName()));
    Optional<Field> first = fieldStream.findFirst();
//    System.out.println(first.isPresent());
    if (!first.isPresent()) {
      return null;
    }//use user1;  alter table user modify fffffint int;
    Field field = dict.getFields().stream().filter(s -> o.equals(s.getName())).findFirst().get();
    dict.getFields().remove(field);
    return dict;
  }

  /**
   * 删字段前先把那一列数据删了
   *
   * @param path
   * @param o
   * @return
   */
  private static boolean dropData(String path, String o) {
    List<Data> data = DataParse.getData(path);
    if (data == null) {
      return false;
    }
    List<Data> data1 = data;
    TableDataFile.clearn(path + ".data");
    data.forEach(vo -> {
      vo.getFields().removeIf(v -> v.getName().equals(o));
    });
    int oo = 0;
    for (Data datum : data) {
      DataParse.input(datum, path);
      oo++;
    }
    if (oo == data.size()) {
      return true;
    } else {//删错了可以再写回去
      for (Data datum : data1) {
        DataParse.input(datum, path);
      }
      return false;
    }
  }


  /**
   * 修改一个字段的属性
   *
   * @param dict
   * @param o
   * @param t
   * @param key
   * @return
   */
  private static Data modify(Data dict, String o, String t, String key) {
    Stream<Field> fieldStream = dict.getFields().stream().filter(s -> o.equals(s.getName()));
    Optional<Field> first = fieldStream.findFirst();
//    System.out.println(first.isPresent());
    if (!first.isPresent()) {
      return null;
    }//use user1;  alter table user modify fffffint int;
    Field field = dict.getFields().stream().filter(s -> o.equals(s.getName())).findFirst().get();
    field.setType(t);
    if (key == null) {
      field.setIs_key("~");
    } else {
      field.setIs_key("*");
    }
    return dict;
  }
  /**
   use user1;
   use user2;
   alter table user add now datetime;
   alter table user change ssss ok double;
   alter table user drop money;
   alter table user modify money int;
   alter table user add money int;
   alter table user add now datetime is primrykey;
   alter table user change old new datetime is primrykey;
   alter table user drop time is primrykey;
   alter table user modify money int is primrykey;
   alter table user modify ok int;
   alter table user modify fffffint int;
   alter table user drop fffffint;
   */
}
