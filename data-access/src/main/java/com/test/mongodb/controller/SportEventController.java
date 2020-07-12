package com.test.mongodb.controller;

import com.test.mongodb.exception.CustomAppException;
import com.test.mongodb.model.ErrorObject;
import com.test.mongodb.service.SportEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Handles REST requests related to sport events.
 */
@RestController
@RequestMapping("/sport-events")
public class SportEventController {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SportEventController.class);

    @Autowired
    SportEventService sportEventService;

    /**
     * Gets all sport events.
     *
     * @return OK and list of sport events if successful
     *         500 and an error object in case of an error accessing the database
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getSportEvents() {
        try {
            return new ResponseEntity<>(this.sportEventService.getAllSportEvents(), HttpStatus.OK);
        } catch (DataAccessException exception) {
            String errorMessage = "Error occurred looking for all sport events. " + exception.getMessage();
            LOGGER.error("{}", errorMessage, exception);
            ErrorObject errorObject = new ErrorObject(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    exception.getMessage());

            return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Calculates the bonus to be added to user's potential winnings depending on configuration and provided sport events.
     *
     * @param sportEventIds IDs of the sport events to be checked for bonuses.
     * @return OK and bonus value with description if successful
     *         400 and an error object if the provided sport event list is empty or
     *           it contains any id that can't be matched with a sport event
     *         500 and an error object in case of an error accessing the database
     */
    @PostMapping(path = "/bonus", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> calculateBonus(@RequestBody final List<Long> sportEventIds) {
        LOGGER.info("Received a request to calculate bonus factor for sport event IDs: {}", sportEventIds);
        if (sportEventIds.isEmpty()) {
            String message = "Some sport event IDs must be specified";
            LOGGER.error(message);
            ErrorObject errorObject = new ErrorObject(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    message);
            return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<>(this.sportEventService.calculateBonus(sportEventIds), HttpStatus.OK);
        } catch (CustomAppException cae) {
            String errorMessage = "Bonus calculation failed. " + cae.getMessage();
            LOGGER.error("{}", errorMessage, cae);
            ErrorObject errorObject = new ErrorObject(
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    errorMessage
            );

            return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
        } catch (DataAccessException exception) {
            String errorMessage = "Exception occurred while calculating bonus factor. " + exception.getMessage();
            LOGGER.error("{}", errorMessage, exception);

            ErrorObject errorObject = new ErrorObject(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    errorMessage
            );
            return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
