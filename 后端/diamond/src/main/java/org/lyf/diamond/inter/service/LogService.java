package org.lyf.diamond.inter.service;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Regular;
import org.lyf.diamond.core.entity.data.Log;
import org.lyf.diamond.core.execute.log.LogParse;
import org.lyf.diamond.core.execute.log.SelectLog;
import org.lyf.diamond.core.execute.log.TruncateLog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program:IntelliJ IDEA
 * @discription:日志服务
 * @author: GG-lyf
 * @create:2022-32-22.1.25 17:32:32
 */
@SuppressWarnings("all")
@Service
public class LogService {

//  private static final Pattern select_log = Pattern.compile("select\\s+(log)\\s+from+\\s+([^\\s+]+)(\\s+order\\s+by\\s+(date)\\s+(asc|desc))?;");
//  private static final Pattern truncate_log = Pattern.compile("truncate\\s+log\\s+([^\\s+]+);");

  public List<Log> select(String sql) {
    Matcher mcd = Regular.select_log.matcher(sql + ";");
    SelectLog selectLog = new SelectLog();
    mcd.find();
    return selectLog.select_log(mcd, sql + ";");
  }

  public String truncate(String sql) {
    Matcher mcd = Regular.truncate_log.matcher(sql + ";");
    TruncateLog truncateLog = new TruncateLog();
    mcd.find();
    return truncateLog.truncate_log(mcd, sql + ";");
  }

  public List<Log> findOne(String database, String table) {
    String path = PathConfig.logPath + database + "\\" + table + "\\" + table + ".log";
    return LogParse.splitLog(path);
  }


}
