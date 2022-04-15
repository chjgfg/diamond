package org.lyf.diamond.core.file.database;

import org.lyf.diamond.core.config.PathConfig;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
@SuppressWarnings("all")
public class DatabaseFile {

  /**
   * 判断库文件是否存在
   *
   * @param name
   * @return
   */
  public static boolean isExists(String name) {
    File file = new File(name);
    return file.exists();
  }

  /**
   * 创建库文件
   *
   * @param name
   * @return
   */
  public static boolean makeDatabase(String name) {
    String path = PathConfig.dataPath + name;
    if (!isExists(path)) {
      File file = new File(path);
      file.mkdir();
      return true;//创建成功
    } else {
      return false;//常见失败
    }
  }


  /**
   * 看所有库
   *
   * @return
   */
  public static List<String> getDatabases() {
    File file = new File(PathConfig.dataPath);
    return Arrays.asList(Objects.requireNonNull(file.list()));
  }

  /**
   * 递归删除某个目录及目录下的所有子目录和文件
   *
   * @param file
   * @return
   */
  private static boolean delFiles(File file) {
    boolean result;
    //目录
    if (file.isDirectory()) {
      File[] childrenFiles = file.listFiles();
      assert childrenFiles != null;
      for (File childFile : childrenFiles) {
        result = delFiles(childFile);
        if (!result) {
          return result;
        }
      }
    }
    //删除 文件、空目录
    result = file.delete();
    return result;
  }


  /**
   * 删库文件
   *
   * @param name
   * @return
   */
  public static boolean dropFiles(String name) {
    String path = PathConfig.dataPath + name;
    if (!isExists(path)) {
      return false;
      //return "isn't";//这个数据库不存在this database is not exist!
    }
    File file = new File(path);
    return delFiles(file);
  }


  /**
   * 重命名库名
   *
   * @param old_name
   * @param new_name
   * @return
   */
  public static boolean renameDatabase(String old_name, String new_name) {
    return new File(PathConfig.dataPath + "\\" + old_name).renameTo(new File(PathConfig.dataPath + "\\" + new_name));
  }


}
