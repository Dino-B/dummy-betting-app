package com.test.mongodb.model;

import lombok.ToString;

/**
 * Possible results for a sport event.
 */
@ToString
public enum Result {
    DRAW(0),
    HOME_WIN(1),
    AWAY_WIN(2),
    NOT_FINISHED(3);

    /**
     * Integer representation of the result.
     */
    private int resultId;

    /**
     * Constructor.
     *
     * @param resultId integer representation of the result
     */
    Result(final int resultId) {
        this.resultId = resultId;
    }
}
