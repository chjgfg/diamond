package org.lyf.diamond.inter.service;

import org.lyf.diamond.core.entity.auxiliary.Regular;
import org.lyf.diamond.inter.utils.CombineMap;
import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.authority.create.CreateUser;
import org.lyf.diamond.core.execute.authority.drop.DropUser;
import org.lyf.diamond.core.execute.authority.grant.Grant;
import org.lyf.diamond.core.execute.authority.rename.RenameUser;
import org.lyf.diamond.core.execute.authority.revoke.Revoke;
import org.lyf.diamond.core.execute.authority.set.SetPass;
import org.lyf.diamond.core.execute.database.ShowDatabase;
import org.lyf.diamond.core.file.table.TableFile;
import org.lyf.diamond.core.operator.Login;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program:IntelliJ IDEA
 * @discription:用户服务
 * @author: GG-lyf
 * @create:2022-33-22.1.25 17:33:09
 */
@SuppressWarnings("all")
@Service
public class AuthorityService {

//  private static final Pattern grant_admin = Pattern.compile("grant\\s+((all\\s+privileges)|(([^\\s+]+(\\s+,\\s+)?)*))\\s+on\\s+(([^\\s]+(\\s+\\,\\s+)?)*)\\s+to\\s+((([^\\s]+(\\s+\\,\\s+)?)*))(\\s+identified\\s+by\\s+(\\'([^\\s]+)\\'))?(\\s+with grant option)?;");
//  private static final Pattern revoke_admin = Pattern.compile("revoke\\s+((all\\s+privileges\\s+,\\s+grant\\s+option)\\s+|((((([^\\s]+)*(\\s+\\,\\s+[^\\s]+)*?)*)\\s+on\\s+(([^\\s]+(\\s+\\,\\s+)?)*))\\s+))from\\s+(([^\\s]+(\\s+\\,\\s+)?)*);");
//  private static final Pattern set_pass = Pattern.compile("set\\s+password\\s+(for\\s+([^\\s+]+)\\s+)?\\=\\s+password\\(\\'(\\w+)\\'\\);");
//  private static final Pattern drop_user = Pattern.compile("drop\\s+user\\s+((\\w+(\\s+\\,\\s+)?)*);");
//  private static final Pattern create_user = Pattern.compile("create\\s+user\\s+(\\w+)\\s+identified\\s+by\\s+([^\\s+]+);");
//  private static final Pattern rename_user = Pattern.compile("rename\\s+user\\s+(\\w+)\\s+to\\s+(\\w+);");


  public String login(String name, String pass) {
    return Login.login(name, pass);
  }

  public String create(String sql) {
//    System.out.println(sql);
    Matcher m = Regular.create_user.matcher(sql + ";");
    boolean b = m.find();
    CreateUser createUser = new CreateUser();
    return createUser.create_user(m, sql + ";");
  }

  public String drop(String sql) {
//    System.out.println(sql);
    Matcher m = Regular.drop_user.matcher(sql + ";");
    boolean b = m.find();
    DropUser dropUser = new DropUser();
    return dropUser.drop_user(m, sql + ";");
  }

  public String set(String sql) {
//    System.out.println(sql);
    Matcher m = Regular.set_pass.matcher(sql + ";");
    boolean b = m.find();
    SetPass setPass = new SetPass();
    return setPass.set(m, sql + ";");
  }

  public String rename(String sql) {
//    System.out.println(sql);
    Matcher m = Regular.rename_user.matcher(sql + ";");
    boolean b = m.find();
    RenameUser renameUser = new RenameUser();
//    System.out.println(sql);
    return renameUser.rename_user(m, sql + ";");
  }

  public String grant(String sql) {
    Matcher m = Regular.grant_admin.matcher(sql + ";");
    boolean b = m.find();
//    System.out.println(m.find());
    Grant grant = new Grant();
    return grant.grant(m, sql + ";");
  }

  public String revoke(String sql) {
    Matcher m = Regular.revoke_admin.matcher(sql + ";");
    boolean b = m.find();
    Revoke revoke = new Revoke();
    return revoke.revoke(m, sql + ";");
  }

  /**
   * 找所有的库和所有对应下的表
   *
   * @return
   */
  public List<Map<String, List<String>>> findAll() {
    ShowDatabase showDatabase = new ShowDatabase();
    List<String> show = showDatabase.show("show databases;");
    List<Map<String, List<String>>> mm = new ArrayList<>();
    for (String s : show) {//这是所有的库在循环
//      System.out.println("s ------------> " + s);
      Map<String, List<String>> n = new LinkedHashMap<>();
      File file = new File(PathConfig.databasePath + s);
      File[] files1 = new File(file.getPath()).listFiles();
      List<String> poo = new ArrayList<>();
      for (File file1 : files1) {
//        System.out.println("AuthorityService  -> " + file1);
        String s1 = file1 + "";
        String[] split = s1.split("\\\\");
        poo.add(split[split.length - 1]);
      }
      if (files1 != null) {
        n.put(s, poo);
        mm.add(n);
      }

    }
    return mm;
  }

  /**
   * 找到对应的这个用户所有权的所有的库和所有的表
   *
   * @param name
   * @return
   */
  public Map<String, List<String>> findSome(String name) {
    String path = PathConfig.userPath + name + ".user";
    boolean exists = TableFile.isExists(path);
    if (!exists) {
      return null;
    } else {
      Map<String, List<Authority>> user = UserParse.findUser(name);
      List<Map<String, String>> ss = new ArrayList<>();
      String next = user.keySet().iterator().next();
      for (Authority authority : user.get(next)) {
        Map<String, String> nn = new HashMap<>();
        List<String> purview = authority.getPurview();
        nn.put(purview.get(0), purview.get(1));
        ss.add(nn);
      }
      return CombineMap.mapCombine(ss);
    }
  }

  public List<String> findOther(String name) {
    File f = new File(PathConfig.userPath);
    File[] files = f.listFiles();
    List<String> list = new ArrayList<>();
    for (File file : files) {
      String[] split = (file + "").split("\\\\");
      String s = split[split.length - 1].replace(".user", "");
      if ("admin".equals(s) || name.equals(s)) {
        continue;
      }
      list.add(s);
    }
    return list;
  }


  public Map<String, List<Authority>> findOneByName(String name) {
    return UserParse.findUser(name);
  }

  public String findPassByName(String name) {
    Map<String, List<Authority>> user = UserParse.findUser(name);
    if (user == null) {
      return Return.user_dose_not_exist;
    } else {
      return user.keySet().iterator().next();
    }
  }

  public static void main(String[] args) {
//    System.out.println(findOneByName("yy"));
//    findAll();
  }


}
