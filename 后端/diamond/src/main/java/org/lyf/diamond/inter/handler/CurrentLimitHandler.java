package org.lyf.diamond.inter.handler;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONObject;
import cn.yueshutong.springbootstartercurrentlimiting.annotation.CurrentLimiter;
import cn.yueshutong.springbootstartercurrentlimiting.handler.CurrentAspectHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * @program: diamon
 * @description: 接口并发限流
 * @author: GG-lyf
 * @create: 2022-02-10 22:03:01
 */
@SuppressWarnings("all")

@Component
public class CurrentLimitHandler implements CurrentAspectHandler {
  @Override
  public Dict around(ProceedingJoinPoint pjp, CurrentLimiter rateLimiter)  {
    //限流的返回数据可以自己根据需求场景设计
    return Dict.create().set("code",10011).set("message","接口访问繁忙,休息一下");
  }
}
