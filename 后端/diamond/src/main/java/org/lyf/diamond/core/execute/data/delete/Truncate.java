package org.lyf.diamond.core.execute.data.delete;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.data.DataFile;

import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:清空表,不删除表结构
 * @author: GG-lyf
 * @create:2022-10-22.1.8 13:10:03
 */
@SuppressWarnings("all")
public class Truncate {

  public String truncate(Matcher mt, String cmd) {
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      return Return.please_point_database;
    }


    String p = cmd.split(" ")[0];


    String tables_name = mt.group(1);
    if (!"".equals(tables_name)) {
      if (tables_name.contains(" , ")) {//清空多个表
        String[] split = tables_name.split(" , ");
        for (String s : split) {

          String[] splits = UseDatabase.getPath().split("\\\\");
          String cheak = UserParse.cheak(Authority.getName(), splits[splits.length - 1], s, p);
          if (!cheak.equals(p)) {
            return cheak;
          }

          boolean table = DataFile.isTable(s);
          if (table) {
            String path = UseDatabase.getPath() + "\\" + s + "\\" + s;
            return DeleteParse.deleteAll(path + ".data");
          } else {
//            System.out.println("table is not exist");
            return Return.table_is_not_exist;
          }
        }
      } else {//清空一个表

        String[] split = UseDatabase.getPath().split("\\\\");
        String cheak = UserParse.cheak(Authority.getName(), split[split.length - 1], tables_name, p);
        if (!cheak.equals(p)) {
          return cheak;
        }

        boolean table = DataFile.isTable(tables_name);
        if (table) {
          String path = UseDatabase.getPath() + "\\" + tables_name + "\\" + tables_name;
          return DeleteParse.deleteAll(path + ".data");
        } else {
//          System.out.println("table is not exist");
          return Return.table_is_not_exist;
        }
      }
    } else {
//      System.out.println("table is not exist");
      return Return.table_is_not_exist;
    }

    return null;
  }

  /**
   *
   * use user1;
   * truncate table user , user;
   */
}
