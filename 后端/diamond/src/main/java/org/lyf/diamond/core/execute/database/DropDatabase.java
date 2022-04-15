package org.lyf.diamond.core.execute.database;

import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.file.database.DatabaseFile;

import java.util.regex.Matcher;

/**
 * 删除库文件
 */
public class DropDatabase {
  public String drop(Matcher mdd, String cmd) {

    String p = cmd.split(" ")[0];


    String group = mdd.group(1);
    if (group.contains(" , ")) {
      String[] split = group.split(" , ");
      for (String s : split) {

        String cheak = UserParse.cheak(Authority.getName(), s, "", p);
        if (!cheak.equals(p)) {
          return cheak;
        }

        boolean s1 = DatabaseFile.dropFiles(s);
//        System.out.println(s1);
        return s1 ? Return.drop_database_ok : Return.drop_database_error;
      }
    } else {


      String cheak = UserParse.cheak(Authority.getName(), group, "", p);
      if (!cheak.equals(p)) {
        return cheak;
      }


      boolean s1 = DatabaseFile.dropFiles(group);
//      System.out.println(s1);
      return s1 ? Return.drop_database_ok : Return.drop_database_error;
    }
    return Return.unknown_error;
  }
}
