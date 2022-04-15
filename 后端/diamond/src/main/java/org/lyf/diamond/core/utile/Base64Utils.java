package org.lyf.diamond.core.utile;

import java.util.Base64;

/**
 * @program:IntelliJ IDEA
 * @discription:加密算法
 * @author: GG-lyf
 * @create:2022-46-22.1.13 17:46:38
 */
@SuppressWarnings("all")
public class Base64Utils {
  /***
   * Base64加密
   * @param str 需要加密的参数
   * @return
   * @throws Exception
   */
  public static String encode(String str) {
    try {
      return Base64.getEncoder().encodeToString(str.getBytes("UTF-8"));
    } catch (Exception e) {
      return null;
    }
  }

  /***
   * Base64解密
   * @param str 需要解密的参数
   * @return
   * @throws Exception
   */
  public static String decode(String str) {
    try {
      return new String(Base64.getDecoder().decode(str), "UTF-8");
    } catch (Exception e) {
      return null;
    }
  }

  public static void main(String[] args) {
    System.out.println(encode("000000"));
//    System.out.println(decode("MTIzNDU2"));
//    System.out.println("123456".equals(decode("MTIzNDU2")));
  }
}
