import {database_create, database_drop, database_show2, database_show, database_use, database_rename} from "../../axios/diamon"
import {table_create, table_drop, table_show, table_rename, table_alter, table_show_create, table_desc} from "../../axios/diamon"
import {data_insert, data_delete, data_select, data_update, data_truncate} from "../../axios/diamon"
import {authority_grant, authority_revoke, authority_create, authority_drop, authority_set, authority_rename} from "../../axios/diamon"
import {log_truncate, log_select} from "../../axios/diamon"

async function send(sql) {
  let split = sql.split(" ");
  console.log(split);
  let a = split[0] === "create";
  let b = split[0] === "use";
  let c = split[0] === "show";
  let d = split[0] === "drop";
  let e = split[0] === "rename";
  let f = split[0] === "alter";
  let g = split[0] === "desc";
  let h = split[0] === "insert";
  let i = split[0] === "delete";
  let j = split[0] === "truncate";
  let k = split[0] === "update";
  let l = split[0] === "select";
  let m = split[0] === "grant";
  let n = split[0] === "revoke";
  let o = split[0] === "set";

  let p = split[1] === "database";
  let q = split[1] === "table";
  let r = split[1] === "user";
  let s = split[1] === "log";

  let t = split[1] === "databases;";
  let u = split[1] === "tables;";
  let v = split[1] === "create";
  let back = "";
  if (a) {
    if (p) {//建数据库
      back = await database_create(sql);
    } else if (q) {//建表
      back = await table_create(sql);
    } else if (r) {//建用户
      back = await authority_create(sql);
    }
  } else if (b) {//指定表
    back = await database_use(sql);
  } else if (c) {
    if (t) {//显示数据库
      // console.log(sql);
      back = await database_show();
    } else if (u) {//显示表
      back = await table_show(sql);
    } else if (v) {//看建表语句
      back = await table_show_create(sql.replace("\\\G", ""));
    }
  } else if (d) {
    if (p) {//删除数据库
      back = await database_drop(sql);
    } else if (q) {//删除表
      back = await table_drop(sql);
    } else if (r) {//删除用户
      back = await authority_drop(sql);
    }
  } else if (e) {
    if (p) {//重命名数据库
      back = await database_rename(sql);
    } else if (q) {//重命名表
      back = await table_rename(sql);
    } else if (r) {//重命名用户
      back = await authority_rename(sql);
    }
  } else if (f) {
    if (q) {//修改表字段
      back = await table_alter(sql);
    }
  } else if (g) {//看表字段
    back = await table_desc(sql);
  } else if (h) {//插入数据
    back = await data_insert(sql);
  } else if (i) {//删除表数据
    back = await data_delete(sql);
  } else if (j) {
    if (q) {//清空表
      back = await data_truncate(sql);
    } else if (s) {//清空日志
      back = await log_truncate(sql);
    }
  } else if (k) {//更新表数据
    back = await data_update(sql);
  } else if (l) {
    if (s) {//查看日志
      back = await log_select(sql);
    } else {//查看表
      back = await data_select(sql);
    }
  } else if (m) {//给用户授权
    back = await authority_grant(sql);
  } else if (n) {//收回用户权限
    back = await authority_revoke(sql);
  } else if (o) {//设置用户密码
    back = await authority_set(sql);
  }

  // console.log(back);
  return back;
}


async function find(sqls) {
  let array = new Array();
  sqls.forEach((t, i) => {
    let stringPromise = send(t);
    array.push(stringPromise);
  });
  console.log(array[0]);
  return array;
}


function process(sql) {
  let split = sql.split("\n");
  let arr = [];
  split.forEach((t, i) => {
    let n_split = t.split(";").filter((x) => x !== '');
    n_split.forEach((t, i) => {
      arr.push(t)
    });
  });
  return find(arr);
}

export {
  send,
  process

}