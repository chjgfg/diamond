package org.lyf.diamond.core.utile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program:IntelliJ IDEA
 * @discription:日期处理类
 * @author: GG-lyf
 * @create:2022-55-22.1.7 23:55:46
 */
@SuppressWarnings("all")
public class TimeUtils {

  /**
   * y 代表年
   * M 代表月
   * d 代表日
   * H 代表24进制的小时
   * h 代表12进制的小时
   * m 代表分钟
   * s 代表秒
   * S 代表毫秒
   */

  /**
   * 1641545002000 -> 2022-01-07 16:43:22
   *
   * @param time
   * @return
   */
  public static String timestampToDate(String time) {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.parseLong(time));
  }


  /**
   * 2022-01-07 16:43:22 -> 1641545002000
   *
   * @param date
   * @return
   */
  public static String dateToTimestamp(String date) {
    try {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime() + "";
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Fri Jan 07 16:43:22 CST 2022 -> 2022-01-07 16:43:22
   *
   * @param date
   * @return
   */
  public static String nationalToDate(Date date) {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
  }

  /**
   * Fri Jan 07 16:43:22 CST 2022 -> 1641545002000
   *
   * @param date
   * @return
   */
  public static String nationalToTimestamp(Date date) {
    return dateToTimestamp(nationalToDate(date));
  }

//  public static void main(String[] args) {
//    System.out.println(Long.parseLong(nationalToTimestamp(new Date())));
//  }

}
