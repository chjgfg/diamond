package org.lyf.diamond.core.file.table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class TableDataFile {

  /**
   * 插入数据或字典
   *
   * @param path
   * @param data
   * @param flag
   * @return
   */
  public static boolean write(String path, String data, boolean flag) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, flag))) {
      bw.write(data);
      bw.write("\r\n");
      bw.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 读取数据或字典
   *
   * @param path
   * @return
   */
  public static List<String> read(String path) {
    List<String> list = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line;
      while (null != (line = (String) br.readLine())) {
        list.add(line);
      }
      return list;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 清空数据字典
   *
   * @param path
   * @return
   */
  public static boolean clearn(String path) {
    try (FileWriter fileWriter = new FileWriter(path)) {
      fileWriter.write("");
      fileWriter.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
