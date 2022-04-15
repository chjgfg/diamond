package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @program: diamond
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-01 23:05:30
 */
@SuppressWarnings("all")
public class DemoTest {
  public static void main(String[] args) {

    List<TestA> list = new ArrayList<>();
    list.add(TestA.of().setAge(12).setName("zhangsan").setMoney(0.54646));
    list.add(TestA.of().setAge(4).setName("lisi").setMoney(487.85));
    list.add(TestA.of().setAge(54).setName("wangwu").setMoney(455451.54646));
    list.add(TestA.of().setAge(23453).setName("zhaoliu").setMoney(32.3));

//    for (int i = 0; i < list.size(); i++) {
//      for (int j = i; j < list.size() - i-1; j++) {
//        TestA testA = new TestA();
//        if (list.get(j).getAge() - list.get(j + 1).getAge() > 0) {
//          testA = list.get(j);
//          list.remove(j);
//          list.set(j, list.get(j + 1));
//          list.remove(j + 1);
//          list.set(j + 1, testA);
//        }
//      }
//    }

    list.sort((h1, h2) -> h1.getName().compareTo(h2.getName()));
    list.forEach(System.out::println);

  }
}
