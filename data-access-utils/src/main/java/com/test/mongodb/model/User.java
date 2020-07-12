package com.test.mongodb.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user.
 */
@Getter
@ToString
@Document(collection = User.USERS_COLLECTION)
public class User {
    /**
     * User collection.
     */
    public static final String USERS_COLLECTION = "users";

    /**
     * User name will be used as an id.
     */
    @Id
    private String name;

    /**
     * Number of tickets created by this user.
     */
    private int ticketNumber;

    /**
     * Account balance for the user.
     */
    private double accountBalance;

    public User(final String name, final double accountBalance) {
        this.name = name;
        this.ticketNumber = 0;
        this.accountBalance = accountBalance;
    }

    public double getAccountBalance() {
        return this.accountBalance;
    }

    public void setAccountBalance(final double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setTicketNumber(final int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getTicketNumber() {
        return this.ticketNumber;
    }
}
