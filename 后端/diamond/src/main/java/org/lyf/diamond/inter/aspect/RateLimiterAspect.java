package org.lyf.diamond.inter.aspect;

import cn.hutool.core.util.StrUtil;
import org.lyf.diamond.inter.annontation.Limiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import com.google.common.util.concurrent.RateLimiter;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @program:IntelliJ IDEA
 * @discription:
 * @author: GG-lyf
 * @create:2022-30-22.1.20 21:30:09
 */
@SuppressWarnings("all")
@Aspect
@Component
public class RateLimiterAspect {
  /**
   * 单机缓存
   */
  private static final ConcurrentMap<String, RateLimiter> limiter_cache = new ConcurrentHashMap<>();


  @Pointcut("@annotation(org.lyf.diamond.inter.annontation.Limiter)")
  public void rateLimit() {

  }

  @Around("rateLimit()")
  public Object pointcut(ProceedingJoinPoint point) throws Throwable {
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    // 通过 AnnotationUtils.findAnnotation 获取 Limiter 注解
    Limiter rateLimiter = AnnotationUtils.findAnnotation(method, Limiter.class);
    if (rateLimiter != null && rateLimiter.qps() > Limiter.not_limited) {
      double qps = rateLimiter.qps();
      String key = method.getDeclaringClass().getName() + StrUtil.DOT + method.getName();
      if (limiter_cache.get(key) == null) {
        // 初始化 QPS
        limiter_cache.put(key, RateLimiter.create(qps));
      }
      // 尝试获取令牌
      if (limiter_cache.get(key) != null && !limiter_cache.get(key).tryAcquire(rateLimiter.timeout(), rateLimiter.timeUnit())) {
        System.out.println("手速太快了,慢一点吧~");
      }
    }
    return point.proceed();
  }
}