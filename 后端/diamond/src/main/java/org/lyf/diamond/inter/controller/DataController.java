package org.lyf.diamond.inter.controller;

import cn.hutool.core.lang.Dict;
import cn.yueshutong.springbootstartercurrentlimiting.annotation.CurrentLimiter;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.inter.annontation.Limiter;
import org.lyf.diamond.inter.service.DataService;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program:IntelliJ IDEA
 * @discription:数据控制类
 * @author: GG-lyf
 * @create:2022-47-22.1.16 21:47:10
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/data")
public class DataController {
  @Autowired
  private DataService ds;

  @Limiter(value = 1.0)
//  @CurrentLimiter(QPS = 5)//当前接口的每秒的并发量为5
  @PostMapping("/insert/{data}")
  public Dict insert(@PathVariable(value = "data") String sql) {
    String s = ds.insert(sql);
//    System.out.println(s);insert_ok
    return Dict.create().set("code", (s.equals(Return.insert_ok)) ? 100 : 400).set("message", (s.equals(Return.insert_ok)) ? "successful" : "error").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/delete/{data}")
  public Dict delete(@PathVariable(value = "data") String sql) {
    String s = ds.delete(sql);
//    System.out.println(s);
    return Dict.create().set("code", (s.equals(Return.clean_all_success) || s.equals(Return.delete_ok)) ? 100 : 400).set("message", (s.equals(Return.clean_all_success) || s.equals(Return.delete_ok)) ? "successful" : "error").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/update/{data}")
  public Dict update(@PathVariable(value = "data") String sql) {
    String s = ds.update(sql);
//    System.out.println(s);
    return Dict.create().set("code", (s.equals(Return.update_ok)) ? 100 : 400).set("message", (s.equals(Return.update_ok)) ? "successful" : "error").set("data", s);
  }

  //  @Limiter(value = 1.0)
  @PostMapping("/select/{data}")
  public Dict select(@PathVariable(value = "data") String sql) {
    System.out.println(sql);
    List<Data> s = ds.select(sql);
//    System.out.println(s);
    return Dict.create().set("code", 100).set("message", "successful").set("data", s);
  }

  @Limiter(value = 1.0)
  @PostMapping("/truncate/{data}")
  public Dict truncate(@PathVariable(value = "data") String sql) {
    String s = ds.truncate(sql);
//    System.out.println(s);
    return Dict.create().set("code", (s.equals(Return.clean_all_success)) ? 100 : 400).set("message", (s.equals(Return.clean_all_success)) ? "successful" : "error").set("data", s);
  }

}
