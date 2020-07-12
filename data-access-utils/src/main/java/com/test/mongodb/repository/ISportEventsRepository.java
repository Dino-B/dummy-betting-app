package com.test.mongodb.repository;

import com.test.mongodb.model.SportEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISportEventsRepository extends MongoRepository<SportEvent, Long> {
}
