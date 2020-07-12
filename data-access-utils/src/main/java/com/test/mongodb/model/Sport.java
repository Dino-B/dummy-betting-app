package com.test.mongodb.model;

import lombok.ToString;

/**
 * Included sports.
 */
@ToString
public enum Sport {
    FOOTBALL(0),
    BASKETBALL(1),
    RUGBY(2);

    /**
     * Integer representation of the sport.
     */
    private int sportId;

    /**
     * Constructor.
     *
     * @param sportId integer representation of the sport
     */
    Sport(final int sportId) {
        this.sportId = sportId;
    }
}
