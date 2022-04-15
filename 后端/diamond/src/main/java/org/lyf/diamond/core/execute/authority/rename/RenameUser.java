package org.lyf.diamond.core.execute.authority.rename;

import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;

import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:重命名用户
 * @author: GG-lyf
 * @create:2022-34-22.1.13 21:34:04
 */
@SuppressWarnings("all")
public class RenameUser {

  public String rename_user(Matcher mru, String cmd) {

    String p = cmd.split(" ")[0];
    String cheak = UserParse.cheak(Authority.getName(), "", "", p);
    if (!cheak.equals(p)) {
      return cheak;
    }


    String old_name = mru.group(1);
    String new_name = mru.group(2);
    if (old_name != null && new_name != null) {
      return UserParse.rename(old_name, new_name);
    } else {
//      System.out.println("user' name is null");
      return Return.user_name_is_null;
    }
  }

  /**
   *
   * rename user dept to user;
   */
}
