package edu.chalmers.vaporwave.model;

/**
 * Created by andreascarlsson on 2016-04-20.
 */
public class CharacterProperties {
    private String name;
    private SpriteProperties[] spriteProperties;

    public CharacterProperties(String name, SpriteProperties[] spriteProperties) {
        this.name = name;
        this.spriteProperties[] = spriteProperties;
    }

    public String getName() {
        return this.name;
    }

}
