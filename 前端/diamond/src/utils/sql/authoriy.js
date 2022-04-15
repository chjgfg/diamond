/**
 * 添加或修改权限
 * @param user
 * @param num
 * @returns {string}
 */
function add_authoriy(user, num) {
  console.log((user.purview).constructor === String);
  console.log(user.purview);
  let authority = user.authority;
  let key = user.key === "" ? "" : " with grant option";
  let purview = "", purview1 = user.purview;
  let d_t = user.database + "." + user.table;
  if (purview1 === "" || ((user.purview).constructor === String && purview1.startsWith("all"))) {
    purview = "all privileges , grant option "
  } else {
    if ((user.purview).constructor === String) {//添加
      purview1 = purview1.split(",");
    }
    for (let i = 0; i < purview1.length; i++) {
      if (i === purview1.length - 1) {
        purview = purview + purview1[i];
      } else {
        purview = purview + purview1[i] + " , ";
      }
    }
  }
  let sql = "";
  if (num === -1) {
    sql = "grant " + purview + " on " + d_t + " to " + user.user + (user.pass !== "" ? " identified by '" + user.pass : "") + "'" + key + ";";
  } else if (num === 0) {
    sql = "revoke " + purview + (user.purview === "" ? "" : " on " + d_t) + " from " + user.user + ";";
  }
  console.log(sql);
  return sql;
}

function drop_user(user) {
  return "drop user " + user.user + ";";
}


function add_user(user) {
  return "create user " + user.user + (user.pass === "" ? "" : " identified by '" + user.pass + "'") + ";";
}


function rename_user(user) {
  return "rename user " + user.user + " to " + user.n_name + ";";
}


function set_user(user) {
  return "set password for " + user.user + " = password('" + user.pass + "');";
}


export {
  add_authoriy,
  drop_user,
  add_user,
  rename_user,
  set_user
}
