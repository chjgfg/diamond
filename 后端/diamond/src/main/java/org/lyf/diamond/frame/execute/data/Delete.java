package org.lyf.diamond.frame.execute.data;

import org.lyf.diamond.frame.execute.Base;

public class Delete implements Base<Delete> {

  private String name = "delete from {0}";

  private String keys = "";

  @Override
  public Delete build(String name) {
    this.name = this.name.replace("{0}", name).toLowerCase();
    return this;
  }

  @Override
  public Delete of() {
    this.name += this.keys + ";";
    return this;
  }

  @Override
  public String toString() {
    return "Delete{" +
        "name='" + name + '\'' +
        '}';
  }

  public Delete where() {
    this.name += " where ";
    return this;
  }

  public Delete and() {
    this.keys += "and ";
    return this;
  }

  public Delete or() {
    this.keys += "or ";
    return this;
  }

  public Delete in(String key, String... values) {
    String k = "( ";
    for (int i = 0; i < values.length; i++) {
      if (i != values.length - 1) {
        k += values[i] + " ,";
      } else {
        k += values[i] + " )";
      }
    }
    this.keys += key + " in " + k;
    return this;
  }

  public Delete eq(String key, String value) {
    String s = key + " = " + value;
    this.keys += s + " ";
    return this;
  }

  public Delete ge(String key, String value) {
    String s = key + " >= " + value;
    this.keys += s + " ";
    return this;
  }

  public Delete le(String key, String value) {
    String s = key + " <= " + value;
    this.keys += s + " ";
    return this;
  }

  public Delete g(String key, String value) {
    String s = key + " > " + value;
    this.keys += s + " ";
    return this;
  }

  public Delete l(String key, String value) {
    String s = key + " < " + value;
    this.keys += s + " ";
    return this;
  }
  public Delete gl(String key, String value) {
    String s = key + " <> " + value;
    this.keys += s + " ";
    return this;
  }
}
