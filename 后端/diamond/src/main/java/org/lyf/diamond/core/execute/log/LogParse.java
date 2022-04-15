package org.lyf.diamond.core.execute.log;

import ch.qos.logback.core.util.TimeUtil;
import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.entity.data.Log;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.*;

/**
 * @program:IntelliJ IDEA
 * @discription:日志逻辑操作
 * @author: GG-lyf
 * @create:2022-48-22.1.15 17:48:13
 */
@SuppressWarnings("all")
public class LogParse {


  /**
   * 切割,从文件中读出的日志数据
   *
   * @param path
   * @return
   */
  public static List<Log> splitLog(String path) {
    List<String> read = TableDataFile.read(path);
    if (read == null) {
      return null;
    }
    List<Log> log = new ArrayList<Log>();
    read.forEach(vo -> {
      String[] split = vo.split(" -> ");
      log.add(new Log(split[0], split[1], split[2].replace("sql:", "")));
    });
    return log;
  }

  /**
   * 把数据按时间逆序排列
   *
   * @param path
   * @return
   */
  public static List<Log> sort(String path) {
    List<Log> logs = splitLog(path);
    if (logs == null) {
      return null;
    }
    Comparator<Log> comparator = (t1, t2) -> t1.getDate().compareTo(t2.getDate());
    logs.sort(comparator.reversed());
    return logs;
  }

  /**
   * 写数据到日志文件
   *
   * @param path
   * @param condatin
   * @return
   */
  public static boolean insert(String path, String condatin) {
    return TableDataFile.write(path, condatin, true);
  }

  public static void findUser() {
//    Authority.getName()
//    String path = PathConfig.userPath + Authority.getName();
    Map<String, List<Authority>> dd = UserParse.findUser("dd");
    String decode = dd.keySet().iterator().next();
    List<Authority> authorities = dd.get(decode);
    for (Authority authority : authorities) {
      String database = authority.getPurview().get(0);
      String table = authority.getPurview().get(1);
    }
//    System.out.println(UserParse.findUser("dd"));
  }


  public static void main(String[] args) {
    String path1 = "D:\\idea\\idea-workspace\\diamon\\core\\src\\main\\resources\\db\\data\\user1\\user\\user.log";
    String path2 = "D:\\idea\\idea-workspace\\diamon\\core\\src\\main\\resources\\db\\data\\user1\\people\\people.log";
//    System.out.println(splitLog(path1));
    findUser();
//    String condatin = Authority.getName()+ " -> "+TimeUtils.nationalToDate(new Date())+" -> sql:"+"cdscsdcscsdcsdcsdcsd";
//    insert(path1, condatin);
  }


}
