package org.lyf.diamond.frame.process;

import java.lang.reflect.Field;

/**
 * @program: some_middle
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-03 14:15:28
 */
@SuppressWarnings("all")
public class InterfaceProcess {

  public static void interfaceProcess(Class<?> clazz) throws Exception {
    Class<?> aClass = Class.forName(clazz.getName());
    Field[] declaredFields = aClass.getDeclaredFields();
    for (Field declaredField : declaredFields) {
      System.out.println(declaredField.getName());
    }
  }
}
