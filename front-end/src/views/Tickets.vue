<template>
  <div id="tickets">
    <ShowTickets/>
  </div>
</template>

<script>
import ShowTickets from '../components/ShowTickets';

export default {
  name: 'Tickets',
  components: {
    ShowTickets
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
