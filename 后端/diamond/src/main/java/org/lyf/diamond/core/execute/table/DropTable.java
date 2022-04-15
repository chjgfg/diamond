package org.lyf.diamond.core.execute.table;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.table.TableFile;

import java.util.regex.Matcher;

/**
 * 这个好写
 */
@SuppressWarnings("all")
public class DropTable {

  public String drop(Matcher mdt, String cmd) {
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      return Return.please_point_database;
    }

    String table_name = mdt.group(1);

    String p = cmd.split(" ")[0];
    String[] splits = UseDatabase.getPath().split("\\\\");


//    System.out.println(table_name);
    if (table_name.contains(" , ")) {//删除多个表
      String[] split = table_name.split(" , ");
      for (String s : split) {

        String cheak = UserParse.cheak(Authority.getName(), splits[splits.length - 1], s, p);
        if (!cheak.equals(p)) {
          return cheak;
        }

        if (!TableFile.judgeTable(s)) {
//          System.out.println(s + " is not exit");
          return Return.table_is_not_exist;
        }
//        System.out.println(s);
        boolean s1 = TableFile.dropTables(UseDatabase.getPath() + "\\" + s);
//        System.out.println(s1);
      }
      return Return.drop_ok;
    } else {//删除一个表

      String cheak = UserParse.cheak(Authority.getName(), splits[splits.length - 1], table_name, p);
      if (!cheak.equals(p)) {
        return cheak;
      }


      if (!TableFile.judgeTable(table_name)) {
//        System.out.println(table_name + " is not exit");
        return Return.table_is_not_exist;
      }
      boolean s1 = TableFile.dropTables(UseDatabase.getPath() + "\\" + table_name);
//      System.out.println(s1);
      return Return.drop_ok;
    }

  }
  /**
   use user1;
   drop table user;
   drop table user , people;
   drop table user , people , dept;
   drop table people , dept;
   drop table people;
   drop table dept;
   */
}
