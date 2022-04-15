package org.lyf.diamond.core.execute.log;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Log;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.table.TableFile;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:查看日志类
 * @author: GG-lyf
 * @create:2022-24-22.1.13 17:24:48
 */
@SuppressWarnings("all")
public class SelectLog {

  public List<Log> select_log(Matcher msl, String cmd) {

    String table_name = msl.group(2);

    String p = cmd.split(" ")[0];
    String[] split = UseDatabase.getPath().split("\\\\");
    String cheak = UserParse.cheak(Authority.getName(), split[split.length - 1], table_name.replace(";",""), p);
    if (!cheak.equals(p)) {
      List<Log> list = new ArrayList<>();
      list.add(new Log());
      return list;
    }

    List<Log> logs = null;
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      logs.add(new Log(Authority.getName(), TimeUtils.nationalToTimestamp(new Date()), Return.please_point_database));
      return logs;
    }
//    if(Authority.getName().equals("admin")){//执行admin的
//
//    }else{//执行用户的
//
//
//    }
//
    String log = msl.group(1);

    String condation = msl.group(4);
    String sort = msl.group(5);

    String path = UseDatabase.getPath() + "\\" + table_name + "\\" + table_name + ".log";
    if (TableFile.isExists(path) == false) {
//      System.out.println("log is not exist");
      return null;
    }

    if ((condation == null && sort == null) || (condation != null && sort == null)) {
      logs = LogParse.splitLog(path);
    } else if (condation == null && sort != null) {
      return null;
    } else if (condation != null && sort != null) {
      logs = LogParse.sort(path);
    }
//    logs.forEach(System.out::println);
    return logs;
  }
  /**
   * use user1;
   * select log from user order by date asc;
   * select log from user order by ee desc;
   */


}
