package com.test.mongodb.config;

import lombok.ToString;

/**
 * Possible types of bonuses.
 */
@ToString
public enum BonusType {
    UNIQUE_SPORTS("uniqueSports"),
    SAME_SPORT_EVENTS("sameSportEvents");

    private String description;

    BonusType(final String description) {
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
