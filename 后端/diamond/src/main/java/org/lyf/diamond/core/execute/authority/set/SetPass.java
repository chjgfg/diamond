package org.lyf.diamond.core.execute.authority.set;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.utile.Base64Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:设置密码
 * @author: GG-lyf
 * @create:2022-34-22.1.13 00:34:48
 */
@SuppressWarnings("all")
public class SetPass {

  public String set(Matcher msp, String cmd) {

    String p = cmd.split(" ")[0];
    String cheak = UserParse.cheak(Authority.getName(), "", "", p);
    if (!cheak.equals(p)) {
      return cheak;
    }


    String name = msp.group(2);//用户
    String pass = msp.group(3);//密码
    if (name == null) {
      name = Authority.getName() + ".user";
    } else {
      name = name + ".user";
    }

    pass = Base64Utils.encode(pass);
    boolean b = updatePassword(name, pass);
//    System.out.println(b);
    return b ? Return.set_password_ok : Return.set_password_error;
  }


  /**
   * 修改密码,当用户是admin时不让他修改,如果用户不存在也不让他修改
   *
   * @param name
   * @param password
   * @return
   */
  private static boolean updatePassword(String name, String password) {
    String path = PathConfig.userPath + name;
    List<String> read = UserParse.read(path);
    List<String> dd = new ArrayList<String>();
    if ("admin.user".equals(name)) {
//      System.out.println("don't update the password");
      return false;
    } else if (read == null) {
      return false;
    }
    String s = read.stream().findFirst().get();
    for (String s1 : read) {
      if (s1.equals(s)) {
        dd.add(password);
      } else {
        dd.add(s1);
      }
    }

    TableDataFile.clearn(path);
    dd.stream().forEach(vo -> {
      TableDataFile.write(path, vo, true);
    });
    return true;
  }


  /**
   set\s+password\s+(for\s+([^\s+]+)\s+)?\=\s+password\(\'(\w+)\'\);
   set password for user = password('newpass');
   set password for 0aaa = password('11112222444555');
   set password for admin = password('11111');
   set password = password('123454');
   set password for ll = password('000000');
   set password for ll = password('111111');
   */
}
