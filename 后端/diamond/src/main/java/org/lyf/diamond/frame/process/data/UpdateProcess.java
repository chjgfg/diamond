package org.lyf.diamond.frame.process.data;


import org.lyf.diamond.frame.entity.User1;
import org.lyf.diamond.frame.execute.data.Update;
import org.lyf.diamond.frame.process.AnnotationProcess;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: some_middle
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-04 12:24:17
 */
@SuppressWarnings("all")
public class UpdateProcess<T> {

  /**
   * 更新通过id
   *
   * @param t
   * @throws Exception
   */
  public Update updateOneById(T t) {
    try {
      Class<?> clazz = t.getClass();
      String tableName = AnnotationProcess.getTableName(clazz);
      Field[] fields = clazz.getDeclaredFields();
      Update update = new Update().build(tableName);
      String id = "";
      boolean flag = false;
      for (Field field : fields) {
        field.setAccessible(true);
        //值
        String o1 = field.get(t) + "";
        String name = AnnotationProcess.getFieldName(field);
        Object change = AnnotationProcess.compare(field);
        if (!o1.equals(change)) {
          if (!name.equals("id")) {
            update.set(name + " = " + o1);
          } else {
            id = o1;
            flag = true;
          }
        }
      }
      if (!flag) {
        return null;
      }
      update.where().eq("id", id).of();
      System.out.println(update);
      return update;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 更新通过输入的条件
   *
   * @param t
   * @param field
   * @param operation
   * @throws Exception
   */
  public Update updateByCondition(T t, String field, String operation, String condition) {
    try {
      Class<?> clazz = t.getClass();
      String tableName = AnnotationProcess.getTableName(clazz);
      Field[] fields = clazz.getDeclaredFields();
      Update update = new Update().build(tableName);
      String up = "";
      boolean flag = false;
      for (Field attr : fields) {
        attr.setAccessible(true);
        //值
        String o1 = attr.get(t) + "";
        String name = AnnotationProcess.getFieldName(attr);
        Object change = AnnotationProcess.compare(attr);
        if (!o1.equals(change)) {
          if (!name.equals(field)) {
            update.set(name + " = " + o1);
          } else {
//            up = o1;
            flag = true;
          }
        }
      }
      if (!flag) {
        return null;
      }
      update.where();
      if (operation.equals(">")) {
        update.g(field, condition);
      } else if (operation.equals("=")) {
        update.eq(field, condition);
      } else if (operation.equals("<")) {
        update.l(field, condition);
      } else if (operation.equals(">=")) {
        update.ge(field, condition);
      } else if (operation.equals("<=")) {
        update.le(field, condition);
      } else if (operation.equals("<>")) {
        update.gl(field, condition);
      }
      update.of();
      System.out.println(update);
      return update;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 更新通过多个字段
   *
   * @param t
   * @param key
   * @param values
   * @throws Exception
   */
  public Update updateSomeByFields(T t, String key, String... values) {
    try {
      Class<?> clazz = t.getClass();
      String tableName = AnnotationProcess.getTableName(clazz);
      Field[] fields = clazz.getDeclaredFields();
      Update update = new Update().build(tableName);
      String id = "";
      boolean flag = false;
      for (Field field : fields) {
        field.setAccessible(true);
        //值
        String o1 = field.get(t) + "";
        String name = AnnotationProcess.getFieldName(field);
        Object change = AnnotationProcess.compare(field);
        if (!o1.equals(change)) {
          if (!name.equals(key)) {
            update.set(name + " = " + o1);
          } else {
            id = o1;
            flag = true;
          }
        }
      }
      if (!flag) {
        return null;
      }
      update.where().in(key, values).of();
      System.out.println(update);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  public static void main(String[] args) throws Exception {
    User1 a = User1.of().setId(1).setHigh(1.0f).setMoney(1.0).setName("1").setSex(false).setTime(new Date());
    User1 b = User1.of().setHigh(2.0f).setMoney(2).setName("2").setSex(true).setTime(new Date());
    List<User1> l = new ArrayList<>();
    l.add(a);
    l.add(b);
    UpdateProcess<User1> insertProcess = new UpdateProcess<User1>();
//    insertProcess.insertList(l);
//    insertProcess.insertOne(b);
    insertProcess.updateByCondition(a, "id", ">", "2");

//    Update of8 = new Update().build("as").set("cs = cds").set("cds = csdcs").where().eq("osdf", "sdsf").and().eq("d", "df").or().g("hg", "f").or().l("fg", "gf").or().in("飞", "的", "该", "个").of();
//    System.out.println(of8);
  }


}
