package org.lyf.diamond.frame.execute.database;


import org.lyf.diamond.frame.execute.Base;

public class ShowDatabases implements Base<ShowDatabases> {

  private String name = "show databases;";

  @Override
  public ShowDatabases build(String name) {
    return this;
  }

  @Override
  public ShowDatabases of() {
    return this;
  }

  @Override
  public String toString() {
    return "RenameDatabase{" +
        "name='" + name + '\'' + '}';
  }

  public ShowDatabases show() {
    return this;
  }


}
