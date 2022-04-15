package org.lyf.diamond.frame.execute.table;

import org.lyf.diamond.frame.execute.Base;


public class DropTables implements Base<DropTables> {

  private String name = "drop table ";

  @Override
  public DropTables build(String name) {
    this.name +=  name +" ,";
    return this;
  }

  @Override
  public DropTables of() {
    int i = this.name.lastIndexOf(" ,");
    this.name = this.name.substring(0, i) + " ;";
    return this;
  }

  @Override
  public String toString() {
    return "DropTables{" +
        "name='" + name + '\'' +
        '}';
  }

  public DropTables drop(String name) {
    this.name +=  name +" ,";
    return this;
  }


}
