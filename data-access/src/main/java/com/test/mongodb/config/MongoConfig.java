package com.test.mongodb.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    private static final int DEFAULT_PORT = 27017;

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public MongoClient mongoClient() {
        String namespace = System.getenv("NAMESPACE");
        if (namespace == null) {
            throw new IllegalArgumentException("Could not detect k8s namespace!");
        }

        String memberLogicalNameTmpl = "data-mongodb-%s.data-mongodb-headless.%s.svc.cluster.local:%s";
        StringBuilder memberLogicalNames = new StringBuilder();
        int memberNumber = 2;
        for (int i = 0; i < memberNumber; i++) {
            memberLogicalNames.append(String.format(memberLogicalNameTmpl, i, namespace, DEFAULT_PORT));
            if (i + 1 < memberNumber) {
                memberLogicalNames.append(",");
            }
        }

        return MongoClients.create(String.format(
                        "mongodb://%s/?replicaSet=rs0", memberLogicalNames));
    }
}
