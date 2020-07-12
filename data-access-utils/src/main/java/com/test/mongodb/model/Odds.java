package com.test.mongodb.model;

import lombok.Getter;
import lombok.ToString;

/**
 * Odds given to possible outcomes of a sport event.
 */
@ToString
@Getter
public class Odds {
    private float homeWin;
    private float awayWin;
    private float draw;

    public Odds(final float homeWin, final float awayWin, final float draw) {
        this.homeWin = homeWin;
        this.awayWin = awayWin;
        this.draw = draw;
    }
}
