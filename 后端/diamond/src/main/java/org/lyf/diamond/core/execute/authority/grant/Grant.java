package org.lyf.diamond.core.execute.authority.grant;

import org.lyf.diamond.core.entity.auxiliary.Permiss;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.utile.Base64Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:授权
 * @author: GG-lyf
 * @create:2022-50-22.1.12 23:50:09
 */
@SuppressWarnings("all")
public class Grant {
  /**
   * grant all privileges on *.* to 'root'@'%' identified by '123456' with grant option
   * 其中all privileges指所有权限
   * .*中第一个*是哪个数据库，如果为*指所有数据库，第二个*指选中库的哪个表，为*指所有表
   * root指用户名
   * %指授权地址，%指所有地址，localhost指本地，也可以为127.0.0.1
   * 123456为设定密码
   * with grant option这个选项表示该用户可以将自己拥有的权限授权给别人
   *
   * @param mga
   * @param cmd
   */
  public String grant(Matcher mga, String cmd) {//1,4,7,11.13

    String p = cmd.split(" ")[0];
    String cheak = UserParse.cheak(Authority.getName(), "", "", p);
    if (!cheak.equals(p)) {
      return cheak;
    }
//grant select , insert on user.people to user11 identified by '111111';
    String operation = mga.group(1);//select , update(sno) , insert , delete , update(sno)|all privileges
    System.out.println(operation);
    String database_and_table = mga.group(6);//database.table , fff.ff
    String user = mga.group(9);//common_user
    String pass = mga.group(15);//密码
    String authority = mga.group(16);//with grant option
    if (operation.startsWith("all")) {//以这个开始就是所有权限
      String ok = "";
      if (authority == null) {
        ok = "~";
      } else if (authority.contains("with grant option")) {
        ok = "*";
      } else {
//        System.out.println(Return.sql_is_error);
        return Return.sql_is_error;
      }
      String s = GrantParse.setUserList(Permiss.getAllPermiss(), user, database_and_table, pass != null ? Base64Utils.encode(pass) : Base64Utils.encode("000000"), ok);
//      System.out.println(s);
      return s;
    } else {
      List<String> strings = new ArrayList<>();
      String[] split = operation.split(" , ");
      strings.addAll(Arrays.asList(split));
      String ok = "";
      if (authority == null) {
        ok = "~";
      } else if (authority.contains("with grant option")) {
        ok = "*";
      } else {
//        System.out.println("sql error");
        return Return.sql_is_error;
      }
//      System.out.println(ok);
      return GrantParse.setUserList(strings, user, database_and_table, pass != null ? Base64Utils.encode(pass) : Base64Utils.encode("000000"), ok);
    }

  }


  /**
   grant\s+((all\s+privileges)|(([^\s+]+(\s+,\s+)?)*))\s+on\s+(([^\s]+(\s+\,\s+)?)*)\s+to\s+((([^\s]+(\s+\,\s+)?)*))(\s+identified\s+by\s+([^\s]+))?(\s+with grant option)?;
   grant select , update , insert , delete , update(sno) on database.table , fff.ff to user , dd identified by 'passward' with grant option;
   grant insert , select , update on database.table to u_1 , dd identified by '123456' with grant option;
   grant insert , select , update on user1.user to admin , kk identified by '123456' with grant option;
   grant insert , select , update on user1.user to mm , kk identified by '123456' with grant option;
   grant insert , update on user1.user to kk identified by '112233' with grant option;
   grant  update on user1.user to mm , kk with grant option;
   grant insert , select , update on user1.user to mm , kk identified by '123456';
   grant insert , select , update on user1.user to mm , kk;
   grant insert , select , update on ss.* to testdb with grant option;
   grant select , create , alter , drop , insert , update , delete on testdb.* to common_user;
   grant all privileges on database.table to uuu identified by '123456' with grant option;
   grant all privileges on user1.user to mm identified by '123456' with grant option;
   grant all privileges on user1.user to mm with grant option;
   grant all privileges on user1.people to yy identified by '123456';
   grant all privileges on user1.user to yy identified by '123456';
   grant all privileges on user2.people , user2.user to ll identified by '123456';
   grant all privileges on user1.people , user1.user to yy identified by '123456';
   grant select , create , alter , drop on user1.people , user1.user to yy identified by '123456';
   grant insert on user2.user to ew identified by '123456' with grant option;
   grant insert on user1.user to ew identified by '123456' with grant option;
   grant select on user2.people to ew identified by '123456' with grant option;
   */


  /**
   * create(database|table|user)3
   * use(table)1
   * show(database|table)2
   * drop(database|table|user)3
   * rename(database|table|user)3
   * alter(table)1
   * desc(table)1
   * insert(table)1
   * delete(table)1
   * truncate(table|log)2
   * update(table)1
   * select(table|log)2
   * grant(user)1
   * revoke(user)1
   * set(user)1
   */

}
