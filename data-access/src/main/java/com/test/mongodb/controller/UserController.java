package com.test.mongodb.controller;

import com.test.mongodb.exception.CustomAppException;
import com.test.mongodb.model.ErrorObject;
import com.test.mongodb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles REST requests related to users.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /**
     * Gets a user with the given ID (name).
     *
     * @param userId user name
     * @return 200 and user information if successful
     *         404 and an error object if user with the given name is not found
     *         500 and an error object in case of an error accessing the database
     */
    @GetMapping(produces = "application/json", value = "/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable final String userId) {
        LOGGER.info("Received a request get a user with ID: {}", userId);

        try {
            return new ResponseEntity<>(this.userService.getUser(userId), HttpStatus.OK);
        } catch (CustomAppException cae) {
            // User not found

            LOGGER.error(cae.getMessage());
            ErrorObject errorObject = new ErrorObject(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    cae.getMessage());
            return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
        } catch (DataAccessException exception) {
            // Data access error

            LOGGER.error("Error occurred looking for a user with name: {}", userId, exception);
            ErrorObject errorObject = new ErrorObject(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    exception.getMessage());
            return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
