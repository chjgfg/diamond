package org.lyf.diamond.frame.process;

import java.lang.reflect.Field;

/**
 * @program: some_middle
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-03 14:15:13
 */
@SuppressWarnings("all")
public class ClassProcess {

  /**
   * 1.获取表名
   *
   * @param clazz
   * @throws Exception
   */
  public static void classProcess(Class<?> clazz) throws Exception {
    String name = clazz.getSimpleName();
    System.out.println(name);
    Class<?> aClass = Class.forName(clazz.getName());
    Object o = aClass.newInstance();
    Field[] declaredFields = aClass.getDeclaredFields();
    for (Field declaredField : declaredFields) {
      declaredField.setAccessible(true);

      System.out.println(declaredField.getType());
    }
  }


  public static void createTable(Class<?> clazz) throws Exception {
    String tableName = AnnotationProcess.getTableName(clazz);
    System.out.println(tableName);
    Class<?> aClass = Class.forName(clazz.getName());
    Object o = aClass.newInstance();
    Field[] declaredFields = aClass.getDeclaredFields();
    for (Field declaredField : declaredFields) {
      declaredField.setAccessible(true);
      String tableField = AnnotationProcess.getTableField(declaredField);
      System.out.println(tableField);
//      System.out.println(declaredField.getType());
    }
  }


}
