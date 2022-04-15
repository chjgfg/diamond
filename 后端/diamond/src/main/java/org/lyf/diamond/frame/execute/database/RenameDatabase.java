package org.lyf.diamond.frame.execute.database;

import org.lyf.diamond.frame.execute.Base;


public class RenameDatabase implements Base<RenameDatabase> {

  private String name = "rename database {0} to {1};";

  @Override
  public RenameDatabase build(String name) {
    this.name = this.name.replace("{0}", name);
    return this;
  }

  @Override
  public RenameDatabase of() {
    return this;
  }

  @Override
  public String toString() {
    return "RenameDatabase{" +
        "name='" + name + '\'' + '}';
  }

  public RenameDatabase rename(String newName) {
    this.name = this.name.replace("{1}", newName).toLowerCase();
    return this;
  }


}
