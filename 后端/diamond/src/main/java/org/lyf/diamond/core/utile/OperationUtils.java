package org.lyf.diamond.core.utile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program:IntelliJ IDEA
 * @discription:封装的运算用的一些函数工具
 * @author: GG-lyf
 * @create:2022-46-22.1.11 15:46:41
 */
@SuppressWarnings("all")
public class OperationUtils {


  /**
   * 字符串的长度
   *
   * @param str
   * @return
   */
  public static int length(String str) {
    return str.length();
  }

  /**
   * 将str1和str2和str3联系起来
   *
   * @param str1
   * @param str2
   * @param str3
   * @return
   */
  public static String concat(String str1, String str2, String str3) {
    return str1 + str2 + str3;
  }


  /**
   * 字符串变大写
   *
   * @param str
   * @return
   */
  public static String upper(String str) {
    return str.toUpperCase();
  }


  /**
   * 字符串变小写
   *
   * @param str
   * @return
   */
  public static String lower(String str) {
    return str.toLowerCase();
  }

  /**
   * 从哪截
   *
   * @param str
   * @param from
   * @return
   */
  public static String subter(String str, int from) {
    return str.substring(from);
  }

  /**
   * 从哪截几个
   *
   * @param str
   * @param from
   * @param to
   * @return
   */
  public static String subter(String str, int from, int to) {
    return str.substring(from, to);
  }

  /**
   * 找到字符串的下一个位置
   *
   * @param str1
   * @param str2
   * @return
   */
  public static int instr(String str1, String str2) {
    return str1.indexOf(str2);
  }

  /**
   * 在左边或右边添加指定长度的字符串
   * ("sss", 5, "123456", "r")  ->  sss12
   * ("sss", 5, "123456", "l")  ->  12sss
   *
   * @param str1
   * @param str2
   * @return
   */
  public static String pad(String str1, int k, String str2, String str3) {
    if (!(str3 == null)) {
      if (str1.length() <= k) {
        int m = k - str1.length();
        while (m > 0) {
          str2 = str2 + str2;
          m--;
        }
        if (str2.length() > (k - str1.length())) {
          str2 = str2.substring(0, (k - str1.length()));
        }
      }
      if (str1.length() <= k) {
        if ("l".equals(str3)) {
            str1 = str2 + str1;
        } else if ("r".equals(str3)) {
            str1 = str1 + str2;
        }
      }else {
        str1 = str1.substring(0, k);
      }
    }
    return str1;
  }

  /**
   * 替换
   * ("skskskskskkscdvdfvdfv", "sk", "qqqqqqq")  ->   qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqkscdvdfvdfv
   *
   * @param str1
   * @param str2
   * @param str3
   * @return
   */
  public static String replace(String str1, String str2, String str3) {
    return str1.replace(str2, str3);
  }

  /**
   * 四舍五入
   *
   * @param a
   * @return
   */
  public static double round(double a) {
    return Math.round(a);
  }
//
//  public static float round(float a) {
//    return Math.round(a);
//  }


  /**
   * 向上向下取整
   * (111.22, true)  ->  112.0
   * (111.22, false)  ->  111.0
   *
   * @param a
   * @param b
   * @return
   */
  public static double integer(double a, boolean b) {
    if (b) {
      return Math.ceil(a);
    } else {
      return Math.floor(a);
    }
  }

  /**
   * 保留小数点后指定位数
   * (1.66666999999,1)  ->  1.7
   *
   * @param a
   * @param b
   * @return
   */
  public static double truncate(double a, int b) {
    return Double.parseDouble(String.format("%." + b + "f", a));
  }

  /**
   * 取余
   * (10.33, 3)  ->  1.33
   *
   * @param a
   * @param b
   * @return
   */
  public static double mod(double a, int b) {
    return a % b;
  }


  /**
   * 求和
   *
   * @param d
   * @return
   */
  public static double sum(List<Double> d) {
    return d.stream().filter(Objects::nonNull).mapToDouble(v -> v).sum();
  }

  /**
   * 求个数
   *
   * @param d
   * @return
   */
  public static long count(List<Double> d) {
    return d.stream().filter(Objects::nonNull).count();
  }

  /**
   * 求平均值
   *
   * @param d
   * @return
   */
  public static double avg(List<Double> d) {
    return sum(d) / count(d);
  }

  /**
   * 求最大值
   *
   * @param d
   * @return
   */
  public static double max(List<Double> d) {
    return d.stream().filter(Objects::nonNull).max(Comparator.comparingDouble(v -> v)).get();
  }

  /**
   * 求最小值
   *
   * @param d
   * @return
   */
  public static double min(List<Double> d) {
    return d.stream().filter(Objects::nonNull).min(Comparator.comparingDouble(v -> v)).get();
  }

  
  public static void main(String[] args) {
    List<Double> d = new ArrayList<Double>();
    d.add(1.2);
    d.add(1.3);
    d.add(1.4);
    d.add(1.02);
    d.add(1.5);
    d.add(1.26);
//    System.out.println(min(d));
    System.out.println(pad("sss", 10, "123456789", "r"));
  }
  
  
}
