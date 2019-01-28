package com.github.vkolencik.foosball.player;

public enum PlayerOrder {
    WINS("wins"),
    LOSSES("losses");

    private String propertyName;

    public String getPropertyName() {
        return propertyName;
    }

    PlayerOrder(String propertyName) {
        this.propertyName = propertyName;
    }
}
