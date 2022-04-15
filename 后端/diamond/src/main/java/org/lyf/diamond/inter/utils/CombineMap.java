package org.lyf.diamond.inter.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program:IntelliJ IDEA
 * @discription:map中键一样的值合并
 * @author: GG-lyf
 * @create:2022-36-22.1.26 14:36:37
 */
@SuppressWarnings("all")
public class CombineMap {
  
  public static Map<String, List<String>> mapCombine(List<Map<String, String>> list) {
    Map<String, List<String>> map = new HashMap<>();
    for (Map<String, String> m : list) {
      Iterator<String> it = m.keySet().iterator();
      while (it.hasNext()) {
        String key = it.next();
        if (!map.containsKey(key)) {
          List newList = new ArrayList<>();
          newList.add(m.get(key));
          map.put(key, newList);
        } else {
          map.get(key).add(m.get(key));
        }
      }
    }
    return map;
  }
  
}
