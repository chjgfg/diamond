package org.lyf.diamond.core.execute.data.insert;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.execute.log.LogParse;
import org.lyf.diamond.core.file.data.DataFile;
import org.lyf.diamond.core.file.table.TableFile;
import org.lyf.diamond.core.execute.data.DataParse;
import org.lyf.diamond.core.utile.TimeUtils;

import java.util.Date;
import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:插入数据到数据库表文件
 * @author: GG-lyf
 * @create:2022-16-22.1.3 17:16:50
 */
@SuppressWarnings("all")
public class Insert {

  public String insert(Matcher mi, String cmd) {
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      return Return.please_point_database;
    }


    String table_name = mi.group(1);//表名

    String p = cmd.split(" ")[0];
    String[] split = UseDatabase.getPath().split("\\\\");
    String cheak = UserParse.cheak(Authority.getName(), split[split.length - 1], table_name, p);
    if (!cheak.equals(p)) {
      return cheak;
    }

    String ketStr = mi.group(2);
    String valStr = mi.group(5);//值字符串
//    System.out.println(valStr);
    if (valStr.contains("Now")) {
      valStr = valStr.replace("Now", TimeUtils.nationalToDate(new Date()));
    }
//    System.out.println(valStr);
    boolean table = DataFile.isTable(table_name);
    String path = UseDatabase.getPath() + "\\" + table_name + "\\" + table_name;
    String condatin = Authority.getName() + " -> " + TimeUtils.nationalToDate(new Date()) + " -> " + cmd;
    LogParse.insert(path + ".log", condatin);
    Data dict = TableFile.getDict(path + ".dict");
    if (dict == null) {
      return Return.table_is_not_exist;
    }
    String s = DataParse.setDictToParentheses(dict);
    if (table) {
      String s1 = null;
      if (ketStr == null) {//插入多个
        s1 = InsertParse.combinationSome(s, valStr, dict, path);
//        System.out.println(s1);
      } else {
        s1 = InsertParse.combinationOne(ketStr, valStr, dict, path);
      }
      System.out.println(s1);
      return s1;
    } else {
      System.out.println("table is not exist");
      return Return.table_is_not_exist;
    }
  }
}
/**
 * use user1;
 * use user2;
 * insert into user (id,name,time,sex) values (2,'cscscsc',2021-08-10 00:51:14,1);
 * insert into user (id,name,time,sex) values (2,'cscscsc',Now,1);
 * insert into user (id,name,sex) values (2,'cscscsc',1);
 * insert into user (id,name) values (2,'cscscsc');
 * insert into user values (1,'11',2021-08-10 00:51:14,1) , (2,'22',2021-08-10 00:51:14,2) , (3,'33',2021-08-10 00:51:14,3) , (4,'44',2021-08-10 00:51:14,4);
 * insert into user values (1,'11',Now,1) , (2,'22',Now,2) , (3,'33',Now,3) , (4,'44',Now,4) , (5,'55',Now,5) , (6,'66',Now,66) , (7,'77',Now,76) , (8,'8',Now,88) , (9,'999',Now,99);
 * insert into user values (1,2021-08-10 00:51:14,1) , (2,'22',2021-08-10 00:51:14,2) , (3,'33',2021-08-10 00:51:14,3) , (4,'44',2021-08-10 00:51:14,4);
 * insert into people values (1,2021-08-10 00:51:14,1) , (2,'22',2021-08-10 00:51:14,2) , (3,'33',2021-08-10 00:51:14,3) , (4,'44',2021-08-10 00:51:14,4);
 * insert into people values (1,'vdvd') , (2,'cscscsc') , (3,'fgbfbf') , (4,'nhgng');
 * insert into user values (1,'vdvd') , (2,'cscscsc') , (3,'fgbfbf') , (4,'nhgng');
 * insert into dept values (1,'11',Now,1102,22) , (2,'22',Now,22.2,3) , (3,'33',Now,3.22,7) , (4,'44',Now,4555.5,425) , (5,'55',Now,5551.5,1) , (6,'66',Now,6622.5,4) , (7,'77',Now,76.3,6) , (8,'8',Now,881.9,8) , (9,'999',Now,99.9,7);
 * insert into dept values (1,'11') , (2,'22') , (3,'33') , (4,'44') , (5,'55') , (6,'66') , (7,'77') , (8,'8');
 * insert into dept (id,name,now,salary,age) values (2,'22',Now,222.2,3);
 * insert into user (id,name) values (5,'111111');
 * insert into person values (1,'11',Now,1102,22) , (2,'22',Now,22.2,3) , (3,'33',Now,3.22,7) , (4,'44',Now,4555.5,425) , (5,'55',Now,5551.5,1) , (6,'66',Now,6622.5,4) , (7,'77',Now,76.3,6);
 * insert into people values (3,'people3') , (4,'people4');
 * insert into people values (3,'people3',1) , (4,'people4');
 * insert into user values (3,'people3') , (4,'people4');
 * insert into dept values (3,'people3') , (4,'people4');
 * insert into people values (4,'people4') , (5,'people4');
 * <p>
 */
