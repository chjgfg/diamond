package org.lyf.diamond.frame.execute.data;

import org.lyf.diamond.frame.execute.Base;

public class Select implements Base<Select> {

  private String name = "select {-1} from {0}";

  private String keys = "";

  private String tables = "";

  private String link = "";

  private String where = "";

  private String condition = "";


  @Override
  public Select build(String name) {
    tables += name;
    return this;
  }

  public Select tables(String... name) {
    for (int i = 0; i < name.length; i++) {
      if (i != name.length - 1) {
        tables += " ," + name[i];
      }
    }

    return this;
  }


  @Override
  public Select of() {
    if (!condition.equals("")) {
      int i = condition.indexOf(",");
      condition = condition.substring(i + 1, condition.length());
      this.name = this.name.replace("{-1}", condition);
    } else {
      this.name = this.name.replace("{-1}", "*");
    }
    this.name = this.name.replace("{0}", tables);
    if (link != null) {
      this.name += link;
    }
    if (where != null) {
      this.name += where;
    }
    this.name += keys + ";";
    return this;
  }

  @Override
  public String toString() {
    return "Select{" +
        "name='" + name + '\'' +
        '}';
  }

  public Select count(String field) {
    this.condition += " ,count( " + field + " )";
    return this;
  }

  public Select max(String field) {
    this.condition += " ,max( " + field + " )";
    return this;
  }

  public Select min(String field) {
    this.condition += " ,min( " + field + " )";
    return this;
  }

  public Select avg(String field) {
    this.condition += " ,avg( " + field + " )";
    return this;
  }

  public Select length(String field) {
    this.condition += " ,length( " + field + " )";
    return this;
  }


  public Select left(String left, String right) {
    this.link += " left join " + left + " = " + right;
    return this;
  }

  public Select right(String left, String right) {
    this.link += " right join " + left + " = " + right;
    return this;
  }

  public Select inner(String left, String right) {
    this.link += " inner join " + left + " = " + right;
    return this;
  }


  public Select where() {
    this.where = " where ";
    return this;
  }

  public Select and() {
    this.keys += "and ";
    return this;
  }

  public Select or() {
    this.keys += "or ";
    return this;
  }

  public Select in(String key, String... values) {
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

  public Select eq(String key, String value) {
    String s = key + " = " + value;
    this.keys += s + " ";
    return this;
  }

  public Select ge(String key, String value) {
    String s = key + " >= " + value;
    this.keys += s + " ";
    return this;
  }

  public Select le(String key, String value) {
    String s = key + " <= " + value;
    this.keys += s + " ";
    return this;
  }

  public Select g(String key, String value) {
    String s = key + " > " + value;
    this.keys += s + " ";
    return this;
  }

  public Select l(String key, String value) {
    String s = key + " < " + value;
    this.keys += s + " ";
    return this;
  }

  public Select gl(String key, String value) {
    String s = key + " <> " + value;
    this.keys += s + " ";
    return this;
  }

  public Select group(String... key) {
    String s = "group by ";
    if (key.length == 1) {
      s += key[0];
    } else {
      s += key[0] + " " + key[1] + " " + key[2];
    }
    this.keys += s + " ";
    return this;
  }


  public Select order(String... key) {
    String s = "order by ";
    if (key.length == 1) {
      s += key[0];
    } else {
      s += key[0] + " " + key[1];
    }
    this.keys += s + " ";
    return this;
  }

  public Select like(String key, String value) {
    String s = key + " like %" + value + "%";
    this.keys += s + " ";
    return this;
  }

  public Select limit(String field, String... key) {
    String s = field + " limit ";
    if (key.length == 1) {
      s += key[0];
    } else {
      s += key[0] + " ," + key[1];
    }
    this.keys += s + " ";
    return this;
  }

}
