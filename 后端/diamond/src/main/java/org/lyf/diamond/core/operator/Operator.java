package org.lyf.diamond.core.operator;


import org.lyf.diamond.core.entity.auxiliary.Regular;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Log;
import org.lyf.diamond.core.execute.authority.create.CreateUser;
import org.lyf.diamond.core.execute.authority.drop.DropUser;
import org.lyf.diamond.core.execute.authority.grant.Grant;
import org.lyf.diamond.core.execute.authority.rename.RenameUser;
import org.lyf.diamond.core.execute.authority.revoke.Revoke;
import org.lyf.diamond.core.execute.authority.set.SetPass;
import org.lyf.diamond.core.execute.data.delete.Delete;
import org.lyf.diamond.core.execute.data.insert.Insert;
import org.lyf.diamond.core.execute.data.select.Select;
import org.lyf.diamond.core.execute.data.delete.Truncate;
import org.lyf.diamond.core.execute.data.update.Update;
import org.lyf.diamond.core.execute.database.CreateDatabase;
import org.lyf.diamond.core.execute.database.DropDatabase;
import org.lyf.diamond.core.execute.database.RenameDatabase;
import org.lyf.diamond.core.execute.database.ShowDatabase;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.execute.log.SelectLog;
import org.lyf.diamond.core.execute.log.TruncateLog;
import org.lyf.diamond.core.execute.table.AlterTable;
import org.lyf.diamond.core.execute.table.CreateTable;
import org.lyf.diamond.core.execute.table.DescTable;
import org.lyf.diamond.core.execute.table.DropTable;
import org.lyf.diamond.core.execute.table.RenameTable;
import org.lyf.diamond.core.execute.table.ShowCreateTable;
import org.lyf.diamond.core.execute.table.ShowTables;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operator {
//  //库
//  private static final Pattern create_database = Regular.create_database;
//  private static final Pattern use_database = Regular.use_database;
//  private static final Pattern show_database = Regular.show_database;
//  private static final Pattern drop_database = Regular.drop_database;
//  private static final Pattern rename_database = Regular.rename_database;
//  //表
//  private static final Pattern create_table = Regular.create_table;
//  private static final Pattern drop_table = Regular.drop_table;
//  private static final Pattern rename_table = Regular.rename_table;
//  private static final Pattern alter_table = Regular.alter_table;
//  private static final Pattern show_table = Regular.show_table;
//  private static final Pattern desc_table = Regular.desc_table;
//  private static final Pattern show_create_table = Regular.show_create_table;
//  //数据
//  private static final Pattern insert = Regular.insert;
//  private static final Pattern delete = Regular.delete;
//  private static final Pattern truncate = Regular.truncate;
//  private static final Pattern update = Regular.update;
//  private static final Pattern select = Regular.select;
//  //授权
//  private static final Pattern grant_admin = Regular.grant_admin;
//  private static final Pattern revoke_admin = Regular.revoke_admin;
//  private static final Pattern set_pass = Regular.set_pass;
//  private static final Pattern drop_user = Regular.drop_user;
//  private static final Pattern create_user = Regular.create_user;
//  private static final Pattern rename_user = Regular.rename_user;
//  //日志
//  private static final Pattern select_log = Regular.select_log;
//  private static final Pattern truncate_log = Regular.truncate_log;

  public void start() {
    Scanner sc = new Scanner(System.in);
    String cmd = null;

    while (!"exit".equals(cmd = sc.nextLine())) {
      Matcher mcd = Regular.create_database.matcher(cmd);
      Matcher mud = Regular.use_database.matcher(cmd);
      Matcher msd = Regular.show_database.matcher(cmd);
      Matcher mdd = Regular.drop_database.matcher(cmd);
      Matcher mrd = Regular.rename_database.matcher(cmd);

      Matcher mct = Regular.create_table.matcher(cmd);
      Matcher mdt = Regular.drop_table.matcher(cmd);
      Matcher mrt = Regular.rename_table.matcher(cmd);
      Matcher mat = Regular.alter_table.matcher(cmd);
      Matcher mst = Regular.show_table.matcher(cmd);
      Matcher mdct = Regular.desc_table.matcher(cmd);
      Matcher msct = Regular.show_create_table.matcher(cmd);

      Matcher mi = Regular.insert.matcher(cmd);
      Matcher md = Regular.delete.matcher(cmd);
      Matcher mt = Regular.truncate.matcher(cmd);
      Matcher mu = Regular.update.matcher(cmd);
      Matcher ms = Regular.select.matcher(cmd);

      Matcher mga = Regular.grant_admin.matcher(cmd);
      Matcher mra = Regular.revoke_admin.matcher(cmd);
      Matcher msp = Regular.set_pass.matcher(cmd);
      Matcher mdu = Regular.drop_user.matcher(cmd);
      Matcher mcu = Regular.create_user.matcher(cmd);
      Matcher mru = Regular.rename_user.matcher(cmd);

      Matcher msl = Regular.select_log.matcher(cmd);
      Matcher mtl = Regular.truncate_log.matcher(cmd);

      //建库
      while (mcd.find()) {
        CreateDatabase create = new CreateDatabase();
        String s = create.create(mcd, cmd);
        System.out.println(s);
      }

      //用库
      while (mud.find()) {
        UseDatabase use = new UseDatabase();
        String use1 = use.use(mud, cmd);
        System.out.println(use1);
      }

      //看库
      while (msd.find()) {
        ShowDatabase show = new ShowDatabase();
        List<String> show1 = show.show(cmd);
        System.out.println(show1);
      }

      //删库
      while (mdd.find()) {
        DropDatabase drop = new DropDatabase();
        String drop1 = drop.drop(mdd, cmd);
        System.out.println(drop1);
      }

      //重命名库
      while (mrd.find()) {
        RenameDatabase rename = new RenameDatabase();
        String rename1 = rename.rename(mrd, cmd);
        System.out.println(rename1);
      }

      //建表
      while (mct.find()) {
        CreateTable create = new CreateTable();
        String s = create.create(mct, cmd);
        System.out.println(s);
      }

      //删表
      while (mdt.find()) {
        DropTable drop = new DropTable();
        String drop1 = drop.drop(mdt, cmd);
        System.out.println(drop1);
      }

      //重命名表
      while (mrt.find()) {
        RenameTable rename = new RenameTable();
        String rename1 = rename.rename(mrt, cmd);
        System.out.println(rename1);
      }

      //改表
      while (mat.find()) {
        AlterTable alter = new AlterTable();
        String alter1 = alter.alter(mat, cmd);
        System.out.println(alter1);
      }

      //看一个库下所有表
      while (mst.find()) {
        ShowTables show = new ShowTables();
        List<String> show1 = show.show(cmd);
        System.out.println(show1);
      }

      //看表字段
      while (mdct.find()) {
        DescTable desc = new DescTable();
        List<String> desc1 = desc.desc(mdct, cmd);
        System.out.println(desc1);
      }

      //看建表语句
      while (msct.find()) {
        ShowCreateTable show_create = new ShowCreateTable();
        String s = show_create.show_create(msct, cmd);
        System.out.println(s);
      }

      //插入
      while (mi.find()) {
        Insert insert = new Insert();
        String insert1 = insert.insert(mi, cmd);
        System.out.println(insert1);
      }

      //删除
      while (md.find()) {
        Delete delete = new Delete();
        String delete1 = delete.delete(md, cmd);
        System.out.println(delete1);
      }

      //清空表
      while (mt.find()) {
        Truncate truncate = new Truncate();
        String truncate1 = truncate.truncate(mt, cmd);
        System.out.println(truncate1);
      }

      //修改
      while (mu.find()) {
        Update update = new Update();
        String update1 = update.update(mu, cmd);
        System.out.println(update1);
      }

      //查看
      while (ms.find()) {
        Select select = new Select();
        List<Data> select1 = select.select(ms, cmd);
        System.out.println(select1);
      }

      //授权
      while (mga.find()) {
        Grant grant = new Grant();
        String grant1 = grant.grant(mga, cmd);
        System.out.println(grant1);
      }

      //收回权限
      while (mra.find()) {
        Revoke revoke = new Revoke();
        String revoke1 = revoke.revoke(mra, cmd);
        System.out.println(revoke1);
      }

      //设置密码
      while (msp.find()) {
        SetPass setPass = new SetPass();
        String set = setPass.set(msp, cmd);
        System.out.println(set);
      }

      //删除用户
      while (mdu.find()) {
        DropUser drop_user = new DropUser();
        String s = drop_user.drop_user(mdu, cmd);
        System.out.println(s);
      }

      //创建用户
      while (mcu.find()) {
        CreateUser create_user = new CreateUser();
        String user = create_user.create_user(mcu, cmd);
        System.out.println(user);
      }

      //重命名用户
      while (mru.find()) {
        RenameUser rename_user = new RenameUser();
        String s = rename_user.rename_user(mru, cmd);
        System.out.println(s);
      }

      //看日志
      while (msl.find()) {
        SelectLog select_log = new SelectLog();
        List<Log> logs = select_log.select_log(msl, cmd);
        System.out.println(logs);
      }

      //清空日志
      while (mtl.find()) {
        TruncateLog truncate_log = new TruncateLog();
        String s = truncate_log.truncate_log(mtl, cmd);
        System.out.println(s);
      }

    }

    System.out.print("bye");
    System.exit(0);
  }


}
