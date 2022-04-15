package org.lyf.diamond.core.execute.data.insert;

import org.lyf.diamond.core.entity.auxiliary.Relation;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.auxiliary.Dictionary;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.execute.data.DataParse;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program:IntelliJ IDEA
 * @discription:插入操作
 * @author: GG-lyf
 * @create:2022-09-22.1.7 18:09:15
 */
@SuppressWarnings("all")
public class InsertParse {

  /**
   * 合并一个
   *
   * @param keys
   * @param values
   * @param path
   * @return
   */
  public static String combinationOne(String keys, String values, Data dict, String path) {
    List<String> keys_list = DataParse.removeComma(keys);
    List<String> values_list = DataParse.splitData(values);
    if (keys_list.size() == values_list.size()) {
      Map<String, String> map = new HashMap<>();
      for (int i = 0; i < keys_list.size(); i++) {
        map.put(keys_list.get(i), values_list.get(i));
      }
      for (String s : map.keySet()) {
        for (Field field : dict.getFields()) {
          if (field.getValue()=="null"){
            return Return.insert_error;
          }
          if (field.getName().equals(s)) {
            if (field.getType().equals(Dictionary.date)) {
              field.setValue(TimeUtils.dateToTimestamp(map.get(s)));
              continue;
            }
            field.setValue(map.get(s));
          }
        }
      }
      System.out.println(dict);
      if (findKey(path, dict)) {//如果返回的是true就说明没有重复主键的
        DataParse.input(dict, path);
        return Return.insert_ok;//插入成功
      } else {
        return Return.the_primary_key_repeat;//主键重复
      }
    }
    return Return.data_mismatch;//数据不匹配
  }

  /**
   * 合并多个
   *
   * @param keys
   * @param values
   * @param path
   */
  public static String combinationSome(String keys, String values, Data dict, String path) {
    if (!DataParse.judgeMultipleLengths(dict, values)) { //判断数据的长度
      List<String> values_list = DataParse.splitData(values);
      List<String> keys_list = DataParse.removeComma(keys);
      Iterator<String> it = values_list.iterator();
      while (it.hasNext()) {
        List<String> list = DataParse.removeComma(it.next());
        Data add = setFields(dict, list);
        if (findKey(path, add)) {//如果返回的是true就说明没有重复主键的
          DataParse.input(add, path);
        } else {
          return Return.the_primary_key_repeat;//主键重复
        }
      }
      return Return.insert_ok;//插入成功
    } else {
      return Return.data_mismatch;//数据不匹配
    }
  }

  /**
   * 找到是否有主键相同的数据
   *
   * @param path
   * @param add
   * @return
   */
  public static boolean findKey(String path, Data add) {
    List<Field> collect = add.getFields().stream().filter(vo -> vo.getIs_key().equals(Relation.mul)).collect(Collectors.toList());
    List<Data> data = DataParse.getData(path);
    if (data == null) {
      return true;
    }
    for (Data datum : data) {
      for (Field field : datum.getFields()) {
        if (field.getIs_key().equals(Relation.mul)) {//找到一个主键
          for (Field field1 : collect) {
            if (field1.getValue().equals(field.getValue())) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  /**
   * 在合并多个的时候总是出现list集合浅复制的情况,不得已又添加了一个新的方法进行遍历添加
   *
   * @param dict
   * @param list
   * @return
   */
  public static Data setFields(Data dict, List<String> list) {
    for (int j = 0; j < list.size(); j++) {
      if (dict.getFields().get(j).getType().equals(Dictionary.date)) {
        dict.getFields().get(j).setValue(TimeUtils.dateToTimestamp(list.get(j)));
        continue;
      }
      dict.getFields().get(j).setValue(list.get(j));
    }
    return dict;
  }


}
