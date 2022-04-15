package org.lyf.diamond.frame.entity;

import org.lyf.diamond.frame.annotation.TableField;
import org.lyf.diamond.frame.annotation.TableName;

import java.util.Date;

/**
 * @program: some_middle
 * @description: 用户实体
 * @author: GG-lyf
 * @create: 2022-04-02 22:20:20
 */
@SuppressWarnings("all")
@TableName(name = "user1")
public class User1 {
  @TableField(value = "id", key = true)
  private int id;
  @TableField(value = "name")
  private String name;
  @TableField(value = "money")
  private double money;
  @TableField(value = "high")
  private float high;
  @TableField(value = "sex")
  private boolean sex;
  @TableField(value = "time")
  private Date time;
  @TableField(value = "card")
  private long card;

  public long getCard() {
    return card;
  }

  public User1 setCard(long card) {
    this.card = card;
    return this;
  }

  public double getMoney() {
    return money;
  }

  public User1 setMoney(double money) {
    this.money = money;
    return this;
  }

  public float getHigh() {
    return high;
  }

  public User1 setHigh(float high) {
    this.high = high;
    return this;
  }

  public boolean isSex() {
    return sex;
  }

  public User1 setSex(boolean sex) {
    this.sex = sex;
    return this;
  }

  public Date getTime() {
    return time;
  }

  public User1 setTime(Date time) {
    this.time = time;
    return this;
  }

  public int getId() {
    return id;
  }

  public User1 setId(int id) {
    this.id = id;
    return this;
  }

  public static User1 of() {
    return new User1();
  }

  public User1() {
  }

  public User1(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public User1 setName(String name) {
    this.name = name;
    return this;
  }


}
