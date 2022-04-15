package org.lyf.diamond.core.execute.authority.drop;

import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;

import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:删除用户
 * @author: GG-lyf
 * @create:2022-37-22.1.13 18:37:01
 */
@SuppressWarnings("all")
public class DropUser {

  public String drop_user(Matcher mdu, String cmd) {

    String p = cmd.split(" ")[0];
    String cheak = UserParse.cheak(Authority.getName(), "", "", p);
    if (!cheak.equals(p)) {
      return cheak;
    }


    String names = mdu.group(1);
//    System.out.println(names);
    boolean drop = false;
    if (names.contains(" , ")) {
      String[] split = names.split(" , ");
      for (String s : split) {
        s = s + ".user";
        drop = UserParse.drop(s);
      }
      return drop ? Return.drop_user_ok : Return.drop_user_error;
    } else {
      names = names + ".user";
      drop = UserParse.drop(names);
      return drop ? Return.drop_user_ok : Return.drop_user_error;

    }
  }

  /**
   * drop\s+user\s+(\w+);
   * drop user dcsccsd;
   */
//  public static void main(String[] args) {
//    for (int i = 0; i < 10; i++) {
//      String k = i + "aaa.user";
//      UserParse.drop(k);
//    }
//  }

}
