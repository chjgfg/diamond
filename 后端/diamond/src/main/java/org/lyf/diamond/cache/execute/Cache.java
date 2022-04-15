package org.lyf.diamond.cache.execute;

import org.lyf.diamond.cache.entity.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: diamon
 * @description: 缓存
 * @author: GG-lyf
 * @create: 2022-03-07 10:47:19
 */
@SuppressWarnings("all")
public class Cache {

  /**
   * 设置值
   *
   * @param sql
   * @param entity
   */
  public static int set(String sql, Storage entity) {
    return CacheParse.set(sql, entity);
  }

  /**
   * 获取值
   *
   * @param sql
   * @return
   */
  public static Storage get(String sql) {
    return CacheParse.get(sql);
  }

  /**
   * 删除值
   *
   * @param sql
   */
  public static int remove(String sql) {
    return CacheParse.remove(sql);
  }

  public static List<Object> all() {
    return CacheParse.all();
  }


  public static Storage input(String sql, Storage entity) {
    Storage storage = get(sql);
    if (get(sql) == null) {
      CacheParse.set(sql, entity);
      return null;
    } else {
      return storage;
    }
  }


}
