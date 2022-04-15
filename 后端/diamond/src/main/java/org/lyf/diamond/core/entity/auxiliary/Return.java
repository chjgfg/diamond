package org.lyf.diamond.core.entity.auxiliary;

/**
 * @program:IntelliJ IDEA
 * @discription:返回字符串数据类
 * @author: GG-lyf
 * @create:2022-46-22.1.15 21:46:14
 */
@SuppressWarnings("all")
public class Return {
  public static final String database_not_found = "database does not found";//数据库没有找到

  public static final String create_database_ok = "create database is ok";//数据库创建成功

  public static final String create_database_error = "create database is error";//数据库没有找到

  public static final String drop_database_ok = "drop database is ok";//删除数据库成功

  public static final String drop_database_error = "drop database is error";//删除数据库失败

  public static final String rename_database_ok = "rename database is ok";//重命名数据库成功

  public static final String rename_database_error = "rename database is error";//重命名数据库失败

  public static final String use_database_ok = "use database is ok";//指定数据库成功

  public static final String please_point_database = "please point database";//没有指定数据库

  public static final String table_is_not_exist = "table is not exist";//表不存在

  public static final String table_is_exist = "table is also exist";//表存在

  public static final String create_table_ok = "create table ok";//建表成功

  public static final String rename_ok = "rename table ok";//成功重命名表

  public static final String insert_ok = "insert ok";//插入数据成功

  public static final String insert_error = "insert error";//插入数据失败

  public static final String delete_ok = "delete ok";//删除数据成功

  public static final String clean_all_success = "clean all success";//清空数据成功

  public static final String clean_all_errer = "clean all errer";//清空数据失败

  public static final String update_ok = "update ok";//修改数据成功

  public static final String update_errer = "update errer";//修改数据失败

  public static final String field_is_not_exist = "delete error , field is not exist";//删除失败,字段不存在

  public static final String key_is_exist = "key is exists";//键不存在

  public static final String data_type_is_error = "data type is error";//数据类型错误

  public static final String data_mismatch = "insert error , data mismatch";//数据不匹配

  public static final String data_mismatch2 = "update error , data mismatch";//数据不匹配

  public static final String the_primary_key_repeat = "the primary key repeat";//主键重复

  public static final String drop_ok = "drop field ok";//删除字段成功

  public static final String drop_error = "drop field error";//删除失败

  public static final String modify_ok = "modify field ok";//修改成功

  public static final String user_not_found = "user does not found";//用户没有找到

  public static final String user_log_success = "user logged successfully";//用户登录成功

  public static final String user_is_exist = "user is exist";//用户已存在

  public static final String user_dose_not_exist = "user dose not exist";//用户不存在

  public static final String create_user_ok = "create user ok";//用户创建成功

  public static final String create_user_error = "create user error";//用户创建失败

  public static final String drop_user_ok = "drop user ok";//删除用户成功

  public static final String authority_is_not_exist = "authority isn't exist";//权限不存在

  public static final String do_not_have_authority = "do not have authority";//没有权限

  public static final String do_not_give_authority_to_yourself = "don't give authority to yourself";//不能给自己赋权

  public static final String database_or_table_is_not_exist = "database or table is not exist";//库或表不存在

  public static final String authorization_success = "authorization success";//授权成功

  public static final String user_name_is_null = "user' name is null";//用户名不存在

  public static final String user_cannot_be_deleted = "user cannot be deleted";//用户不能被删除

  public static final String drop_user_error = "drop user error";//删除用户失败

  public static final String rename_user_ok = "rename user ok";//重命名用户成功

  public static final String have_deleted_all_permissions = "have deleted all permissions";//已删除所有权限

  public static final String rename_user_error = "rename user error";//重命名用户失败

  public static final String do_not_have_any_permission = "don't have any permission";//没有任何许可

  public static final String have_deleted_some_permissions = "have deleted some permissions";//删除部分权限

  public static final String set_password_ok = "set password ok";//设置密码成功

  public static final String set_password_error = "set password error";//设置密码失败

  public static final String user_password_error = "user password error";//密码错误

  public static final String select_ok = "select ok";//查询数据成功

  public static final String log_is_not_exist = "log is not exist";//日志不存在

  public static final String clearn_log_ok = "clearn log ok";//删除日志成功

  public static final String clearn_log_error = "clearn log error";//删除日志失败

  public static final String sql_is_error = "sql is error";//语句错误

  public static final String unknown_error = "unknown error";//未知错误

  public static final String not_unique_table_or_alias = "not unique table/alias";//不是唯一的表/别名

  public static final String add_ok = "add ok";//添加字段成功

  public static final String change_ok = "change ok";//改变字段成功

}
