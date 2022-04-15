package org.lyf.diamond.core.entity.data;

import java.io.Serializable;

@SuppressWarnings("all")
public class Field implements Cloneable, Serializable {

  private String name;//字段名

  private String value;//值

  private String concern;//关系

  private String type;//数据类型

  private String is_key;//是否主键

  private String symbol;//符号

//  public Field(String name, String value) {
//    this.name = name;
//    this.value = value;
//  }

  public Field(String name, String concern, String type, String is_key) {
    this.name = name;
    this.concern = concern;
    this.type = type;
    this.is_key = is_key;
  }

  public Field(String name, String type, String is_key) {
    this.name = name;
    this.type = type;
    this.is_key = is_key;
  }

  public Field(String name, String value, String concern, String type, String is_key, String symbol) {
    this.name = name;
    this.concern = concern;
    this.value = value;
    this.type = type;
    this.is_key = is_key;
    this.symbol = symbol;
  }

  public Field() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getConcern() {
    return concern;
  }

  public void setConcern(String concern) {
    this.concern = concern;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getIs_key() {
    return is_key;
  }

  public void setIs_key(String is_key) {
    this.is_key = is_key;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public Field clone() {
    try {
      return (Field) super.clone();
    } catch (CloneNotSupportedException e) {
      System.out.println(e);
      return null;
    }
  }

  @Override
  public String toString() {
    return "Field{" +
        "name='" + name + '\'' +
        ", concern='" + concern + '\'' +
        ", value='" + value + '\'' +
        ", type='" + type + '\'' +
        ", is_key='" + is_key + '\'' +
        ", symbol='" + symbol + '\'' +
        '}';
  }
}
