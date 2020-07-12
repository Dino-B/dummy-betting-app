package com.test.mongodb.bonus;

import com.test.mongodb.config.ConfiguredBonus;
import com.test.mongodb.config.ConfiguredBonuses;
import com.test.mongodb.config.BonusType;
import com.test.mongodb.model.Sport;
import com.test.mongodb.model.SportEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Handles bonus calculation.
 */
@Component
public class BonusHandler {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BonusHandler.class);

    @Autowired
    ConfiguredBonuses configuredBonuses;

    /**
     * Checks if unique sport event bonus is realized depending on configuration and provided sport events.
     *
     * @param sportEvents to be checked
     * @return bonus value and its description
     */
    private RealizedBonus checkUniqueSportEventsBonus(final List<SportEvent> sportEvents) {
        String message = "Unique sport bonus is not configured.";
        RealizedBonus realizedBonus = new RealizedBonus(ConfiguredBonuses.NO_BONUS, message);
        Optional<ConfiguredBonus> uniqueSportBonus =
                configuredBonuses.getConfiguredBonuses()
                        .stream()
                        .filter(configuredBonus ->  BonusType.UNIQUE_SPORTS.getDescription().equals(configuredBonus.getBonusType()))
                        .findAny();
        if (uniqueSportBonus.isEmpty()) {
            // Unique sport events bonus is not configured
            LOGGER.info(message);
            return realizedBonus;
        }

        // Get the number of unique sports present in the provided sport events and compare it with the configured limit
        HashSet<Sport> sports = new HashSet<>();
        sportEvents.forEach(sportEvent -> sports.add(sportEvent.getSport()));
        if (sports.size() >= uniqueSportBonus.get().getRequired()) {
            float bonus = uniqueSportBonus.get().getValue();
            message = String.format(
                    "Unique sport bonus of %s realized! Found %s unique sports.", bonus, sports.size());
            LOGGER.info(message);
            realizedBonus.setDescription(message);
            realizedBonus.setValue(bonus);
            return realizedBonus;
        }
        message = "Unique sport bonus was not realized.";
        LOGGER.info(message);
        realizedBonus.setDescription(message);
        return realizedBonus;
    }

    /**
     * Checks if same sport event bonus is realized depending on configuration and provided sport events.
     *
     * @param sportEvents to be checked
     * @return bonus value and its description
     */
    private RealizedBonus checkSameSportEventsBonus(final List<SportEvent> sportEvents) {
        String message = "Same sport bonus is not configured.";
        RealizedBonus realizedBonus = new RealizedBonus(ConfiguredBonuses.NO_BONUS, message);

        Optional<ConfiguredBonus> sameSportEventsBonus =
                configuredBonuses.getConfiguredBonuses()
                        .stream()
                        .filter(configuredBonus ->  BonusType.SAME_SPORT_EVENTS.getDescription().equals(configuredBonus.getBonusType()))
                        .findAny();
        if (sameSportEventsBonus.isEmpty()) {
            // Same sport events bonus is not configured
            LOGGER.info(message);
            return realizedBonus;
        }

        List<Sport> sports = new ArrayList<>();
        sportEvents.forEach(sportEvent -> sports.add(sportEvent.getSport()));
        LOGGER.info("The following sports were present in the selected events: {}", sports);

        // check if the same sport was present in X number of sport events
        HashSet<Sport> distinct = new HashSet<>(sports);
        for (Sport sport : distinct) {
            int numSameSportEvents = Collections.frequency(sports, sport);
            if (numSameSportEvents >= sameSportEventsBonus.get().getRequired()) {
                float bonus = sameSportEventsBonus.get().getValue();
                message = String.format("Same sport bonus of %s realized! Found %s events for the same sport.",
                        bonus, numSameSportEvents);
                LOGGER.info(message);
                realizedBonus.setValue(bonus);
                realizedBonus.setDescription(message);
                return realizedBonus;
            }
        }
        message = "Same sport bonus was not realized.";
        LOGGER.info(message);
        realizedBonus.setDescription(message);
        return realizedBonus;
    }

    /**
     * Checks if any sport event bonus is realized depending on configuration and provided sport events.
     *
     * @param sportEvents to be checked
     * @return bonus value and its description
     */
    public RealizedBonus checkConfiguredBonuses(final List<SportEvent> sportEvents) {
        LOGGER.info("Configured bonuses: {}", configuredBonuses);
        RealizedBonus realizedUniqueSportsBonus = this.checkUniqueSportEventsBonus(sportEvents);
        RealizedBonus realizedSameEventBonus = this.checkSameSportEventsBonus(sportEvents);

        RealizedBonus totalRealizedBonus = RealizedBonus.add(realizedUniqueSportsBonus, realizedSameEventBonus);
        LOGGER.info("Total realized bonus is: {}", totalRealizedBonus);
        return totalRealizedBonus;
    }
}
