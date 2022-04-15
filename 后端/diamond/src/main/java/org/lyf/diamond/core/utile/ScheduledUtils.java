package org.lyf.diamond.core.utile;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.file.table.TableDataFile;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @program:IntelliJ IDEA
 * @discription:定时任务工具类
 * @author: GG-lyf
 * @create:2022-36-22.1.15 19:36:25
 */
@SuppressWarnings("all")
public class ScheduledUtils {


  /**
   * 通过路径找.log的文件,文件大小大于2mb就清空
   */
  private static void scheduleds() {
    File f = new File(PathConfig.dataPath);
    File[] files = f.listFiles();
    if (files.length != 0) {
      for (File file : files) {
        File file1 = new File(file.getPath());
        File[] files1 = file1.listFiles();
        for (File file2 : files1) {
          File file3 = new File(file2.getPath());
          File[] files2 = file3.listFiles();
          for (File file4 : files2) {
            String path = file4.getPath();
            if (path.endsWith("log")) {
              File file5 = new File(path);
              long l = file5.length() / 1024 / 1024;
              if (l >= 2) {
                TableDataFile.clearn(path);
              }
            }
          }
        }
      }
    }
  }

  /**
   * 定时器,10小时执行一次
   */
  public static void scheduled() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        scheduleds();
//        System.out.println(100);
      }
    }, 1000, 1000 * 60 * 60 * 10);//设定指定任务task在指定延迟delay毫秒后进行固定延迟peroid的执行  
  }

  public static void main(String[] args) throws InterruptedException {
//    scheduled();

    scheduled();

  }

}
