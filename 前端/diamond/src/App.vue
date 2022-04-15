<template>
  <router-view/>
</template>

<script>
  // import {start} from "./back/diamond";
  export default {
    name: 'App',
    data: () => ({
    }),
    methods: {
      beforeunloadHandler(e) {
        e = e || window.event;
        if (e) {
          e.returnValue = '关闭提示'
        }
        console.log('关闭提示');
        return '关闭提示'
      }
    },
    mounted() {
    },
    destroyed() {
      // window.removeEventListener('beforeunload', e => this.beforeunloadHandler(e))
    },
  };
</script>
<style>
  * {
    /*background-color: #8bffce;*/
  }

</style>
