package com.test.mongodb.provision.provisioner;

import com.test.mongodb.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles ticket provisioning.
 */
@Service
public class TicketProvisioner implements IProvisioner {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketProvisioner.class);

    @Autowired
    MongoTemplate mongoClient;

    private List<Ticket> generate() {
        // We don't really want to generate any tickets here
        // Instead, the idea is to clear the tickets collection
        return new ArrayList<>();
    }

    private void provision(final List<Ticket> tickets) {
        LOGGER.info("Deleting previously provisioned tickets");
        this.mongoClient.dropCollection(Ticket.class);

        LOGGER.info("Provisioning provided tickets: {}", tickets);
        this.mongoClient.createCollection(Ticket.class);
    }

    @Override
    public void generateAndProvision() {
        this.provision(this.generate());
    }
}
