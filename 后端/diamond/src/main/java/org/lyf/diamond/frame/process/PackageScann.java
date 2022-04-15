package org.lyf.diamond.frame.process;

import org.lyf.diamond.frame.annotation.PackageScan;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 1.通过类.class拿到这个类上的注解内容,即包路径
 * 2.将包路径中的格式转换为真实的路径
 * 3.使用线程的类加载器加载路径中的资源
 * 4.循环拿到这些资源,即 .class文件,用toURL()获取文件真实路径
 * 5.使用文件过滤器判断文件是以 .class结尾的就返回,自己收集所有的.class文件
 * 6.收集之后就判断是否为文件夹,是就递归
 * 7.不是就判断是否为注解类、枚举类和8大数据类型,是就跳过该循环
 * 8.不是就收集起来
 */

public class PackageScann {
//
//  public static void main(String[] args) throws Exception {
//    System.out.println(scanner(EntityFunctions.class));
//  }


  public static void scanner(Class<?> entity) throws Exception {
    String packageName = entity.getAnnotation(PackageScan.class).packageName();
    List<Class<?>> l = new ArrayList<>();
    String replace = packageName.replace(".", "/");
    Enumeration<URL> r = Thread.currentThread().getContextClassLoader().getResources(replace);
    while (r.hasMoreElements()) {
      URL url = r.nextElement();
      File f = new File(url.toURI());
      if (!f.exists()) {
        continue;
      }
      l = scan(packageName, f);
    }
    System.out.println(l);
    TypeProcess.typePrcess(l);
  }

  private static List<Class<?>> scan(String packageName, File f) throws Exception {
    List<Class<?>> l = new ArrayList<>();
    File[] files = f.listFiles(pathname -> {
      //文件过滤器 FileFilter 的 accept
      if (pathname.isDirectory()) {
        return true;
      }
      //文件结尾是以 .class结尾的就返回
      return pathname.getName().endsWith(".class");
    });
    for (File file : files) {
      if (file.isDirectory()) {
        scan(packageName + "." + file.getName(), file);
      } else {
        String replace = file.getName().replace(".class", "");
        String s = packageName + "." + replace;
        Class<?> clazz = Class.forName(s);
        if (clazz.isAnnotation() || clazz.isEnum() || clazz.isPrimitive()) {
          //注解类,枚举和八大数据类型不扫
          continue;
        }
        l.add(clazz);
      }
    }
    return l;
  }


}
