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
          <v-col md="3" ffset-md="3" v-for="(item,index) in users">
            <v-chip class="ma-md-10 " outlined tile :color="item.color" @click.stop="goToOnePurview(item.user)">
              {{item.user}}
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon small class="ml-10" v-bind="attrs" v-on="on" @click.stop="rename_dialog(item.user)">
                    mdi-pencil
                  </v-icon>
                </template>
                <span>修改用户名</span>
              </v-tooltip>
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon small class="ml-1" v-bind="attrs" v-on="on" @click.stop="set_dialog(item.user)">
                    mdi-onepassword
                  </v-icon>
                </template>
                <span>修改密码</span>
              </v-tooltip>
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon small class="ml-1" v-bind="attrs" v-on="on" @click.stop="remove_one(item.user)"> mdi-delete
                  </v-icon>
                </template>
                <span>删除用户</span>
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
                <span>添加用户</span>
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
          <span style="color:#6777ef" class="text-h5">{{kool===1?(look === -1 ? "添加用户":"修改用户"):("删除用户")}}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <h4 :class="kool === 1 ? 'none':'block'">真的要删除吗 ? ? ? </h4>
            <v-row :class="kool === 0 ? 'none':'block'">
              <v-col cols="12" sm="12" md="12">
                <v-text-field v-model="user.user" :disabled="look !== -1" label="输入用户名"/>
              </v-col>
              <v-col cols="12" sm="12" md="12">
                <v-text-field v-model="user.n_name" :class="look !== 0 ? 'none':'block'" label="输入新的用户名"/>
              </v-col>
              <v-col cols="12" sm="12" md="12">
                <v-text-field v-model="user.pass" :class="look === 0 ? 'none':'block'" label="输入用户密码"/>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer/>
          <v-btn color="blue darken-1" text @click="dialog = false"> 取消</v-btn>
          <v-btn color="blue darken-1" text @click="add_or_rename_or_set_one()">确定</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


  </v-container>
</template>

<script>
  import {authority_find_other, authority_create, authority_drop, authority_set, authority_rename, authority_find_pass_by_name} from "../../../axios/diamon"
  import {get} from "../../../utils/local"
  import {get_color, get_random} from "../../../utils/color"
  import {decode} from "../../../utils/base64"
  import {drop_user, add_user, rename_user, set_user} from "../../../utils/sql/authoriy"

  export default {
    data: () => ({
      reveal: false,
      items: [
        {text: '首页', disabled: false, to: '/database',},
        {text: '权限列表', disabled: false, to: '',},],
      users: [
        // {user: "cscsdc", color: "#F0FF0F",},
        // {user: "cscsdc", color: "#000000",},
        // {user: "cscsdc", color: "#FAEBD7",},
        // {user: "cscsdc", color: "#00FFFF",},
        // {user: "cscsdc", color: "#7FFFD4"},
      ],
      dialog: false,
      user: {user: "", pass: "", n_name: ""},
      look: -1,
      kool: 1,
      snackbar: false,
      text: "",
    }),
    methods: {
      async authority_find_others() {
        let user = get("user");
        console.log(user);
        let newVar = await authority_find_other(user.name);
        let data = newVar.data;
        this.random(data);
        // this.user = get("user");
        // console.log(user);
      },

      random(data) {
        this.users = [];
        let set = get_random(data.length);
        set.forEach((vo, i) => {
          const iter = {
            user: data[i],
            // color: get_color()[vo]
            color: "#6777ef"
          };
          this.users.push(iter);
        });
      },

      goToOnePurview(item) {
        this.$router.replace({path: "/one_purview2", query: {user: item}})
      },

      remove_one(user) {
        this.kool = 0;
        this.dialog = true;
        this.user.user = user;
      },

      add_dialog() {//添加
        this.dialog = true;
        this.look = -1;
        this.kool = 1;
        this.user.user = "";
        this.user.pass = "";
        this.user.n_name = "";
      },

      async rename_dialog(user) {//重名名
        this.dialog = true;
        this.look = 0;
        this.kool = 1;
        let promise = await authority_find_pass_by_name(user);
        this.user.user = user;
      },

      async set_dialog(user) {//设置密码
        this.dialog = true;
        this.look = 1;
        this.kool = 1;
        let promise = await authority_find_pass_by_name(user);
        this.user.user = user;
        this.user.pass = decode(promise.data);
      },

      async add_or_rename_or_set_one() {
        let newVar;
        if (this.kool === 0) {//删除用户
          let s = drop_user(this.user);
          newVar = await authority_drop(s);
        } else {
          if (this.look === -1) {//添加用户
            let s = add_user(this.user);
            newVar = await authority_create(s);
          } else if (this.look === 0) {//重命名用户
            let s = rename_user(this.user);
            newVar = await authority_rename(s);
          } else if (this.look === 1) {//设置密码
            let s = set_user(this.user);
            newVar = await authority_set(s);
          }
        }
        if (newVar.code === 400) {
          this.snackbar = true;
          this.text = newVar.data;
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
