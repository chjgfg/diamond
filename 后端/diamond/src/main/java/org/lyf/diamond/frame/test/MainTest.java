package org.lyf.diamond.frame.test;


import org.lyf.diamond.frame.execute.data.Delete;
import org.lyf.diamond.frame.execute.data.Insert;
import org.lyf.diamond.frame.execute.data.Select;
import org.lyf.diamond.frame.execute.data.Update;
import org.lyf.diamond.frame.execute.database.*;
import org.lyf.diamond.frame.execute.table.*;

/**
 * @program: some_middle
 * @description: 测试类
 * @author: GG-lyf
 * @create: 2022-04-02 22:25:32
 */
@SuppressWarnings("all")
public class MainTest {

  public static void main(String[] args) {
    DropTables of1 = new DropTables().build("cas").drop("c1").drop("c1").drop("c1").drop("c1").of();
    System.out.println(of1);
    AlterTable of2 = new AlterTable().build("cas").alter("add", "c1 char(1)").alter("modify", "c2 char(1)").of();
    System.out.println(of2);
    CreateTable of3 = new CreateTable().build("cas").create("c1 char(1) primary key").create("c1 char(1) not null").create("c1 char(1)").create("c1 char(1)").of();
    System.out.println(of3);
    RenameTable of4 = new RenameTable().build("cas").rename("c1").of();
    System.out.println(of4);
    ShowTable of5 = new ShowTable().build("").show("casc").of();
    System.out.println(of5);
    ShowTable of6 = new ShowTable().build("").showCreate("casc").of();
    System.out.println(of6);
    Insert of7 = new Insert().build("as").insert("cs", "cds").insert("cds", "csdcs").insert("csd", "s").of();
    System.out.println(of7);
    Update of8 = new Update().build("as").set("cs = cds").set("cds = csdcs").where().eq("osdf", "sdsf").and().eq("d", "df").or().g("hg", "f").or().l("fg", "gf").or().in("飞", "的", "该", "个").of();
    System.out.println(of8);
    Delete of9 = new Delete().build("as").where().eq("osdf", "sdsf").and().eq("d", "df").or().g("hg", "f").or().l("fg", "gf").or().in("飞", "的", "该", "个").of();
    System.out.println(of9);
//    Select of10 = new Select().count("vfd").avg("vfd").length("cvf").max("fvd").build("是").tables("d", "vd", "v", "vrf", "Vtf").left("v", "vrf").right("sdc", "f").inner("f", "sd").where().eq("osdf", "sdsf").and().eq("d", "df").or().g("hg", "f").or().l("fg", "gf").or().in("飞", "的", "该", "个").of();
    Select of10 = new Select().build("是").tables("d", "vd", "v", "vrf", "Vtf").left("v", "vrf").right("sdc", "f").inner("f", "sd").where().eq("osdf", "sdsf").and().eq("d", "df").or().g("hg", "f").or().l("fg", "gf").or().in("飞", "的", "该", "个").of();
//    Select of10 = new Select().build("是").tables("d").left("v", "vrf").where().eq("osdf", "sdsf").group("dsf", "having", "sdf").order("dsf", "desc").and().like("v", "f").and().limit("上的","1", "5").of();
//    Select of10 = new Select().build("是").tables("d", "vd", "v", "vrf", "Vtf").left("v", "vrf").right("sdc", "f").inner("f", "sd").where().eq("osdf", "sdsf").and().eq("d", "df").or().g("hg", "f").or().l("fg", "gf").or().in("飞", "的", "该", "个").of();
    System.out.println(of10);

    CreateDatabases of11 = new CreateDatabases().build("as").create("dc", "cd", "Cds", "Cds").of();
    System.out.println(of11);
    DropDatabases of12 = new DropDatabases().build("as").drop("dc", "cd", "Cds", "Cds").of();
    System.out.println(of12);
    RenameDatabase of13 = new RenameDatabase().build("as").rename("dc").of();
    System.out.println(of13);
    UseDatabase of14 = new UseDatabase().build("").use("dc").of();
    System.out.println(of14);
    ShowDatabases of15 = new ShowDatabases().build("").show().of();
    System.out.println(of15);

  }
}
