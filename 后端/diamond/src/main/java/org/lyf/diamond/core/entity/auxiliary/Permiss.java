package org.lyf.diamond.core.entity.auxiliary;

import java.util.ArrayList;
import java.util.List;

/**
 * @program:IntelliJ IDEA
 * @discription:权限类
 * @author: GG-lyf
 * @create:2022-35-22.1.13 22:35:51
 */
@SuppressWarnings("all")
public class Permiss {

  /**
   * create(database|table|user)
   */
  public static final String create = "create";

  /**
   * use(table)
   */
  public static final String use = "use";

  /**
   * show(database|table)
   */
  public static final String show = "show";

  /**
   * drop(database|table|user)
   */
  public static final String drop = "drop";

  /**
   * rename(database|table|user)
   */
  public static final String rename = "rename";

  /**
   * alter(table)
   */
  public static final String alter = "alter";

  /**
   * desc(table)
   */
  public static final String desc = "desc";

  /**
   * insert(table)
   */
  public static final String insert = "insert";

  /**
   * delete(table)
   */
  public static final String delete = "delete";

  /**
   * truncate(table|log)
   */
  public static final String truncate = "truncate";

  /**
   * update(table)
   */
  public static final String update = "update";

  /**
   * select(table|log)
   */
  public static final String select = "select";

  /**
   * grant(user)
   */
  public static final String grant = "grant";

  /**
   * revoke(user)
   */
  public static final String revoke = "revoke";

  /**
   * set(user)
   */
  public static final String set = "set";

  /**
   * 获取所有的权限
   * @return
   */
  public static final List<String> getAllPermiss() {
    List<String> strings = new ArrayList<>();
    strings.add(create);
    strings.add(use);
    strings.add(show);
    strings.add(drop);
    strings.add(rename);
    strings.add(alter);
    strings.add(desc);
    strings.add(insert);
    strings.add(delete);
    strings.add(truncate);
    strings.add(update);
    strings.add(select);
    strings.add(grant);
    strings.add(revoke);
    strings.add(set);
    return strings;
  }

}
