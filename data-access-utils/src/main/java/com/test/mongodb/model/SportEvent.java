package com.test.mongodb.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Represents a sport event.
 */
@Getter
@ToString
@Document(collection = SportEvent.SPORT_EVENTS_COLLECTION)
public class SportEvent {
    /**
     * Name of the collection to be used for storing StoreEvent objects.
     */
    public static final String SPORT_EVENTS_COLLECTION = "sportEvents";

    /**
     * Start date/time of the sport event.
     */
    private LocalDateTime startDateTime;

    /**
     * Home team.
     */
    private String homeTeam;

    /**
     * Away team.
     */
    private String awayTeam;

    /**
     * Type of sport this event belongs to.
     */
    private Sport sport;

    /**
     * End result.
     */
    private Result result;

    /**
     * Odds associated to possible outcomes of the event.
     */
    private Odds odds;

    /**
     * Id. Self-generated.
     */
    @Id
    private long id;

    public SportEvent(final long id, final LocalDateTime startDateTime,
                      final String homeTeam, final String awayTeam,
                      final Sport sport, final Odds odds) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.sport = sport;
        this.odds = odds;
        this.result = Result.NOT_FINISHED;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Sport getSport() {
        return this.sport;
    }
}
