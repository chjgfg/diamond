package org.lyf.diamond.cache.execute;

import org.lyf.diamond.cache.entity.Storage;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: diamon
 * @description: 缓存处理类
 * @author: GG-lyf
 * @create: 2022-03-07 11:06:28
 */
@SuppressWarnings("all")
public class CacheParse {

  private volatile static ConcurrentHashMap cache = new ConcurrentHashMap<>();

  /**
   * 获取一个
   *
   * @param sql
   * @return
   */
  private synchronized static Storage getOne(String sql) {
    return (Storage) cache.get(sql);
  }

  /**
   * 设置一个
   *
   * @param sql
   * @param entity
   * @return
   */
  private synchronized static int setOne(String sql, Storage entity) {
    cache.put(sql, entity);
    return 1;
  }


  /**
   * 删除一个
   *
   * @param sql
   * @return
   */
  private synchronized static int removeOne(String sql) {
    cache.remove(sql);
    return 1;
  }

  /**
   * 替换一个
   *
   * @param sql
   * @param entity
   * @return
   */
  private static int replaceOne(String sql, Storage entity) {
    cache.replace(sql, entity);
    return 1;
  }

  /**
   * 检查一个是不是存在
   *
   * @param sql
   * @return
   */
  private static int hasOne(String sql) {
    boolean b = cache.containsKey(sql);
    if (b == false) {
      cache.remove(sql);
      return 1;//键不存在
    } else {
      boolean b1 = cache.containsValue(cache.get(sql));
      if (b1) {
        return 2;//键和值都存在
      } else {
        return 3;//值不存在
      }
    }
  }

  /**
   * 检查是否超时
   * 1.直接删
   * 2.未超时
   * 3.超时
   *
   * @param sql
   * @return
   */
  public static int check(String sql) {
    if (hasOne(sql) == 1) {
      return 1;//直接删
    } else if (hasOne(sql) == 2) {
      long timeOut = getOne(sql).getTimeOut();
      long now = Long.parseLong(TimeUtils.nationalToTimestamp(new Date()));
      if (timeOut <= 0) {
        return 1;//直接删
      } else if (now <= timeOut) {//未超时
        return 2;
      } else {
        return 3;//超时可以删了
      }
    } else { //if (hasOne(sql) == 3)
      return 1;//直接删
    }
  }


  /**
   * 获取
   *
   * @param sql
   * @return
   */
  public static Storage get(String sql) {
    if (check(sql) == 1 || check(sql) == 3) {
      remove(sql);
      return null;
    } else {
      return getOne(sql);
    }
  }

  /**
   * 设置值
   *
   * @param sql
   * @param entity
   * @return
   */
  public synchronized static int set(String sql, Storage entity) {
    tidy();
    if (hasOne(sql) == 1) {//存入
      return setOne(sql, entity);
    } else if (hasOne(sql) == 2 || hasOne(sql) == 3) {
      return replaceOne(sql, entity);
    } else {
      return 0;
    }
  }

  /**
   * 判断是否超时
   *
   * @param sql
   * @return
   */
  public synchronized static int remove(String sql) {
    int check = check(sql);
    if (check == 1 || check == 3) {//其他或超时,直接删
      return removeOne(sql);
    } else {
      return 0;//未超时,未删除
    }
  }


  /**
   * 清理值为空的键
   */
  private static void tidy() {
    ConcurrentHashMap.KeySetView keySetView = cache.keySet();
    for (Object o : keySetView) {
      remove((String) o);
    }
  }

  /**
   * 查找所有
   *
   * @return
   */
  public static List<Object> all() {
    ConcurrentHashMap.KeySetView keySetView = cache.keySet();
    return new ArrayList<>(keySetView);
  }

}
