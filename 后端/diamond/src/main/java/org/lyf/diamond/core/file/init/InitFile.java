package org.lyf.diamond.core.file.init;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.file.table.TableFile;
import org.lyf.diamond.core.utile.Base64Utils;

import java.io.File;

@SuppressWarnings("all")
public class InitFile {

  private static boolean isExists(String name) {
    File file = new File(name);
    return file.exists();
  }

  private static boolean init() {
    File data = new File(PathConfig.initPath);
    if (!isExists(PathConfig.initPath)) {
      data.mkdir();
    }
    return true;
  }

  public static boolean makeDatabase() {
    try {
      int k = 4;
      System.out.print("initializing");
      for (int i = 1; i <= k; i++) {
//        Thread.sleep(800);
        System.out.print(".");
        if (i == k) {
          System.out.print(".");
          System.out.println();
        }
      }
      if (init()) {
        File data = new File(PathConfig.dataPath);
        if (!isExists(PathConfig.dataPath)) {
          data.mkdir();
        }
        File user = new File(PathConfig.userPath);
        if (!isExists(PathConfig.userPath)) {
          user.mkdir();
          String path = user.getPath() + "\\admin.user";
          TableFile.makeDataAndDictFile(path);
          TableDataFile.write(path, Base64Utils.encode("000000"), false);
        }
//        user.setWritable(false, false);
//        user.setReadOnly();
        Thread.sleep(1000);
        System.out.println("initialization succeeded!");
        return true;
      } else {
        System.out.println("initialization failed!");
        return false;
      }
    } catch (Exception e) {
      return false;
    }
  }

}
