package org.lyf.diamond.inter;

import org.lyf.diamond.core.file.init.InitFile;
import org.lyf.diamond.core.utile.ScheduledUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program:IntelliJ IDEA
 * @discription:启动类
 * @author: GG-lyf
 * @create:2022-20-22.1.16 18:20:35
 */
//前端控制器(dispatcherServlet) , 请求到处理器映射(handlerMapping), 处理器适配器(HandlerAdapter), 视图解析器(ViewResolver)。
@SuppressWarnings("all")
@SpringBootApplication
public class InterApplication {
  public static void main(String[] args) {
    SpringApplication.run(InterApplication.class);
    InitFile.makeDatabase();
    ScheduledUtils.scheduled();

  }

}
