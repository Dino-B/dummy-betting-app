package com.test.mongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the application.
 */
@SpringBootApplication
public class DataAccessClientMain {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessClientMain.class);

    public static void main(String[] args) {
        LOGGER.info("Starting data access application");
        SpringApplication app = new SpringApplication(DataAccessClientMain.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}