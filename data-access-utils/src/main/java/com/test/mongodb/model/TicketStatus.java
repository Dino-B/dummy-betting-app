package com.test.mongodb.model;

import lombok.Getter;
import lombok.ToString;

/**
 * Statuses a ticket can be in.
 */
@ToString
@Getter
public enum TicketStatus {
    NOT_FINISHED("Not finished"),
    WON("Won"),
    LOST("Lost");

    private String description;

    TicketStatus(String description) {
        this.description = description;
    }
}
