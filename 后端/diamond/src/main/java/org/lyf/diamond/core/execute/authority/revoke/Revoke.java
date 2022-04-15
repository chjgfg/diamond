package org.lyf.diamond.core.execute.authority.revoke;

import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;

import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:收回权限
 * @author: GG-lyf
 * @create:2022-09-22.1.13 00:09:12
 */
@SuppressWarnings("all")
public class Revoke {

  public String revoke(Matcher mra, String cmd) {//1,5,10,13,16

    String p = cmd.split(" ")[0];
    String cheak = UserParse.cheak(Authority.getName(), "", "", p);
    if (!cheak.equals(p)) {
      return cheak;
    }

    String all = mra.group(1);//select , cdcdcd(sno) , update(sno) on database.table , dd.dd |	all privileges , grant option 
    String condition = mra.group(5);//select , cdcdcd(sno) , update(sno)
    String database_and_table = mra.group(10);//database.table , dd.dd
    String users = mra.group(12);//u1 , dd
    if (all.startsWith("all")) {//收取全部的权限
      return RevokeParse.removeAll(users);
    } else {//收取部分权限
      return RevokeParse.removeSome(users, database_and_table, condition);
    }
  }

  /**
   revoke\s+((all\s+privileges\s+,\s+grant\s+option)\s+|((((([^\s]+)*(\s+\,\s+[^\s]+)*?)*)\s+on\s+(([^\s]+(\s+\,\s+)?)*))\s+))from\s+(([^\s]+(\s+\,\s+)?)*);
   revoke select , cdcdcd , update on user1.user , dd.dd from u1 , dd;
   revoke select , vfvfvf , update on user1.user , dd from public;
   revoke insert , vfvf , vdv , gbfb , cdccd , vdfvdv on user1.user from u5 , ff;
   revoke select , create , update on user1.user from yy;
   revoke select , ssss , update on user1.user from yy;
   revoke all privileges , grant option from user , uu;
   revoke show on user1.dept from dd;
   revoke create , use , drop on user1.dept from dd;
   revoke all privileges , grant option from dd;
   revoke all privileges , grant option from aaa;
   */

  /**
   *     if (all.startsWith("all")) {//收取全部的权限
   *       if (cascade != null) {//要级联收取
   *
   *
   *       } else {
   *
   *
   *       }
   *     } else {//收取部分权限
   *       if (cascade != null) {
   *
   *
   *       } else {
   *
   *
   *       }
   *     }
   */

  /**
   * create(database|table|user)3
   * use(table)1
   * show(database|table)2
   * drop(database|table|user)3
   * rename(database|table|user)3
   * alter(table)1
   * desc(table)1
   * insert(table)1
   * delete(table)1
   * truncate(table|log)2
   * update(table)1
   * select(table|log)2
   * grant(user)1
   * revoke(user)1
   * set(user)1
   */


}
