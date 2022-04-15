package org.lyf.diamond.frame.execute.database;


import org.lyf.diamond.frame.execute.Base;

public class UseDatabase implements Base<UseDatabase> {

  private String name = "use {0};";

  @Override
  public UseDatabase build(String name) {
    return this;
  }

  @Override
  public UseDatabase of() {
    return this;
  }

  @Override
  public String toString() {
    return "RenameDatabase{" +
        "name='" + name + '\'' + '}';
  }

  public UseDatabase use(String newName) {
    this.name = this.name.replace("{0}", newName);
    return this;
  }

}
