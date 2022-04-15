package org.lyf.diamond.inter.controller;

import cn.hutool.core.lang.Dict;
import org.lyf.diamond.core.entity.auxiliary.Relation;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.inter.annontation.Limiter;
import org.lyf.diamond.inter.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program:IntelliJ IDEA
 * @discription:库文件管理
 * @author: GG-lyf
 * @create:2022-36-22.1.16 21:36:26
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/database")
public class DatabaseController {
  @Autowired
  private DatabaseService dbs;

  @Limiter(value = 1.0)
  @PostMapping("/create/{data}")
  public Dict create(@PathVariable(value = "data") String sql) {
    String s = dbs.create(sql);
//    System.out.println(s);
    return Dict.create().set("code", (s.equals(Return.create_database_ok)) ? 100 : 400).set("message", (s.equals(Return.create_database_ok)) ? "success" : "error").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/drop/{data}")
  public Dict drop(@PathVariable(value = "data") String sql) {
    String drop = dbs.drop(sql);
//    System.out.println(drop);
    return Dict.create().set("code", (drop.equals(Return.drop_database_ok)) ? 100 : 400).set("message", (drop.equals(Return.drop_database_ok)) ? "success" : "error").set("data", drop);
  }

  @Limiter(value = 1.0)
  @PostMapping("/show")
  public Dict show() {
    List<String> show = dbs.show();
    System.out.println(show);
    return Dict.create().set("code", 100).set("message", "success").set("data", show).set("other", "databases");
  }


  @Limiter(value = 1.0)
  @PostMapping("/use/{data}")
  public Dict use(@PathVariable(value = "data") String sql) {
    String use = dbs.use(sql);
//    System.out.println(use);
    return Dict.create().set("code", (use.equals(Return.use_database_ok)) ? 100 : 400).set("message", (use.equals(Return.use_database_ok)) ? "success" : "error").set("data", use);
  }

  @Limiter(value = 1.0)
  @PostMapping("/rename/{data}")
  public Dict rename(@PathVariable(value = "data") String sql) {
    String rename = dbs.rename(sql);
//    System.out.println(rename);
    return Dict.create().set("code", (rename.equals(Return.rename_database_ok)) ? 100 : 400).set("message", (rename.equals(Return.rename_database_ok)) ? "success" : "error").set("data", rename);

  }


}
