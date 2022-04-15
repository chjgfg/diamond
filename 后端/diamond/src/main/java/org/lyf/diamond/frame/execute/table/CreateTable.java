package org.lyf.diamond.frame.execute.table;


import org.lyf.diamond.frame.execute.Base;

public class CreateTable implements Base<CreateTable> {
  private String name = "create table {0} (";

  @Override
  public CreateTable build(String name) {
    this.name = this.name.replace("{0}", name);
    return this;
  }

  @Override
  public CreateTable of() {
    int i = this.name.lastIndexOf(" ,");
    this.name = this.name.substring(0, i) + " );";
    return this;
  }


  @Override
  public String toString() {
    return "CreateTable{" +
        "name='" + name + '\'' +
        '}';
  }

  public CreateTable create(String field) {
    this.name = this.name + " " + field + " ,";
    return this;
  }


}
