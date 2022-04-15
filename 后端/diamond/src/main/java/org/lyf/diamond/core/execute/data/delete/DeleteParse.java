package org.lyf.diamond.core.execute.data.delete;

import org.lyf.diamond.core.entity.auxiliary.Dictionary;
import org.lyf.diamond.core.entity.auxiliary.Relation;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.execute.data.DataParse;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.utile.CharsetUtils;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.Iterator;
import java.util.List;

/**
 * @program:IntelliJ IDEA
 * @discription:删除数据工具类
 * @author: GG-lyf
 * @create:2022-13-22.1.8 00:13:49
 */
@SuppressWarnings("all")
public class DeleteParse {

  /**
   * 把数据表文件清空
   *
   * @param path
   */
  public static String deleteAll(String path) {
    boolean clearn = TableDataFile.clearn(path);
    if (clearn) {
//      System.out.println(Return.clean_all_success);
      return Return.clean_all_success;
    } else {
//      System.out.println(Return.clean_all_errer);
      return Return.clean_all_errer;
    }
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
   * 处理对象
   *
   * @param path
   * @param conditions
   */
  public static String process(String path, String conditions) {
    List<Data> data = DataParse.getData(path);
    if (data == null) {
      return Return.table_is_not_exist;
    }
    List<List<String>> lists = DataParse.regroupKeys(conditions);
    data.stream().forEach(datum -> {
      lists.stream().forEach(list -> {
        get(datum, list);
      });
    });
    List<Data> data1 = deleteSome(data);
    TableDataFile.clearn(path + ".data");
    data1.forEach(vo -> {
      DataParse.input(vo, path);
//      System.out.println(vo);
    });
    return Return.delete_ok;
  }


  /**
   * 删除标记的那条数据
   *
   * @param data
   */
  private static List<Data> deleteSome(List<Data> data) {
    Iterator<Data> iter = data.iterator();
    while (iter.hasNext()) {
      if ("t".equals(iter.next().getFields().get(0).getSymbol())) {
        iter.remove();
      }
    }
    return data;
  }

//  public static void main(String[] args) {
//    String path = "D:\\idea\\idea-workspace\\diamon\\core\\src\\main\\resources\\db\\data\\user1\\user\\user";
//    String s1 = "sex <= 5 and sex <= 2 or sex = 11";
//    String s2 = "id in (1,2,3,4,5,5,5555) and sex <= 22.2 or sex = 11";
//    String s3 = "name = '222222' and sex <= 22.2 or ss = 11 or name = '2121212'";
//    String s4 = "name in ('vsds','cdvs','ssc') and sex <= 22.2 or ss = 11";
//    String s5 = "id in (10.2,11.2,12.2) and sex <= 22.2 or sex = 11";
//    String s6 = "time in (2021-08-10 00:51:14,2021-08-10 10:51:14) and sex <= 22.2 or sex = 11";
//    String s7 = "time = 2021-08-10 00:51:14 and sex <= 22.2 or sex = 11";
//    String s8 = "time in (2021-08-10 00:51:14,2021-08-10 10:51:14)";
//    String s9 = "id < 1";
//    String s10 = "sex != 5";
//    process(path, s8);
//
//  }


}
