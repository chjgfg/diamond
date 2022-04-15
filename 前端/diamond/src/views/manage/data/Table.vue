<template>
  <v-container fluid>

    <v-snackbar color="red " outlined light tile centered v-model="snackbar" :timeout="2000">
      {{ text }}
      <template v-slot:action="{ attrs }">
        <v-btn color="blue" text v-bind="attrs" @click="snackbar = false">关闭</v-btn>
      </template>
    </v-snackbar>

    <v-card class="mx-auto" min-height="620">
      <template>
        <v-toolbar flat color=" " class="mb-1">
          <v-toolbar-title>
            <v-breadcrumbs :items="item"/>
          </v-toolbar-title>
          <v-divider class="mx-4" inset vertical/>
          <v-spacer/><!--间隔-->
          <v-btn color="primary" dark class="mb-2" @click="add_one">添加</v-btn><!--table_create-->
        </v-toolbar>
      </template>


      <template>
        <v-row>
          <v-col cols="12" sm="6" md="4" lg="3" v-for="(t,i) in table">
            <v-card @click="goToData(t)">
              <v-card-title>
                <v-row>
                  <v-col style="color:#6777ef" cols="7" sm="7" md="7">
                    {{t.name }}
                  </v-col>
                  <v-col cols="1" sm="1" md="1">
                    <v-tooltip top>
                      <template v-slot:activator="{ on, attrs }">
                        <v-icon small class="" color="#6777ef" v-bind="attrs" v-on="on" @click.stop="rename_one(t)">
                          mdi-human-edit
                        </v-icon><!--table_rename-->
                      </template>
                      <span>重命名表</span>
                    </v-tooltip>
                  </v-col>
                  <v-col cols="1" sm="1" md="1">
                    <v-tooltip top>
                      <template v-slot:activator="{ on, attrs }">
                        <v-icon small class="" color="#6777ef" v-bind="attrs" v-on="on" @click.stop="drop_one(t)">
                          mdi-delete
                        </v-icon>
                        <!--table_drop-->
                      </template>
                      <span>删除表</span>
                    </v-tooltip>
                  </v-col>
                  <v-col cols="1" sm="1" md="1">
                    <v-tooltip top>
                      <template v-slot:activator="{ on, attrs }">
                        <v-icon small class="" color="#6777ef" v-bind="attrs" v-on="on" @click.stop="alter_one(t)">
                          mdi-pencil
                        </v-icon>
                        <!--table_alter-->
                      </template>
                      <span>修改表</span>
                    </v-tooltip>
                  </v-col>
                  <v-col cols="1" sm="1" md="1">
                    <v-tooltip top>
                      <template v-slot:activator="{ on, attrs }">
                        <v-icon small class="" color="#6777ef" v-bind="attrs" v-on="on" @click.stop="show_crate_one(t)">
                          mdi-eye
                        </v-icon>
                        <!--table_show_create-->
                      </template>
                      <span>查看建表语句</span>
                    </v-tooltip>
                  </v-col>
                </v-row>
              </v-card-title>
              <v-divider/>
              <v-list dense>
                <v-list-item v-for="(m,j) in t.items">
                  <v-list-item-content>{{ m.name}}</v-list-item-content>
                  <v-list-item-content>{{ m.type}}</v-list-item-content>
                  <v-list-item-content>{{ m.is_key === "*"? 'key' :""}}</v-list-item-content>
                </v-list-item>
              </v-list>
            </v-card>
          </v-col>
        </v-row>
      </template>

      <!--添加模态框-->
      <v-dialog v-model="dialog" persistent width="600">
        <v-card>
          <v-card-title>
            <span style="color:#6777ef" class="text-h5">{{one === -1 ? "创建表" : (one === 0 ? "重命名表" :( one===1?"删除表":"查看建表语句"))}}</span>
          </v-card-title>
          <v-card-title class="text-sm-h6" v-if="one ===1">你确定要删除这个表吗?</v-card-title>
          <v-card-title class="text-sm-h6 font" v-if="one ===2">{{sql}}</v-card-title>
          <v-card-title class="text-h5 lighten-2" v-if="one <= 0" sm="3" md="3">
            <v-text-field sm="3" md="3" v-model="name" label="表名" :disabled="(one === 0)"/>
          </v-card-title>
          <v-card-title class="text-h5 lighten-2" v-if="one ===0" sm="3" md="3">
            <v-text-field sm="3" md="3" v-model="n_name" label="新表名"/>
          </v-card-title>
          <v-container @keyup.enter="add_one_line" v-if="one ===-1">
            <v-row v-for="(t,i) in dict">
              <v-col cols="12" sm="4" md="4">
                <v-text-field v-model="t.name" label="字段"/>
              </v-col>
              <v-col cols="12" sm="4" md="3">
                <v-select v-model="t.type" :items="items" label="类型"/>
              </v-col>
              <v-col cols="12" sm="4" md="3">
                <v-text-field v-model="t.is_key" label="主键"/>
              </v-col>
              <v-col cols="12" sm="4" md="2">
                <v-icon small class="ml-1" v-if="i!==0" @click.stop="remove_one(i)"> mdi-delete</v-icon>
              </v-col>
            </v-row>
          </v-container>
          <v-divider/>
          <v-card-actions>
            <v-spacer/>
            <v-btn color="blue darken-1" text @click="dialog=false">取消</v-btn>
            <v-btn color="blue darken-1" text @click="add(name,dict)">确定</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>


      <!--修改模态框-->
      <v-dialog v-model="alter_dialog" persistent width="600">
        <v-card>
          <v-card-title class="text-h5 lighten-2" sm="3" md="3">
            <v-row>
              <v-col cols="8" sm="8" md="8">
                <v-text-field sm="2" md="2" v-model="alter_name" disabled label="表名"/>
              </v-col>
              <v-col cols="1" sm="1" md="1">
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-icon small class="" v-bind="attrs" v-on="on" v-if="" @click.stop="alter_add"> mdi-plus</v-icon>
                  </template>
                  <span>添加字段(add)</span>
                </v-tooltip>
              </v-col>
              <v-col cols="1" sm="1" md="1">
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-icon small class="" v-bind="attrs" v-on="on" v-if="" @click.stop="alter_drop"> mdi-delete
                    </v-icon>
                  </template>
                  <span>删除字段(drop)</span>
                </v-tooltip>
              </v-col>
              <v-col cols="1" sm="1" md="1">
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-icon small class="" v-bind="attrs" v-on="on" v-if="" @click.stop="alter_change">
                      mdi-wrench-outline
                    </v-icon>
                  </template>
                  <span>改变字段(change)</span>
                </v-tooltip>
              </v-col>
              <v-col cols="1" sm="1" md="1">
                <v-tooltip top>
                  <template v-slot:activator="{ on, attrs }">
                    <v-icon small class="" v-bind="attrs" v-on="on" v-if="" @click.stop="alter_modify"> mdi-wrench
                    </v-icon>
                  </template>
                  <span>修改字段(modify)</span>
                </v-tooltip>
              </v-col>
            </v-row>
          </v-card-title>

          <v-container v-if="two !== -1">
            <v-row>
              <v-col cols="12" sm="4" md="3" v-if="three===1">
                <v-text-field v-model="process_one.old_name" label="旧名字"/>
              </v-col>
              <v-col cols="12" sm="4" md="4">
                <v-text-field v-model="process_one.name" label="新名字"/>
              </v-col>
              <v-col cols="12" sm="4" md="3" v-if="three !== 0">
                <v-select v-model="process_one.type" :items="items" label="类型"/>
              </v-col>
            </v-row>
          </v-container>
          <v-divider/>
          <v-card-actions>
            <v-spacer/>
            <v-btn color="blue darken-1" text @click="alter_dialog=false">取消</v-btn>
            <v-btn color="blue darken-1" text @click="alter">修改</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

    </v-card>
  </v-container>
</template>

<script>

  import {
    database_use,
    table_show,
    table_dict,
    table_create,
    table_drop,
    table_rename,
    table_alter,
    table_show_create,
  } from "../../../axios/diamon"
  import {
    show_tables,
    create_tables,
    rename_tables,
    drop_tables,
    show_create_tables,
    alter_tables
  } from "../../../utils/sql/table"
  import {use_database} from "../../../utils/sql/database"
  import {get} from "../../../utils/local"

  export default {
    name: "",
    data() {
      return {
        database: "",
        dialog: false,
        alter_dialog: false,
        item: [
          {text: '首页', disabled: false, to: '/database',},
          {text: '数据表列表', disabled: false, to: '',},],
        search: '',
        table: [],
        items: ['int', 'double', 'boolean', 'varchar', 'datetime'],
        dict: [
          {concern: "", is_key: "", name: "", symbol: "", type: "", value: ""}
        ],
        name: "",
        n_name: "",
        alter_dict: [
          {concern: "", is_key: "", name: "", symbol: "", type: "", value: ""}
        ],
        alter_name: "",
        one: -1,
        two: -1,
        three: -1,
        sql: "",
        process_one: {table: "", old_name: "", name: "", type: this.items},
        snackbar: false,
        text: "",
      }
    },

    methods: {
      async init() {
        this.table = [];
        let database = "";
        if (this.$route.query.database === undefined) {
          database = get("database");
        } else {
          database = JSON.parse(this.$route.query.database);
        }
        console.log(database);
        this.database = get("database");
        let s = use_database(database);
        console.log(s);
        let databaseUse = await database_use(s);
        let tableShow = await table_show(show_tables());
        console.log(databaseUse);
        console.log(tableShow);
        let data1 = tableShow.data;
        if (data1.length === 0) {
          return;
        }
        data1.forEach((t, i) => {
          this.set_dict(database, t)
        });
      },
      async set_dict(d, s) {
        let newVar = await table_dict(d, s);
        console.log(newVar);
        console.log(newVar.data);
        if (newVar.data === null) {
          return;
        }
        let fields = newVar.data.fields;
        const one = {name: "", items: []};
        one.name = s;
        fields.forEach((m, j) => {
          const ll = {
            name: m.name,
            value: m.value,
            type: m.type,
            symbol: m.symbol,
            is_key: m.is_key,
            concern: m.concern
          };
          one.items.push(ll);
        });
        this.table.push(one);
      },


      add_one_line() {//动态添加一行
        this.dict.push({concern: "", is_key: "", name: "", symbol: "", type: "", value: ""})
      },


      remove_one(i) {//动态删除一行
        this.dict.splice(i, 1)
      },


      add_one() {//打开模态框并初始化
        this.dialog = true;
        this.dict.length = 1;
        this.one = -1;
        this.name = "";
      },
      rename_one(name) {
        this.one = 0;
        this.dialog = true;
        this.name = name.name;
      },
      drop_one(name) {
        this.one = 1;
        this.dialog = true;
        this.name = name.name;
      },
      async show_crate_one(name) {
        this.one = 2;
        let s = show_create_tables(name.name);
        let tableShowCreate = await table_show_create(s);
        this.dialog = true;
        this.sql = tableShowCreate.data;
      },
      alter_one(t) {
        this.alter_dialog = true;
        this.two = -1;
        this.alter_name = t.name
      },


      async add(name, item) {

        let newVar;
        if (this.one === -1) {//create_tables
          let s = create_tables(name, item);
          // console.log(s);
          newVar = await table_create(s);
        } else if (this.one === 0) {//rename_tables
          let s = rename_tables(name, this.n_name);
          newVar = await table_rename(s);
        } else if (this.one === 1) {//drop_tables
          let s = drop_tables(name);
          newVar = await table_drop(s);
        } else if (this.one === 2) {//show_create_tables
          this.dialog = false;
        }
        if (newVar.code === 400) {
          this.snackbar = true;
          this.text = newVar.data;
        } else {
          this.dialog = false;
          window.location.reload()
        }

      },


      alter_add() {
        this.two = 1;
        this.three = -1;
        this.process_one.table = this.alter_name;
      },
      alter_drop() {
        this.two = 1;
        this.three = 0;
        this.process_one.table = this.alter_name;
      },
      alter_change() {
        this.two = 1;
        this.three = 1;
        this.process_one.table = this.alter_name;
      },
      alter_modify() {
        this.two = 1;
        this.three = 2;
        this.process_one.table = this.alter_name;
      },

      async alter() {
        let obj = this.process_one;
        let newVar;
        if (this.three === -1) {
          let s = alter_tables(-1, obj);
           newVar = await table_alter(s);
        } else if (this.three === 0) {
          let s = alter_tables(0, obj);
           newVar = await table_alter(s);
        } else if (this.three === 1) {
          let s = alter_tables(1, obj);
           newVar = await table_alter(s);
        } else if (this.three === 2) {
          let s = alter_tables(2, obj);
           newVar = await table_alter(s);
        }
        if (newVar.code === 400) {
          this.snackbar = true;
          this.text = newVar.data;
        } else {
          this.dialog = false;
          window.location.reload()
        }
        // this.alter_dialog = false;
        // window.location.reload();
      },


      goToData(item) {
        // console.log(item.name);
        const d_t = {
          database: this.database,
          table: item.name
        };
        // console.log(d_t);
        this.$router.replace({path: "/data", query: {d_t: JSON.stringify(d_t)}});
      }

    },


    created() {
      this.table = [];
      this.init()
    },

    watch: {
      $route: {
        handler(val, oldval) {//新路由信息 老路由信息
          this.database = JSON.parse(val.query.database);
          this.init();
        },
        // 深度观察监听
        deep: true
      },
      table: {
        handler(newName, oldName) {
          return this.$route.query.table;
        },
        immediate: true
      }
    }
  }
</script>

<style scoped>
  .v-application a {
    color: rgb(255 255 255);
  }

  .v-application .text-h5 {
    /* font-size: 1.5rem !important; */
    letter-spacing: normal !important;
    font-family: "Roboto", sans-serif !important;
  }
</style>
