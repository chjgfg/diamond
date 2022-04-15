package org.lyf.diamond.core.execute.authority.create;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.file.table.TableFile;
import org.lyf.diamond.core.utile.Base64Utils;

import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:创建用户
 * @author: GG-lyf
 * @create:2022-42-22.1.13 18:42:36
 */
@SuppressWarnings("all")
public class CreateUser {

  public String create_user(Matcher mcu, String cmd) {

    String p = cmd.split(" ")[0];
    String cheak = UserParse.cheak(Authority.getName(), "", "", p);
    if (!cheak.equals(p)) {
      return cheak;
    }

    String name = mcu.group(1);
    String pass = mcu.group(2).replace("'", "");



    boolean exists = TableFile.isExists(PathConfig.userPath + name + ".user");
    if (exists) {
      return Return.user_is_exist;
    } else {
      boolean b = UserParse.create(name + ".user", pass != null ? Base64Utils.encode(pass) : Base64Utils.encode("000000"));
      return b ? Return.create_user_ok : Return.create_user_error;
    }
  }

  /**
   * create\s+user\s+(\w+)\s+identified\s+by\s+([^\s+]+);
   * create user aaa identified by 'vdddvd';
   * create user bbb identified by 'vdddvd';
   * create user ccc identified by '111111';
   * create user ddd identified by 'vdddvd';
   */


  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      UserParse.create(i + "aaa.user", Base64Utils.encode("111111"));
    }

  }


}
