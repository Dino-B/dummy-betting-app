package com.test.mongodb.service;

import com.test.mongodb.exception.CustomAppException;
import com.test.mongodb.model.Ticket;
import com.test.mongodb.model.TicketStatus;
import com.test.mongodb.repository.ITicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains logic for ticket management.
 */
@Service
public class TicketService {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    ITicketRepository ticketRepository;

    @Autowired
    UserService userService;

    @Autowired
    SportEventService sportEventService;

    /**
     * @return a list of all created tickets
     */
    public List<Ticket> getAllTickets() {
        LOGGER.info("Looking for all tickets");
        List<Ticket> tickets = this.ticketRepository.findAll();
        LOGGER.info("Returning the following tickets: {}", tickets);
        return tickets;
    }

    /**
     * Validates the provided ticket and, if it's OK, writes it to database.
     *
     * @param ticket to be validated and, possibly, created
     * @return created ticket information if successful
     * @throws CustomAppException in case: - the provided ticket doesn't actually contain any sport events
     *                                     - the provided sport events don't exist
     *                                     - user name is not found or the user doesn't have enough funds to go ahead
     *                                       with the bet
     */
    @Transactional
    public Ticket addNewTicket(final Ticket ticket) throws CustomAppException {
        LOGGER.info("Ticket to be added: {}", ticket);

        List<Long> sportEventIds = new ArrayList<>(ticket.getBets().keySet());
        if (sportEventIds.isEmpty()) {
            throw new CustomAppException("Ticket cannot be created without selecting any sport events.");
        }
        // Check that all selected sport events actually exist
        this.sportEventService.getSportEventsFromIds(sportEventIds);

        // Check user's account balance and update it with the new status if validation is OK
        this.userService.validateAndUpdateUserBalance(ticket.getUser(), ticket.getStake());

        LOGGER.info("User and sport event data were verified for ticket: {}. Inserting it", ticket);
        ticket.setTicketStatus(TicketStatus.NOT_FINISHED);
        return this.ticketRepository.insert(ticket);
    }
}
