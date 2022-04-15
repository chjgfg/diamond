export function set(flag, name, pass) {
  const user = {
    flag: flag,
    name: name,
    pass: pass
  };
  localStorage.setItem("user", JSON.stringify(user));
}

export function get(user) {
  return JSON.parse(localStorage.getItem(user))
}


export function remove(user) {
  localStorage.removeItem(user)
}


export function set_database(database) {
  localStorage.setItem("database", JSON.stringify(database));
}
