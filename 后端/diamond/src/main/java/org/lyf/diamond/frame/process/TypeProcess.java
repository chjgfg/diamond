package org.lyf.diamond.frame.process;

import java.util.List;

/**
 * @program: some_middle
 * @description: 类型\处理
 * @author: GG-lyf
 * @create: 2022-04-03 14:02:54
 */
@SuppressWarnings("all")
public class TypeProcess {

  public static void typePrcess(List<Class<?>> l) throws Exception {
    for (Class<?> clazz : l) {
      if (clazz.isInterface()) {
        //如果是接口
//        InterfaceProcess.interfaceProcess(clazz);
      } else {
        //如果是类
        ClassProcess.createTable(clazz);
      }
//      System.out.println(clazz.isInterface());
//      System.out.println(clazz.getTypeName());
//      System.out.println(clazz.getSimpleName());
    }

  }

}
