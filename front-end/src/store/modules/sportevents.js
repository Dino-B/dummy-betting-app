import axios from 'axios';

const dataAccessEndpoint = "http://10.0.2.15:30334";
const userName = "DummyUser";

const state = {
    sportEvents: [],
    potentialWinnings: 1,
    tickets: [],
    userBalance: 0,
    ticketNumber: 0
};

const getters = {
  allSportEvents: state => state.sportEvents,
  allTickets: state => state.tickets
};

const actions = {
  async fetchSportEvents( {commit, dispatch} ) {
    try {
      const response = await axios.get(dataAccessEndpoint + '/sport-events');
      document.getElementById("userName").value = userName;
      commit('setSportEvents', response.data);
      dispatch('fetchUserInfo');
    } catch (error) {
      alert('Failed to get the list of sport events!')
      console.log('Failed to get the list of sport events', error);
      console.log(error.request);
      console.log(error.response);
    }
  },

  async calculatePotentialWinnings( {commit} ) {
    // take user's bet as input
    var potentialWinnings = document.getElementById("stake").value;
    var regex=/^[1-9][0-9]*$/;
    if (!potentialWinnings.match(regex)) {
      alert("Invalid bet value! Please enter an unsigned integer");
      return;
    }

    var sportEventIds = [];
    var sportEventCounter = 0;
    // Go through all radio buttons and multiply the odds of the selected choices with user's input
    for (var i=0; i < state.sportEvents.length; i++) {
      var radios = document.getElementsByName(state.sportEvents[i].id);
        for (var j=0; j < radios.length; j++) {
          if (radios[j].checked) {
            var factor;
            sportEventIds[sportEventCounter] = state.sportEvents[i].id;
            sportEventCounter++;
            switch(radios[j].value) {
              case "0":
                factor = state.sportEvents[i].odds.draw;
                break;
              case "1":
                factor = state.sportEvents[i].odds.homeWin;
                break;
              case "2":
                factor = state.sportEvents[i].odds.awayWin;
                break;
              default:
            }
            potentialWinnings *= factor;
            break;
          }
        }
      }
      potentialWinnings = potentialWinnings.toFixed(2);
      document.getElementById("multi").value = potentialWinnings;

      try {
        const response = await axios.post(dataAccessEndpoint + '/sport-events/bonus', sportEventIds);
        document.getElementById("bonus").value = response.data.description;
        document.getElementById("bonusValue").value = response.data.value;
        potentialWinnings = parseFloat(potentialWinnings) + parseFloat(response.data.value);
        potentialWinnings = parseFloat(potentialWinnings).toFixed(2);
      } catch (error) {
        // set bonus to 0 if unable to retrieve related information
        document.getElementById("bonus").value = 'Failed to get bonus information';
        document.getElementById("bonusValue").value = 0;
        console.log(error)
        console.log(error.response.data);
        console.log(error.response.status);
      }

      document.getElementById("potentialWinnings").value = potentialWinnings;
      commit('setPotentialWinnings', potentialWinnings);
  },

  async addTicket( {dispatch} ) {
    var stake = document.getElementById("stake").value;
    var regex=/^[1-9][0-9]*$/;
    if (!stake.match(regex)) {
      alert("Invalid bet value! Please enter an unsigned integer");
      return;
    }

    var bets = {};
    // capture all selected radio buttons
    for (var i=0; i < state.sportEvents.length; i++) {
      var radios = document.getElementsByName(state.sportEvents[i].id);
      for (var j=0; j < radios.length; j++) {
        if (radios[j].checked) {
          bets[state.sportEvents[i].id] = radios[j].value;
          break;
        }
      }
    }

    dispatch('calculatePotentialWinnings');
    var potentialWinnings = state.potentialWinnings;
    var user = userName;
    try {
      await axios.post(dataAccessEndpoint + '/tickets', {user, bets, stake, potentialWinnings});
      dispatch('fetchUserInfo');
    } catch (error) {
      let errorMsg = "Failed to add a new ticket!";
      console.log(errorMsg, error);
      console.log(error.response.data);
      if (error.response.data.error) {
        errorMsg += " " + error.response.data.error;
      }
      alert("Failed to create the ticket! " + error.response.data.error)
    }
  },

  async fetchTickets( {commit} ) {
    try {
      const response = await axios.get(dataAccessEndpoint + '/tickets');
      commit('setTickets', response.data);
    } catch (error) {
      console.log("Failed to list all tickets!", error);
      alert("Failed to list all tickets!")
    }
  },

  async fetchUserInfo( {commit} ) {
    try {
      const response = await axios.get(dataAccessEndpoint + '/users/' + userName);

      commit('setUserBalance', response.data.accountBalance);
      document.getElementById("balance").value = state.userBalance;

      commit('setTicketNumber', response.data.ticketNumber);
      document.getElementById("ticketNumber").value = response.data.ticketNumber;
    } catch (error) {
      console.log("Failed to get user information", error);
      commit('setUserBalance', 0);
      document.getElementById("balance").value = 'Failed to get user balance!';
    }
  }
};

const mutations = {
  setSportEvents: (state, sportEvents) => (state.sportEvents = sportEvents),
  setPotentialWinnings: (state, potentialWinnings) => (state.potentialWinnings = potentialWinnings),
  setTickets: (state, tickets) => (state.tickets = tickets),
  setUserBalance: (state, userBalance) => (state.userBalance = userBalance),
  setTicketNumber: (state, ticketNumber) => (state.ticketNumber = ticketNumber)
};


export default {
  state,
  getters,
  actions,
  mutations
};