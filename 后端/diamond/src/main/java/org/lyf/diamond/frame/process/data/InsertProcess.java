package org.lyf.diamond.frame.process.data;


import org.lyf.diamond.frame.annotation.TableField;
import org.lyf.diamond.frame.entity.User1;
import org.lyf.diamond.frame.execute.data.Insert;
import org.lyf.diamond.frame.process.AnnotationProcess;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: some_middle
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-04 08:46:24
 */
@SuppressWarnings("all")
public class InsertProcess<T> {

  /**
   * 插入一条数据
   *
   * @param t
   * @return
   * @throws Exception
   */
  public Insert insertOne(T t) {
    try {
      Class<?> clazz = t.getClass();
      String tableName = AnnotationProcess.getTableName(clazz);
      Field[] declaredFields = clazz.getDeclaredFields();
      T o = (T) clazz.newInstance();
      Insert insert = new Insert().build(tableName);
      for (Field field : declaredFields) {
        field.setAccessible(true);
        //数据值
        String o1 = field.get(t) + "";
        Object change = AnnotationProcess.compare(field);
        if (!o1.equals(change)) {
          //获取注解上的数据
          TableField annotation = field.getAnnotation(TableField.class);
          String comment = "", value = "", aNull = "", key = "", all = "";
          if (annotation != null) {
            //字段名
            value = annotation.value() != null ? annotation.value() : field.getName().toLowerCase();
            //注释
            comment = annotation.comment() != null ? annotation.comment() : "";
            //是否为主键
            key = annotation.key() ? "primary key" : "";
            //是否为空
            aNull = annotation.is_null() ? "" : "not null";
          }
          all += o1 + " " + key + " " + aNull + " " + comment;
//        System.out.println(all);
          insert.insert(value, all.trim());
        }
      }
      insert.of();
//      System.out.println(insert);
      return insert;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 掺入多条数据
   *
   * @param en
   * @throws Exception
   */
  public Insert insertList(List<T> en) {
    try {
      T t = en.get(0);
      Class<?> clazz = t.getClass();
      String tableName = AnnotationProcess.getTableName(clazz);
      Insert insert = new Insert().build(tableName);
      for (T t2 : en) {
        Class<?> aClass = t2.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
          field.setAccessible(true);
          //数据值
          String o1 = field.get(t2) + "";
          Object change = AnnotationProcess.compare(field);
          if (!o1.equals(change)) {
            //获取注解上的数据
            TableField annotation = field.getAnnotation(TableField.class);
            String comment = "", value = "", aNull = "", key = "", all = "";
            if (annotation != null) {
              //字段名
              value = annotation.value() != null ? annotation.value() : field.getName().toLowerCase();
              //注释
              comment = annotation.comment() != null ? annotation.comment() : "";
              //是否为主键
              key = annotation.key() ? "primary key" : "";
              //是否为空
              aNull = annotation.is_null() ? "" : "not null";
            }
            all += o1 + " " + key + " " + aNull + " " + comment;
            insert.insert(all.trim());
          }
        }
        insert.add();
      }
      insert.of();
//      System.out.println(insert);
      return insert;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) throws Exception {
    User1 a = User1.of().setHigh(1.0f).setMoney(1.0).setName("1").setSex(false).setTime(new Date());
    User1 b = User1.of().setHigh(2.0f).setMoney(2).setName("2").setSex(true).setTime(new Date());
    List<User1> l = new ArrayList<>();
    l.add(a);
    l.add(b);
    InsertProcess<User1> insertProcess = new InsertProcess<User1>();
    insertProcess.insertList(l);
    insertProcess.insertOne(b);
  }

}
