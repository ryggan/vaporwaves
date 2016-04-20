package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.State;

import java.util.Map;

/**
 * Created by andreascarlsson on 2016-04-20.
 */
public class CharacterProperties {
    private String name;
    private Map<State, SpriteProperties> spriteProperties;

    public CharacterProperties(String name, Map<State, SpriteProperties> spriteProperties) {
        this.name = name;
        this.spriteProperties = spriteProperties;
    }

    public String getName() {
        return this.name;
    }

    public Map getSpriteProperties() {
        return this.spriteProperties;
    }

    public SpriteProperties getSpriteProperties(State state) {
        return this.spriteProperties.get(state);
    }

}
