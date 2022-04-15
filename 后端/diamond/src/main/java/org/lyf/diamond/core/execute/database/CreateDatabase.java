package org.lyf.diamond.core.execute.database;

import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.file.database.DatabaseFile;

import java.util.regex.Matcher;


/**
 * 创建库文件
 */
public class CreateDatabase {

  public String create(Matcher mcd, String cmd) {
    String group = mcd.group(1);

    String p = cmd.split(" ")[0];

    if (group.contains(" , ")) {
      String[] split = group.split(" , ");
      boolean s1 = false;
      for (String s : split) {

        String cheak = UserParse.cheak(Authority.getName(), s, "", p);
        if (!cheak.equals(p)) {
          return cheak;
        }

        s1 = DatabaseFile.makeDatabase(s);
//        System.out.println(s1);
      }
      return s1 ? Return.create_database_ok : Return.create_database_error;
    } else {

      String cheak = UserParse.cheak(Authority.getName(), group, "", p);
      if (!cheak.equals(p)) {
        return cheak;
      }

      boolean s1 = DatabaseFile.makeDatabase(group);
//      System.out.println(s1);
      return s1 ? Return.create_database_ok : Return.create_database_error;
    }
//    return Return.unknown_error;
  }
  //create database user , user1 , user2;


}
