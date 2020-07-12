package com.test.mongodb.provision;

import com.test.mongodb.provision.provisioner.IProvisioner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

/**
 * Entry point for the application.
 */
@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.test.mongodb.repository"})
public class ProvisioningApplication implements CommandLineRunner {
	/**
	 * Logger for the class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ProvisioningApplication.class);

	@Autowired
	List<IProvisioner> provisioners;

	public static void main(String[] args) {
		LOGGER.info("Starting the provisioning application");
		SpringApplication app = new SpringApplication(ProvisioningApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	/*
	 * (non-Javadoc)
	 *
	 * Main method..
	 */
	@Override
	public void run(String... args) {
		// Generate and provision the necessary information
		try {
			this.provisioners.forEach((IProvisioner::generateAndProvision));
		} catch (DataAccessException exception) {
			LOGGER.error("Error occurred while trying to provision necessary data! Exiting..", exception);
			System.exit(1);
		}
		LOGGER.info("Execution successful! Exiting..");
	}
}
