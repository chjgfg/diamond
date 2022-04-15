package org.lyf.diamond.core.utile;

import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.auxiliary.Dictionary;
import org.lyf.diamond.core.entity.data.Field;

import java.util.ArrayList;
import java.util.List;

public class FieldUtils {

  /**
   * 检查数据类型
   *
   * @param fields
   * @return
   */
  public static Data disposeField(String fields) {
    Data data = new Data();
    String[] split = fields.split(",");
    List<Field> fieldsList = new ArrayList<>();
    for (String s : split) {
      Field field = new Field();
      String[] s1 = s.split(" ");
      field.setName(s1[0]);
      if (isField(s1[1])) {
        field.setType(s1[1]);
        if (s1.length == 4) {
          field.setIs_key("*");
        } else {
          field.setIs_key("~");
        }
      }
      fieldsList.add(field);
    }
    data.setFields(fieldsList);
    return data;
  }

  /**
   * 判断数据类型
   *
   * @param field
   * @return
   */
  public static boolean isField(String field) {
    boolean flag;
    switch (field) {
      case Dictionary.inte:
      case Dictionary.doub:
      case Dictionary.bool:
      case Dictionary.varchar:
      case Dictionary.date:
        flag = true;
        break;
      default:
        flag = false;
        break;
    }
    return flag;
  }


}
