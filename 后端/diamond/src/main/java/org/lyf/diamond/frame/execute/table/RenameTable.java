package org.lyf.diamond.frame.execute.table;


import org.lyf.diamond.frame.execute.Base;

public class RenameTable implements Base<RenameTable> {

  private String name = "alter table {0} rename to {1} ;";

  @Override
  public RenameTable build(String oldName) {
    this.name = this.name.replace("{0}", oldName);
    return this;
  }

  @Override
  public RenameTable of() {
    return this;
  }

  @Override
  public String toString() {
    return "RenameTable{" +
        "name='" + name + '\'' +
        '}';
  }

  public RenameTable rename(String newName) {
    this.name = this.name.replace("{1}", newName);
    return this;
  }
}
