<template>
  <v-container fluid>
    <v-row>
      <v-col cols="12" sm="12" md="12" lg="12">
        <v-data-table :headers="headers" :items="desserts" sort-by="calories" class="elevation-1">
          <template v-slot:top>
            <v-toolbar flat>
              <v-breadcrumbs :items="items"/>
              <v-divider class="mx-4" inset vertical/>
            </v-toolbar>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-tooltip top>
              <template v-slot:activator="{ on, attrs }">
                <!--                <v-icon small class="mr-2" color="#6777ef" v-bind="attrs" v-on="on"  @click="delete_one(item)">mdi-delete</v-icon>-->
                <v-icon small class="mr-2" color="#6777ef" v-bind="attrs" v-on="on" @click="goToLog(item)">
                  mdi-arrow-right-bold-circle
                </v-icon>
              </template>
              <span>去日志详情页</span>
            </v-tooltip>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import {authority_find} from "../../../axios/diamon"
  import {get} from "../../../utils/local"

  export default {
    data: () => ({
      headers: [],//表头
      desserts: [],//表数据
      editedIndex: -1,
      items: [
        {text: '首页', disabled: false, to: '/database',},
        {text: '日志列表', disabled: false, to: '',},],
    }),
    methods: {
      async init() {
        console.log(get("user").name);
        let newVar = await authority_find({name: get("user").name});
        let data = newVar.data;
        let otherDate = newVar.other_date;
        let other = newVar.other;
        if (newVar.other === undefined) {//其他的
          otherDate.forEach((t, i) => {
            // console.log(t);
            data[t].forEach((m, j) => {
              console.log(m);
              const ll = {database: t, table: m};
              this.desserts.push(ll);
            });
          });
        } else {
          data.forEach((m, j) => {
            let mElement = m[otherDate[j]];
            if (mElement.length !== 0) {
              mElement.forEach((t, i) => {
                let newVar = other + otherDate[j] + "\\";
                let replace = t.replace(newVar, "");
                const ll = {database: otherDate[j], table: replace};
                this.desserts.push(ll);
              });
            }
          });
        }
        this.headers = [
          {text: "Database", align: 'start', value: 'database',},
          {text: 'Table', value: 'table', sortable: false},
          {text: 'Actions', value: 'actions', sortable: false},
        ]
      },

      goToLog(key) {
        this.$store.dispatch("key", key.database + "-" + key.table);
        this.$router.replace({name: "log", query: {key: key}})
      },
    },

    created() {
      this.init()
    },

  }
</script>

<style scoped>

</style>
