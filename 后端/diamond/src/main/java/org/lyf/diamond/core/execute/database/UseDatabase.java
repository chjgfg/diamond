package org.lyf.diamond.core.execute.database;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.file.database.DatabaseFile;

import java.util.regex.Matcher;

/**
 * 切换的库路径
 */
public class UseDatabase {

  public static String path = PathConfig.databasePath;

  public static String getPath() {
    return path;
  }

  public static void setPath(String path) {
    UseDatabase.path = path;
  }

  public String use(Matcher mud, String cmd) {
//    System.out.println(mud.group(1));
    String name = mud.group(1);

    String p = cmd.split(" ")[0];
    String cheak = UserParse.cheak(Authority.getName(), name, "", p);
    if (!cheak.equals(p)) {
      return cheak;
    }


    boolean exists = DatabaseFile.isExists(PathConfig.databasePath + name);
//    System.out.println(mud.group(1));
    if (exists) {
      setPath(PathConfig.databasePath + mud.group(1));
      return Return.use_database_ok;
    } else {
//      System.out.println(false);
      return Return.database_not_found;
    }
  }

  /**
   * use user1;
   * use user2;
   */
  
}
