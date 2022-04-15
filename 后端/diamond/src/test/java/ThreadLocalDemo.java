import java.lang.reflect.Field;

/**
 * @program: diamond
 * @description:
 * @author: GG-lyf
 * @create: 2022-03-23 08:25:42
 */
@SuppressWarnings("all")
public class ThreadLocalDemo {

  public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
    Thread t = new Thread(() -> test("abc", false));
    t.start();
    t.join();
    System.out.println("--after gc--");
    Thread t2 = new Thread(() -> test("def", true));
    t2.start();
    t2.join();
  }

  private static void test(String s, boolean isGC) {
    try {
      new ThreadLocal<>().set(s);
      if (isGC) {
        System.gc();
      }
      Thread t = Thread.currentThread();
      Class<? extends Thread> clz = t.getClass();
      Field field = clz.getDeclaredField("threadLocals");
      field.setAccessible(true);
      Object ThreadLocalMap = field.get(t);
      Class<?> tlmClass = ThreadLocalMap.getClass();
      Field tableField = tlmClass.getDeclaredField("table");
      tableField.setAccessible(true);
      Object[] arr = (Object[]) tableField.get(ThreadLocalMap);
      for (Object o : arr) {
        if (o != null) {
          Class<?> entryClass = o.getClass();
          Field valueField = entryClass.getDeclaredField("value");
          Field referenceField = entryClass.getSuperclass().getSuperclass().getDeclaredField("referent");
          valueField.setAccessible(true);
          referenceField.setAccessible(true);
          System.out.println(String.format("key:%s,value:%s", referenceField.get(o), valueField.get(o)));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

