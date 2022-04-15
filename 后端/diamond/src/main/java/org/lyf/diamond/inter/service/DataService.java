package org.lyf.diamond.inter.service;

import org.lyf.diamond.core.config.PathConfig;
import org.lyf.diamond.core.entity.auxiliary.Dictionary;
import org.lyf.diamond.core.entity.auxiliary.Regular;
import org.lyf.diamond.core.entity.data.Data;
import org.lyf.diamond.core.entity.data.Field;
import org.lyf.diamond.core.execute.data.DataParse;
import org.lyf.diamond.core.execute.data.delete.Delete;
import org.lyf.diamond.core.execute.data.delete.Truncate;
import org.lyf.diamond.core.execute.data.insert.Insert;
import org.lyf.diamond.core.execute.data.select.Select;
import org.lyf.diamond.core.execute.data.update.Update;
import org.lyf.diamond.core.execute.database.UseDatabase;
import org.lyf.diamond.core.file.table.TableFile;
import org.lyf.diamond.core.utile.TimeUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program:IntelliJ IDEA
 * @discription:数据服务
 * @author: GG-lyf
 * @create:2022-33-22.1.25 17:33:27
 */
@SuppressWarnings("all")
@Service
public class DataService {

//  private static final Pattern insert = Pattern.compile("insert\\s+into\\s+(\\w+)\\s+(\\(((\\w+,?)+)\\))?\\s?\\w+\\s?(\\(([^\\)]+,?)\\)((\\s+\\,\\s+(\\(([^\\)]+,?)\\))?)*)?);");
//  private static final Pattern delete = Pattern.compile("delete\\s+from\\s+(\\w+)(\\s+(where)\\s+(((\\s?(and|or)\\s?)*(\\w+(\\s+)?(((([<=>])|(\\<\\>)|(\\!\\=)|(\\>\\=)|(\\<\\=))(\\s+)?(([^\\s]+)?|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d))?))|(\\s?in\\s?\\((([^\\)]+,?)|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d))?)\\))?)))*)(\\s+)?)?;");
//  private static final Pattern truncate = Pattern.compile("truncate\\s+table\\s+((\\w+)((\\s+,\\s+(\\w+))*)?);");
//  private static final Pattern update = Pattern.compile("update\\s+(\\w+)\\s+set\\s+(\\w+\\s+?=\\s+(([^,\\s]+)?|((\\w+\\s+?([+|\\-|\\*|\\/])\\s+?(\\w+)|([0-9]{1,}[.][0-9]*))+)?|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d))?)(\\s+?,\\s+?\\w+\\s?=\\s+?(([^,\\s])|((\\w+(\\s+)?([+|\\-|\\*|\\/])(\\s+)?\\w+)+)?|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d)))+)*)(\\s+(where)\\s+(((\\s?(and|or)\\s?)*(\\w+(\\s+)?(((([<=>])|(\\<\\>)|(\\!\\=)|(\\>\\=)|(\\<\\=))(\\s+)?(([^\\s]+)?|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d))?))|(\\s?in\\s?\\((([^\\)]+,?)|(\\s?([1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d))?)\\))?)))*))?(\\s+)?;");
//  private static final Pattern select = Pattern.compile("select\\s+((\\*|((\\w+(\\.\\w+)?)+(\\s?,\\s?\\w+(\\.\\w+)?)*)|((\\w+\\([^\\s]+|(\\w+\\.\\w+)?\\))(\\s+\\,\\s+)?)+)\\s+from\\s+(\\w+(\\s?,\\s?\\w+)*\\;?)(\\s+((\\w+\\s+join)\\s+(\\w+(\\.\\w)?\\s+on\\s+(\\w+(\\.\\w+)?\\s+=\\s+\\w+(\\.\\w+)?)?)(\\s+,\\s+)?)+)?(\\s+((limit\\s+(\\w+)?\\s?(\\,\\w+\\;)?)|((where)?\\s+?([^\\;]+\\s?;))))?|((database|user|version)\\(\\));)");

  public String insert(String sql) {
    Matcher m = Regular.insert.matcher(sql + ";");
    boolean b = m.find();
    Insert insert = new Insert();
    return insert.insert(m, sql + ";");
  }

  public String delete(String sql) {
    Matcher m = Regular.delete.matcher(sql + ";");
    boolean b = m.find();
    Delete delete = new Delete();
    return delete.delete(m, sql);
  }

  public String truncate(String sql) {
    Matcher m = Regular.truncate.matcher(sql + ";");
    boolean b = m.find();
    Truncate truncate = new Truncate();
    return truncate.truncate(m, sql);
  }

  public String update(String sql) {
    Matcher m = Regular.update.matcher(sql + ";");
    boolean b = m.find();
    Update update = new Update();
    return update.update(m, sql);
  }

  public List<Data> select(String sql) {
    Matcher m = Regular.select.matcher(sql + ";");
    boolean b = m.find();
    Select select = new Select();
    List<Data> select1 = select.select(m, sql);
    if (select1 == null) {
      return select1;
    }
    return select1;
  }

  public Data findDict(String table) {
    String path = UseDatabase.getPath() + "\\" + table + "\\" + table + ".dict";
    return TableFile.getDict(path);
  }


}
