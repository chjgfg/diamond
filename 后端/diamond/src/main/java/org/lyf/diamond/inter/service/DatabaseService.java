package org.lyf.diamond.inter.service;

import org.lyf.diamond.core.entity.auxiliary.Regular;
import org.lyf.diamond.core.execute.database.CreateDatabase;
import org.lyf.diamond.core.execute.database.DropDatabase;
import org.lyf.diamond.core.execute.database.RenameDatabase;
import org.lyf.diamond.core.execute.database.ShowDatabase;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program:IntelliJ IDEA
 * @discription:数据库文件服务
 * @author: GG-lyf
 * @create:2022-30-22.1.25 17:30:51
 */
@SuppressWarnings("all")
@Service
public class DatabaseService {
//
//  private static final Pattern create_database = Pattern.compile("create\\s+database\\s+(([^\\s+]+(\\s+,\\s+)?)*);");
//  private static final Pattern use_database = Pattern.compile("use\\s+(\\w+);");
//  private static final Pattern show_database = Pattern.compile("show\\s+databases;");
//  private static final Pattern drop_database = Pattern.compile("drop\\s+database\\s+(([^\\s+]+(\\s+,\\s+)?)*);");
//  private static final Pattern rename_database = Pattern.compile("rename\\s+database\\s+(\\w+)\\s+to\\s+(\\w+);");

  public String create(String sql) {
    Matcher mcd = Regular.create_database.matcher(sql + ";");
    boolean b = mcd.find();
    CreateDatabase createDatabase = new CreateDatabase();
    return createDatabase.create(mcd, sql + ";");
  }


  public String use(String sql) {
    Matcher mcd = Regular.use_database.matcher(sql + ";");
    boolean b = mcd.find();
    UseDatabase useDatabase = new UseDatabase();
    return useDatabase.use(mcd, sql + ";");
  }

  public String drop(String sql) {
    Matcher mcd = Regular.drop_database.matcher(sql + ";");
    boolean b = mcd.find();
    DropDatabase dropDatabase = new DropDatabase();
    return dropDatabase.drop(mcd, sql + ";");
  }

  public List<String> show() {
    ShowDatabase showDatabase = new ShowDatabase();
    return showDatabase.show("show databases;");
  }

  public String rename(String sql) {
    Matcher mcd = Regular.rename_database.matcher(sql + ";");
    boolean b = mcd.find();
    RenameDatabase renameDatabase = new RenameDatabase();
    return renameDatabase.rename(mcd, sql + ";");
  }


}
