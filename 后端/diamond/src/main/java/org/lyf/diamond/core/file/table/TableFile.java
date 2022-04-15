package org.lyf.diamond.core.file.table;

import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.execute.database.UseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("all")
public class TableFile {

  private static int index = 0;//判断递归用

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

  ////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * 创建数据文件和字典文件
   *
   * @param name
   */
  public static boolean makeDataAndDictFile(String name) {
    try {
      return new File(name).createNewFile();
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 创建表
   *
   * @param name
   * @return
   */
  public static boolean makeTable(String path, String name) {
    if (!isExists(path + "\\" + name)) {
      File file = new File(path + "\\" + name);
      file.mkdir();
      String subPath = path + "\\" + name + "\\" + name;
      makeDataAndDictFile(subPath + ".data");
      makeDataAndDictFile(subPath + ".dict");
      makeDataAndDictFile(subPath + ".log");
      return true;//创建成功
    } else {
      return false;//常见失败
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * 递归删除某个目录及目录下的所有子目录和文件
   *
   * @param file
   * @return
   */
  private static boolean delTables(File file) {
    boolean result;
    //目录
    if (file.isDirectory()) {
      File[] childrenFiles = file.listFiles();
      assert childrenFiles != null;
      for (File childFile : childrenFiles) {
        result = delTables(childFile);
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
   * 删表文件
   *
   * @param name
   * @return
   */
  public static boolean dropTables(String name) {
//    System.out.println(name);
    if (!isExists(name)) {
      return false;
    }
    File file = new File(name);
    return delTables(file);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * 重命名表数据文件和表字典
   *
   * @param old_name
   * @param new_name
   * @return
   */
  public static void renameAllFileName(String path, String newName) {
    File file = new File(path);
    File[] files = file.listFiles();
    for (File file2 : files) {
      if (file2.isDirectory()) {
        renameAllFileName(file2.getAbsolutePath(), newName);
      } else {
        String filePath = file2.getAbsolutePath();
        String updFileName = null;
        if (index == 0) {
          updFileName = filePath.substring(0, filePath.lastIndexOf("\\") + 1) + newName + ".data";
          index++;
        } else if (index == 1) {
          updFileName = filePath.substring(0, filePath.lastIndexOf("\\") + 1) + newName + ".dict";
          index++;
        } else {
          updFileName = filePath.substring(0, filePath.lastIndexOf("\\") + 1) + newName + ".log";
          index = 0;
        }
        file2.renameTo(new File(updFileName));
      }
    }
  }

  /**
   * 重命名表文件
   *
   * @param old_name
   * @param new_name
   * @return
   */
  public static boolean renameTable(String old_name, String new_name) {
    String opath = UseDatabase.getPath() + "\\" + old_name;
//    System.out.println(opath);
    String npath = UseDatabase.getPath() + "\\" + new_name;
    renameAllFileName(opath, new_name);//一个路径,一个新文件名
    return new File(opath).renameTo(new File(npath));
  }

  ////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * 获取字典的字段属性
   *
   * @param path
   * @return
   */
  public static Data getDict(String path) {
    Data data = new Data();
    List<String> read = TableDataFile.read(path);
//    System.out.println(read);
    if (read == null) {
      return null;
    }
    List<Field> fields = new ArrayList<>();
    for (String s : read) {
      Field field = new Field();
      String[] s1 = s.split(" ");
      field.setName(s1[0]);
      field.setType(s1[1]);
      field.setIs_key(s1[2]);
      fields.add(field);
    }
    data.setFields(fields);
    return data;
  }

  /**
   * 看一个库的所有表
   *
   * @return
   */
  public static List<String> getTables() {
//    System.out.println(UseDatabase.getPath());
    File file = new File(UseDatabase.getPath());
//    System.out.println(file.getPath());
    return Arrays.asList(Objects.requireNonNull(file.list()));
  }

  /**
   * 判断表是否存在
   */
  public static boolean judgeTable(String name) {
    List<String> tables = getTables();
    for (String table : tables) {
      if (table.equals(name)) {
        return true;
      }
    }
    return false;
  }


}
