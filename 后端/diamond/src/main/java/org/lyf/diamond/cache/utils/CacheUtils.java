package org.lyf.diamond.cache.utils;

import org.lyf.diamond.cache.annotation.Caches;

import java.lang.reflect.Method;

/**
 * @program: diamond
 * @description: cache工厂
 * @author: GG-lyf
 * @create: 2022-03-18 09:32:07
 */
@SuppressWarnings("all")
public class CacheUtils {

  public static boolean check(String name) {
    if (name.equals("wait") || name.equals("equals") || name.equals("toString") || name.equals("hashCode") || name.equals("getClass") || name.equals("notify") || name.equals("notifyAll") || name.equals("main")) {
      return true;
    } else {
      return false;
    }
  }


  public static int get(Class<?> someClass, String methordName) {
    Method[] methods = someClass.getMethods();
    for (Method method : methods) {
      if (methordName.equals(method.getName())) {
        Caches annotation = method.getAnnotation(Caches.class);
        return annotation.time();
      }
    }
    return 1000;
  }


}
