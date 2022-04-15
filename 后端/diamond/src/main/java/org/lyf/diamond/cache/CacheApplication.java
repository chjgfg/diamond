package org.lyf.diamond.cache;

import org.lyf.diamond.cache.annotation.Caches;
import org.lyf.diamond.cache.entity.Storage;
import org.lyf.diamond.cache.execute.Cache;
import org.lyf.diamond.cache.utils.CacheUtils;

import java.util.*;

/**
 * @program:IntelliJ IDEA
 * @discription:
 * @author: GG-lyf
 * @create:2022-24-22.2.1 10:24:19
 */
@SuppressWarnings("all")
public class CacheApplication {
  private static int i = 1;

  public static void main(String[] args) throws Exception {
    schedule();
    /*while (true) {

      new Thread(() -> {
        Cache.set("s1", new Storage("China", "1", 1000));
      }, "t1").start();

      new Thread(() -> {
        System.out.println(Cache.get("s1"));
      }, "t1").start();

    }*/
//    System.out.println(set("casdsd"));
  }

  /**
   * 一个SQL,一个方法名
   * 先查,有就返回,无就先填
   */
  @Caches(time = 5000)
  public static Storage set(String sql, String methordName) {
    Storage input = Cache.input(sql, new Storage());
    if (input != null) {
      return input;
    }
    //查
    //查完放
    Cache.set(sql, new Storage(sql, "data", CacheUtils.get(CacheApplication.class, methordName)));
    return Cache.get(sql);
  }


  @Caches(time = 5000)
  public static Storage vdv(String sql, String methordName) {
    Storage input = Cache.input(sql, new Storage());
    if (input != null) {
      return input;
    }
    //查
    //查完放
    Cache.set(sql, new Storage(sql, "data", CacheUtils.get(CacheApplication.class, methordName)));
    return Cache.get(sql);
  }


  private static void modual() throws InterruptedException {
    Cache.set("select * from table", new Storage("China", "1", 1000));
    System.out.println(Cache.get("select * from table"));
    Cache.set("select * from table", new Storage("vsdvsdvsdvdsvdsv", "cascassssssssssssssssssssssssssssssssssssssss", 301));
    System.out.println(Cache.get("select * from table"));
    Thread.sleep(300);
    Cache.remove("select * from table");
    System.out.println(Cache.get("select * from table"));
    System.out.println(Cache.get("select * from table"));
  }

  private static void schedule() {

    set("oo", "set");
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        Cache.set("s" + ++i, new Storage("China", "1", 1000));
//        System.out.println(Cache.get("select * from table"));
        System.out.println(Cache.get("oo"));
        System.out.println(Cache.all());
      }
    }, 1000, 1000);//设定指定任务task在指定延迟delay毫秒后进行固定延迟peroid的执行
  }

}
