package org.lyf.diamond.cache.entity;

import org.lyf.diamond.core.utile.TimeUtils;

import java.util.Date;

/**
 * @program:IntelliJ IDEA
 * @discription:缓存实体类
 * @author: GG-lyf
 * @create:2022-29-22.2.1 12:29:04
 */
@SuppressWarnings("all")
public class Storage {

  private String key;//缓存ID
  private Object value;//缓存数据
  //更新时间,毫秒
  private long timeOut = Long.parseLong(TimeUtils.nationalToTimestamp(new Date()));

  public Storage() {
  }

  public Storage(String key) {
    this.key = key;
  }

  public Storage(Object value) {
    this.value = value;
  }

  public Storage(long timeOut) {
    this.timeOut += timeOut;
  }

  public Storage(String key, Object value) {
    this.key = key;
    this.value = value;
  }

  public Storage(String key, long timeOut) {
    this.key = key;
    this.timeOut += timeOut;
  }

  public Storage(Object value, long timeOut) {
    this.value = value;
    this.timeOut += timeOut;
  }

  public Storage(String key, Object value, long timeOut) {
    this.key = key;
    this.value = value;
    this.timeOut += timeOut;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public long getTimeOut() {
    return timeOut;
  }

  public void setTimeOut(long timeOut) {
    this.timeOut += timeOut;
  }

  @Override
  public String toString() {
    return "Cache{" +
        "key='" + key + '\'' +
        ", value=" + value +
        ", timeOut=" + timeOut +
        '}';
  }
}
