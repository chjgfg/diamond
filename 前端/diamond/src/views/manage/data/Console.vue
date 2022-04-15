<template>
  <v-container fluid>

    <v-snackbar color="primary" outlined light tile centered v-model="snackbar" :timeout="2000">
      {{ text }}
      <template v-slot:action="{ attrs }">
        <v-btn color="blue" text v-bind="attrs" @click="snackbar = false">关闭</v-btn>
      </template>
    </v-snackbar>


    <v-row align="center">
      <v-col class="d-flex" offset="1" cols="3" sm="3" md="3" lg="3">
        <!--        <v-select outlined :items="database" @input="one_database($event)" background-color="#" label="Database"/>-->
      </v-col>
      <v-col class="d-flex" offset="1" cols="3" sm="3" md="3" lg="3">
        <!--        <v-select :items="table" @input="one_table($event)" light background-color="#" outlined label="Table"/>-->
      </v-col>
      <v-col cols="1" offset="2" sm="1" md="1" lg="1">
        <v-btn top depressed right color="#6777ef" @click="sub(all)">提交</v-btn>
      </v-col>
      <v-col offset="1" cols="10" sm="10" md="10" lg="10">
        <v-textarea :auto-grow="true" background-color=" lighten-2" outlined name="input-7-4" label="写完SQL，选中再提交"
                    v-model="all" @mouseup.stop="selected" loader-height="500"/> <!-- @mouseup.stop冒泡了-->
      </v-col>
    </v-row>
    <v-row align="center" v-if="(show === true&&other === false)">
      <v-divider/>
    </v-row>
    <v-row align="center" v-if="(show === true&&other === false)">
      <v-col offset="1" cols="10" sm="10" md="10" lg="10">
        <v-data-table :headers="title" :items="context" :items-per-page="5" class="elevation-1"/>
      </v-col>
    </v-row>
    <v-row align="center" v-if="(show === false&&other ===true )">
      <v-col offset="1" cols="10" sm="10" md="10" lg="10">
        <v-simple-table>
          <template v-slot:default>
            <thead>
            <tr>
              <th class="text-left">
                {{tables===false?"database":"table"}}
              </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in desserts" :key="item.name">
              <td>{{ item.name }}</td>
            </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
    </v-row>


  </v-container>
</template>

<script>

  import {get, set} from "../../../utils/local"
  import {send} from "../../../utils/console/console"

  export default {
    name: "",
    data: () => ({
      title: [],
      context: [],
      title_context: [],
      value: "",
      all: "",
      show: false,
      headers: [
        {text: 'Dessert (100g serving)', align: 'start', sortable: false, value: 'name',},
        {text: 'Calories', value: 'calories'},
        {text: 'Fat (g)', value: 'fat'},
        {text: 'Carbs (g)', value: 'carbs'},
        {text: 'Protein (g)', value: 'protein'},
        {text: 'Iron (%)', value: 'iron'},
      ],
      desserts: [],
      snackbar: false,
      text: "",
      other: false,
      tables: false,
    }),
    mounted() {
      const l = "show databases;\r\n" +
        "use company;\r\n" +
        "show tables;\r\n" +
        "insert into worker (id,name,sex,phone,email,dept_id,salary_id) values (3,'one',1,1234567878,'1234567878@qq.com',3,3);\r\n" +
        "update worker set dept_id = 2 where id = 3;\r\n" +
        "delete from worker where id = 3;\r\n" +
        "select * from worker;\r\n" +
        "select * from worker left join department on department.id = worker.dept_id;";
      this.all = l;
    },
    methods: {
      // async find(sqls) {
      //   let array = new Array();
      //   sqls.forEach((t, i) => {
      //     let stringPromise = send(t);
      //     array.push(stringPromise);
      //   });
      //   console.log(array[0]);
      //   return array;
      // },
      //
      //
      // process(sql) {
      //   let split = sql.split("\n");
      //   let arr = [];
      //   split.forEach((t, i) => {
      //     let n_split = t.split(";").filter((x) => x !== '');
      //     n_split.forEach((t, i) => {
      //       arr.push(t)
      //     });
      //   });
      //   return this.find(arr);
      // },

      selected() {
        this.value = window.getSelection().toString();
      },

      async sub(all) {
        if (this.value === "") {
          return
        }
        let process1 = await send(this.value);
        this.show_table(process1);
      },
      show_table(process1) {
        let data1 = process1.data;
        // console.log(typeof data1 === 'string');
        console.log(process1);
        console.log(typeof data1);
        if (typeof data1 === 'string') {//字符串
          console.log(process1);
          // if (process1.code === 100) {
            this.snackbar = true;
            this.text = data1;
          // }
          return;
        } else if (process1.other !== undefined) {
          this.desserts = [];
          data1.forEach((t, i) => {
            const item = {name: t};
            this.desserts.push(item);
          });
          if (process1.other === "databases") {
            this.tables = false;
          } else {
            this.tables = true;
          }
          console.log(process1.other);
          this.other = true;
          this.show = false;
        } else {
          this.title = [];
          this.context = [];
          let get_title = data1[0];
          let aliasFields = get_title.fields;
          this.show = true;
          this.other = false;
          aliasFields.forEach((t, i) => {
            let item = {
              text: (t.concern + "." + t.name),
              value: (t.concern + "." + t.name),
              type: t.type,
              is_key: t.is_key,
              concern: t.concern
            };
            this.title.push(item);
            this.title_context.push(item);
          });
          console.log(data1);
          data1.forEach((t, i) => {
            let fields = t.fields;
            let obj = {};
            fields.forEach((m, l) => {
              let titleContextElement = this.title_context[l].value;
              obj[titleContextElement] = m.value;
            });
            this.context.push(obj);
          });
        }
      },
      //select * from dw left join uu on dw.uu_id = uu.id , right join uuk on uuk.uu_id = uu.id where uu.id > 1
    },

  }
</script>

<style>
  .v-text-field.v-text-field--enclosed .v-text-field__details {
    display: none !important;
  }
</style>
