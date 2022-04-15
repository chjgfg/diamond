package org.lyf.diamond.frame.entity;


import org.lyf.diamond.frame.annotation.Database;

/**
 * @program: some_middle
 * @description: 学校实体类
 * @author: GG-lyf
 * @create: 2022-04-02 23:30:48
 */

@Database(name = "school")
public interface School {

  public String student = null;
  public String class_room = null;
  public String teacher = null;
  public String salary = null;
  public String score = null;
  public String bedroom = null;
  public String subject = null;


}
