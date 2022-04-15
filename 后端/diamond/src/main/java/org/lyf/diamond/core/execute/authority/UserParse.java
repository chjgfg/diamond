package org.lyf.diamond.core.execute.authority;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Permiss;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.file.table.TableFile;
import org.lyf.diamond.core.utile.Base64Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program:IntelliJ IDEA
 * @discription:用户逻辑操作
 * @author: GG-lyf
 * @create:2022-45-22.1.13 19:45:23
 */
@SuppressWarnings("all")
public class UserParse {


  /**
   * 看一个库的所有表
   *
   * @return
   */
  public static List<String> getTables(String path) {
    File file = new File(path);
    return Arrays.asList(Objects.requireNonNull(file.list()));
  }

  /**
   * 用一个变量计算所有的权限,是否存在于权限库中,不存在变量不+1,
   * 最后比较变量和集合大小如果不相等就说明有权限不正确,就返回false
   *
   * @param process
   * @return
   */
  public static boolean permissionExists(List<String> process) {
    int k = 0;//计算相等的次数
    for (String allPermiss : Permiss.getAllPermiss()) {
      for (int i = 0; i < process.size(); i++) {
        if (process.get(i).equals(allPermiss)) {
          k++;
        }
      }
    }
    if (k != process.size()) {
      return false;
    }
    return true;
  }


  /**
   * 创建用户文件并写入数据
   *
   * @param name
   * @param pass
   * @return
   */
  public static boolean create(String name, String pass) {
    try {
      File file = new File(PathConfig.userPath + name);
      file.createNewFile();
      return TableDataFile.write(file.getPath(), pass, true);
    } catch (Exception e) {
      return false;
    }
  }


  /**
   * 删除用户,
   * 有就删,没有就不删
   *
   * @param name
   * @return
   */
  public static boolean drop(String name) {
    //MDAwMDAw
    String path = PathConfig.userPath + name;
    if ("admin".equals(name) || name.equals(Authority.getName())) {
//      System.out.println("the user cannot be deleted");
      return false;
    }
    if (TableFile.isExists(path)) {
      return new File(path).delete();
    } else {
      return false;
    }
  }


  /**
   * 读取某一个用户文件的信息
   *
   * @param name
   * @return
   */
  public static List<String> read(String path) {
    List<String> read = new ArrayList<String>();
    if (TableFile.isExists(path)) {
      read = TableDataFile.read(path);
    } else {
//      System.out.println("user is not exist");
      read = null;
    }
    return read;
  }

  /**
   * 重命名要命名的用户
   *
   * @param old_name
   * @param new_name
   * @return
   */
  public static String rename(String old_name, String new_name) {
    String path = PathConfig.userPath + old_name + ".user";
    if ("admin".equals(old_name)) {
//      System.out.println("user cannot be deleted");
      return Return.user_cannot_be_deleted;
    }
    String npath = PathConfig.userPath + new_name + ".user";
    if (TableFile.isExists(path)) {
      boolean b = new File(path).renameTo(new File(npath));
      return b ? Return.rename_user_ok : Return.rename_user_error;
    } else {
      return Return.rename_user_error;
    }
  }


  /**
   * 处理库和表的关系,并检查表和库是否存在
   * user1.user , user1.dept  ->  [user1, user] [user1, dept]
   *
   * @param d_a_t
   * @return
   */
  public static List<List<String>> processDatabaseAndTable(String d_a_t) {
    String dataPath = PathConfig.dataPath;
    List<List<String>> splitList = new ArrayList<List<String>>();
    if (d_a_t.contains(" , ")) {
      String[] split = d_a_t.split(" , ");
      for (String s : split) {
        List<String> splits = new ArrayList<String>();
        if (s.contains(".")) {
          String[] split1 = s.split("\\.");
          if (!TableFile.isExists(dataPath + split1[0]) || !TableFile.isExists(dataPath + split1[0] + "\\" + split1[1])) {//库不存在就直接返回null
//            System.out.println("database is not exist");
            return null;
          }
          splits.addAll(Arrays.asList(split1));
          splitList.add(splits);
        } else {
          if (!TableFile.isExists(dataPath + s)) {
//            System.out.println("database is not exist");
            return null;
          }
          List<String> tables = getTables(dataPath + s);
          tables.stream().forEach(vo -> {
            List<String> sp = new ArrayList<String>();
            sp.add(s);//库
            sp.add(vo);//所有表
            splitList.add(sp);
          });
//          splits.add(s);//库
//          splits.add("*");//所有表
        }

      }
    } else if (d_a_t.contains(".") && !d_a_t.contains(" , ")) {
      List<String> splits = new ArrayList<String>();
      String[] split1 = d_a_t.split("\\.");
      if (!TableFile.isExists(dataPath + split1[0]) || !TableFile.isExists(dataPath + split1[0] + "\\" + split1[1])) {//库不存在就直接返回null
//        System.out.println("database is not exist");
        return null;
      }
      splits.addAll(Arrays.asList(split1));
      splitList.add(splits);
    } else {
      if (!TableFile.isExists(dataPath + d_a_t)) {//看库是否存在
//        System.out.println("database is not exist");
        return null;
      }
      List<String> splits = new ArrayList<String>();
//      splits.add(d_a_t);//库
//      splits.add("*");//所有表
//      splitList.add(splits);
      List<String> tables = getTables(PathConfig.dataPath + "\\" + d_a_t);
      tables.stream().forEach(vo -> {
        List<String> sp = new ArrayList<String>();
        sp.add(d_a_t);//库
        sp.add(vo);//所有表
        splitList.add(sp);
      });
//      splitList.add(splits);
    }
    return splitList;
  }

  /**
   * 找到这个用户,封装成一个map集合
   * 默认以默认的密码为键,以所有的权限中每一条权限为一个对象封装为一个集合,以此为值
   *
   * @param user
   */
  public static Map<String, List<Authority>> findUser(String user) {
    String path = PathConfig.userPath + user + ".user";
    List<String> read = TableDataFile.read(path);
    if (read == null) {
      return null;
    }
    Map<String, List<Authority>> kk = new HashMap<String, List<Authority>>();
    List<Authority> l = new ArrayList<Authority>();
    if (read.size() == 1) {//只有自己的一个初始密码
      l = null;
    }
    for (int i = 1; i < read.size(); i++) {
      l.add(toUser(user, read.get(i)));
    }
    kk.put(read.get(0), l);
    return kk;
  }

  /**
   * 把字符串解析成对象
   * admin-MDAwMDAw-{database table create use show drop rename alter desc insert delete truncate update grant revoke set}-*
   * \
   * Authority{user='mm', authority='admin', pass='MDAwMDAw', purview=[database, table, create, use, show, drop, rename, alter, desc, insert, delete, truncate, update, select, grant, revoke, set], ok='*'}
   * <p>
   * admin-MDAwMDAw-{fff[*(create use show drop rename alter desc insert delete truncate update select grant revoke set)]}-*
   * \
   * Authority{user='mm', authority='admin', pass='MDAwMDAw', purview=[fff, *, create, use, show, drop, rename, alter, desc, insert, delete, truncate, update, select, grant, revoke, set], ok='*'}
   * <p>
   * admin-MDAwMDAw-{ff[*(create use show drop rename alter desc insert delete truncate update select grant revoke set)]}-*
   * \
   * Authority{user='mm', authority='admin', pass='MDAwMDAw', purview=[ff, *, create, use, show, drop, rename, alter, desc, insert, delete, truncate, update, select, grant, revoke, set], ok='*'}
   *
   * @param user
   * @param str
   */
  public static Authority toUser(String user, String str) {
//    System.out.println(str);
    String[] split = str.split("-");
    Authority author = new Authority();
    author.setUser(user);
    author.setAuthority(split[0]);
    author.setPass(split[1]);
    author.setOk(split[3]);
    String[] s = split[2].replace("{", "").replace("}", "").split(" ");
    author.setPurview(Arrays.asList(s));
//    System.out.println(author);
    return author;
  }


  private static String check_table(String database, String process, List<Authority> authorities, String table) {
    boolean db = false, tab = false, auth = false;
    for (Authority vo : authorities) {
      List<String> purview = vo.getPurview();
      if (purview.get(0).equals(database)) {
        db = true;
        if (purview.get(1).equals(table)) {
          tab = true;
          for (int i = 2; i < purview.size(); i++) {
            if (process.equals(purview.get(i))) {
              auth = true;
              return process;
            }
          }
        }
      }
    }
    if (db == false) {
      return Return.database_not_found;
    } else if (tab == false) {
      return Return.table_is_not_exist;
    } else if (auth == false) {
      return Return.authority_is_not_exist;
    } else {
      return Return.unknown_error;
    }
  }

  private static String check_database(String database, String process, List<Authority> authorities) {
    boolean db = false, auth = false;
    for (Authority vo : authorities) {
      List<String> purview = vo.getPurview();
      if (purview.get(0).equals(database)) {
        db = true;
        for (int i = 2; i < purview.size(); i++) {
          if (process.equals(purview.get(i))) {
            auth = true;
            return process;
          }
        }
      }
    }
    if (db == false) {
      return Return.database_not_found;
    } else if (auth == false) {
      return Return.authority_is_not_exist;
    } else {
      return Return.unknown_error;
    }
  }

  private static String check_authority(List<Authority> authorities, String process) {
    for (Authority vo : authorities) {
      List<String> purview = vo.getPurview();
      for (int i = 2; i < purview.size(); i++) {
        if (process.equals(purview.get(i))) {
          return process;
        }
      }
    }
    return Return.authority_is_not_exist;
  }

  /**
   * 5个权限的检查
   * 1.看授权的方式:直接查每一个Authority里面的每一个权限,和当前的权限进行对比
   * 2.看库的方式:找到每个getPurview()里面的每第一个,和输入的库进行对比,成功直接就和权限对比
   * 3.看表的方式,先用找库的方式去匹配库,之后再拿到getPurview()里面的每第二个,和输入的表进行对比,成功直接就和权限对比
   * 4.看数据的方式:先找库和表,之后再拿到getPurview()里面的每第二个后面的权限,和输入的操作进行对比
   * 5.看日志的方式:和找表的方式一样
   *
   * @param database
   * @param table
   * @param process
   * @param flag
   */
  public static String cheak(String user, String database, String table, String process) {
    if (user.equals("admin")) {
      return process;
    }
    if (process == null) {
//      System.out.println(Return.authority_is_not_exist);
      return Return.authority_is_not_exist;
    }
    String msg = "";
    Map<String, List<Authority>> s1 = findUser(user);
    if (s1 == null) {
//      System.out.println(Return.user_dose_not_exist);
      return Return.user_dose_not_exist;
    }
    String next = s1.keySet().iterator().next();
    List<Authority> authorities = s1.get(next);
//    System.out.println(authorities);
    if (!user.isEmpty() && database.isEmpty() && table.isEmpty()) {//用户
      msg = check_authority(authorities, process);
    } else if (!user.isEmpty() && !database.isEmpty() && table.isEmpty()) {//数据库
      msg = check_database(database, process, authorities);
    } else if (!user.isEmpty() && !database.isEmpty() && !table.isEmpty()) {//数据表 数据 日志
      msg = check_table(database, process, authorities, table);
    } else {
      msg = Return.user_dose_not_exist;
    }
//    System.out.println(msg);
    return msg;
  }


  public static void main(String[] args) {
    Authority.setName("ddd");
    cheak(Authority.getName(), "", "", "set");//用户
    cheak(Authority.getName(), "user1", "", "set");//库
    cheak(Authority.getName(), "user11", "people", "select");//表
  }


}
