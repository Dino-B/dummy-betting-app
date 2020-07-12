package com.test.mongodb.config;

import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Represents a configuration file for configured bonuses.
 */
@ToString
@Configuration
@ConfigurationProperties(prefix = "app")
public class ConfiguredBonuses {
    public static final float NO_BONUS = 0.00f;

    private List<ConfiguredBonus> configuredBonuses;

    public ConfiguredBonuses(final List<ConfiguredBonus> configuredBonuses) {
        this.configuredBonuses = configuredBonuses;
    }

    public void setConfiguredBonuses(final List<ConfiguredBonus> configuredBonuses) {
        this.configuredBonuses = configuredBonuses;
    }

    public final List<ConfiguredBonus> getConfiguredBonuses() {
        return this.configuredBonuses;
    }
}
