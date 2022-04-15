package org.lyf.diamond.inter.controller;

import cn.hutool.core.lang.Dict;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.inter.annontation.Limiter;
import org.lyf.diamond.inter.service.LogService;
import org.lyf.diamond.core.entity.data.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program:IntelliJ IDEA
 * @discription:日志控制类
 * @author: GG-lyf
 * @create:2022-48-22.1.16 21:48:34
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/log")
public class LogContorller {
  @Autowired
  private LogService ls;

  @Limiter(value = 1.0)
  @PostMapping("/select/{data}")
  public Dict select(@PathVariable("data") String sql) {
    List<Log> s = ls.select(sql);
//    System.out.println(s);
    return Dict.create().set("code", 100).set("message", "successful").set("data",s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/truncate/{data}")
  public Dict truncate(@PathVariable("data") String sql) {
    String s = ls.truncate(sql);
//    System.out.println(s);
    return Dict.create().set("code", (s.equals(Return.clearn_log_ok)) ? 100 : 400).set("message", (s.equals(Return.clearn_log_ok)) ? "success" : "error").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/find_one/{database}/{table}")
  public Dict findOne(@PathVariable(value = "database") String database, @PathVariable(value = "table") String table) {
    List<Log> s = ls.findOne(database, table);
//    System.out.println(s);
    return Dict.create().set("code", 100).set("message", "successful").set("data", s);
  }

}
