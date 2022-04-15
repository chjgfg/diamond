package org.lyf.diamond.frame.execute.table;


import org.lyf.diamond.frame.execute.Base;

public class ShowTable implements Base<ShowTable> {

  private String name = "show ";

  @Override
  public ShowTable build(String name) {
    return this;
  }

  @Override
  public ShowTable of() {
    return this;
  }

  @Override
  public String toString() {
    return "ShowTable{" +
        "name='" + name + '\'' +
        '}';
  }

  public ShowTable show(String name) {
    this.name += "table " + name + " ;";
    return this;
  }

  public ShowTable showCreate(String name) {
    this.name += "create table " + name + " ;";
    return this;
  }
}
