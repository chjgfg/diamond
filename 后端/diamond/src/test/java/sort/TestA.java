package sort;

/**
 * @program: diamond
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-01 23:04:32
 */
@SuppressWarnings("all")
public class TestA {

  private int key;

  private String name;

  private Integer age;

  private Double money;

  public TestA() {
  }

  public int getKey() {
    return key;
  }

  public TestA setKey(int key) {
    this.key = key;
    return this;
  }

  public static TestA of() {
    return new TestA();
  }

  public TestA(String name, Integer age) {
    super();
    this.name = name;
    this.age = age;
  }

  public Double getMoney() {
    return money;
  }

  public TestA setMoney(Double money) {
    this.money = money;
    return this;
  }

  public String getName() {
    return name;
  }

  public TestA setName(String name) {
    this.name = name;
    return this;
  }

  public Integer getAge() {
    return age;
  }

  public TestA setAge(Integer age) {
    this.age = age;
    return this;
  }

  @Override
  public String toString() {
    return "TestA{" +
        "key=" + key +
        ", name='" + name + '\'' +
        ", age=" + age +
        ", money=" + money +
        '}';
  }
}
