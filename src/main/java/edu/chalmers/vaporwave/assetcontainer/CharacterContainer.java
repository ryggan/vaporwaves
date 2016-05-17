package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.CharacterStat;
import edu.chalmers.vaporwave.util.MovableState;
import edu.chalmers.vaporwave.util.XMLReader;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class CharacterContainer {

    private static final MovableState[] CHARACTER_CHARACTER_STATE = { MovableState.WALK, MovableState.IDLE, MovableState.FLINCH, MovableState.DEATH, MovableState.SPAWN };

    private Map<CharacterID, CharacterSprite> spriteContainer;
    private Map<CharacterID, CharacterProperties> propertiesContainer;

    private static double tasksDone;
    private static final double totalTasks = 14 * 5;

    CharacterContainer() {

        spriteContainer = new HashMap<>();
        propertiesContainer = new HashMap<>();

        // TODO: OBS!!! IF ADDING CHARACTER SPRITES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        initCharacterSprites(CharacterID.ALYSSA);
        initCharacterSprites(CharacterID.CHARLOTTE);
        initCharacterSprites(CharacterID.ZYPHER);
        initCharacterSprites(CharacterID.MEI);

        initCharacterSprites(CharacterID.PCCHAN);
    }

    CharacterSprite getCharacterSprite(CharacterID characterID) {
        return this.spriteContainer.get(characterID);
    }

    private void initCharacterSprites(CharacterID characterID) {
        CharacterSprite characterSprite = new CharacterSprite(characterID.toString());
        this.spriteContainer.put(characterID, characterSprite);

        XMLReader reader = new XMLReader(Container.getFile(FileID.XML_CHARACTER_ENEMY));
        CharacterProperties characterProperties = CharacterLoader.loadCharacter(reader.read(), characterSprite.getName());
        this.propertiesContainer.put(characterID, characterProperties);

        for (MovableState characterState : CHARACTER_CHARACTER_STATE) {
            CharacterSpriteProperties characterSpriteProperties = characterProperties.getSpriteProperties(characterState);

            switch (characterState) {
                case SPAWN:
                    characterSprite.setSpawnSprite(
                            new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                                    new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                                    characterSpriteProperties.getFrames(),
                                    characterSpriteProperties.getDuration(),
                                    characterSpriteProperties.getFirstFrame(),
                                    characterSpriteProperties.getOffset())
                    );
                    tasksDone++;
                    break;
                case DEATH:
                    characterSprite.setDeathSprite(
                            new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                                    new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                                    characterSpriteProperties.getFrames(),
                                    characterSpriteProperties.getDuration(),
                                    characterSpriteProperties.getFirstFrame(),
                                    characterSpriteProperties.getOffset())
                    );
                    tasksDone++;
                    break;
                case WALK:
                case IDLE:
                case FLINCH:
                    int startIndexX = characterSpriteProperties.getFirstFrame()[0];
                    int startIndexY = characterSpriteProperties.getFirstFrame()[1];
                    int spritesheetWidth = (int)Math.floor(characterSpriteProperties.getSpritesheet().getWidth() / characterSpriteProperties.getDimensionX());

                    for (int i = 0; i < 4; i++) {

                        if (startIndexX >= spritesheetWidth) {
                            startIndexX -= spritesheetWidth;
                            startIndexY++;
                        }

                        switch (characterState) {
                            case WALK:
                                characterSprite.setWalkSprite(
                                        new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                                                new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                                                characterSpriteProperties.getFrames(),
                                                characterSpriteProperties.getDuration(),
                                                new int[]{startIndexX, startIndexY},
                                                characterSpriteProperties.getOffset()),
                                        i);
                                break;
                            case IDLE:
                                characterSprite.setIdleSprite(
                                        new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                                                new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                                                characterSpriteProperties.getFrames(),
                                                characterSpriteProperties.getDuration(),
                                                new int[]{startIndexX, startIndexY},
                                                characterSpriteProperties.getOffset()),
                                        i);
                                break;
                            case FLINCH:
                                characterSprite.setFlinchSprite(
                                        new AnimatedSprite(characterSpriteProperties.getSpritesheet(),
                                                new Dimension(characterSpriteProperties.getDimensionX(), characterSpriteProperties.getDimensionY()),
                                                characterSpriteProperties.getFrames(),
                                                characterSpriteProperties.getDuration(),
                                                new int[]{startIndexX, startIndexY},
                                                characterSpriteProperties.getOffset()),
                                        i);
                                break;
                            default:
                        }

                        tasksDone++;
                        startIndexX += characterSpriteProperties.getFrames();
                    }
                    break;
                default:
            }
        }

    }

    double getCharacterStat(CharacterID characterID, CharacterStat characterStat) {
        return this.propertiesContainer.get(characterID).getCharacterStat(characterStat);
    }

    static double getTasksDone() {
        return tasksDone;
    }

    static double getTotalTasks() {
        return totalTasks;
    }
}
