package com.nutrition.model;

import static com.nutrition.model.Food.FatRating.*;

public final class Food {

    private final String name;
    private final Integer calories;
    private final Double totalFat;
    private final FatRating fatRating;
    private final String caffeine;

    public enum FatRating {
        LOW,
        MEDIUM,
        HIGH;
    }

    public Food(
        String name,
        Integer calories,
        Double totalFat,
        String caffeine
    ) {
        this.name = name;
        this.calories = calories;
        this.totalFat = totalFat;
        this.caffeine = caffeine;

        if (totalFat >= 17.5) {
            fatRating = HIGH;
        } else if (totalFat <= 3) {
            fatRating = LOW;
        } else {
            fatRating = MEDIUM;
        }
    }

    public String getName() {
        return name;
    }

    public Integer getCalories() {
        return calories;
    }

    public String getCaffeine() {
        return caffeine;
    }

    public Double getTotalFat() {
        return totalFat;
    }

    public FatRating getFatRating() {
        return fatRating;
    }
}
