package org.lyf.diamond.inter.controller;

import cn.hutool.core.lang.Dict;
import org.lyf.diamond.inter.annontation.Limiter;
import org.lyf.diamond.inter.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


/**
 * @program: diamon
 * @description: 发送邮件
 * @author: GG-lyf
 * @create: 2022-02-10 14:29:26
 */
@RestController
@RequestMapping("/email")
@SuppressWarnings("all")
public class EmailController {
//  xgnyvukpzgwsbagi

  @Autowired
  private EmailService es;

  @Limiter(value = 1.0)
  @PostMapping("/send/{title}/{context}")
  public Dict email(@PathVariable("title") String title, @PathVariable("context") String context) {
    int i = es.sendEmail("2083298549@qq.com", title, context);
    String msg = (i == 0) ? "send error" : "send ok";
    return Dict.create().set("code", (i == 0) ? 400 : 100).set("message", (i == 0) ? "error" : "ok").set("data", msg);
  }

}
