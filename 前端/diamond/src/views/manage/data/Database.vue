<template>
  <v-container fluid class="grey lighten-5">

    <v-snackbar color="red " outlined light tile centered v-model="snackbar" :timeout="2000">
      {{ text }}
      <template v-slot:action="{ attrs }">
        <v-btn color="blue" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>

    <v-col cols="12" sm="12" md="12" lg="12">
      <v-card class="mx-auto" min-height="620">
        <template>
          <v-toolbar flat>
            <v-toolbar-title>
              <v-breadcrumbs :items="items"/>
            </v-toolbar-title>
            <v-divider class="mx-4" inset vertical/>
          </v-toolbar>
        </template>
        <v-row class="mb-6" no-gutters>
          <v-col md="3" ffset-md="3" v-for="(item,index) in databases">
            <v-chip class="ma-md-10 " outlined tile :color="item.color"
                    @click.stop="goToOnePurview(item.database)">
              {{item.database}}
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon small class="ml-10" v-bind="attrs" v-on="on" @click.stop="rename_dialog(item.database)">
                    mdi-pencil
                  </v-icon>
                </template>
                <span>重命名数据库</span>
              </v-tooltip>

              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon small class="ml-1" v-bind="attrs" v-on="on" @click.stop="remove_one(item.database)">
                    mdi-delete
                  </v-icon>
                </template>
                <span>删除数据库</span>
              </v-tooltip>

            </v-chip>
          </v-col>
          <v-col md="2" ffset-md="3">
            <v-chip class="ma-md-10 " outlined tile>
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon size="30" color="#6777ef" class="me-5 ml-5" v-bind="attrs" v-on="on" @click="add_dialog()">
                    mdi-plus
                  </v-icon>
                </template>
                <span>创建数据库</span>
              </v-tooltip>
            </v-chip>
          </v-col>
        </v-row>
      </v-card>
    </v-col>

    <!--              添加模态框开始-->
    <v-dialog v-model="dialog" persistent max-width="500px">
      <v-card>
        <v-card-title>
          <span style="color:#6777ef" class="text-h5">{{kool===1?(look === -1 ? "添加数据库":"修改数据库"):("删除数据库")}}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <h4 :class="kool === 1 ? 'none':'block'">
              确定要删除这个数据库吗 ? ? ?
            </h4>
            <v-row :class="kool === 0 ? 'none':'block'">
              <v-col cols="12" sm="12" md="12">
                <v-text-field v-model="database.database" :disabled="look !== -1" label="输入数据库名字"/>
              </v-col>
              <v-col cols="12" sm="12" md="12">
                <v-text-field v-model="database.n_database" :class="look !== 0 ? 'none':'block'"
                              label="输入新的数据库名"/>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer/>
          <v-btn color="blue darken-1" text @click="dialog = false"> 取消</v-btn>
          <v-btn color="blue darken-1" text @click="add_or_rename_or_drop_one()">确定</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


  </v-container>
</template>
<script>
  import {database_create, database_drop, database_show, database_use, database_rename} from "../../../axios/diamon"
  import {get_color, get_random} from "../../../utils/color"
  import {use_database, create_database, drop_database, rename_database} from "../../../utils/sql/database"
  import {set_database} from "../../../utils/local"

  export default {
    data: () => ({
      reveal: false,
      items: [
        {text: '首页', disabled: false, to: '',},],
      databases: [
        {database: "cscsdc", color: "#F0FF0F",},
        {database: "cscsdc", color: "#000000",},
        {database: "cscsdc", color: "#FAEBD7",},
        {database: "cscsdc", color: "#00FFFF",},
        {database: "cscsdc", color: "#7FFFD4"},],
      dialog: false,
      database: {database: "", n_database: ""},
      look: -1,
      kool: 1,
      snackbar: false,
      text: "",
    }),
    methods: {
      async authority_find_others() {
        let newVar = await database_show();
        let set = new Set();
        newVar.data.forEach((vo, i) => {
          set.add(vo);
        });
        let ls = [];
        set.forEach((vo, i) => {
          ls.push(vo);
        });
        this.random(ls);
      },

      random(data) {
        this.databases = [];
        let set = get_random(data.length);
        set.forEach((vo, i) => {
          var iter = {
            database: data[i],
            // color: get_color()[vo]
            color: "#6777ef"
          };
          this.databases.push(iter);
        });
      },

      goToOnePurview(item) {
        set_database(item);
        this.$router.replace({path: "/table", query: {database: JSON.stringify(item)}})
      },

      remove_one(user) {
        this.kool = 0;
        this.dialog = true;
        this.database.database = user;
      },

      add_dialog() {//添加
        this.dialog = true;
        this.look = -1;
        this.kool = 1;
        this.database.database = "";
        this.database.n_database = "";
      },

      async rename_dialog(user) {//重名名
        this.dialog = true;
        this.look = 0;
        this.kool = 1;
        // let promise = await authority_find_pass_by_name(user);
        this.database.database = user;
      },


      async add_or_rename_or_drop_one() {
        let newVar;
        if (this.kool === 0) {//删除库
          let s = drop_database(this.database.database);
          newVar = await database_drop(s);
        } else {
          if (this.look === -1) {//添加库
            let s = create_database(this.database.database);
            newVar = await database_create(s);
          } else if (this.look === 0) {//重命名库
            let s = rename_database(this.database.database, this.database.n_database);
            newVar = await database_rename(s);
          }
        }
        console.log(newVar);
        if (newVar.code === 400) {
          this.snackbar = true;
          this.text = newVar.data
        } else {
          this.dialog = false;
          window.location.reload();
        }
      }

    }
    ,
    created() {
      this.authority_find_others();

    }
  }
</script>

<style scoped>
  .v-card--reveal {
    bottom: 0;
    opacity: 1 !important;
    position: absolute;
    width: 100%;
  }

  .v-chip.v-size--default {
    border-radius: 16px;
    font-size: 24px;
    height: 50px;
  }

  .v-chip.v-chip--outlined {
    border-width: revert;
  }

  .none {
    display: none;
  }

  .block {
    display: block;
  }
</style>
