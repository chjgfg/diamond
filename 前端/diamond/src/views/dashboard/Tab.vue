<template>
  <nav>
    <!--    顶部导航栏-->
    <v-app-bar app color="#6777ef">
      <v-app-bar-nav-icon @click="drawer = !drawer"/>
      <v-toolbar-title>
        <span class="front-weight-bold color" to="/admin">Diamond</span>
      </v-toolbar-title>
      <v-spacer/>
      <!--      菜单-->
      <v-menu offset-y>
        <template v-slot:activator="{on,attrs}" class="color">
          <v-btn v-bind="attrs" v-on="on" text>
            菜单
          </v-btn>
        </template>
        <v-list>
          <v-list-item v-for="(item,index) in items" :key="index" :to="item.to">
            <v-list-item-title class="v-list-item-title">{{item.title}}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <!--      退出-->
      <v-btn text @click="logout" class="">
        <span>退出</span>
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-app-bar>

    <!--    侧边栏-->
    <v-navigation-drawer app v-model="drawer" color="#f8f8f8">
      <v-card-text class="text-center title color">Dashboard</v-card-text>
      <v-list>
        <v-list-group prepend-icon="mdi-transit-connection">
          <template v-slot:activator><!--具名插槽-->
            <v-list-item-title>用户连接</v-list-item-title>
          </template>
          <v-list-item>
            <v-list-group no-action sub-group prepend-icon="mdi-cable-data"><!--sub-group很重要-->
              <template v-slot:activator>
                <v-list-item-title off-icon="mdi-cog">数据管理</v-list-item-title>
              </template>
              <v-list-item>
                <V-list-item-content>
                  <v-treeview transition :items="item" item-key="id" activatable open-on-click dense light no-action v-model="selected" @update:active="use_database">
                    <template v-slot:prepend="{ item, open }">
                      <v-icon v-if="!item.icon" @click.stop="ttt(item)">{{ open ? 'mdi-database-outline' : 'mdi-database-cog' }}</v-icon>
                      <v-icon v-else >{{ item.icon }}</v-icon>
                    </template>
                  </v-treeview><!--触发事件的写法很奇葩-->
                </V-list-item-content>
              </v-list-item>
            </v-list-group>
          </v-list-item>

          <v-list-item to="/purview">
            <v-list-group sub-group prepend-icon="mdi-account-switch"><!--sub-group很重要-->
              <template v-slot:activator>
                <!--                <v-icon>mdi-cog</v-icon>-->
                <v-list-item-title>
                  权限管理
                </v-list-item-title>
              </template>
            </v-list-group>
          </v-list-item>

          <v-list-item to="/logger">
            <v-list-group sub-group prepend-icon="mdi-post-outline"><!--sub-group很重要-->
              <template v-slot:activator>
                <v-list-item-title off-icon="mdi-cog">日志管理</v-list-item-title>
              </template>
            </v-list-group>
          </v-list-item>

<!--          <v-list-item to="/test">-->
<!--            <v-list-group sub-group prepend-icon="mdi-post-outline">&lt;!&ndash;sub-group很重要&ndash;&gt;-->
<!--              <template v-slot:activator>-->
<!--                <v-list-item-title off-icon="mdi-cog">测试</v-list-item-title>-->
<!--              </template>-->
<!--            </v-list-group>-->
<!--          </v-list-item>-->
        </v-list-group>
      </v-list>

      <v-list>
        <v-list-group prepend-icon="mdi-information">
          <template v-slot:activator>
            <v-list-item-title>关&emsp;&emsp;于</v-list-item-title>
          </template>
          <v-list-item to="/user">
            <v-list-group no-action sub-group prepend-icon="mdi-human"><!--sub-group很重要-->
              <template v-slot:activator>
                <v-list-item-title off-icon="mdi-cog">关于我们</v-list-item-title>
              </template>
            </v-list-group>
          </v-list-item>
        </v-list-group>
      </v-list>
    </v-navigation-drawer>
  </nav>
</template>

<script>
  import {authority_logout, authority_find, database_show, database_use, table_show} from "../../axios/diamon"
  import {use_database} from "../../utils/sql/database"
  import {show_tables} from "../../utils/sql/table"
  import {get, set, set_database} from "../../utils/local"

  export default {
    name: "",
    data: () => ({
      items: [//菜单
        {title: "首页", to: "/database"},
        {title: "控制台", to: "/console"},
        {title: "邮件", to: "/email"},
        // {title: "点击4"},
      ],
      drawer: false,
      selected: [],//选择的数据库表id
      item: [],
      database: "",
    }),
    methods: {

      async logout() {
        let user = get("user");
        user.flag = false;
        user.name = "";
        user.pass = "";
        set(user.flag, user.name, user.pass);
        let newVar = await authority_logout();
        this.$router.replace("*")
      },

      ttt(t) {
        console.log(t);
        // console.log(t.children[0].id);
        // let id = t.children[0].id;
        // let splitElement = id.split("-")[0];
        // this.$router.replace({name: "table", query: {database: JSON.stringify(t.name)}});
      },

      async use_database(item) {
        if (item[0] === undefined) {
          return;
        }
        let s = use_database(item[0]);
        await database_use(s);
        // let tableShow = await table_show(show_tables());
        // let data1 = tableShow.data;
        // let other = tableShow.other;
        // this.item.forEach((k, j) => {
        //   if (k.name === item[0] && k.name === other) {
        //     k.children = [];
        //     data1.forEach((m, o) => {
        //       const c1 = {id: m, name: m, icon: "mdi-table-edit",};
        //       k.children.push(c1)
        //     });
        //   }
        // });
        set_database(item[0]);
        this.goToTable(item[0]);
      },

      goToTable(item) {
        set_database(item);
        this.$router.replace({name: "table", query: {database: JSON.stringify(item)}});
      },

      async get_database() {
        let newVar = await database_show();
        const data = newVar.data;//表里面的数据
        let set = new Set(newVar.data);
        let array = [];
        set.forEach((t, i) => {
          array.push(t);
        });
        array.forEach((t, i) => {
          const iterms = {id: t, name: t, children: [],};
          this.item.push(iterms)
        });
      }

    },

    created() {
      this.get_database();
    },

    watch: {},
  }
</script>

<style scoped>
  .v-list-item-title {
    cursor: pointer;
  }

  .color {
    font-family: impact, sans-serif;
    color: rgb(16, 12, 42);
    font-size: 40px;
    letter-spacing: 0.8pt;
    word-spacing: 3.8pt;

  }

  .menu__item {
    display: block;
    line-height: 20px;
    text-align: center;
  }

</style>
