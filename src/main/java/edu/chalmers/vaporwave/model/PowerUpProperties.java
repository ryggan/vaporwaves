package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.CharacterState;
import edu.chalmers.vaporwave.util.PowerUpState;

import java.util.Map;

/**
 * Created by FEngelbrektsson on 22/04/16.
 */
public class PowerUpProperties {
    private String type;
    private Map<PowerUpState, PowerUpSpriteProperties> spriteProperties;

    public PowerUpProperties(String type, Map<PowerUpState, PowerUpSpriteProperties> spriteProperties) {
        this.type = type;
        this.spriteProperties = spriteProperties;
    }

    public String getType() {
        return this.type;
    }

    public Map getSpriteProperties() {
        return this.spriteProperties;
    }

    public PowerUpSpriteProperties getSpriteProperties(PowerUpState powerUpState) {
        return this.spriteProperties.get(powerUpState);
    }

}
