<template>
  <v-container>
    <v-row justify="space-around">
      <v-col lg="12" sm="12" md="12" cols="12">
        <v-card>
          <v-toolbar-title>
            <v-breadcrumbs :items="item"/>
          </v-toolbar-title>
          <v-divider class="mx-4" inset vertical/>
          <v-spacer/><!--间隔-->
          <v-img src="../../../assets/zuanshi.jpg">
          </v-img>
          <v-card-text>
            <div class="font-weight-bold ml-8 mb-2">
              我们
            </div>
            <v-timeline align-top dense>
              <v-timeline-item v-for="(message,index) in messages" :key="index" :color="message.color"
                               small>
                <div>
                  <div class="font-weight-normal">
                    <strong>{{ message.from }}</strong> @{{ message.name }}
                  </div>
                  <div v-if="message.from !=='email'">{{ message.message }}</div>
                  <div class="proint" v-if="message.from ==='email'" @click="goToEmail">{{ message.message }}</div>
                </div>
              </v-timeline-item>
            </v-timeline>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  export default {
    data: () => ({
      item: [
        {text: '首页', disabled: false, to: '/database',},
        {text: '关于', disabled: false, to: '',},],
      messages: [
        {from: 'phone', message: `15993596769.`, name: 'diamond', color: 'deep-purple lighten-1',},
        {from: 'qq', message: '2083298549', name: 'diamond', color: 'green',},
        {from: 'email', message: 'zefegg@163.com', name: 'diamond', color: 'deep-purple lighten-1',},
      ],
    }),
    methods: {
      goToEmail() {
        this.$router.replace("/email")
      },
    },
  }
</script>

<style scoped>
  .font-weight-bold {
    cursor: pointer;
  }

  .proint {
    cursor: pointer;
    color: #000000;
  }
</style>
