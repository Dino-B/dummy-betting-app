package com.test.mongodb.bonus;

import lombok.ToString;

@ToString
public class RealizedBonus {
    private float value;
    private String description;

    public RealizedBonus(float value, String description) {
        this.value = value;
        this.description = description;
    }

    public RealizedBonus() {
        //
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static RealizedBonus add(final RealizedBonus realizedBonus1, final RealizedBonus realizedBonus2) {
        return new RealizedBonus(
                realizedBonus1.getValue() + realizedBonus2.getValue(),
                realizedBonus1.getDescription() + "\n\n" + realizedBonus2.getDescription()
                );
    }
}
