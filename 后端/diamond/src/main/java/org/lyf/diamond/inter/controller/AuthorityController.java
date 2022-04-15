package org.lyf.diamond.inter.controller;

import cn.hutool.core.lang.Dict;
import org.lyf.diamond.inter.annontation.Limiter;
import org.lyf.diamond.inter.service.AuthorityService;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.database.ShowDatabase;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program:IntelliJ IDEA
 * @discription:权限控制类
 * @author: GG-lyf
 * @create:2022-47-22.1.16 21:47:58
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/authority")
public class AuthorityController {

  @Autowired
  private AuthorityService as;

  @Limiter(value = 1.0)
  @PostMapping("/login")
  public Dict login(@RequestBody Authority o) {
    if (o.getUser().equals(null) || o.getPass().equals(null)) {
      return Dict.create().set("code", 400).set("message", "error").set("data", Return.data_mismatch);
    }
    String s = as.login(o.getUser(), o.getPass());
//    System.out.println(s);
    return Dict.create().set("code", (s.equals(Return.user_log_success)) ? 100 : 400).set("message", (s.equals(Return.user_log_success)) ? "success" : "error").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/logout")
  public Dict logout() {
    Authority.setName("");
    return Dict.create().set("code", 100).set("message", "success").set("data", Authority.getName());
  }

  @Limiter(value = 1.0)
  @PostMapping("/find")
  public Dict finddDatabaseAndTable(@RequestBody String name) {
//    System.out.println("authority : " + name);
    String n_name = "";
    if (name == null) {
      n_name = Authority.getName();
    } else {
      n_name = name.split(":")[1].replace("\"", "").replace("}", "");
    }
//    System.out.println("n_name : " + n_name);
    if ("admin".equals(n_name)) {
      ShowDatabase showDatabase = new ShowDatabase();
      List<String> show = showDatabase.show("show databases;");
      List<Map<String, List<String>>> all = as.findAll();
      return Dict.create().set("code", 100).set("message", "success").set("data", all).set("other", UseDatabase.getPath()).set("other_date", showDatabase.show("show databases;"));
    } else {
      Map<String, List<String>> some = as.findSome(n_name);
      if (some == null) {
        return Dict.create().set("code", 100).set("message", "success").set("data", Return.user_dose_not_exist);
      } else {
        return Dict.create().set("code", 100).set("message", "success").set("data", some).set("other_date", some.keySet());
      }
    }
  }

  @Limiter(value = 1.0)
  @PostMapping("/find_other/{name}")
  public Dict findOther(@PathVariable(value = "name") String name) {
    List<String> other = as.findOther(name);
//    System.out.println(other);
    return Dict.create().set("code", 100).set("message", "success").set("data", other);
  }

  @Limiter(value = 1.0)
  @PostMapping("/find_one_by_name/{name}")
  public Dict findOneByName(@PathVariable(value = "name") String name) {
    Map<String, List<Authority>> oneByName = as.findOneByName(name);
//    System.out.println(oneByName);
    return Dict.create().set("code", 100).set("message", "success").set("data", oneByName).set("pass", oneByName.keySet().iterator().next());
  }

  @Limiter(value = 1.0)
  @PostMapping("/find_pass_by_name/{name}")
  public Dict findPassByName(@PathVariable(value = "name") String name) {
    String passByName = as.findPassByName(name);
//    System.out.println(passByName);
    return Dict.create().set("code", 100).set("message", "success").set("data", passByName);
  }

  @Limiter(value = 1.0)
  @PostMapping("/create/{name}/{data}")
  public Dict create(@PathVariable(value = "name") String name, @PathVariable(value = "data") String sql) {
    Authority.setName(name);
    String s = as.create(sql);
//    System.out.println(s);
    return Dict.create().set("code", (s.equals(Return.create_user_ok)) ? 100 : 400).set("message", (s.equals(Return.create_user_ok)) ? "success" : "error").set("data", s);

  }

  @Limiter(value = 1.0)
  @PostMapping("/drop/{name}/{data}")
  public Dict drop(@PathVariable(value = "name") String name, @PathVariable(value = "data") String sql) {
    Authority.setName(name);
    String s = as.drop(sql);
//    System.out.println(drop);
    return Dict.create().set("code", (s.equals(Return.drop_user_ok)) ? 100 : 400).set("message", (s.equals(Return.drop_user_ok)) ? "success" : "error").set("data", s);

  }

  @Limiter(value = 1.0)
  @PostMapping("/set/{name}/{data}")
  public Dict set(@PathVariable(value = "name") String name, @PathVariable(value = "data") String sql) {
    Authority.setName(name);
    String s = as.set(sql);
//    System.out.println(set);
    return Dict.create().set("code", (s.equals(Return.set_password_ok)) ? 100 : 400).set("message", (s.equals(Return.set_password_ok)) ? "success" : "error").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/rename/{name}/{data}")
  public Dict rename(@PathVariable(value = "name") String name, @PathVariable(value = "data") String sql) {
    Authority.setName(name);
    String s = as.rename(sql);
//    System.out.println(rename);
    return Dict.create().set("code", (s.equals(Return.rename_user_ok)) ? 100 : 400).set("message", (s.equals(Return.rename_user_ok)) ? "success" : "error").set("data", s);
  }

  /**
   * 有bug
   *
   * @param name
   * @param sql
   * @return
   */
  @Limiter(value = 1.0)
  @PostMapping("/grant/{name}/{data}")
  public Dict grant(@PathVariable(value = "name") String name, @PathVariable(value = "data") String sql) {
    Authority.setName(name);
    String s = as.grant(sql);
//    System.out.println(grant);
    return Dict.create().set("code", (s.equals(Return.authorization_success)) ? 100 : 400).set("message", (s.equals(Return.authorization_success)) ? "success" : "error").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/revoke/{name}/{data}")
  public Dict revoke(@PathVariable(value = "name") String name, @PathVariable(value = "data") String sql) {
    Authority.setName(name);
    String s = as.revoke(sql);
//    System.out.println(revoke);
    return Dict.create().set("code", (s.equals(Return.have_deleted_some_permissions)||s.equals(Return.have_deleted_all_permissions)) ? 100 : 400).set("message", (s.equals(Return.have_deleted_some_permissions)||s.equals(Return.have_deleted_all_permissions)) ? "success" : "error").set("data", s);
  }


}
