package org.lyf.diamond.core.entity.data;

import org.lyf.diamond.core.entity.data.Table;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("all")
public class Database implements Cloneable, Serializable {

  private String databaseName;//数据库名

  private List<Table> tables;//数据库之下所有的表的集合

  public Database(String databaseName) {
    this.databaseName = databaseName;
  }

  public Database() {
  }

  public List<Table> getTables() {
    return tables;
  }

  public void setTables(List<Table> tables) {
    this.tables = tables;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  @Override
  public String toString() {
    return "Database{" +
        "databaseName='" + databaseName + '\'' +
        ", tables=" + tables +
        '}';
  }
}
