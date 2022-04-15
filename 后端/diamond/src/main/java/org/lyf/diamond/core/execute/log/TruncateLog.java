package org.lyf.diamond.core.execute.log;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.file.table.TableFile;

import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:清空日志类
 * @author: GG-lyf
 * @create:2022-25-22.1.13 17:25:35
 */
@SuppressWarnings("all")
public class TruncateLog {

  public String truncate_log(Matcher mtl, String cmd) {

    String table_name = mtl.group(1);


    String p = cmd.split(" ")[0];
    String[] splits = UseDatabase.getPath().split("\\\\");
    String cheak = UserParse.cheak(Authority.getName(), splits[splits.length - 1], table_name, p);
    if (!cheak.equals(p)) {
      return cheak;
    }



//    String path = UseDatabase.getPath() + "\\" + table_name + "\\" + table_name + ".log";
    String[] split = table_name.split("\\.");
    String path = PathConfig.logPath+ "\\" + split[0] + "\\" + split[1] + "\\" + split[1] + ".log";
    if (!TableFile.isExists(path)) {
//      System.out.println("log is not exist");
      return Return.log_is_not_exist;
    }
    boolean clearn = TableDataFile.clearn(path);
//    System.out.println(clearn);
    return clearn? Return.clearn_log_ok:Return.clearn_log_error;
  }

  /**
   * truncate log mysql.general_log;
   */
}
