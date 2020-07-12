package com.test.mongodb.controller;

import com.test.mongodb.exception.CustomAppException;
import com.test.mongodb.model.ErrorObject;
import com.test.mongodb.model.Ticket;
import com.test.mongodb.service.TicketService;
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

/**
 * Handles REST requests related to tickets.
 */
@RestController
@RequestMapping("/tickets")
public class TicketController {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    TicketService ticketService;

    /**
     * Gets all tickets.
     *
     * @return OK and list of tickets if successful
     *         500 and an error object in case of an error accessing the database
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getAllTickets() {
        LOGGER.info("Received a request to list all tickets");

        try {
            return new ResponseEntity<>(this.ticketService.getAllTickets(), HttpStatus.OK);
        } catch (DataAccessException exception) {
            String errorMessage = "Error occurred looking for all tickets. " + exception.getMessage();
            LOGGER.error("{}", errorMessage, exception);
            ErrorObject errorObject = new ErrorObject(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    errorMessage);

            return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Validate and then insert the provided ticket to the database.
     *
     * @param ticket ticket to be validated/inserted
     * @return 200 and ticket information if successful
     *         400 and error object in case ticket validation fails
     *         500 and error object in case of an error accessing the database
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addTicket(@RequestBody final Ticket ticket) {
        LOGGER.info("Received a request to add a new ticket {}", ticket);

        try {
            return new ResponseEntity<>(this.ticketService.addNewTicket(ticket), HttpStatus.OK);
        } catch (CustomAppException cae) {
            String errorMessage = "Ticket validation failed. " + cae.getMessage();
            LOGGER.error("{}", errorMessage, cae);
            ErrorObject errorObject = new ErrorObject(
              HttpStatus.BAD_REQUEST.value(),
              HttpStatus.BAD_REQUEST.getReasonPhrase(),
              errorMessage
            );

            return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
        } catch (DataAccessException  exception) {
            String errorMessage = "Error occurred while adding a new ticket. " + exception.getMessage();
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
