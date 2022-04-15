import request from "./request";
import request2 from "./request2";
import {get} from "../utils/local"

export const database_create = (data) => request(`database/create/${data}`, "", "post");
export const database_drop = (data) => request(`database/drop/${data}`, "", "post");
export const database_show = () => request(`database/show`, "", "post");
export const database_show2 = (data) => request(`database/show/${data}`, "", "post");
export const database_use = (data) => request(`database/use/${data}`, "", "post");
export const database_rename = (data) => request(`database/rename/${data}`, "", "post");

export const table_create = (data) => request(`table/create/${data}`, "", "post");
export const table_drop = (data) => request(`table/drop/${data}`, "", "post");
export const table_show = (data) => request(`table/show/${data}`, "", "post");
export const table_dict = (database, name) => request(`table/dict//${database}/${name}`, "", "post");
export const table_rename = (data) => request(`table/rename/${data}`, "", "post");
export const table_alter = (data) => request(`table/alter/${data}`, "", "post");
export const table_show_create = (data) => request(`table/show_create/${data}`, "", "post");//不能传 \G
export const table_desc = (data) => request(`table/desc/${data}`, "", "post");

export const data_select = (data) => request(`data/select/${data}`, "", "post");
export const data_insert = (data) => request(`data/insert/${data}`, "", "post");
export const data_delete = (data) => request(`data/delete/${data}`, "", "post");
export const data_update = (data) => request(`data/update/${data}`, "", "post");
export const data_truncate = (data) => request(`data/truncate/${data}`, "", "post");

export const authority_grant = (data) => request(`authority/grant/${get("user").name}/${data}`, "", "post");//有bug
export const authority_revoke = (data) => request(`authority/revoke/${get("user").name}/${data}`, "", "post");
export const authority_create = (data) => request(`authority/create/${get("user").name}/${data}`, "", "post");
export const authority_drop = (data) => request(`authority/drop/${get("user").name}/${data}`, "", "post");
export const authority_set = (data) => request(`authority/set/${get("user").name}/${data}`, "", "post");
export const authority_rename = (data) => request(`authority/rename/${get("user").name}/${data}`, "", "post");
export const authority_login = ({user, pass}) => request('authority/login', {user, pass}, "post");
export const authority_logout = () => request('authority/logout', {}, "post");
export const authority_find = ({name}) => request('authority/find', {name}, "post");
export const authority_find_other = (name) => request(`authority/find_other/${name}`, "", "post");
export const authority_find_one_by_name = (name) => request(`authority/find_one_by_name/${name}`, "", "post");
export const authority_find_pass_by_name = (name) => request(`authority/find_pass_by_name/${name}`, "", "post");

export const log_truncate = (data) => request(`log/truncate/${data}`, "", "post");
export const log_select = (data) => request(`log/select/${data}`, "", "post");
export const log_find_one = (database, table) => request(`log/find_one/${database}/${table}`, "", "post");

export const send_email = (title, context) => request(`email/send/${title}/${context}`, "", "post");

export const ooo = () => request2("https://globalcharge.oss-cn-hongkong.aliyuncs.com/prod/wechat/jsonInfo/flag.json", "", "get");