<template>

  <v-container fluid>

    <v-snackbar :color=" flag?'red':'green' " outlined light tile centered v-model="snackbar" :timeout="2000">
      {{ text }}
      <template v-slot:action="{ attrs }">
        <v-btn color="blue" text v-bind="attrs" @click="snackbar = false">关闭</v-btn>
      </template>
    </v-snackbar>


    <v-row align="center">

      <v-col class="d-flex" offset="1" cols="3" sm="3" md="3" lg="3">
        <v-text-field outlined dense clearable :items="title" v-model="title" placeholder="输入标题"
                      background-color=" lighten-2" label="标题"/>
      </v-col>
      <v-col class="d-flex" offset="1" cols="3" sm="3" md="3" lg="3"/>
      <v-col cols="1" offset="2" sm="1" md="1" lg="1">
        <v-btn top depressed right color="#6777ef" @click="sub">发送</v-btn>
      </v-col>
      <v-col offset="1" cols="10" sm="10" md="10" lg="10">
        <v-textarea auto-grow background-color=" lighten-2" outlined clearable name="input-7-4" label="邮件主体，暂只支持文本"
                    v-model="context"
                    height="500"/> <!-- @mouseup.stop冒泡了-->
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import {send_email} from "../../../axios/diamon"

  export default {
    name: "email",
    data: () => ({
      title: "",
      context: "",
      snackbar: false,
      text: "",
      flag: false,
    }),
    mounted() {
    },
    methods: {
      async sub() {
        if (this.title === "" || this.context === "") {
          return;
        }
        let newVar = await send_email(this.title, this.context);
        this.snackbar = true;
        if (newVar.code === 400) {
          this.text = newVar.data;
          this.flag = true;
        } else {
          this.title = "";
          this.context = "";
          this.text = "发送成功";
        }


      },

    },
    created() {
    },
  }
</script>

<style>
  .v-text-field.v-text-field--enclosed .v-text-field__details {
    display: none !important;
  }
</style>
