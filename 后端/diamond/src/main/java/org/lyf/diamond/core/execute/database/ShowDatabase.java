package org.lyf.diamond.core.execute.database;

import org.lyf.diamond.cache.annotation.Caches;
import org.lyf.diamond.cache.entity.Storage;
import org.lyf.diamond.cache.execute.Cache;
import org.lyf.diamond.core.entity.data.Authority;
import org.lyf.diamond.core.execute.authority.UserParse;
import org.lyf.diamond.core.file.database.DatabaseFile;

import java.util.*;
import java.util.stream.Collectors;

public class ShowDatabase {

  public List<String> show(String cmd) {
//    System.out.println(Authority.getName());

//    String p = cmd.split(" ")[0];
//    String cheak = UserParse.cheak(Authority.getName(), "", "", p);
//    if (!cheak.equals(p)) {
//      List<String> list = new ArrayList<>();
//      list.add(cheak);
//      return list;
//    }
    Storage storage = Cache.get(cmd);
    if ("admin".equals(Authority.getName())) {
      if (storage == null) {
        Cache.set(cmd, new Storage(cmd, DatabaseFile.getDatabases(), 1000 * 30));
        return (List<String>) Cache.get(cmd).getValue();
      } else {
        return DatabaseFile.getDatabases();
      }
    } else {
      if (storage == null) {
        Cache.set(cmd, new Storage(cmd, getUserDatabase(), 1000 * 30));
        return (List<String>) Cache.get(cmd).getValue();
//        return getUserDatabase();
      } else {
        return getUserDatabase();
      }
    }
//    if ("admin".equals(Authority.getName())) {
//      return DatabaseFile.getDatabases();
//    } else {
//      return getUserDatabase();
//    }
  }

  private List<String> getUserDatabase() {
    List<String> list = new ArrayList<>();
    Map<String, List<Authority>> user = UserParse.findUser(Authority.getName());
    if (user == null) {
      return new ArrayList<>();
    }
    for (String s : user.keySet()) {
      List<Authority> authorities = user.get(s);
      authorities.forEach(vo -> {
        list.add(vo.getPurview().get(0));
      });
    }
    return list.stream().distinct().collect(Collectors.toList());
  }
/**
 * show databases;
 */

}
