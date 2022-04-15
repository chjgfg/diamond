package org.lyf.diamond.inter.service;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Regular;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.execute.table.AlterTable;
import org.lyf.diamond.core.execute.table.CreateTable;
import org.lyf.diamond.core.execute.table.DescTable;
import org.lyf.diamond.core.execute.table.DropTable;
import org.lyf.diamond.core.execute.table.RenameTable;
import org.lyf.diamond.core.execute.table.ShowCreateTable;
import org.lyf.diamond.core.execute.table.ShowTables;
import org.lyf.diamond.core.file.table.TableFile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program:IntelliJ IDEA
 * @discription:表文件服务
 * @author: GG-lyf
 * @create:2022-32-22.1.25 17:32:08
 */
@SuppressWarnings("all")
@Service
public class TableService {

//  private static final Pattern create_table = Pattern.compile("create\\s+table\\s+(\\w+)\\s+\\(((?:\\s?\\w+\\s+\\w+,?)+)\\);");
//  private static final Pattern drop_table = Pattern.compile("drop\\s+table\\s+((\\w+(\\s+\\,\\s+)?)*);");
//  private static final Pattern rename_table = Pattern.compile("rename\\s+table\\s+(\\w+)\\s+to\\s+(\\w+);");
//  private static final Pattern alter_table = Pattern.compile("alter\\s+table\\s+(\\w+)\\s+(\\w+)\\s+((\\w+)\\s?(\\w+)?\\s?(\\w+)?)\\s?(is\\s+primrykey)?;");
//  private static final Pattern show_table = Pattern.compile("show\\s+tables;");
//  private static final Pattern desc_table = Pattern.compile("desc\\s+(\\w+)?;");
//  private static final Pattern show_create_table = Pattern.compile("show\\s+create\\s+table\\s+(\\w+)\\s+\\\\G;");


  public String create(String sql) {
    Matcher m = Regular.create_table.matcher(sql + ";");
    boolean b = m.find();
    CreateTable createTable = new CreateTable();
    return createTable.create(m, sql + ";");
  }

  public String drop(String sql) {
    Matcher m = Regular.drop_table.matcher(sql + ";");
    boolean b = m.find();
    DropTable dropTable = new DropTable();
    return dropTable.drop(m, sql + ";");
  }

  public String rename(String sql) {
    Matcher m = Regular.rename_table.matcher(sql + ";");
    boolean b = m.find();
    RenameTable renameTable = new RenameTable();
    return renameTable.rename(m, sql + ";");
  }

  public String alter(String sql) {
    Matcher m = Regular.alter_table.matcher(sql + ";");
    boolean b = m.find();
    AlterTable alterTable = new AlterTable();
    return alterTable.alter(m, sql + ";");
  }

  //  public List<String> show(String name, String sql) {
  public List<String> show(String sql) {
    Matcher m = Regular.show_table.matcher(sql + ";");
    boolean b = m.find();
//    if (name == "") {
//      ShowTables showTables = new ShowTables();
//      return showTables.show();
//    } else {
//      File file = new File(PathConfig.dataPath + name);
//      return Arrays.asList(Objects.requireNonNull(file.list()));
//    }
    ShowTables showTables = new ShowTables();
    return showTables.show("show tables;");
  }


  public Data find_dict(String database, String table_name) {
    return TableFile.getDict(PathConfig.dataPath + database + "\\" + table_name + "\\" + table_name + ".dict");
  }


  public List<String> desc(String sql) {
    Matcher m = Regular.desc_table.matcher(sql + ";");
    boolean b = m.find();
    DescTable descTable = new DescTable();
    return descTable.desc(m, sql + ";");
  }

  public String show_create(String sql) {
    Matcher m = Regular.show_create_table.matcher(sql + " \\G;");
    boolean b = m.find();
    ShowCreateTable showCreateTable = new ShowCreateTable();
    return showCreateTable.show_create(m, sql + ";");
  }


}
