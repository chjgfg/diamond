<template>
  <v-bottom-navigation>
    <v-btn>
      <span>15993596769</span>
      <span>phone:</span>
    </v-btn>
    <v-divider class="mx-4" inset vertical/>
    <v-btn>
      <span>2083298549</span>
      <span>qq:</span>
    </v-btn>
    <v-divider class="mx-4" inset vertical/>
    <v-btn @click="goToEmail">
      <span>zefegg@163.com</span>
      <span>email:</span>
    </v-btn>
  </v-bottom-navigation>
</template>

<script>
  export default {
    data: () => ({
      value: 'recent'
    }),
    methods: {
      goToEmail() {
        this.$router.replace("/email")
      }
    }
  }
</script>

<style scoped>

</style>
