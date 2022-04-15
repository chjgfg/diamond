package org.lyf.diamond.core.entity.data;

import java.io.Serializable;
import java.util.List;
@SuppressWarnings("all")
public class Authority implements Serializable, Cloneable {
  private static final long serialVersionUID = 4122974131420281791L;
  private static String name;//现在登录的这个用户,方便用
  private String user;//用户名
  private String authority;//现在登录的这个用户
  private String pass;
  private List<String> purview;
  private String ok;//权限是否可以继承

  public Authority() {
  }

  public Authority(String user, String authority, String pass, List<String> purview, String ok) {
    this.pass = pass;
    this.user = user;
    this.purview = purview;
    this.ok = ok;
    this.authority = authority;
    Authority.name = authority;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }


  public String getOk() {
    return ok;
  }

  public void setOk(String ok) {
    this.ok = ok;
  }

  public static String getName() {
    return name;
//    return "admin";
  } //"admin"

  public static void setName(String name) {
    Authority.name = name;
  }

  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public List<String> getPurview() {
    return purview;
  }

  public void setPurview(List<String> purview) {
    this.purview = purview;
  }
  @Override
  public Authority clone() {
    try {
      return (Authority) super.clone();
    } catch (CloneNotSupportedException e) {
      System.out.println(e);
      return null;
    }
  }

  @Override
  public String toString() {
    return "Authority{" +
        "user='" + user + '\'' +
        ", authority='" + authority + '\'' +
        ", pass='" + pass + '\'' +
        ", purview=" + purview +
        ", ok='" + ok + '\'' +
        '}';
  }
}
