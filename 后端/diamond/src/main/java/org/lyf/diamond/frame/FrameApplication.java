package org.lyf.diamond.frame;

import org.lyf.diamond.frame.annotation.PackageScan;
import org.lyf.diamond.frame.process.PackageScann;

/**
 * @program: some_middle
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-02 22:14:13
 */
@SuppressWarnings("all")
@PackageScan(packageName = "frame.entity")
public class FrameApplication {

  public static void main(String[] args) throws Exception {
    PackageScann.scanner(FrameApplication.class);
  }


}
