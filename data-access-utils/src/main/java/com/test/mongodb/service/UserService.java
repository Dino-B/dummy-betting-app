package com.test.mongodb.service;

import com.test.mongodb.exception.CustomAppException;
import com.test.mongodb.model.User;
import com.test.mongodb.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Contains logic for user management.
 */
@Service
public class UserService {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    IUserRepository userRepository;

    /**
     * Finds an user with the given id (name).
     *
     * @param userId user name
     * @return User with the given id
     * @throws CustomAppException in case an user with the provided name is not found
     */
    public User getUser(final String userId) throws CustomAppException {
        Optional<User> potentialUser = this.userRepository.findById(userId);
        if (potentialUser.isEmpty()) {
            throw new CustomAppException(String.format("User with ID = %s was not found", userId));
        }

        User user = potentialUser.get();
        LOGGER.info("Found a user with id {}: {}", userId, user);
        return user;
    }

    /**
     * Find a user with the given name and check that there are enough funds on his account to make the proposed bet.
     *
     * @param userId user name
     * @param stake amount of money to be used as stake
     * @throws CustomAppException in case: - the user with the provided name is not found
     *                                     - user doesn't have enough funds to make the bet
     */
    public void validateAndUpdateUserBalance(final String userId, final double stake) throws CustomAppException {
        User user = this.getUser(userId);
        double remainingBalance = user.getAccountBalance() - stake;
        if (remainingBalance < 0) {
            String message = String.format(
                    "There are not enough found on %s's account to place a bet with stake %s", userId, stake);
            throw new CustomAppException(message);
        }
        LOGGER.info("{} has enough funds on his account to place a bet with stake {}", userId, stake);

        user.setAccountBalance(remainingBalance);
        user.setTicketNumber(user.getTicketNumber() + 1);
        LOGGER.info("Updating {}'s account balance to {}", userId, remainingBalance);
        this.userRepository.save(user);
    }
}
