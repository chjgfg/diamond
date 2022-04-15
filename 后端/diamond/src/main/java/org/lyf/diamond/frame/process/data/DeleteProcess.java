package org.lyf.diamond.frame.process.data;


import org.lyf.diamond.frame.entity.User1;
import org.lyf.diamond.frame.execute.data.Delete;
import org.lyf.diamond.frame.process.AnnotationProcess;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @program: some_middle
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-04 13:31:33
 */
@SuppressWarnings("all")
public class DeleteProcess<T> {

  /**
   * 删除通过id
   *
   * @param t
   * @return
   */
  public Delete deleteOneById(T t) {
    try {
      Class<?> clazz = t.getClass();
      String tableName = AnnotationProcess.getTableName(clazz);
      Field[] fields = clazz.getDeclaredFields();
      Delete delete = new Delete().build(tableName).where();
      String id = "";
      boolean flag = false;
      for (Field field : fields) {
        field.setAccessible(true);
        //值
        String o1 = field.get(t) + "";
        String name = AnnotationProcess.getFieldName(field);
        Object change = AnnotationProcess.compare(field);
        if (!o1.equals(change)) {
          if (name.equals("id")) {
            delete.eq("id", o1);
            flag = true;
          }
        }
      }
      if (!flag) {
        return null;
      }
      delete.of();
      System.out.println(delete);
      return delete;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 删除通过传入的条件
   *
   * @param t
   * @param field
   * @param operation
   * @return
   */
  public Delete deleteByCondition(T t, String field, String operation, String condition) {
    try {
      Class<?> clazz = t.getClass();
      String tableName = AnnotationProcess.getTableName(clazz);
      Field[] fields = clazz.getDeclaredFields();
      Delete delete = new Delete().build(tableName).where();
      boolean flag = false;
      for (Field attr : fields) {
        attr.setAccessible(true);
        //值
        String o1 = attr.get(t) + "";
        String name = AnnotationProcess.getFieldName(attr);
        Object change = AnnotationProcess.compare(attr);
        if (!o1.equals(change)) {
          if (name.equals(field)) {
            flag = true;
          }
        }
      }
      if (!flag) {
        return null;
      }
      if (">".equals(operation)) {
        delete.g(field, condition);
      } else if ("=".equals(operation)) {
        delete.eq(field, condition);
      } else if ("<".equals(operation)) {
        delete.l(field, condition);
      } else if (">=".equals(operation)) {
        delete.ge(field, condition);
      } else if ("<=".equals(operation)) {
        delete.le(field, condition);
      } else if ("<>".equals(operation)) {
        delete.gl(field, condition);
      }
      delete.of();
      System.out.println(delete);
      return delete;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 删除通过批量的条件
   *
   * @param t
   * @param key
   * @param values
   * @return
   */
  public Delete deleteSomeByFields(T t, String key, String... values) {
    try {
      Class<?> clazz = t.getClass();
      String tableName = AnnotationProcess.getTableName(clazz);
      Field[] fields = clazz.getDeclaredFields();
      Delete delete = new Delete().build(tableName).where();
      String up = "";
      boolean flag = false;
      for (Field field : fields) {
        field.setAccessible(true);
        //值
        String o1 = field.get(t) + "";
        String name = AnnotationProcess.getFieldName(field);
        Object change = AnnotationProcess.compare(field);
        if (!o1.equals(change)) {
          if (name.equals(key)) {
            flag = true;
          }
        }
      }
      if (!flag) {
        return null;
      }
      delete.in(key, values).of();
      return delete;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  public static void main(String[] args) throws Exception {
    User1 a = User1.of().setId(1).setHigh(1.0f).setMoney(1.0).setName("1").setSex(false).setTime(new Date());
    DeleteProcess<User1> insertProcess = new DeleteProcess<User1>();
    insertProcess.deleteByCondition(a, "name", "<", "2");

  }


}
