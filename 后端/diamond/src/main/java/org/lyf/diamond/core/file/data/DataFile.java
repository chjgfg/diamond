package org.lyf.diamond.core.file.data;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.execute.database.UseDatabase;

import java.io.File;

/**
 * @program:IntelliJ IDEA
 * @discription:数据文件
 * @author: GG-lyf
 * @create:2022-48-22.1.7 16:48:47
 */
@SuppressWarnings("all")
public class DataFile {

  /**
   * 判断表文件是否存在,包括表的字典文件和数据文件以及日志文件
   *
   * @param name
   * @return
   */
  public static boolean isTable(String name) {
    File file = new File(UseDatabase.getPath() + "\\" + name);
    if (file.exists()) {
      String data = UseDatabase.getPath() + "\\" + name + "\\" + name + ".data";
      String dict = UseDatabase.getPath() + "\\" + name + "\\" + name + ".dict";
      String log = UseDatabase.getPath() + "\\" + name + "\\" + name + ".log";
      if (isFile(data) && isFile(dict) && isFile(log)) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public static boolean isFile(String name) {
    return new File(name).exists();
  }


}
