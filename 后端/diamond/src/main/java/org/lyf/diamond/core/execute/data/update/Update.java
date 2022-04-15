package org.lyf.diamond.core.execute.data.update;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.execute.log.LogParse;
import org.lyf.diamond.core.file.data.DataFile;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.Date;
import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:在数据库文件中修改数据
 * @author: GG-lyf
 * @create:2022-18-22.1.3 17:18:47
 */
@SuppressWarnings("all")
public class Update {

  public String update(Matcher mu, String cmd) {
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      return Return.please_point_database;
    }

    String table_name = mu.group(1);

    String p = cmd.split(" ")[0];
    String[] split = UseDatabase.getPath().split("\\\\");
    String cheak = UserParse.cheak(Authority.getName(), split[split.length - 1], table_name, p);
    if (!cheak.equals(p)) {
      return cheak;
    }


    String values = mu.group(2);
    String keys = mu.group(30);
    boolean table = DataFile.isTable(table_name);
    if (table) {
      String path = UseDatabase.getPath() + "\\" + table_name + "\\" + table_name;
      String condatin = Authority.getName() + " -> " + TimeUtils.nationalToDate(new Date()) + " -> " + cmd;
      LogParse.insert(path + ".log", condatin);
      String combination = UpdateParse.combination(path, keys, values);
//      System.out.println(combination);
      return combination.equals(Return.data_mismatch2) ? Return.update_errer : combination;
    } else {
//      System.out.println("table is not exist");
      return Return.table_is_not_exist;
    }

  }


  /***
   *
   use user1;
   use user2;
   update user set money = 121212 where id <= 4;
   update user set name = '历史' , salary = salary * 10.0 , now = 2021-08-13 10:51:14 where salary <= 22.2 and salary <= 22.2 or ss = 11;
   update user set name = "54545454545" where id in (1,2,3,4,5,5,5555) and salary <= 22.2 or ss = 11;
   update user set money = money + 10.0 where name = '张三' and salary <= 22.2 or ss = 11;
   update user set money = money * 10.0 where name in ('张三','李四','李四1') and salary <= 22.2 or ss = 11;
   update user set salary = 22.2 where name in ('张三','李四','韩阳','赵六') and salary <= 22.2 or ss = 11;
   update user set name = 10 where money in (10.2,11.2,12.2) and salary <= 22.2 or ss = 11;
   update user set name = 10 where salary in (10.2,11.2,12.2) and salary <= 22.2 or ss = 11;
   update user set name = 10 where time in (2021-08-10 00:51:14,2021-08-10 10:51:14) and salary <= 22.2 or ss = 11;
   update peoper set name = '张三' where id = 1;
   update user set u_d_id = 1 where id in (1,2);
   update user set name = 10 , age = 11 , age = 12 , age = 13 where id >= 1;
   update user set name = 10 , age = 11 , now = 2021-08-10 00:51:14 , salary = 13.2 where id <= 1;
   update user set name = 10 , age = 11 , age = 12 , age = 13 where id > 1;
   update user set name = 10 , age = 11 , age = 12 , age = 13 where id < 1;
   update user set name = '历史' where time = 2021-08-10 00:51:14;
   update user set name = '历史' where id < 1;
   update user set name = '历史' where now in (2021-08-10 00:51:14,2021-08-10 10:51:14);
   update user set name = '历史' where id in (1,2,3,4,5);
   update user set name = '历史' , money = money * 10.22  where id >= 1;
   update user set name = '历史' , money = money * 10.0 , time = 2021-08-13 10:51:14 where id >= 1;
   update user set name = '历史' , time = 2021-08-10 10:51:14 where id >= 1;
   update user set time = 2021-08-10 10:51:14 where id >= 1;
   update user set name = '历史' , money = money * 10.0 , time = 2021-08-13 10:51:14 where id = 1;
   update user set money = money + 10.5 , name = '历史' , time = 2021-08-10 10:51:14 where name = '张三';
   update user set money = money + 10.5 , name = '历史' , id = 1 , time = 2021-08-10 10:51:14 where name = '张三';
   update user set id = 1 , money = money + 10.5 , name = '历史' , time = 2021-08-13 10:51:14  where name = '张三';
   update user set money = money + 10.5 , age = 2 , now = 2021-08-13 10:51:14  where name = '张三';
   update user set salary = salary + 10.5 , age = 2 , now = 2021-08-13 10:51:14  where name = '张三';
   update user set salary = salary * 10.0 where name in ('张三','李四','李四1');
   update user set name = '历史' , salary = salary * 10.0 , now = 2021-08-13 10:51:14 where salary <= 22.2;
   update user set name = '历史' , age = age * 10 , now = 2021-08-13 10:51:14 where id <= 3;
   update user set time = 2022-01-08 22:14:14 where id <= 32;
   update user set sex = 1 where id <= 32;
   update user set name = 'cdssdcs' where id <= 3;
   update dept set name = 'vdsv' , now = 2022-01-05 12:37:00 , salary = 33 , age = 5555 where id = 4;
   update dept set name = 'dept3' where id = 2;
   
   
   
   
   
   
   delete from user where salary <= 22.2 and salary <= 22.2 or ss = 11;
   delete from user where id in (1,2,3,4,5,5,5555) and salary <= 22.2 or ss = 11;
   delete from user where name = '张三' and salary <= 22.2 or ss = 11;
   delete from user where name in ('张三','李四','李四1') and salary <= 22.2 or ss = 11;
   delete from user where name in ('张三','李四','韩阳','赵六') and salary <= 22.2 or ss = 11;
   delete from user where money in (10.2,11.2,12.2) and salary <= 22.2 or ss = 11;
   delete from user where salary in (10.2,11.2,12.2) and salary <= 22.2 or ss = 11;
   delete from user where time in (2021-08-10 00:51:14,2021-08-10 10:51:14) and salary <= 22.2 or ss = 11;
   delete from peoper where id = 1 and salary <= 22.2 or ss = 11;;
   delete from user where id <= 1;
   delete from user where id > 1;
   delete from user where id < 1;
   delete from user where time = 2021-08-10 00:51:14 and salary <= 22.2 or ss = 11;
   delete from user where id < 1;
   delete from user where now in (2021-08-10 00:51:14,2021-08-10 10:51:14);
   delete from user where id in (1,2,3,4,5);
   delete from user where name in ('张三','李四','李四1');
   */

}
