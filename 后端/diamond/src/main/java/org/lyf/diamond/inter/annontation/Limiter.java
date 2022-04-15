package org.lyf.diamond.inter.annontation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limiter {
  int not_limited = 0;

  /**
   * qps (每秒并发量)
   */
  @AliasFor("qps") double value() default not_limited;

  /**
   * qps (每秒并发量)
   */
  @AliasFor("value") double qps() default not_limited;

  /**
   * 超时时长,默认不等待
   */
  int timeout() default 0;

  /**
   * 超时时间单位,默认毫秒
   */
  TimeUnit timeUnit() default TimeUnit.MICROSECONDS;
}