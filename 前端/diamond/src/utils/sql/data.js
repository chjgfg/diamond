function select_data(name) {
  return "select * from " + name + ";";
}

function update_data(table, obj) {
  let key = {};
  let second2 = "";
  let l = obj[0].length;
  obj[0].forEach((t, i) => {
    if (t.is_key === "*") {
      key = t;
    } else {
      if (i === 0) {
        key = t;
      }
    }
    if (i !== 0) {
      if (t.type === "varchar") {
        second2 += t.name + " = " + "'" + t.value + "'";
      } else {
        second2 += t.name + " = " + t.value;
      }
      if (i !== l - 1) {
        second2 += " , ";
      }
    }
  });
  return "update " + table + " set " + second2 + " where " + key.name + " = " + key.value + ";";

}

function delete_data(table, obj) {
  console.log(obj);
  let key = {};
  let l = obj[0].length;
  obj[0].forEach((t, i) => {
    if (t.is_key === "*") {
      key = t;
    } else {
      if (i === 0) {
        key = t;
      }
    }

  });
  return "delete from " + table + " where " + key.name + " = " + key.value + ";";
}

function insert_data(table, obj) {
  let first1 = "";
  let second1 = "";
  let l = obj[0].length;
  obj[0].forEach((t, i) => {
    first1 += t.name;
    if (t.type === "varchar") {
      second1 += "'" + t.value + "'";
    } else {
      second1 += t.value;
    }
    if (i !== l - 1) {
      first1 += ",";
      second1 += ",";
    }
  });
  return "insert into " + table + " (" + first1 + ") values (" + second1 + ");";
}

function truncate_database(name2) {
  return "truncate table " + name2 + ";";
}

export {
  select_data, update_data, delete_data, insert_data, truncate_database
}
