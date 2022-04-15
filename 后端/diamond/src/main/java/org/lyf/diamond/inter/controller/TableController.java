package org.lyf.diamond.inter.controller;

import cn.hutool.core.lang.Dict;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.inter.annontation.Limiter;
import org.lyf.diamond.inter.service.DatabaseService;
import org.lyf.diamond.inter.service.TableService;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program:IntelliJ IDEA
 * @discription:表控制类
 * @author: GG-lyf
 * @create:2022-46-22.1.16 21:46:27
 */
@SuppressWarnings("all")

@RestController
@RequestMapping("/table")
public class TableController {

  @Autowired
  private TableService ts;
  @Autowired
  private DatabaseService ds;

  @Limiter(value = 1.0)
  @PostMapping("/create/{data}")
  public Dict create(@PathVariable(value = "data") String sql) {
    String s = ts.create(sql);
//    System.out.println(s);
    return Dict.create().set("code", (s.equals(Return.create_table_ok)) ? 100 : 400).set("message", (s.equals(Return.create_table_ok)) ? "success" : "error").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/drop/{data}")
  public Dict drop(@PathVariable(value = "data") String sql) {
    String s = ts.drop(sql);
//    System.out.println(s);
    return Dict.create().set("code", (s.equals(Return.drop_ok)) ? 100 : 400).set("message", (s.equals(Return.drop_ok)) ? "success" : "error").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/show/{data}")
  public Dict show(@PathVariable(value = "data") String sql) {
    List<String> s = ts.show(sql);
    String[] split = UseDatabase.getPath().split("\\\\");
//    System.out.println(s);
    return Dict.create().set("code", 100).set("message", "success").set("data", s).set("other", split[split.length - 1]);
  }

  @Limiter(value = 1.0)
  @PostMapping("/dict/{database}/{name}")
  public Dict dict(@PathVariable(value = "database") String database, @PathVariable(value = "name") String name) {
    Data s = ts.find_dict(database, name);
//    System.out.println(s);
    return Dict.create().set("code", 100).set("message", "success").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/show_create/{data}")
  public Dict show_create(@PathVariable(value = "data") String sql) {
//    System.out.println(UseDatabase.getPath());
    String s = ts.show_create(sql);
//    System.out.println(s);
    return Dict.create().set("code", 100).set("message", "success").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/rename/{data}")
  public Dict rename(@PathVariable(value = "data") String sql) {
    String s = ts.rename(sql);
//    System.out.println(s);
    return Dict.create().set("code", (s.equals(Return.rename_ok)) ? 100 : 400).set("message", (s.equals(Return.rename_ok)) ? "success" : "error").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/desc/{data}")
  public Dict desc(@PathVariable(value = "data") String sql) {
    List<String> s = ts.desc(sql);
//    System.out.println(s);
    return Dict.create().set("code", 100).set("message", "success").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/alter/{data}")
  public Dict alter(@PathVariable(value = "data") String sql) {
    String s = ts.alter(sql);
//    System.out.println(s);
    if (s.equals(Return.add_ok)) {
      return Dict.create().set("code", (s.equals(Return.add_ok)) ? 100 : 400).set("message", (s.equals(Return.add_ok)) ? "success" : "error").set("data", s);
    } else if (s.equals(Return.change_ok)) {
      return Dict.create().set("code", (s.equals(Return.change_ok)) ? 100 : 400).set("message", (s.equals(Return.change_ok)) ? "success" : "error").set("data", s);
    } else if (s.equals(Return.drop_ok)) {
      return Dict.create().set("code", (s.equals(Return.drop_ok)) ? 100 : 400).set("message", (s.equals(Return.drop_ok)) ? "success" : "error").set("data", s);
    } else if (s.equals(Return.modify_ok)) {
      return Dict.create().set("code", (s.equals(Return.modify_ok)) ? 100 : 400).set("message", (s.equals(Return.modify_ok)) ? "success" : "error").set("data", s);
    } else {
      return Dict.create().set("code", 400).set("message", "error").set("data", Return.unknown_error);
    }
  }

}
