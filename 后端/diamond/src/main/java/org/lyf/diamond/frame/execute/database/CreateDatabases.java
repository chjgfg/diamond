package org.lyf.diamond.frame.execute.database;

import org.lyf.diamond.frame.execute.Base;


public class CreateDatabases implements Base<CreateDatabases> {

  private String name = "create database {0}";

  private String tables = "";


  @Override
  public CreateDatabases build(String name) {
    this.name = this.name.replace("{0}", name);
    return this;
  }

  @Override
  public CreateDatabases of() {
    this.name += tables;
    return this;
  }

  @Override
  public String toString() {
    return "CreateDatabases{" +
        "name='" + name + '\'' + '}';
  }

  public CreateDatabases create(String... name) {
    for (int i = 0; i < name.length; i++) {
      if (i != name.length - 1) {
        tables += name[i] + " ,";
      } else {
        tables += name[i] + ";";
      }
    }
    return this;
  }


}
