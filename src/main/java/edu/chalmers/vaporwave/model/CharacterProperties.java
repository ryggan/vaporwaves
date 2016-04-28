package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.MovableState;
import edu.chalmers.vaporwave.view.CharacterSpriteProperties;

import java.util.Map;

/**
 * Created by andreascarlsson on 2016-04-20.
 */
public class CharacterProperties {
    private String name;
    private Map<MovableState, CharacterSpriteProperties> spriteProperties;

    public CharacterProperties(String name, Map<MovableState, CharacterSpriteProperties> spriteProperties) {
        this.name = name;
        this.spriteProperties = spriteProperties;
    }

    public String getName() {
        return this.name;
    }

    public Map getSpriteProperties() {
        return this.spriteProperties;
    }

    public CharacterSpriteProperties getSpriteProperties(MovableState characterState) {
        return this.spriteProperties.get(characterState);
    }

}
