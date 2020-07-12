<template>
  <div class="sportevents">
    <table class="sportevent">
    <caption class="caption"> Tickets: </caption>
       <thead>
        <tr>
          <th> ID </th>
          <th> Stake </th>
          <th> Potential Winnings </th>
          <th> Status </th>
          <th> Event ID | Chosen Result </th>
        </tr>
      </thead>
      <tr class="sportevent" v-bind:key="ticket.id" v-for="ticket in allTickets">
        <td> {{ticket.id}} </td>
        <td> {{ticket.stake}} </td>
        <td> {{ticket.potentialWinnings}} </td>
        <td> {{ticket.ticketStatus}} </td>
        <td>
          <tr class="sportevents" v-bind:key=key  v-for="(value, key) in ticket.bets">
            {{ key }} | {{ value }}
          </tr>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: "ShowTickets",
  methods: {
    ...mapActions(['fetchTickets'])
  },
  computed: mapGetters(['allTickets']),
  created() {
    this.fetchTickets()
  }
}
</script>


<style scoped>

.sportevents {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  grid-gap: 0rem;
}

.sportevent {
  border: 1px solid #ccc;
  background: #FFFFF0;
  padding: 2rem;
  border-radius: 1px;
  text-align: center;
  position: relative;
  cursor: pointer;
}

.caption {
  border: 1px solid #ccc;
  background: #F5F5DC;
  padding: 1rem;
  border-radius: 1px;
  text-align: left;
  position: relative;
  cursor: pointer;
}

</style>