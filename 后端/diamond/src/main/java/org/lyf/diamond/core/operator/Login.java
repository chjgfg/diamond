package org.lyf.diamond.core.operator;

import org.lyf.diamond.cache.entity.Storage;
import org.lyf.diamond.cache.execute.Cache;
import org.lyf.diamond.core.entity.auxiliary.Permiss;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.authority.grant.GrantParse;
import org.lyf.diamond.core.utile.Base64Utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @program:IntelliJ IDEA
 * @discription:使用者登录
 * @author: GG-lyf
 * @create:2022-36-22.1.15 21:36:44
 */
@SuppressWarnings("all")
public class Login {

  /**
   * 用户登录
   *
   * @param user
   * @param pass
   * @return
   */
  public static String login(String name, String pass) {
//    System.out.println(name + " " + pass);
    Map<String, List<Authority>> user1 = UserParse.findUser(name);
    if (user1 == null) {
      return Return.user_not_found;
    }

    String decode = user1.keySet().iterator().next();
    List<String> all_pass = new LinkedList<>();
    all_pass.add(Base64Utils.decode(decode));
    if (!(user1.get(decode) == null)) {
      for (Authority authority : user1.get(decode)) {
        all_pass.add(Base64Utils.decode(authority.getPass()));
      }
    }

    Cache.set(name, new Storage(name, user1, 5000));

    for (String allPass : all_pass) {
      if (allPass.equals(pass)) {
        Authority.setName(name);
        return Return.user_log_success;
      }
    }
    return Return.user_password_error;
  }

  /**
   * 检验权限看是否自己有该权限
   *
   * @param user
   * @param sql
   * @return
   */
  public static boolean checkPermission(String user, String sql) {
    int i = sql.indexOf(" ");
    boolean result = false;
    String persession = sql.substring(0, i);
    System.out.println(persession);
    List<String> list = Arrays.asList(persession);
    switch (persession) {
      case Permiss.create:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.use:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.show:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.drop:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.rename:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.alter:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.desc:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.insert:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.delete:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.truncate:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.update:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.select:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.grant:
        return (GrantParse.check(user, list) == true) ? true : false;
      case Permiss.revoke:
        System.out.println(GrantParse.check(user, list));
      case Permiss.set:
        return (GrantParse.check(user, list) == true) ? true : false;
      default:
        return false;
    }
  }


  public static void main(String[] args) {
    String user = "admin";
    String pass = "000001";
    String login = login(user, pass);
//    if (login.equals(Return.user_log_success)) {
//      System.out.println("1");
//    }
    System.out.println(login);
//    

    /**
     * create (database|table|user)3
     * use (table)1
     * show (database|table)2
     * drop (database|table|user)3
     * rename (database|table|user)3
     * alter (table)1
     * desc (table)1
     * insert (table)1
     * delete (table)1
     * truncate (table|log)2
     * update (table)1
     * select (table|log)2
     * grant (user)1
     * revoke (user)1
     * set (user)1
     */


    String sql = "insert user";
    checkPermission(user, sql);
  }


}
