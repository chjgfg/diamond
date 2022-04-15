function show_tables() {
  return "show tables;";
}


function show_create_tables(name) {
  return "show create table " + name;
}


function alter_tables(num, obj) {
  let table = obj.table;
  let old_name = obj.old_name;
  let name = obj.name;
  let type = obj.type;
  if (num === -1) {
    return "alter table " + table + " add " + name + " " + type + ";";
  } else if (num === 0) {
    return "alter table " + table + " drop " + name + ";";
  } else if (num === 1) {
    return "alter table " + table + " change " + old_name + " " + name + " " + type + ";";
  } else if (num === 2) {
    return "alter table " + table + " modify " + name + " " + type + ";";
  }
}

function desc_tables(name) {
  return "desc " + name + ";";
}

function drop_tables(name) {
  return "drop table " + name + ";";
}

function rename_tables(name, n_nmae) {
  return "rename table " + name + " to " + n_nmae + ";";
}

function create_tables(name, obj) {
  if (name === "") {
    return;
  }
  let condition = "";
  for (let i = 0; i < obj.length; i++) {
    if (obj[i].type === "" || obj[i].name === "") {
      continue;
    }
    let s = obj[i].name + " " + obj[i].type;
    if (obj[i].is_key === "true") {
      s = s + " primary key";
    }
    if (i !== obj.length - 1) {
      s = s + ",";
    }
    condition += s;
  }
  // console.log(condition.endsWith(","));
  condition = condition.endsWith(",") ? condition.substring(0, condition.lastIndexOf(",")) : condition;
  // console.log(condition);
  return "create table " + name + " (" + condition + ");";
}

export {
  show_tables, alter_tables, desc_tables, show_create_tables, drop_tables, rename_tables, create_tables
}
