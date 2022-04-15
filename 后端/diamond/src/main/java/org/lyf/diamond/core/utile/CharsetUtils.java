package org.lyf.diamond.core.utile;

import org.lyf.diamond.core.entity.data.Data;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;

/**
 * @program:IntelliJ IDEA
 * @discription:字符串变成编码比较测试
 * @author: GG-lyf
 * @create:2022-49-22.1.8 16:49:25
 */
@SuppressWarnings("all")
public class CharsetUtils {

  public static double compareTo(String s1, String s2) {
//    System.out.println(charset(s1).compareTo(charset(s2)));
    return Double.parseDouble(s1) - Double.parseDouble(s2);
  }

}
