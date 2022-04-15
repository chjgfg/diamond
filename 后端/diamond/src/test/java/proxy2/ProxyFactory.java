package proxy2;

import java.lang.reflect.Proxy;

/**
 * @program: diamond
 * @description:
 * @author: GG-lyf
 * @create: 2022-03-18 11:24:05
 */
@SuppressWarnings("all")
public class ProxyFactory {

  public static Object invoke(Object object) {
    return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), (proxy, method, args) -> {
      return method.invoke(object, args);
    });
  }

  public static void testJdkProxy(String params){
    CacheService cacheService = new CacheServiceImpl();
    CacheService jdkProxy = (CacheService) ProxyFactory.invoke(cacheService);
    System.out.println(jdkProxy.execute(params));
  }


}
