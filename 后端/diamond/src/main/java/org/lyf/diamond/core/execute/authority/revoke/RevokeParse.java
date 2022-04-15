package org.lyf.diamond.core.execute.authority.revoke;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Permiss;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.file.table.TableFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program:IntelliJ IDEA
 * @discription:收回权限逻辑操作
 * @author: GG-lyf
 * @create:2022-18-22.1.13 18:18:04
 */


/**
 * 有问题,没写完
 */
@SuppressWarnings("all")
public class RevokeParse {

  /**
   * 找到一个用户的所有权限,找不到用户的时候就是null
   *
   * @param user
   * @return
   */
  public static Map<String, List<Authority>> findSome(String user) {
    Map<String, List<Authority>> user1 = UserParse.findUser(user);
    if (user1 == null) {
//      System.out.println("user is not exist");
      return null;
    } else {
      return user1;
    }
  }

  /**
   * @param user
   * @param d_a_t
   * @param condition
   * @return
   */
  public static String removeSome(String user, String d_a_t, String condition) {
    List<String> users = new ArrayList<String>();
    if (user.contains(" , ")) {
      String[] split = user.split(" , ");
      for (String s : split) {
        String path = PathConfig.userPath + s + ".user";
        if (TableFile.isExists(path) == false) {
//          System.out.println(Return.user_dose_not_exist);
          return Return.user_dose_not_exist;
        }
      }
      users.addAll(Arrays.asList(split));
    } else {
      if (TableFile.isExists(PathConfig.userPath + user + ".user") == false) {
//        System.out.println(Return.user_dose_not_exist);
        return Return.user_dose_not_exist;
      }
      users.addAll(Arrays.asList(user));
    }

    List<List<String>> lists = UserParse.processDatabaseAndTable(d_a_t);//处理库和表从而找到要修改的库和表
    if (lists == null) {
//      System.out.println(Return.database_or_table_is_not_exist);
      return Return.database_or_table_is_not_exist;
    }
    List<String> process = new ArrayList<String>();
    if (condition.contains(" , ")) {
      String[] split = condition.split(" , ");
      process.addAll(Arrays.asList(split));
    } else {
      process.addAll(Collections.singleton(condition));
    }
//    System.out.println(process);

    if (!UserParse.permissionExists(process)) {
//      System.out.println(Return.authority_is_not_exist);
      return Return.authority_is_not_exist;
    }
    for (String s : users) {//找用户
      Map<String, List<Authority>> some = findSome(s);//找到用户所有信息
      List<Authority> ll = new ArrayList<Authority>();
      List<Authority> authorities = some.get(some.keySet().iterator().next());
      if (authorities == null) {
//        System.out.println(Return.do_not_have_any_permission);
        return Return.do_not_have_any_permission;
      }
      authorities.stream().forEach(vo -> {
        Authority andRemove = findAndRemove(vo, lists, process);
        ll.add(andRemove);
      });
      write(s, some.keySet().iterator().next(), ll);
    }
    return Return.have_deleted_some_permissions;
  }

  /**
   * 把一个个对象组装成一个个的字符串
   * 先把这个文件清空,
   * 再把用户的第一个密码写进去
   * 再循环这个对象集合,把集合中的每一个对象组装成一个个字符串,之后再写入
   *
   * @param user
   * @param next
   * @param ll
   */
  public static void write(String user, String next, List<Authority> ll) {
    ll.stream().forEach(x -> {
      x.getPurview().removeIf(mm -> "".equals(mm));
    });
    Iterator<Authority> iterator = ll.iterator();
    while (iterator.hasNext()) {
      if (iterator.next().getPurview().size() == 2) {
        iterator.remove();
      }
    }
    String userPath = PathConfig.userPath + user + ".user";
    TableDataFile.clearn(userPath);
    TableDataFile.write(userPath, next, true);//第一个密码
    ll.stream().forEach(vo -> {//把用户组装成一个字符串
      String one = vo.getAuthority() + "-" + vo.getPass() + "-{" + "(0)" + "}" + "-" + vo.getOk();
      String dt = "";
      for (int i = 0; i < vo.getPurview().size(); i++) {
        dt = dt + vo.getPurview().get(i);
        if (i == vo.getPurview().size() - 1) {
          dt = dt;
        } else {
          dt = dt + " ";
        }
      }
      one = one.replace("(0)", dt);
      TableDataFile.write(userPath, one, true);
    });
  }


  /**
   * 打印的数据格式
   * vo  ->  Authority{user='mm', authority='admin', pass='MDAwMDAw', purview=[user1, user, select], ok='*'}
   * lists  ->  [[user1, user], [user1, people]]
   * process  ->  [select, update]
   *
   * @param vo
   * @param lists
   * @param process
   */
  public static Authority findAndRemove(Authority vo, List<List<String>> lists, List<String> process) {
    List<String> arrList = new ArrayList(vo.getPurview());
    String database = arrList.get(0);//每一个的库名
    String table = arrList.get(1);//每一个的表名
    for (int j = 0; j < lists.size(); j++) {
      if (lists.get(j).get(0).equals(database) && lists.get(j).get(1).equals(table)) {
        for (int k = 2; k < arrList.size(); k++) {
          for (int i = 0; i < process.size(); i++) {
            if (process.get(i).equals(arrList.get(k))) {
              arrList.set(k, "");
            }
          }
        }
      }
    }
    vo.setPurview(arrList);
    return vo;
  }

  /**
   * 将这个用户的所有权限都给删了,只留下自己的那个密码
   *
   * @param user
   * @param d_a_t
   * @param condition
   * @return
   */
  public static String removeAll(String user) {
    List<String> users = new ArrayList<String>();
    if (user.contains(" , ")) {
      String[] split = user.split(" , ");
      for (String s : split) {
        String path = PathConfig.userPath + s + ".user";
        if (TableFile.isExists(path) == false) {
//          System.out.println(Return.user_dose_not_exist);
          return Return.user_dose_not_exist;
        }
      }
      users.addAll(Arrays.asList(split));
    } else {
      if (TableFile.isExists(PathConfig.userPath + user + ".user") == false) {
//        System.out.println(Return.user_dose_not_exist);
        return Return.user_dose_not_exist;
      }
      users.addAll(Arrays.asList(user));
    }
    for (String s : users) {//找用户
      Map<String, List<Authority>> some = findSome(s);//找到用户所有信息
      String pass = some.keySet().iterator().next();
      String userPath = PathConfig.userPath + s + ".user";
      TableDataFile.clearn(userPath);
      TableDataFile.write(userPath, some.keySet().iterator().next(), true);//第一个密码
    }
    return Return.have_deleted_all_permissions;
  }


//  public static void main(String[] args) {
//    String user = "mm";
//    String user1 = "mm , kk";
//    String user2 = "mmm , kk";
//    String d_t1 = "user1.user , user1.people";
//    String condation = "select , update";
//
//    String user3 = "ll";
//    String d_t2 = "user1.user";
//    String condation1 = "insert";
//    removeSome(user3, d_t2, condation1);
//  }


}
