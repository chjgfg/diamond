<template>
  <v-app id="login">
    <v-container class="fill-height" lg="5" md="5" sm="5"> <!--垂直居中-->

      <v-snackbar color="red " outlined light tile centered v-model="snackbar" :timeout="2000">
        {{ text }}
        <template v-slot:action="{ attrs }">
          <v-btn color="blue" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
        </template>
      </v-snackbar>


      <v-row class="justify-center"><!--flex布局-->
        <v-col cols="12" md="5" lg="5" sm="5">
          <!--          <v-snackbar :timeout="2000" :value="look" color="success" outlined absolute centered shaped top>{{msg}}-->
          <!--          </v-snackbar>-->
          <!--          <v-snackbar :timeout="2000" :value="kool" color="red" outlined absolute centered shaped top>{{msg}}</v-snackbar>-->
          <v-card color="" xs="5" sm="3" md="3" lg="3" elevation="8" outlined :loading="loading">
            <v-card-text>
              <h1 class="blue--text display-1 text-center color">Diamond</h1>
            </v-card-text>
            <!--          表单-->
            <v-card-text>
              <v-form ref="form" v-model="valid" lazy-validation class="form">
                <v-text-field v-model="name" label="账号" required/><!--          :rules="nameRules" -->
                <v-text-field v-model="pass" label="密码" required/>
                <br/>
                <br/>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer/>  <!--     可以用来控制元素的排布局         -->
              <v-btn class="primary mr-4" color="success" @click="login">登录</v-btn>
              <v-spacer/>  <!--     可以用来控制元素的排布局         -->
              <v-btn color="error" class="mr-4" @click="reset">重置</v-btn>
              <v-spacer/>  <!--     可以用来控制元素的排布局         -->
            </v-card-actions>
          </v-card>

        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>

  import {authority_login, ooo} from "../../axios/diamon"
  import {set} from "../../utils/local"

  export default {
    name: "login",

    data: () => ({
      loading: false,//加载条
      valid: true,
      name: '',
      pass: '',
      snackbar: false,
      text: "",
    }),
    methods: {
      reset() {
        this.$refs.form.reset()
      },
      async login() {
        let newVar = await authority_login({user: this.name, pass: this.pass});
        console.log(newVar);
        if (newVar.code === 400) {
          this.snackbar = true;
          this.text = newVar.data;
        } else {
          set(true, this.name, this.pass);
          await this.$router.replace("/database");
        }

      },
      // async oo(){
      //   let newVar = await ooo();
      //   console.log(newVar);
      // }
    },
    mounted() {
      // this.oo()
    }
  }
</script>

<style scoped>
  .color {
    font-family: impact, sans-serif !important;
    color: rgb(16, 12, 42) !important;
    font-size: 46px !important;
    font-weight: bold !important;
    letter-spacing: 2.2pt !important;
  }

  .card {
    margin: auto;
    width: 700px;
    height: 400px;
  }

  .card-child {
    margin: 5% 5%;
  }

  .form {
    width: 90%;
    margin-left: 5%;
  }

  .form > * {
    margin-top: 20px;
  }

</style>
