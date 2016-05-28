package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.CharacterStat;
import edu.chalmers.vaporwave.util.MovableState;

import java.util.Map;

/**
 * A simple container for all character specific information, to be used in CharacterContainer.
 */
class CharacterProperties {
    private String name;

    private Map<CharacterStat, Double> characterStats;
    private Map<MovableState, CharacterSpriteProperties> spriteProperties;

    public CharacterProperties(String name, Map<MovableState, CharacterSpriteProperties> spriteProperties,
                               Map<CharacterStat, Double> characterStats) {
        this.name = name;
        this.characterStats = characterStats;
        this.spriteProperties = spriteProperties;
    }

    public String getName() {
        return this.name;
    }

    public double getCharacterStat(CharacterStat characterStat) {
        return this.characterStats.get(characterStat);
    }

    public CharacterSpriteProperties getSpriteProperties(MovableState characterState) {
        return this.spriteProperties.get(characterState);
    }

}
