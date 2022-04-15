package org.lyf.diamond.core.entity.data;

import org.lyf.diamond.core.entity.auxiliary.Dictionary;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("all")
public class Table implements Cloneable, Serializable {
  
  private String tableName;//表名
  
  private Dictionary dictionary;//加载一下字典
  
  private List<Data> data;//数据

  private List<Authority> authorities;
  
  public Table() {
  }

  public Table(String tableName, Dictionary dictionary, List<Data> data, List<Authority> authorities) {
    this.tableName = tableName;
    this.dictionary = dictionary;
    this.data = data;
    this.authorities = authorities;
  }

  public List<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(List<Authority> authorities) {
    this.authorities = authorities;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public Dictionary getDictionary() {
    return dictionary;
  }

  public void setDictionary(Dictionary dictionary) {
    this.dictionary = dictionary;
  }

  public List<Data> getData() {
    return data;
  }

  public void setData(List<Data> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Table{" +
        "tableName='" + tableName + '\'' +
        ", dictionary=" + dictionary +
        ", data=" + data +
        ", authorities=" + authorities +
        '}';
  }
}
