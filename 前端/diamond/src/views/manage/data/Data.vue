<template>
  <v-container fluid>

    <v-snackbar color="red " outlined light tile centered v-model="snackbar" :timeout="2000">
      {{ text }}
      <template v-slot:action="{ attrs }">
        <v-btn color="blue" text v-bind="attrs" @click="snackbar = false">关闭</v-btn>
      </template>
    </v-snackbar>


    <v-card class="mx-auto" min-height="620">
      <v-data-table :headers="headers" :items="desserts" sort-by="calories" class="elevation-1">
        <template v-slot:top>
          <v-toolbar flat>
            <v-toolbar-title>
              <v-breadcrumbs :items="item"/>
            </v-toolbar-title>
            <v-divider class="mx-4" inset vertical/>
            <v-spacer/>
            <v-btn color="#6777ef" dark class="mb-2" @click="add_one">添加</v-btn>
            <v-divider class="mx-4" inset vertical/>
            <v-btn color="#6777ef" dark class="mb-2" @click="dialog2 = true">清空</v-btn>
            <v-dialog v-model="dialog" persistent max-width="500px">
              <v-card>
                <v-card-title>
                  <span style="color:#6777ef"
                        class="text-h5">{{flag === -1 ? "添加数据" : (flag === 0 ? "修改数据" : "删除数据")}}</span>
                </v-card-title>
                <v-card-title v-if="flag === 1">你确定想要删除这条数据吗?</v-card-title>
                <v-card-text v-if="flag !== 1">
                  <v-container>
                    <v-row>
                      <v-col cols="12" sm="4" md="4" v-for="(t,i) in data[0]">
                        <!--                        <v-text-field v-if="t.type !== 'datetime'" v-model="t.value" :label="t.name"/>-->
                        <v-text-field v-if="t.type" :disabled="flag === 0 &&(t.key === '*' || i === 0 )"
                                      v-model="t.value" :label="t.name"/>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-spacer/>

                  <v-btn color="blue darken-1" text @click="dialog = false">取消</v-btn>
                  <v-btn color="blue darken-1" text @click="save">确定</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

            <!--              添加模态框开始-->
            <v-dialog v-model="dialog2" persistent max-width="500px">
              <v-card>
                <v-card-title style="color:#6777ef" class="text-h5">
                  清空
                </v-card-title>
                <v-card-text>
                  <v-container>
                    <h4>
                      真的想要清空该表吗 ? ? ?
                    </h4>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-spacer/>
                  <v-btn color="blue darken-1" text @click="dialog2 = false"> 取消</v-btn>
                  <v-btn color="blue darken-1" text @click="truncate_one()"> 确定</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>


          </v-toolbar>
        </template>
        <template v-slot:item.actions="{ item }">
          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-icon small class="mr-2" color="#6777ef" v-bind="attrs" v-on="on" @click="edit_one(item)">mdi-pencil
              </v-icon>
            </template>
            <span>修改这条数据</span>
          </v-tooltip>

          <v-tooltip top>
            <template v-slot:activator="{ on, attrs }">
              <v-icon small class="mr-2" color="#6777ef" v-bind="attrs" v-on="on" @click="delete_one(item)">mdi-delete
              </v-icon>
            </template>
            <span>删除这条数据</span>
          </v-tooltip>
        </template>
      </v-data-table>

    </v-card>
  </v-container>
</template>

<script>
  import {data_select, data_insert, data_delete, data_update, table_dict, data_truncate} from "../../../axios/diamon"
  import {select_data, update_data, delete_data, insert_data, truncate_database} from "../../../utils/sql/data"
  import {set_database} from "../../../utils/local"
  import {clone} from "../../../utils/clone"

  export default {
    data: () => ({
      date: "",
      time: "",
      database: "",
      table: "",
      item: [
        {text: '首页', disabled: false, to: '/database',},
        {text: "数据表列表", disabled: false, to: '/table'},
        {text: "表详情", disabled: false, to: ''},],
      dialog: false,
      headers: [],
      desserts: [],
      data: [],
      flag: -1,
      dialog2: false,
      snackbar: false,
      text: "",
    }),

    methods: {

      async get_data() {
        this.headers = [];
        this.desserts = [];
        let tableDict = await table_dict(this.database, this.table);
        let fields = tableDict.data.fields;
        let s = select_data(this.table);
        let newVar = await data_select(s);
        let data1 = newVar.data;
        const fields_name = [];
        // console.log(tableDict);
        // console.log(newVar);
        //绑定表头
        fields.forEach((t, i) => {
          let item = {text: t.name, value: t.name, type: t.type, is_key: t.is_key, concern: t.concern};
          this.headers.push(item);
          fields_name.push(t.name);
        });
        this.headers.push({text: 'Actions', value: 'actions', sortable: false});
        const ll = {};
        fields_name.forEach((c, p) => {
          ll[c] = "";
        });
        //开始绑定表格里面的数据
        data1.forEach((m, j) => {
          let fields1 = m.fields;
          const pp = clone(ll);
          fields1.forEach((k, l) => {
            pp[fields_name[l]] = k.value;
          });
          this.desserts.push(pp);
        });
        //给弹框一个初始值
        let oo = [];
        fields.forEach((k, l) => {
          // console.log(k);
          const pp = {name: k.name, value: "", type: k.type, key: k.is_key,};
          oo.push(pp);
        });
        this.data.push(oo);

      },


      add_one() {
        this.flag = -1;
        this.dialog = true;
        console.log(this.data);
        this.data[0].forEach((t, i) => {
          t.value = "";
        });
        console.log(this.date);
      },
      edit_one(item) {
        this.flag = 0;
        this.dialog = true;
        this.data[0].forEach((t, i) => {
          t.value = item[t.name];
        });
      },
      delete_one(item) {
        this.flag = 1;
        this.dialog = true;
        this.data[0].forEach((t, i) => {
          t.value = item[t.name];
        });
      },

      year_month_day() {
        console.log(this.date);
      },
      house_minute_second() {
        console.log(this.time + ":00");
      },

      async save() {
        let newVar;
        if (this.flag === -1) {
          let s = insert_data(this.table, this.data);
          newVar = await data_insert(s);
        } else if (this.flag === 0) {
          let s = update_data(this.table, this.data);
          newVar = await data_update(s);
        } else if (this.flag === 1) {
          let s = delete_data(this.table, this.data);
          newVar = await data_delete(s);
        }
        if (newVar.code === 400) {
          this.snackbar = true;
          this.text = newVar.data;
        } else {
          this.dialog = false;
          window.location.reload()
        }
        // window.location.reload()
      },

      async truncate_one() {
        let s = truncate_database(this.table);
        let newVar = await data_truncate(s);
        if (newVar.code === 400) {
          this.snackbar = true;
          this.text = newVar.data;
        } else {
          this.dialog = false;
          window.location.reload()
        }

      },
    },

    created() {
      let d_t = JSON.parse(this.$route.query.d_t);
      this.database = d_t.database;
      set_database(d_t.database);
      this.table = d_t.table;
      this.get_data();
    },

  }
</script>

<style scoped>

</style>
<!--headers: [-->
<!--{text: 'Dessert (100g serving)', align: 'start', sortable: false, value: 'name',},-->
<!--{text: 'Calories', value: 'calories'},-->
<!--{text: 'Fat (g)', value: 'fat'},-->
<!--{text: 'Carbs (g)', value: 'carbs'},-->
<!--{text: 'Protein (g)', value: 'protein'},-->
<!--{text: 'Actions', value: 'actions', sortable: false},],-->
<!--desserts: [-->
<!--{name: 'Frozen Yogurt', calories: 159, fat: 6.0, carbs: 24, protein: 4.0,},-->
<!--{name: 'Ice cream sandwich', calories: 237, fat: 9.0, carbs: 37, protein: 4.3,},-->
<!--{name: 'Eclair', calories: 262, fat: 16.0, carbs: 23, protein: 6.0,},-->
<!--{name: 'Cupcake', calories: 305, fat: 3.7, carbs: 67, protein: 4.3,},-->
<!--{name: 'Gingerbread', calories: 356, fat: 16.0, carbs: 49, protein: 3.9,},-->
<!--{name: 'Jelly bean', calories: 375, fat: 0.0, carbs: 94, protein: 0.0,},-->
<!--{name: 'Lollipop', calories: 392, fat: 0.2, carbs: 98, protein: 0,},-->
<!--{name: 'Honeycomb', calories: 408, fat: 3.2, carbs: 87, protein: 6.5,},-->
<!--{name: 'Donut', calories: 452, fat: 25.0, carbs: 51, protein: 4.9,},-->
<!--{name: 'KitKat', calories: 518, fat: 26.0, carbs: 65, protein: 7,},-->
<!--],-->
