import Vue from 'vue'
import Vuex from 'vuex'
import states from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [states()],
  state: {
    selected: "",
    key: "",
    table: ""
  },
  mutations: {
    selected(state, selected) {
      state.selected = selected;
    },
    key(state, key) {
      state.key = key;
    },
    table(state, table) {
      state.table = table;
    },
  },
  actions: {
    selected({commit}, selected) {
      commit("selected", selected);
    },
    key({commit}, key) {
      commit("key", key);
    },
    table({commit}, table) {
      commit("table", table);
    },
  },
  modules: {}
})
