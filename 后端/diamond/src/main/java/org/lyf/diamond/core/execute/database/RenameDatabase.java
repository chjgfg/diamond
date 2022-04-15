package org.lyf.diamond.core.execute.database;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.file.database.DatabaseFile;

import java.util.regex.Matcher;

public class RenameDatabase {

  public String rename(Matcher mrd, String cmd) {


    String g1 = mrd.group(1);
    String g2 = mrd.group(2);

    String p = cmd.split(" ")[0];
    String cheak = UserParse.cheak(Authority.getName(), g1, "", p);
    if (!cheak.equals(p)) {
      return cheak;
    }



    boolean exists = DatabaseFile.isExists(PathConfig.dataPath + "\\" + g1);
    if (exists) {
      boolean b = DatabaseFile.renameDatabase(g1, g2);
//      System.out.println(b);
      return b ? Return.rename_database_ok : Return.rename_database_error;
    } else {
      return Return.database_not_found;
    }
  }
//rename database sss to kk;


}
