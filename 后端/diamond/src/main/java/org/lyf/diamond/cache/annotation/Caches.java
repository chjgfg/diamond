package org.lyf.diamond.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: diamond
 * @description: cache注解
 * @author: GG-lyf
 * @create: 2022-03-18 09:29:39
 */
@SuppressWarnings("all")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Caches {

  String name() default "";

  int time() default 0;

}
