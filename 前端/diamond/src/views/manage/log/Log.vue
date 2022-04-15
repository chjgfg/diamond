<template>
  <v-container fluid>


    <v-snackbar color="red " outlined light tile centered v-model="snackbar" :timeout="2000">
      {{ text }}
      <template v-slot:action="{ attrs }">
        <v-btn color="blue" text v-bind="attrs" @click="snackbar = false">关闭</v-btn>
      </template>
    </v-snackbar>


    <template>
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
                <v-btn color="#6777ef" dark class="mb-2" @click="dialog = true">清空</v-btn>
              </v-toolbar>
            </template>
          </v-data-table>
        </v-col>
      </v-row>
    </template>
    <!--              添加模态框开始-->
    <v-dialog v-model="dialog" persistent max-width="500px">
      <v-card>
        <v-card-title style="color:#6777ef" class="text-h5 lighten-2">
          清空
        </v-card-title>
        <v-card-text>
          <v-container>
            <h4>
              确定要清空所有日志吗??
            </h4>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer/>
          <v-btn color="blue darken-1" text @click="dialog = false"> 取消</v-btn>
          <v-btn color="blue darken-1" text @click="truncate_one()"> 确定</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>

</template>

<script>
  import {log_find_one, log_truncate} from "../../../axios/diamon"
  import {truncate_log} from "../../../utils/sql/log"

  export default {
    name: "",
    data: () => ({
      key: "",
      items: [
        {text: '首页', disabled: false, to: '/database',},
        {text: '日志列表', disabled: false, to: '/logger',},
        {text: '日志详情', disabled: true, to: '',},],
      headers: [],
      desserts: [],
      database: "",
      tables: "",
      dialog: false,
      snackbar: false,
      text: "",
    }),

    methods: {
      async log_find_ones(database, table) {
        let newVar = await log_find_one(database, table);
        newVar.data.forEach((t, i) => {
          const s = {user: t.name, time: t.date, sql: t.info};
          this.desserts.push(s);
        });
        this.headers = [
          {text: "User", align: 'start', value: 'user',},
          {text: 'Time', value: 'time', sortable: false},
          {text: 'Sql', value: 'sql', sortable: false},
        ]
      },

      async truncate_one() {
        let s = truncate_log(this.database, this.table);
        let newVar = await log_truncate(s);
        if (newVar.code === 400) {
          this.snackbar = true;
          this.text = newVar.data;
        } else {
          this.dialog = false;
          window.location.reload();
        }

      }
    },

    mounted() {
      // let database = "", table = "";
      let key = this.$route.query.key;
      console.log(key);
      if (key === "[object Object]") {
        let split = this.keys.split("-");
        this.database = split[0];
        this.table = split[1];
      } else {
        this.database = key.database;
        this.table = key.table;
      }
      this.log_find_ones(this.database, this.table)
    },
    computed: {
      keys: {
        get() {
          return this.$store.state.key;//获取存在vuex里的东西
        },
      }
    },
    watch: {
      $route: {
        handler(val, oldval) {//新路由信息 老路由信息
          console.log(val);
          console.log(oldval);
        },
        // 深度观察监听
        deep: true
      },
      // keys: {
      //   handler(newName, oldName) {
      //     // console.log(newName);
      //   },
      //   immediate: true
      // },
    }
  }
</script>

<style scoped>

</style>
