<template>
  <v-app>
    <Tab/>
    <v-main>
      <router-view/>
    </v-main>
    <Footer/>
  </v-app>
</template>

<script>
  import Tab from "./Tab";
  import Footer from "./Footer";

  export default {
    name: "dashboard",
    components: {Tab, Footer},
    data: () => ({}),
    methods: {},
    created() {
    }
  }
</script>

<style scoped>

</style>
