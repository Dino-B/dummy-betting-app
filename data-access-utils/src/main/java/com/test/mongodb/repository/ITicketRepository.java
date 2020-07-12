package com.test.mongodb.repository;

import com.test.mongodb.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketRepository extends MongoRepository<Ticket, String> {
}
