package org.lyf.diamond.frame.entity;


import org.lyf.diamond.frame.annotation.Entity;
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
@TableName(name = "user")
@Entity
public class User {

  @TableField(value = "u_id", key = true, comment = "用户id",is_null = false)
  private int id;
  @TableField(value = "u_name", comment = "用户名")
  private String name;
  @TableField(value = "u_phone", comment = "手机号")
  private String phone;
  @TableField(value = "u_sex", comment = "性别")
  private boolean sex;
  @TableField(value = "u_money", comment = "工资")
  private double money;
  @TableField(value = "u_times", comment = "时间")
  private Date times;


}
