package com.test.mongodb.provision.provisioner;

import com.test.mongodb.model.Odds;
import com.test.mongodb.model.Sport;
import com.test.mongodb.model.SportEvent;
import com.test.mongodb.repository.ISportEventsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles provisioning of sport events.
 */
@Service
public class SportEventProvisioner implements IProvisioner {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SportEventProvisioner.class);

    @Autowired
    ISportEventsRepository sportEventsRepository;

    /**
     * Football team names to be used when generating sport events.
     */
    private static final List<String> FOOTBALL_TEAM_POOL = List.of(
            "Hajduk Split", "Slavia Prague", "Man Utd", "Bayern Munchen", "Real Madrid", "Juventus"
    );

    /**
     * Basketball team names to be used when generating sport events.
     */
    private static final List<String> BASKETBALL_TEAM_POOL = List.of(
            "LA Lakers", "Chicago Bulls", "CSKA", "Barcelona", "Zadar", "KK Split"
    );

    private static final List<String> RUGBY_TEAM_POOL = List.of(
            "RK Nada", "Leicester Tigers", "Hurricanes", "Jaguares"
    );

    /**
     * Generates date/time offset by hour value in 1-72 interval compared to current date/time.
     *
     * @return LocalDateTime object with random date/time in 1-72h interval from now
     */
    private LocalDateTime generateRandomDateTime() {
        long minHourIncrement = 1L;
        long maxHourIncrement = 72L;
        long hourIncrement = minHourIncrement + (long) (Math.random() * (maxHourIncrement - minHourIncrement));

        return LocalDateTime.now().plus(hourIncrement, ChronoUnit.HOURS);
    }

    private float generateRandomOdd() {
        float minOdd = 1.00f;
        float maxOdd = 5.00f;
        float randomOdd = minOdd + (float) (Math.random() * (maxOdd - minOdd));

        return (float) Math.round(randomOdd * 100) / 100;
    }

    /**
     * Generates Odds object with odds in 1.00 - 5.00 range.
     *
     * @return object with random home/away win and draw odds
     */
    private Odds generateRandomOdds() {
        return new Odds(this.generateRandomOdd(), this.generateRandomOdd(), this.generateRandomOdd());
    }

    /**
     * Generates a list of sport events to be provisioned.
     *
     * @return list of sport events
     */
    private List<SportEvent> generate() {
        LOGGER.info("Generating sport events to be provisioned");
        List<SportEvent> sportEvents = new ArrayList<>();

        // Map with sport categories as keys and corresponding team names as values
        Map<Sport, List<String>> teamsMap = new HashMap<>();
        teamsMap.put(Sport.FOOTBALL, FOOTBALL_TEAM_POOL);
        teamsMap.put(Sport.BASKETBALL, BASKETBALL_TEAM_POOL);
        teamsMap.put(Sport.RUGBY, RUGBY_TEAM_POOL);

        List<String> teamList;
        // Go through all map elements and create sport event objects
        long idCounter = 0;
        for (Sport sport : teamsMap.keySet()) {
            teamList = new ArrayList<>(List.copyOf(teamsMap.get(sport)));
            Collections.shuffle(teamList);

            // Remove the last list element in case the number of elements is not an even number
            if (teamList.size() % 2 != 0) {
                teamList.remove(teamList.get(teamList.size() - 1));
            }

            // Element at id i is the home team, i+1 is the away team
            // Iterate the list in increments of 2
            for (int i = 0; i <= teamList.size() - 1; i += 2) {
                idCounter++;
                SportEvent sportEvent = new SportEvent(
                        idCounter,
                        this.generateRandomDateTime(),
                        teamList.get(i), // home team
                        teamList.get(i + 1), // away team
                        sport,
                        this.generateRandomOdds());
                sportEvents.add(sportEvent);
            }
        }
        LOGGER.info("Generated the following events: {}", sportEvents);
        return sportEvents;
    }

    /**
     * Provision generated sport events.
     *
     * @param sportEvents events to be provisioned
     */
    private void provision(final List<SportEvent> sportEvents) {
        LOGGER.info("Deleting all previously provisioned sport events");
        this.sportEventsRepository.deleteAll();

        LOGGER.info("Provisioning sport events");
        this.sportEventsRepository.insert(sportEvents);
    }

    @Override
    public void generateAndProvision() {
        this.provision(this.generate());
    }
}
