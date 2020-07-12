package com.test.mongodb.service;

import com.test.mongodb.bonus.BonusHandler;
import com.test.mongodb.bonus.RealizedBonus;
import com.test.mongodb.exception.CustomAppException;
import com.test.mongodb.model.SportEvent;
import com.test.mongodb.repository.ISportEventsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages sport events operations.
 */
@Service
public class SportEventService {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SportEventService.class);

    @Autowired
    ISportEventsRepository sportEventsRepository;

    @Autowired
    BonusHandler bonusHandler;

    /**
     * @return a list of all sport events
     */
    public List<SportEvent> getAllSportEvents() {
        List<SportEvent> sportEvents = this.sportEventsRepository.findAll();
        LOGGER.info("Found the following sport events: {}", sportEvents);
        return sportEvents;
    }

    /**
     * Looks for and then returns a list sport events that match the given IDs.
     *
     * @param sportEventIds sport event IDs
     * @return a list of found sport events
     * @throws CustomAppException in case any of the IDs can't be matched with a sport event
     */
    public List<SportEvent> getSportEventsFromIds(final List<Long> sportEventIds) throws CustomAppException {
        LOGGER.info("Getting sport events from received sport event IDs");
        List<SportEvent> sportEvents = new ArrayList<>();
        for (Long sportEventId : sportEventIds) {
            Optional<SportEvent> tempSportEvent = this.sportEventsRepository.findById(sportEventId);
            if (tempSportEvent.isEmpty()) {
                String message = "Sport event with ID " + sportEventId + " was not found!";
                LOGGER.error(message);
                throw new CustomAppException(message);
            }

            sportEvents.add(tempSportEvent.get());
        }
        LOGGER.info("Sport events: {}", sportEvents);
        return sportEvents;
    }

    /**
     * Calculates a bonus to be added to the user's potential winnings based on the provided sport events.
     *
     * @param sportEventIds sport event IDs to be checked for bonuses
     * @return realized bonus and its description
     * @throws CustomAppException in case any of the IDs can't be matched with a sport event
     */
    public RealizedBonus calculateBonus(final List<Long> sportEventIds) throws CustomAppException {
        LOGGER.info("Calculating bonus for received sport event IDs");
        List<SportEvent> sportEvents = this.getSportEventsFromIds(sportEventIds);

        return this.bonusHandler.checkConfiguredBonuses(sportEvents);
    }
}
