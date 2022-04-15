package org.lyf.diamond.inter.controller;

import cn.hutool.core.lang.Dict;
import org.lyf.diamond.inter.annontation.Limiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @program:IntelliJ IDEA
 * @discription:使用
 * @author: GG-lyf
 * @create:2022-03-22.1.20 21:03:03
 */
@SuppressWarnings("all")
@RequestMapping("/ratelimiter")
@RestController
public class RateLimiterController {


  /**
   * 接口每秒只能请求一次,不等待
   *
   * @return
   */
  @GetMapping("/test1/{num}")
  @Limiter(value = 1.0)
  public Dict test1(@PathVariable("num")String num) {
    System.out.println(num);
    return Dict.create().set("msg", "hello,world!").set("description", "别想一直看到我，不信你快速刷新看看~");
  }

  /**
   * 接口每秒只能请求一次,等待一秒
   *
   * @return
   */
  @GetMapping("/test3")
  @Limiter(value = 1.0, timeout = 1, timeUnit = TimeUnit.SECONDS)
  public Dict test3() {
    return Dict.create().set("msg", "hello,world!").set("description", "别想一直看到我，不信你快速刷新看看~");
  }
}

