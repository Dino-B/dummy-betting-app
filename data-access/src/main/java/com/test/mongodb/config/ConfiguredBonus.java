package com.test.mongodb.config;

import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * Represent a configured bonus.
 */
@ToString
@Component
public class ConfiguredBonus {
  private String bonusType;
  private float value;
  private int required;

  public ConfiguredBonus(String bonusType, float value, int required) {
    this.bonusType = bonusType;
    this.value = value;
    this.required = required;
  }

  ConfiguredBonus() {
  }

  public String getBonusType() {
    return this.bonusType;
  }

  public void setBonusType(final String bonusType) {
    this.bonusType = bonusType;
  }

  public float getValue() {
    return value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  public int getRequired() {
    return required;
  }

  public void setRequired(int required) {
    this.required = required;
  }
}
