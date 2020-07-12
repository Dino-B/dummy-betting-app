package com.test.mongodb.provision.provisioner;

import com.test.mongodb.model.User;
import com.test.mongodb.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles user provisioning.
 */
@Service
public class UserProvisioner implements IProvisioner {
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProvisioner.class);

    @Autowired
    IUserRepository userRepository;

    /**
     * Dummy user initial account balance.
     */
    private static final float DUMMY_USER_BALANCE = 1000.00f;

    /**
     * Dummy user name.
     */
    private static final String DUMMY_USER_NAME = "DummyUser";

    private List<User> generate() {
        LOGGER.info("Generating a dummy user");
        User dummyUser = new User(DUMMY_USER_NAME, DUMMY_USER_BALANCE);
        return List.of(dummyUser);
    }

    /**
     * Provision provided users.
     *
     * @param users to be provisioned
     */
    private void provision(final List<User> users) {
        LOGGER.info("Deleting previously provisioned users");
        this.userRepository.deleteAll();

        LOGGER.info("Provisioning generated users: {}", users);
        this.userRepository.insert(users);
    }

    @Override
    public void generateAndProvision() {
        this.provision(this.generate());
    }
}
