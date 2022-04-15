package org.lyf.diamond.frame.process;


import org.lyf.diamond.frame.annotation.TableField;
import org.lyf.diamond.frame.annotation.TableName;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @program: some_middle
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-03 15:38:52
 */
//@SuppressWarnings("all")
public class AnnotationProcess {

  public static String getTableName(Class<?> clazz) {
    String name = clazz.getSimpleName();
    String name1 = clazz.getAnnotation(TableName.class).name();
//    if (name1 == null) {
//      name = name.toLowerCase();
//    } else {
//      name = name1;
//    }
//    name = (name1 == null) ? name.toLowerCase() : name1;
    return (name1.isEmpty()) ? name.toLowerCase() : name1;
  }


  public static String getTableField(Field clazz) {
    String name = clazz.getName();
    Class<?> type = clazz.getType();
    TableField annotation = clazz.getAnnotation(TableField.class);
    String field = "";
    boolean aNull = true, key = false;
    String comment = "";
    if (annotation != null) {
      String value = annotation.value();
      comment = annotation.comment();
      key = annotation.key();
      aNull = annotation.is_null();
      if (value != null) {
        name = value;
      }
    }
    if (type == int.class || type == Integer.class) {
      field += name + " int(10) " + (!aNull ? "not null " : "") + (key ? "primary key " : "") + (comment != "" ? "comment '" + comment + "'" : "");
    } else if (type == Long.class || type == long.class) {
      field += name + " bigint(10) " + (!aNull ? "not null " : "") + (key ? "primary key " : "") + (comment != "" ? "comment '" + comment + "'" : "");
    } else if (type == float.class || type == Float.class) {
      field += name + " float(10) " + (!aNull ? "not null " : "") + (key ? "primary key " : "") + (comment != "" ? "comment '" + comment + "'" : "");
    } else if (type == double.class || type == Double.class) {
      field += name + " double(10) " + (!aNull ? "not null " : "") + (key ? "primary key " : "") + (comment != "" ? "comment '" + comment + "'" : "");
    } else if (type == boolean.class || type == Boolean.class) {
      field += name + " int(1) " + (!aNull ? "not null " : "") + (key ? "primary key " : "") + (comment != "" ? "comment '" + comment + "'" : "");
    } else if (type == String.class) {
      field += name + " varchar(255) " + (!aNull ? "not null " : "") + (key ? "primary key " : "") + (comment != "" ? "comment '" + comment + "'" : "");
    } else if (type == Date.class) {
      field += name + " time " + (!aNull ? "not null " : "") + (key ? "primary key " : "") + (comment != "" ? "comment '" + comment + "'" : "");
    }
    return field;
  }


  public static String compare(Field clazz) {
    Class<?> type = clazz.getType();
    if (type == int.class || type == Integer.class) {
      return "0";
    } else if (type == Long.class || type == long.class) {
      return "0";
    } else if (type == float.class || type == Float.class) {
      return "0.0";
    } else if (type == double.class || type == Double.class) {
      return "0.0";
    } else if (type == boolean.class || type == Boolean.class) {
      return "false";
    } else if (type == String.class) {
      return "null";
    } else if (type == Date.class) {
      return "null";
    }
    return "null";
  }

  public static String getFieldName(Field clazz) {
    String name = clazz.getName();
    TableField annotation = clazz.getAnnotation(TableField.class);
    if (annotation != null) {
      String value = annotation.value();
      if (value != null) {
        name = value;
      }
    }
    return name;
  }

}
