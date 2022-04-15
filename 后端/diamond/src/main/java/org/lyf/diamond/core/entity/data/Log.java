package org.lyf.diamond.core.entity.data;

import java.io.Serializable;
import java.util.Comparator;
@SuppressWarnings("all")
public class Log implements Cloneable, Serializable, Comparator<Log> {

  private String name;//谁

  private String date;//日期

  private String info;//信息

  public Log() {
  }

  public Log(String name, String date, String info) {
    this.name = name;
    this.date = date;
    this.info = info;
  }

  public Log(String date, String info) {
    this.date = date;
    this.info = info;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  @Override
  public int compare(Log o1, Log o2) {
    return Integer.parseInt(o1.getDate()) - Integer.parseInt(o2.getDate());
  }

  @Override
  public String toString() {
    return "Log{" +
        "name='" + name + '\'' +
        ", date='" + date + '\'' +
        ", info='" + info + '\'' +
        '}';
  }
}
