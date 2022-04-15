package org.lyf.diamond.core.execute.authority.grant;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Permiss;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.authority.revoke.RevokeParse;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.file.table.TableFile;
import org.lyf.diamond.core.utile.Base64Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program:IntelliJ IDEA
 * @discription:授权操作逻辑处理
 * @author: GG-lyf
 * @create:2022-17-22.1.13 18:17:06
 */
@SuppressWarnings("all")
public class GrantParse {

  private static int kkkk = 0;

  /**
   * 设置一共有多少个用户要创建
   * u_1 , dd  ->  [Authority{user='u_1', authority='admin', pass='null', purview=null, ok='null'}, Authority{user='dd', authority='admin', pass='null', purview=null, ok='null'}]
   *
   * @param user
   * @return
   */
  public static List<String> setUsers(String user) {
    List<String> users = new ArrayList<String>();
    if (user.contains(" , ")) {
      String[] split = user.split(" , ");
      users.addAll(Arrays.asList(split));
    } else {
      users.addAll(Arrays.asList(user));
    }
    return users;
  }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * 循环全部的人赋予自己的权限,判断自己给别人的权限是否能行
   * 如果返回了false则说明这个权限不能赋权
   * 只要一个授权最后的为 ~ 就不让他授权
   *
   * @param user
   * @param permiss
   * @return
   */
  public static boolean check(String user, List<String> permiss) {
//    Map<String, List<Authority>> user1 = UserParse.findUser(user);
    Map<String, List<Authority>> user1 = UserParse.findUser(user);
    if (user1 == null) {
      return false;
    }

    Set<String> set = new HashSet<String>();
    if ("admin".equals(user)) {
      for (int i = 2; i < Permiss.getAllPermiss().size(); i++) {
        set.add(Permiss.getAllPermiss().get(i));
      }
    } else {
      String next = user1.keySet().iterator().next();
      if (user1.get(next) == null) {//只有自己的一个初始密码
//        System.out.println("don't have any permission");
        return false;
      }
      for (Authority authority : user1.get(next)) {
        if ("+".equals(authority.getOk())) {
          continue;
        } else if ("~".equals(authority.getOk())) {
          return false;
        }
        for (int i = 2; i < authority.getPurview().size(); i++) {
          set.add(authority.getPurview().get(i));
        }
      }
    }
    int k = 0;
    Iterator<String> iterator = set.iterator();
    while (iterator.hasNext()) {
      String next = iterator.next();
      for (String s : permiss) {
        if (next.equals(s)) {
          k++;
        }
      }
    }
    if (k == permiss.size()) {
      return true;
    }
    return false;
  }

  /**
   * 检查一个对象的权限中是否有自己想赋予的权限,如果有就返回true,如果没有就返回false
   * List<String> purview
   * List<String> permiss
   *
   * @param purview
   * @param permiss
   * @return
   */
//  private static boolean checkOne(List<String> purview, List<String> permiss) {
//    for (String s : permiss) {
//      for (String s1 : purview) {
//        if (s1.equals(s)) {
//          return true;
//        }
//      }
//    }
//    return false;
//  }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * 整理数据并向用户表写入数据
   * 对于双层for来创建对象赋值,总会出现浅复制的现象,那么不要写在一个方法里,直接有n层for就写n+1层方法
   *
   * @param permiss
   * @param user
   * @param d_a_t
   * @param pass
   * @param ok
   */
  public static String setUserList(List<String> permiss, String user, String d_a_t, String pass, String ok) {
    if (!UserParse.permissionExists(permiss)) {//权限校验
//      System.out.println("authority isn't exist");
      return Return.authority_is_not_exist;
    }
    if (!"admin".equals(Authority.getName())) {//不是admin那就检验
      if (check(Authority.getName(), permiss) == false) {
//        System.out.println("don't have authority");
        return Return.do_not_have_authority;
      }
    }
    List<String> users = setUsers(user);//封装的要授权的用户对象
    for (String authority : users) {//如果是当前登录的用户或admin,就不让他赋权
      if (authority.equals(Authority.getName())) {
//        System.out.println("don't give authority to yourself");
        return Return.do_not_give_authority_to_yourself;
      } else if ("admin".equals(authority)) {
//        System.out.println("don't have authority");
        return Return.do_not_have_authority;
      }
    }
    List<List<String>> lists = UserParse.processDatabaseAndTable(d_a_t);//表和库的关系
    if (lists == null) {
//      System.out.println("database or table is not exist");
      return Return.database_or_table_is_not_exist;
    }

    findOrInsert(user, lists, permiss, pass, ok);
    return Return.authorization_success;
  }


  /**
   * 1.找这个用户,用户不存在就创建这个用户,把初始密码设置为加密的000000
   * 2.用户存在,就看他权限是否为空,为空就直接插入
   * 3.不为空就看这个用户有没有这个库或这个表的权限,没有就直接插入
   * 4.有就找是否权限重复,不重复就插入
   * 5.重复就不插入
   * 6.没有就插入
   *
   * @param user
   * @param lists
   * @param permiss
   * @param pass
   * @param ok
   */
  public static void findOrInsert(String user, List<List<String>> lists, List<String> permiss, String pass, String ok) {
//    System.out.println(user + " " + pass + " " + lists + " " + permiss + " " + ok);
    try {
      String userPath = PathConfig.userPath + user + ".user";
      if (!TableFile.isExists(userPath)) {
        File file = new File(userPath);
        file.createNewFile();
        TableDataFile.write(userPath, Base64Utils.encode("000000"), true);
      }
      Map<String, List<Authority>> news = new HashMap<String, List<Authority>>();//新建一个对象用来装数据
      Map<String, List<Authority>> some = RevokeParse.findSome(user);
      String up = some.keySet().iterator().next();
      if (some.get(up) == null) {//只有一个初始密码
        List<Authority> newList = new ArrayList<Authority>();
        lists.stream().forEach(vo -> {//把要加的权限加进去
          vo.addAll(permiss);
          newList.add(new Authority(user, Authority.getName(), pass, vo, ok));
        });
        news.put(up, newList);
      } else {//开始找库和表和权限
        List<Authority> authorities = some.get(up);
        List<Authority> a = new ArrayList<Authority>();
        List<Authority> b = new ArrayList<Authority>();
        authorities.stream().forEach(vo -> {
          List<String> purview = vo.getPurview();
          String database = purview.get(0);
          String table = purview.get(1);
          Authority authoritity = vo.clone();
          List<String> liset = new ArrayList<String>();
          LinkedHashSet<String> set = new LinkedHashSet<String>();//用set集合去重
          List<List<String>> n_set = new ArrayList<>();//新的
          for (int j = 0; j < lists.size(); j++) {//表和库的关系
            for (int i = 0; i < purview.size(); i++) {//循环查出来的权限
              set.add(purview.get(i));
            }
            if (lists.get(j).get(0).equals(database) && lists.get(j).get(1).equals(table)) { //有这个库的这个表,那就开始添加
              for (int k = 0; k < permiss.size(); k++) {//输入的权限
                set.add(permiss.get(k));
              }
            } else {//没有这个库或没有这个表或两者都没有
              ArrayList<String> objects = new ArrayList<>();
              lists.get(j).forEach(v -> {
                objects.add(v);
              });
              for (int k = 0; k < permiss.size(); k++) {//输入的权限
                objects.add(permiss.get(k));
              }
              n_set.add(objects);
            }
          }
          if (n_set != null) {//没有这个库或这个表或这个库和表都不存在,先在这里用一个全局变量控制循环的次数,防止遍历次数过多,再创建对象来进行插入
            if (kkkk == 0) {
              Iterator iterator = n_set.iterator();
              n_set.forEach(kk -> {
                b.add(new Authority(user, Authority.getName(), pass, kk, ok));
              });
              kkkk++;
            }
          }
          Iterator iterator = set.iterator();
          while (iterator.hasNext()) {
            liset.add((String) iterator.next());
          }
          authoritity.setPurview(liset);
          a.add(authoritity);
        });
        if (b != null) {
          b.forEach(l -> {
            a.add(l);
          });
        }
        news.put(up, a);
      }
//      for (String s : news.keySet()) {
//        List<Authority> authorities = news.get(s);
//        for (Authority authority : authorities) {
//          System.out.println(authority);
//        }
//      }
      kkkk = 0;
      writeOne(news);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

  }

  /**
   * 往用户文件写回数据
   *
   * @param news
   */
  public static void writeOne(Map<String, List<Authority>> news) {
//    System.out.println(news);
    String up = news.keySet().iterator().next();
    String userPath = "";
    for (int i = 0; i < news.get(up).size(); i++) {
      if (i == 0) {
        userPath = PathConfig.userPath + news.get(up).get(i).getUser() + ".user";
        TableDataFile.clearn(userPath);
        TableDataFile.write(userPath, up, true);
      }
      String user = news.get(up).get(i).getAuthority() + "-" + news.get(up).get(i).getPass() + "-{" + "(0)" + "}" + "-" + news.get(up).get(i).getOk();
      String dt = "";
      for (int j = 0; j < news.get(up).get(i).getPurview().size(); j++) {
        dt = dt + news.get(up).get(i).getPurview().get(j);
        if (j == news.get(up).get(i).getPurview().size() - 1) {
          dt = dt;
        } else {
          dt = dt + " ";
        }
      }
      user = user.replace("(0)", dt);
      TableDataFile.write(userPath, user, true);
    }
  }


//  public static void main(String[] args) {
//    String name1 = "user1.user , user1.people";
//    String name4 = "user1 , user2 , user3";
//    String name9 = "user1 , user2";
//    String name5 = "user1.user , user2.user , user3";
//    String name2 = "user1.user";
//    String name8 = "user1.dd";
//    String name3 = "user1";
//    String name6 = "dvd";
//    String name10 = "user1.dept , user1.user";
//    List<String> strings = new ArrayList<>();
//    strings.add("insert");
//    strings.add("update");
//    strings.add("set");
////    System.out.println(processDatabaseAndTable(name1));
//    String pass = null;
//    System.out.println(setUserList(strings, "ll", name10, pass != null ? Base64Utils.encode(pass) : Base64Utils.encode("000000"), "*"));
////    System.out.println(UserParse.processDatabaseAndTable(name1));
//  }

}
