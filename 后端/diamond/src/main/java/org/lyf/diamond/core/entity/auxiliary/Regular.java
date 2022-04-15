package org.lyf.diamond.core.entity.auxiliary;

import java.util.regex.Pattern;

/**
 * @program: diamond
 * @description:
 * @author: GG-lyf
 * @create: 2022-04-05 06:20:27
 */
@SuppressWarnings("all")
public class Regular {
  //库
  public static final Pattern create_database = Pattern.compile("create\\s+database\\s+(([^\\s+]+(\\s+,\\s+)?)*);");
  public static final Pattern use_database = Pattern.compile("use\\s+(\\w+);");
  public static final Pattern show_database = Pattern.compile("show\\s+databases;");
  public static final Pattern drop_database = Pattern.compile("drop\\s+database\\s+(([^\\s+]+(\\s+,\\s+)?)*);");
  public static final Pattern rename_database = Pattern.compile("rename\\s+database\\s+(\\w+)\\s+to\\s+(\\w+);");
  //表
  public static final Pattern create_table = Pattern.compile("create\\s+table\\s+(\\w+)\\s+\\(((?:\\s?\\w+\\s+\\w+,?)+)\\);");
  public static final Pattern drop_table = Pattern.compile("drop\\s+table\\s+((\\w+(\\s+\\,\\s+)?)*);");
  public static final Pattern rename_table = Pattern.compile("rename\\s+table\\s+(\\w+)\\s+to\\s+(\\w+);");
  public static final Pattern alter_table = Pattern.compile("alter\\s+table\\s+(\\w+)\\s+(\\w+)\\s+((\\w+)\\s?(\\w+)?\\s?(\\w+)?)\\s?(is\\s+primrykey)?;");
  public static final Pattern show_table = Pattern.compile("show\\s+tables;");
  public static final Pattern desc_table = Pattern.compile("desc\\s+(\\w+)?;");
  public static final Pattern show_create_table = Pattern.compile("show\\s+create\\s+table\\s+(\\w+)\\s+\\\\G;");
  //数据
  public static final Pattern insert = Pattern.compile("insert\\s+into\\s+(\\w+)\\s+(\\(((\\w+,?)+)\\))?\\s?\\w+\\s?(\\(([^\\)]+,?)\\)((\\s+\\,\\s+(\\(([^\\)]+,?)\\))?)*)?);");
  public static final Pattern delete = Pattern.compile("delete\\s+from\\s+(\\w+)(\\s+(where)\\s+(((\\s?(and|or)\\s?)*(\\w+(\\s+)?(((([<=>])|(\\<\\>)|(\\!\\=)|(\\>\\=)|(\\<\\=))(\\s+)?(([^\\s]+)?|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d))?))|(\\s?in\\s?\\((([^\\)]+,?)|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d))?)\\))?)))*)(\\s+)?)?;");
  public static final Pattern truncate = Pattern.compile("truncate\\s+table\\s+((\\w+)((\\s+,\\s+(\\w+))*)?);");
  public static final Pattern update = Pattern.compile("update\\s+(\\w+)\\s+set\\s+(\\w+\\s+?=\\s+(([^,\\s]+)?|((\\w+\\s+?([+|\\-|\\*|\\/])\\s+?(\\w+)|([0-9]{1,}[.][0-9]*))+)?|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d))?)(\\s+?,\\s+?\\w+\\s?=\\s+?(([^,\\s])|((\\w+(\\s+)?([+|\\-|\\*|\\/])(\\s+)?\\w+)+)?|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d)))+)*)(\\s+(where)\\s+(((\\s?(and|or)\\s?)*(\\w+(\\s+)?(((([<=>])|(\\<\\>)|(\\!\\=)|(\\>\\=)|(\\<\\=))(\\s+)?(([^\\s]+)?|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d))?))|(\\s?in\\s?\\((([^\\)]+,?)|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d))?)\\))?)))*))?(\\s+)?;");
  public static final Pattern select = Pattern.compile("select\\s+((\\*|((\\w+(\\.\\w+)?)+(\\s?,\\s?\\w+(\\.\\w+)?)*)|((\\w+\\([^\\s]+|(\\w+\\.\\w+)?\\))(\\s+\\,\\s+)?)+)\\s+from\\s+(\\w+(\\s?,\\s?\\w+)*\\;?)(\\s+((\\w+\\s+join)\\s+(\\w+(\\.\\w)?\\s+on\\s+(\\w+(\\.\\w+)?\\s+=\\s+\\w+(\\.\\w+)?)?)(\\s+,\\s+)?)+)?(\\s+((limit\\s+(\\w+)?\\s?(\\,\\w+\\;)?)|((where)?\\s+?([^\\;]+\\s?;))))?|((database|user|version)\\(\\));)");
  //授权
  public static final Pattern grant_admin = Pattern.compile("grant\\s+((all\\s+privileges)|(([^\\s+]+(\\s+,\\s+)?)*))\\s+on\\s+(([^\\s]+(\\s+\\,\\s+)?)*)\\s+to\\s+((([^\\s]+(\\s+\\,\\s+)?)*))(\\s+identified\\s+by\\s+(\\'([^\\s]+)\\'))?(\\s+with grant option)?;");
  public static final Pattern revoke_admin = Pattern.compile("revoke\\s+((all\\s+privileges\\s+,\\s+grant\\s+option)\\s+|((((([^\\s]+)*(\\s+\\,\\s+[^\\s]+)*?)*)\\s+on\\s+(([^\\s]+(\\s+\\,\\s+)?)*))\\s+))from\\s+(([^\\s]+(\\s+\\,\\s+)?)*);");
  public static final Pattern set_pass = Pattern.compile("set\\s+password\\s+(for\\s+([^\\s+]+)\\s+)?\\=\\s+password\\(\\'(\\w+)\\'\\);");
  public static final Pattern drop_user = Pattern.compile("drop\\s+user\\s+((\\w+(\\s+\\,\\s+)?)*);");
  public static final Pattern create_user = Pattern.compile("create\\s+user\\s+(\\w+)\\s+identified\\s+by\\s+([^\\s+]+);");
  public static final Pattern rename_user = Pattern.compile("rename\\s+user\\s+(\\w+)\\s+to\\s+(\\w+);");
  //日志
  public static final Pattern select_log = Pattern.compile("select\\s+(log)\\s+from+\\s+([^\\s+]+)(\\s+order\\s+by\\s+(date)\\s+(asc|desc))?;");
  public static final Pattern truncate_log = Pattern.compile("truncate\\s+log\\s+([^\\s+]+);");




}
