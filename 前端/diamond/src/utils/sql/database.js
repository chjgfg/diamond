function use_database(name) {
  return "use " + name + ";";
}

function create_database(name) {
  return "create database " + name + ";";
}

function drop_database(name) {
  return "drop database " + name + ";";
}


function rename_database(name1, name2) {
  return "rename database " + name1 + " to " + name2 + ";";
}




export {
  use_database, create_database, drop_database, rename_database
}
