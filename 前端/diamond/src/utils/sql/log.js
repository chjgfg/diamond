function truncate_log(database, table) {
    return "truncate log " + database + "." + table + ";";
}

export {
    truncate_log
}
