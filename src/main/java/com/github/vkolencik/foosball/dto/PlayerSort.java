package com.github.vkolencik.foosball.dto;

public enum PlayerSort {
    WINS("wins"),
    LOSSES("losses");

    private String propertyName;

    public String getPropertyName() {
        return propertyName;
    }

    PlayerSort(String propertyName) {
        this.propertyName = propertyName;
    }
}
