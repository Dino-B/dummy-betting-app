<template>
  <div id="home">
    <SportEvents v-bind:sportEvents="sportEvents"/>
    <AddTicket/>
  </div>
</template>

<script>
import SportEvents from '../components/SportEvents';
import AddTicket from '../components/AddTicket';

export default {
  name: 'Home',
  components: {
    SportEvents,
    AddTicket
  },
  methods: {
    sportEvents() {
    }
  }
}
</script>

<style>
body {
  font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
  line-height: 1.6;
  background: #efefef;
}
.container {
  max-width: 1100px;
  margin: auto;
  overflow: auto;
  padding: 0 2rem;
}

</style>
