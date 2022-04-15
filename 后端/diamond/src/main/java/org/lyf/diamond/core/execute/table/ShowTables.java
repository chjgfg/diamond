package org.lyf.diamond.core.execute.table;

import org.lyf.diamond.cache.entity.Storage;
import org.lyf.diamond.cache.execute.Cache;
import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Return;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.database.DatabaseFile;
import org.lyf.diamond.core.file.table.TableFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
@SuppressWarnings("all")
public class ShowTables {

  public List<String> show(String cmd) {

//    String p = cmd.split(" ")[0];
//    String cheak = UserParse.cheak(Authority.getName(), "", "", p);
//    if (!cheak.equals(p)) {
//      List<String> list = new ArrayList<>();
//      list.add(cheak);
//      return list;
//    }


    List<String> tables = new ArrayList<String>();
    if (UseDatabase.getPath().equals(PathConfig.dataPath)) {
//      System.out.println("point database");
      tables.add(Return.please_point_database);
      return tables;
    }
//    tables = TableFile.getTables();

    Storage storage = Cache.get(cmd);
    if ("admin".equals(Authority.getName())) {
      tables = TableFile.getTables();
      if (storage == null) {
//        System.out.println("admin 的 storage == null");
        Cache.set(cmd, new Storage(cmd, tables, 1000 * 30));
        tables = (List<String>) Cache.get(cmd).getValue();
      } else {
//        System.out.println("admin 的 storage != null");
        return tables;
      }
    } else {
      tables = findTables();
      if (storage == null) {
//        System.out.println("user 的 storage == null");
        Cache.set(cmd, new Storage(cmd, tables, 1000 * 30));
        tables = (List<String>) Cache.get(cmd).getValue();
      } else {
//        System.out.println("user 的 storage != null");
        return tables;
      }
    }
//    System.out.println(tables);
    return tables;
  }

  public List<String> findTables() {
    List<String> list = new ArrayList<>();
    Map<String, List<Authority>> user = UserParse.findUser(Authority.getName());
    assert user != null;
    for (String s : user.keySet()) {
      List<Authority> authorities = user.get(s);
//      System.out.println(authorities);
//      System.out.println(UseDatabase.getPath());
      String[] split = UseDatabase.getPath().split("\\\\");
      String s2 = split[split.length - 1];
//      System.out.println(s2);
      authorities.forEach(vo -> {
        List<String> purview = vo.getPurview();
        String s1 = purview.get(1);
        if (s2.equals(purview.get(0))) {
          list.add(s1);
        }
      });
    }
    return list;
  }
/**
 * show tables;
 */

}
