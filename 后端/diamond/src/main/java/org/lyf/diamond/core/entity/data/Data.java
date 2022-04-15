package org.lyf.diamond.core.entity.data;


import java.util.List;
@SuppressWarnings("all")
public class Data implements Comparable<Data>, Cloneable {
  
  private int key;

  private String name;

  private String value;

  private List<Field> fields;//一堆字段是一条数据对象

  public Data() {
  }

  public Data(int key, String name, String value, List<Field> fields) {
    this.key = key;
    this.name = name;
    this.value = value;
    this.fields = fields;
  }

  public Data(int key, List<Field> fields) {
    this.key = key;
    this.fields = fields;
  }

  @Override
  public int compareTo(Data o) {
    //根据key的值升序排列
    return this.key - o.key;
  }

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public Data(List<Field> fields) {
    this.fields = fields;
  }

  public List<Field> getFields() {
    return fields;
  }

  public void setFields(List<Field> fields) {
    this.fields = fields;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public Data clone() {
    try {
      return (Data) super.clone();
    } catch (CloneNotSupportedException e) {
      System.out.println(e);
      return null;
    }
  }

  @Override
  public String toString() {
    return "Data{" +
        "key=" + key +
        ", name='" + name + '\'' +
        ", value='" + value + '\'' +
        ", fields=" + fields +
        '}';
  }
}
