package org.lyf.diamond.frame.process.data;


import org.lyf.diamond.frame.entity.User1;
import org.lyf.diamond.frame.execute.data.Select;
import org.lyf.diamond.frame.process.AnnotationProcess;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: some_middle
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-04 14:09:25
 */
@SuppressWarnings("all")
public class SelectProcess {

  public Select selectById(Object t) {
    try {
      Class<?> clazz = t.getClass();
      String tableName = AnnotationProcess.getTableName(clazz);
      Field[] fields = clazz.getDeclaredFields();
      Select select = new Select().build(tableName);
      String id = "";
      boolean flag = false;
      for (Field field : fields) {
        field.setAccessible(true);
        //值
        String o1 = field.get(t) + "";
        String name = AnnotationProcess.getFieldName(field);
        Object change = AnnotationProcess.compare(field);
        if (!o1.equals(change)) {
          if (name.equals("id")) {
            id = o1;
            flag = true;
          }
        }
      }
      if (!flag) {
        return null;
      }
      select.where().eq("id", id).of();
      System.out.println(select);
      return select;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;

  }

  public Select selectByCondition(Object t, String field, String operation, String condition) {
    try {
      Class<?> clazz = t.getClass();
      String tableName = AnnotationProcess.getTableName(clazz);
      Field[] fields = clazz.getDeclaredFields();
      Select select = new Select().build(tableName);
      String up = "";
      boolean flag = false;
      for (Field attr : fields) {
        attr.setAccessible(true);
        //值
        String o1 = attr.get(t) + "";
        String name = AnnotationProcess.getFieldName(attr);
        Object change = AnnotationProcess.compare(attr);
        if (!o1.equals(change)) {
          if (name.equals(field)) {
            flag = true;
          }
        }
      }
      if (!flag) {
        return null;
      }
      select.where();
      if (operation.equals(">")) {
        select.g(field, condition);
      } else if (operation.equals("=")) {
        select.eq(field, condition);
      } else if (operation.equals("<")) {
        select.l(field, condition);
      } else if (operation.equals(">=")) {
        select.ge(field, condition);
      } else if (operation.equals("<=")) {
        select.le(field, condition);
      } else if (operation.equals("<>")) {
        select.gl(field, condition);
      }
      select.of();
      System.out.println(select);
      return select;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void selectBySomeTables(Object... o) {



  }

  public void selectCount() {
  }

  public void selectList() {
  }

  public void selectMaps() {
  }

  public void selectObjs() {
  }

  public void selectPage() {
  }

  public void selectMapsPage() {
  }

  public static void main(String[] args) throws Exception {
    User1 a = User1.of().setId(1).setHigh(1.0f).setMoney(1.0).setName("1").setSex(false).setTime(new Date());
    User1 b = User1.of().setHigh(2.0f).setMoney(2).setName("2").setSex(true).setTime(new Date());
    List<User1> l = new ArrayList<>();
    l.add(a);
    l.add(b);
    SelectProcess insertProcess = new SelectProcess();
//    insertProcess.insertList(l);
//    insertProcess.insertOne(b);
    insertProcess.selectByCondition(a, "id", ">=", "都是");

//    Select of10 = new Select().count("vfd").avg("vfd").length("cvf").max("fvd").build("是").tables("d", "vd", "v", "vrf", "Vtf").left("v", "vrf").right("sdc", "f").inner("f", "sd").where().eq("osdf", "sdsf").and().eq("d", "df").or().g("hg", "f").or().l("fg", "gf").or().in("飞", "的", "该", "个").of();
    Select of10 = new Select().build("是").tables("d", "vd", "v", "vrf", "Vtf").left("v", "vrf").right("sdc", "f").inner("f", "sd").where().eq("osdf", "sdsf").and().eq("d", "df").or().g("hg", "f").or().l("fg", "gf").or().in("飞", "的", "该", "个").of();
//    Select of10 = new Select().build("是").tables("d").left("v", "vrf").where().eq("osdf", "sdsf").group("dsf", "having", "sdf").order("dsf", "desc").and().like("v", "f").and().limit("上的","1", "5").of();
//    Select of10 = new Select().build("是").tables("d", "vd", "v", "vrf", "Vtf").left("v", "vrf").right("sdc", "f").inner("f", "sd").where().eq("osdf", "sdsf").and().eq("d", "df").or().g("hg", "f").or().l("fg", "gf").or().in("飞", "的", "该", "个").of();
    System.out.println(of10);
  }


}
