import Vuex from 'vuex';
import Vue from 'vue';
import sportevents from './modules/sportevents';

// Load Vuex
Vue.use(Vuex);

// Create store
export default new Vuex.Store({
  modules: {
    sportevents
  }
});