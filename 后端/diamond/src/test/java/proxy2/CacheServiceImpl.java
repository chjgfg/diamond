package proxy2;


interface CacheService {
  Object execute(Object sql);
}

public class CacheServiceImpl implements CacheService {
  @Override
  public Object execute(Object sql) {
    System.out.println("2");
    return sql;
  }
}