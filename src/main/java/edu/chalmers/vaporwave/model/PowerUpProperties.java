package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.PowerUpType;

import java.util.Map;

public class PowerUpProperties {
    private String type;
    private Map<PowerUpType, PowerUpSpriteProperties> spriteProperties;

    public PowerUpProperties(String type, Map<PowerUpType, PowerUpSpriteProperties> spriteProperties) {
        this.type = type;
        this.spriteProperties = spriteProperties;
    }

    public String getType() {
        return this.type;
    }

    public Map getSpriteProperties() {
        return this.spriteProperties;
    }

    public PowerUpSpriteProperties getSpriteProperties(PowerUpType powerUpState) {
        return this.spriteProperties.get(powerUpState);
    }

}
