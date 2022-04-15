package org.lyf.diamond.core.utile;

/**
 * @program:IntelliJ IDEA
 * @discription:判断字符串是否为数字
 * @author: GG-lyf
 * @create:2022-51-22.1.20 17:51:40
 */
@SuppressWarnings("all")
public class NumberUtils {


  public static void main(String[] args) {
    String s1 = "-123";
    String s2 = "123.345";
    String s3 = "scscds.345";

    System.out.println(match(s1));
    System.out.println(match(s2));
    System.out.println(match(s3));

  }

  public static boolean match(String s) {
    boolean flag = false;
    if (s.contains("-")) {
      s = s.replace("-", "");
    }
    if (s.matches("^[0-9]+$")) {//是否为整数
      flag = true;
    } else if (s.matches("\\d+.\\d+")) {//是否为小数
      flag = true;
    } else {
      flag = false;
    }
    return flag;
  }
  
}
