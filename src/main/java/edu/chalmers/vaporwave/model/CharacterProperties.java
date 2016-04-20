package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.CharacterState;

import java.util.Map;

/**
 * Created by andreascarlsson on 2016-04-20.
 */
public class CharacterProperties {
    private String name;
    private Map<CharacterState, SpriteProperties> spriteProperties;

    public CharacterProperties(String name, Map<CharacterState, SpriteProperties> spriteProperties) {
        this.name = name;
        this.spriteProperties = spriteProperties;
    }

    public String getName() {
        return this.name;
    }

    public Map getSpriteProperties() {
        return this.spriteProperties;
    }

    public SpriteProperties getSpriteProperties(CharacterState state) {
        return this.spriteProperties.get(state);
    }

}
