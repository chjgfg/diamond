package org.lyf.diamond.core;

import org.lyf.diamond.cache.execute.Cache;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.file.init.InitFile;
import org.lyf.diamond.core.operator.Login;
import org.lyf.diamond.core.operator.Operator;
import org.lyf.diamond.core.utile.ScheduledUtils;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class CoreApplication {

  public static void main(String[] args) {

    InitFile.makeDatabase();
    ScheduledUtils.scheduled();
//    Scanner sc = new Scanner(System.in);
//    String[] s = sc.nextLine().split(" ");
//    String sc = "admin 000000";
    String sc = "pp 111111";
    String[] s = sc.split(" ");
    for (String value : s) {
      System.out.println(value);
    }
    String login = Login.login(s[0], s[1]);

    System.out.println(Cache.get(s[0]));

    if (login.equals(Return.user_log_success)) {
      Operator o = new Operator();
      o.start();
    } else {
      System.out.println(login);
    }
  }
/**
 * admin 000000
 * use kk;
 * revoke select , update , insert on user1.dept from mm;
 */
}
