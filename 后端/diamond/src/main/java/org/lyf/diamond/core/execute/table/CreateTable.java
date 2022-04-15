package org.lyf.diamond.core.execute.table;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.table.TableDataFile;
import org.lyf.diamond.core.file.table.TableFile;
import org.lyf.diamond.core.utile.FieldUtils;

import java.util.List;
import java.util.regex.Matcher;

@SuppressWarnings("all")
public class CreateTable {


  public String create(Matcher mct, String cmd) {
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      return Return.please_point_database;
    }

    String table_name = mct.group(1);

    String p = cmd.split(" ")[0];
    String[] split = UseDatabase.getPath().split("\\\\");
    String cheak = UserParse.cheak(Authority.getName(), split[split.length - 1], table_name, p);
    if (!cheak.equals(p)) {
      return cheak;
    }


    if (TableFile.judgeTable(table_name)) {
//      System.out.println(table_name + " is exit");
      return Return.table_is_exist;
    }
    String fields = mct.group(2);
    TableFile.makeTable(UseDatabase.getPath(), table_name);//建表文件夹
    Data data = FieldUtils.disposeField(fields);
    String path = UseDatabase.getPath() + "\\" + table_name + "\\" + table_name + ".dict";
    List<Field> fields1 = data.getFields();
    for (Field field : fields1) {
      String s1 = field.getName() + " " + field.getType() + " " + field.getIs_key();
      TableDataFile.write(path, s1, true);
    }
    return Return.create_table_ok;
  }

  /**
   use user1;
   use user2;
   create table user (id int primary key,name varchar);
   create table people (id int primary key,name varchar);
   create table dept (id int primary key,name varchar,now datetime,salary double,age int);
   create table person (id int primary key,name varchar,time datetime,salary double,age int);
   create table people (id int primary key,name varchar,now datetime,salary double,age int,sex int,phone int);
   */
}
