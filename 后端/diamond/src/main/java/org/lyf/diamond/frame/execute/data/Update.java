package org.lyf.diamond.frame.execute.data;


import org.lyf.diamond.frame.execute.Base;

public class Update implements Base<Update> {

  private String name = "update {0} set {1}";
//  private String name = "update {0} set {1} where {2}";

  private String values = "";

  private String keys = "";

  @Override
  public Update build(String name) {
    this.name = this.name.replace("{0}", name).toLowerCase();
    return this;
  }


  @Override
  public Update of() {
    int i = this.values.lastIndexOf(" ,");
    this.values = this.values.substring(0, i);
    this.name = this.name.replace("{1}", this.values);
    this.name += this.keys + ";";
    return this;
  }

  @Override
  public String toString() {
    return "Update{" +
        "name='" + name + '\'' +
        '}';
  }

  public Update set(String value) {
    this.values += value + " ,";
    return this;
  }

  public Update where() {
    this.name += " where ";
    return this;
  }

  public Update and() {
    this.keys += "and ";
    return this;
  }

  public Update or() {
    this.keys += "or ";
    return this;
  }

  public Update in(String key, String... values) {
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

  public Update eq(String key, String value) {
    String s = key + " = " + value;
    this.keys += s + " ";
    return this;
  }

  public Update ge(String key, String value) {
    String s = key + " >= " + value;
    this.keys += s + " ";
    return this;
  }

  public Update le(String key, String value) {
    String s = key + " <= " + value;
    this.keys += s + " ";
    return this;
  }

  public Update g(String key, String value) {
    String s = key + " > " + value;
    this.keys += s + " ";
    return this;
  }

  public Update l(String key, String value) {
    String s = key + " < " + value;
    this.keys += s + " ";
    return this;
  }
  public Update gl(String key, String value) {
    String s = key + " <> " + value;
    this.keys += s + " ";
    return this;
  }
}
