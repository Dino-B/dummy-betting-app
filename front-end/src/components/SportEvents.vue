<template>
  <div class="sportevents">
    <table class="sportevent">
    <caption class="caption"> Select sport events, enter your bet, check potential winnings and then click the button to create a ticket</caption>
       <thead>
        <tr>
          <th> ID </th>
          <th> Start Time </th>
          <th> Sport </th>
          <th> Home Team </th>
          <th> Away Team </th>
          <th> Home Win </th>
          <th> Draw </th>
          <th> Away Win </th>
        </tr>
      </thead>
      <tr class="sportevent" v-bind:key="spe.id" v-for="spe in allSportEvents">
        <td> {{spe.id}} </td>
        <td> {{spe.startDateTime}} </td>
        <td> {{spe.sport}} </td>
        <td> {{spe.homeTeam}} </td>
        <td> {{spe.awayTeam}} </td>
        <td>
          <input type="radio" :name=spe.id :value="1" @change="onChange" /><label> {{spe.odds.homeWin}} </label>
        </td>
        <td>
         <input type="radio" :name=spe.id :value="0" @change="onChange" /><label> {{spe.odds.draw}} </label>
        </td>
        <td>
          <input type="radio" :name=spe.id :value="2" @change="onChange" /><label> {{spe.odds.awayWin}} </label>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: "SportEvents",
  methods: {
    ...mapActions(['fetchSportEvents', 'calculatePotentialWinnings']),
    onChange(e) {
      e.preventDefault();
      this.calculatePotentialWinnings();
    }
  },
  computed: mapGetters(['allSportEvents']),
  created() {
    this.fetchSportEvents()
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