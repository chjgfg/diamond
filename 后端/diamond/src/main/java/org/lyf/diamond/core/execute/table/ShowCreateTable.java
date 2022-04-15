package org.lyf.diamond.core.execute.table;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.table.TableFile;

import java.util.List;
import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:看建表语句
 * @author: GG-lyf
 * @create:2022-12-22.1.3 16:12:10
 */
@SuppressWarnings("all")
public class ShowCreateTable {

  public String show_create(Matcher msct, String cmd) {
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      return Return.please_point_database;
    }

    String table_name = msct.group(1);

    String p = cmd.split(" ")[0];
    String[] split = UseDatabase.getPath().split("\\\\");
    String cheak = UserParse.cheak(Authority.getName(), split[split.length - 1], table_name, p);
    if (!cheak.equals(p)) {
      return cheak;
    }

//    System.out.println(table_name);
    if (!TableFile.judgeTable(table_name)) {
//      System.out.println(table_name + " is not exit");
      return Return.table_is_not_exist;
    }
    Data dict = TableFile.getDict(UseDatabase.getPath() + "\\" + table_name + "\\" + table_name + ".dict");
    if (dict ==null){
      return Return.table_is_not_exist;
    }
    List<Field> fields = dict.getFields();
    String s = "create table {0} ({1});", s1 = "";
    for (int i = 0; i < fields.size(); i++) {
      if (i > 0 && i < fields.size()) {
        s1 += " ";
      }
      s1 += fields.get(i).getName() + " " + fields.get(i).getType();
      if ("*".equals(fields.get(i).getIs_key())) {
        s1 += " primary key,";
      } else {
        if (!(i == fields.size() - 1)) {
          s1 += ",";
        }
      }
    }
    String replace = s.replace("{0}", table_name).replace("{1}", s1);
//    System.out.println(replace);
    return replace;
  }

  /**
   * use user2;
   * show create table dept \G;
   */
}
