<template>
  <v-container fluid>
    <v-card class="mx-auto"  min-height="620">
      <v-row>
        <v-col cols="12" sm="12" md="12" lg="12">
          <v-data-table :headers="headers" :items="desserts" sort-by="calories" class="elevation-1">
            <template v-slot:top>
              <v-toolbar flat>
                <v-toolbar-title>
                  <v-breadcrumbs :items="items"/>
                </v-toolbar-title>
                <v-divider class="mx-4" inset vertical/>
                <v-spacer/>
                <!--              添加模态框开始-->
                <v-dialog v-model="dialog" persistent max-width="500px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on" @click="add">添&emsp;加</v-btn>
                  </template>
                  <v-card>
                    <v-card-title>
                      <span class="text-h5">{{ formTitle }}</span>
                    </v-card-title>
                    <v-card-text>
                      <v-container>
                        <v-row>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field v-model="editedItem.user" label="User name" disabled/>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field v-model="editedItem.authority" label="Authority"
                                          :disabled="(editedIndex>-1 )? false : true"/>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field v-model="editedItem.pass" label="Password"/>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field v-model="editedItem.database" label="Database"/>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field v-model="editedItem.table" label="Table"/>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field v-model="editedItem.key" label="Key"/>
                          </v-col>
                          <v-col cols="12" sm="12" md="12">
                            <v-text-field v-model="editedItem.purview" label="Purview"/>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer/>
                      <v-btn color="blue darken-1" text @click="close">取消</v-btn>
                      <v-btn color="blue darken-1" text @click="save">确定</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
                <!--添加模态框结束-->

                <!--              删除模态框开始-->
                <v-dialog v-model="dialogDelete" max-width="500px">
                  <v-card>
                    <v-card-title class="text-h5">你真的想要删除这个选项吗?</v-card-title>
                    <v-card-actions>
                      <v-spacer/>
                      <v-btn color="blue darken-1" text @click="close_delete">取消</v-btn>
                      <v-btn color="blue darken-1" text @click="delete_one">确定</v-btn>
                      <v-spacer/>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
                <!--              删除模态框结束-->
              </v-toolbar>
            </template>

            <template v-slot:item.actions="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  &emsp;
                  <v-icon small color="#6777ef" v-bind="attrs" v-on="on" class="mr-2" @click="edit(item)"> mdi-pencil
                  </v-icon><!--修改-->
                </template>
                <span>修改这条数据</span>
              </v-tooltip>
            </template>

          </v-data-table>

        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
  import {authority_find_one_by_name, authority_revoke, authority_grant} from "../../../axios/diamon"
  import {add_authoriy} from "../../../utils/sql/authoriy"
  import {decode} from "../../../utils/base64"
  import {get} from "../../../utils/local"


  export default {
    data: () => ({
      items: [
        {text: '首页', disabled: false, to: '/database',},
        {text: '权限列表', disabled: false, to: '/purview',},
        {text: '权限', disabled: false, to: '',},],
      dialog: false,
      dialogDelete: false,
      headers: [],//表头
      desserts: [],//表数据
      editedIndex: -1,
      editedItem: {user: "", authority: "", pass: "", purview: "", database: "", table: "", key: ""},
      defaultItem: {user: "", authority: "", pass: "", purview: "", database: "", table: "", key: ""},
    }),
    methods: {
      async get_user() {
        this.desserts = [];
        this.headers = [];
        let user = this.$route.query.user;
        let newVar = await authority_find_one_by_name(user);
        let data = newVar.data;
        console.log(data[newVar.pass]);
        if (data[newVar.pass] === null) {
          return;
        }
        data[newVar.pass].forEach((t, i) => {
          let l = t.ok !== "~";
          let purview = t.purview;
          const database = purview[0];
          const table = purview[1];
          let news = [];
          for (let j = 2; j < purview.length; j++) {
            news.push(purview[j])
          }
          const u = {
            authority: t.authority,
            pass: decode(t.pass),
            purview: news,
            database: database,
            table: table,
            key: l
          };
          this.desserts.push(u);
        });
        this.headers = [
          {text: 'AUTHORITY', align: 'start', sortable: false, value: 'authority',},
          {text: 'DATABASE', value: 'database'},
          {text: 'TABLE', value: 'table'},
          {text: 'PURVIEW', value: 'purview'},
          {text: 'PASSWORD', value: 'pass'},
          {text: 'KEY', value: 'key'},
          {text: 'ACTIONS', value: 'actions', sortable: false},
        ]
      },

      edit(item) {
        this.editedIndex = this.desserts.indexOf(item);
        this.editedItem = Object.assign({}, item);
        this.dialog = true;
        this.editedItem.user = this.$route.query.user;
      },

      // deletes(item) {
      //   this.editedIndex = this.desserts.indexOf(item);
      //   this.editedItem = Object.assign({}, item);
      //   this.dialogDelete = true
      // },

      delete_one() {
        this.desserts.splice(this.editedIndex, 1);
        this.close_delete();
        this.editedItem.user = this.$route.query.user;
      },

      close() {
        this.dialog = false;
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem);
          this.editedIndex = -1
        })
      },

      close_delete() {
        this.dialogDelete = false;
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem);
          this.editedIndex = -1
        })
      },

      add() {//新增按钮
        this.editedItem.authority = get("user").name;
        this.editedItem.user = this.$route.query.user;
      },

      async save() {
        if (this.editedIndex > -1) {//修改
          Object.assign(this.desserts[this.editedIndex], this.editedItem);
          let sql = add_authoriy(this.editedItem, 0);
          let newVar = await authority_revoke(sql);
          console.log(newVar);
        } else {//添加
          this.desserts.push(this.editedItem);
          let sql = add_authoriy(this.editedItem, -1);
          console.log(sql);
          let newVar = await authority_grant(sql);
        }
        this.close();
        window.location.reload();
      },
//grant select , update , insert , delete , create , desc on user1.dept to mm identified by '000000' with grant option;
      //select,update,insert,delete,create,desc
    },

    computed: {
      formTitle() {
        return this.editedIndex === -1 ? '添加一条' : '修改一条'
      },
    },

    created() {
      this.get_user();
    },


    watch: {
      '$route': function () {//ma,得监听路由,干
        this.v = this.$route.query.selected;
      },
      dialog(val) {
        val || this.close()
      },
      dialogDelete(val) {
        val || this.close_delete()
      },
    },
  }
</script>

<style scoped>

</style>
