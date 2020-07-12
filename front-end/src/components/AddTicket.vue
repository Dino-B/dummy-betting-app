<template>
  <div class="grid-container">
    <form>
      <label for="balance" class="userName">User name: </label>
      <input id="userName" readonly/>

      <label for="balance" class="balance">Account balance: </label>
      <input type="text" id="balance" readonly/>

      <label for="ticketNumber" class="ticketNumber">Total tickets: </label>
      <input type="text" id="ticketNumber" readonly/>
    </form>
    <form @submit="onSubmit">
      <label for="bet" class="bet">Bet: </label>
      <input type="input" id="stake" @change="onChange" value=1 required />

      <label for="potentialWinnings" class="potentialWinnings">Potential winnings: </label>
      <input type="text" readonly placeholder="Potential winnings" id="potentialWinnings"/>

      <input type="submit" class="submit" value="Click to create the ticket" />
    </form>
    <form>
      <label for="multi" class="bonus">Multiplier: </label>
      <input type="text" readonly placeholder="1" id="multi" class="bonus"/>

      <label for="bonus" class="bonus">Bonus: </label>
      <input type="text" readonly placeholder="0" id="bonusValue" class="bonus"/>
      <textarea type="text" readonly id="bonus"></textarea>
    </form>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: "AddTicket",
  methods: {
    ...mapActions(['addTicket', 'calculatePotentialWinnings']),
    onSubmit(e) {
      e.preventDefault();
      this.addTicket();
    },
    onChange(e) {
      e.preventDefault();
      this.calculatePotentialWinnings();
    }
  }
}
</script>


<style scoped>

form {
  display: grid;
  padding: 1em;
  background: #FFFFF0;
  border: 1px solid #c1c1c1;
  margin: 1rem auto 1rem auto;
  max-width: 600px;
  padding: 1em;
  grid-template-columns: 150px 1fr;
  grid-gap: 10px;
}

.grid-container {
  display: grid;
  grid-template-columns: auto auto auto;
  grid-gap: 10px;
  padding: 10px;
}

form input {
  background: #fff;
  border: 1px solid #9c9c9c;
}

label {
  padding: 0.5em 0.5em 0.5em 0;
  text-align: right;
  grid-column: 1 / 2;
  vertical-align: middle;
}

label.bonus {
  text-align: center;
  grid-column: 1 / 2;
}

input {
  padding: 0.7em;
  margin-bottom: 0.5rem;
  grid-column: 2 / 3;
}

input.bonus {
  padding: 0.7em;
  margin-bottom: 0.5rem;
}

input.submit {
  padding: 0.7em;
  margin-bottom: 0.5rem;
  grid-column: 1 / 3;
}

input:focus {
  outline: 3px solid black;
}

input:read-only {
  background: #F5F5DC;
}

textarea {
  background: #F5F5DC;
  grid-column: 1 / 3;
  height: 75px;
}

textarea:focus {
  outline: 3px solid black;
}
</style>