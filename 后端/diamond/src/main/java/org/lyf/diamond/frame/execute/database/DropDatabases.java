package org.lyf.diamond.frame.execute.database;

import org.lyf.diamond.frame.execute.Base;


public class DropDatabases implements Base<DropDatabases> {

  private String name = "drop database {0}";

  private String tables = "";


  @Override
  public DropDatabases build(String name) {
    this.name = this.name.replace("{0}", name);
    return this;
  }

  @Override
  public DropDatabases of() {
    this.name += tables;
    return this;
  }

  @Override
  public String toString() {
    return "DropDatabases{" +
        "name='" + name + '\'' + '}';
  }

  public DropDatabases drop(String... name) {
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
