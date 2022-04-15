package org.lyf.diamond.core.execute.table;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.table.TableFile;

import java.util.regex.Matcher;

/**
 * 这个递归重命名真难写
 */
@SuppressWarnings("all")
public class RenameTable {
  public String rename(Matcher mrt, String cmd) {
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      return Return.please_point_database;
    }

    String old_name = mrt.group(1);

    String p = cmd.split(" ")[0];
    String[] split = UseDatabase.getPath().split("\\\\");
    String cheak = UserParse.cheak(Authority.getName(), split[split.length - 1], old_name, p);
    if (!cheak.equals(p)) {
      return cheak;
    }

    if (!TableFile.judgeTable(old_name)) {
//      System.out.println(old_name + " is not exit");
      return Return.table_is_not_exist;
    }
    String new_name = mrt.group(2);
    if (TableFile.judgeTable(new_name)) {
//      System.out.println(new_name + " is exit");
      return Return.table_is_exist;
    }
    boolean b = TableFile.renameTable(old_name, new_name);
//    System.out.println(b);
    return Return.rename_ok;
  }

  /**
   * use user2;
   * rename table dept to user;
   * rename table user to dept;
   * 
   * 
   * 
   */
}
