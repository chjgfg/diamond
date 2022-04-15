package org.lyf.diamond.core.execute.table;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.table.TableFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @program:IntelliJ IDEA
 * @discription:看表字段
 * @author: GG-lyf
 * @create:2022-17-22.1.3 16:17:19
 */
@SuppressWarnings("all")
public class DescTable {
  public List<String> desc(Matcher mdct, String cmd) {


    String table_name = mdct.group(1);

    String p = cmd.split(" ")[0];
    String[] split = UseDatabase.getPath().split("\\\\");
    String cheak = UserParse.cheak(Authority.getName(), split[split.length - 1], table_name, p);
    if (!cheak.equals(p)) {
      List<String> list = new ArrayList<>();
      list.add(cheak);
      return list;
    }


    List<String> kk = new ArrayList<String>();
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      kk.add(Return.please_point_database);
      return kk;
    }

    if (!TableFile.judgeTable(table_name)) {
//      System.out.println(table_name + " is not exit");
      kk.add(Return.table_is_not_exist);
      return kk;
    }
    Data dict = TableFile.getDict(UseDatabase.getPath() + "\\" + table_name + "\\" + table_name + ".dict");
    if (dict ==null){
//      System.out.println(Return.table_is_not_exist);
      kk.add(Return.table_is_not_exist);
      return kk;
    }
    Iterator<Field> iterator = dict.getFields().stream().iterator();
    while (iterator.hasNext()) {
      Field next = iterator.next();
      kk.add(next.getName() + "\t" + next.getType() + "\t" + next.getIs_key());
    }
//    kk.forEach(System.out::println);
    return kk;
  }

  /**
   * desc dept;
   */
}
