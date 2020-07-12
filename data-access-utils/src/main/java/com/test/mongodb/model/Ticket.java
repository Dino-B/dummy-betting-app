package com.test.mongodb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Represents a betting ticket.
 */
@RequiredArgsConstructor
@Getter
@ToString
@Document(collection = Ticket.TICKETS_COLLECTION)
public class Ticket {
    /**
     * Collection name.
     */
    public static final String TICKETS_COLLECTION = "tickets";

    @Id
    private String id;

    /**
     * User who owns the ticket.
     */
    private String user;

    /**
     * Map of sport event ids and chosen outcomes.
     */
    private Map<Long, Result> bets;

    /**
     * Winning if all chosen outcomes turn out to be correct.
     */
    private double potentialWinnings;

    /**
     * Input stake.
     */
    private double stake;

    /**
     * Current status of the ticket.
     */
    private TicketStatus ticketStatus;

    public Ticket(final String user,
                  final Map<Long, Result> bets,
                  final double stake,
                  final double potentialWinnings) {
        this.user = user;
        this.bets = bets;
        this.stake = stake;
        this.potentialWinnings = potentialWinnings;
        this.ticketStatus = TicketStatus.NOT_FINISHED;
    }

    public final Map<Long, Result> getBets() {
        return this.bets;
    }

    public final String getUser() {
        return this.user;
    }

    public final double getStake() {
        return this.stake;
    }

    public final void setTicketStatus(final TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
