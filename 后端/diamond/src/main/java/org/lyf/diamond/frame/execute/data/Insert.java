package org.lyf.diamond.frame.execute.data;

import org.lyf.diamond.frame.execute.Base;

public class Insert implements Base<Insert> {

  private String name = "insert into {0} {1} values ( {2} );";

  private String keys = "";

  private String values = "";

  @Override
  public Insert build(String name) {
    this.name = this.name.replace("{0}", name);
    return this;
  }

  @Override
  public Insert of() {
    int k = 0;
    if (this.keys != null) {
      int i = this.keys.lastIndexOf(" ,");
      this.keys = this.keys.substring(0, i);
      this.keys = "( " + this.keys + " )";
      this.name = this.name.replace("{1}", keys);
      k = this.values.lastIndexOf(" ,");
    } else {
      this.name = this.name.replace("{1}", "");
      k = this.values.lastIndexOf(" )");
    }
    this.values = this.values.substring(0, k);
    this.name = this.name.replace("{2}", values);
    return this;
  }

  @Override
  public String toString() {
    return "Insert{" +
        "name='" + name + '\'' +
        '}';
  }

  public Insert insert(String key, String value) {
    this.keys += key + " ,";
    this.values += value + " ,";
    return this;
  }

  public Insert insert(String value) {
    this.keys = null;
    this.values += value + " ,";
    return this;
  }

  public Insert add() {
    int i = this.values.lastIndexOf(" ,");
    this.values = this.values.substring(0, i) + " ) , ( ";
    return this;
  }
}
