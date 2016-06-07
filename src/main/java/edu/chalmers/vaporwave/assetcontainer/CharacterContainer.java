package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.CharacterStat;
import edu.chalmers.vaporwave.util.MovableState;
import edu.chalmers.vaporwave.util.Pair;
import edu.chalmers.vaporwave.util.XMLReader;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A container class for all character properties, which are read from XML-file and structured
 * in CharacterProperties, CharacterSprite and CharacterSpriteProperties classes.
 * There so much to initialize that all this was put in the loader, together with all the other files.
 */
class CharacterContainer {

    private static final MovableState[] CHARACTER_CHARACTER_STATE = { MovableState.WALK, MovableState.IDLE, MovableState.FLINCH, MovableState.DEATH, MovableState.SPAWN };

    private static Map<CharacterID, CharacterSprite> spriteContainer;
    private static Map<CharacterID, CharacterProperties> propertiesContainer;

    private static int tasksDone = 0;
//    private static final int totalTasks = 14 * 5;
    private static int totalTasks = 0;
    private static Set<CharacterID> characterSet = new HashSet<>();

    static void prepare() throws Exception {
        spriteContainer = new HashMap<>();
        propertiesContainer = new HashMap<>();

        prepareCharacterInit(CharacterID.ALYSSA);
        prepareCharacterInit(CharacterID.CHARLOTTE);
        prepareCharacterInit(CharacterID.ZYPHER);
        prepareCharacterInit(CharacterID.MEI);

        prepareCharacterInit(CharacterID.PCCHAN);
    }

    static void init() throws Exception {
        initCharacters();
    }

    private static void prepareCharacterInit(CharacterID characterID) {
        characterSet.add(characterID);
        totalTasks += 14;
    }

    private static void initCharacters() throws Exception {
        for (CharacterID characterID : characterSet) {
            initCharacterSprites(characterID);
        }
    }

    static CharacterSprite getCharacterSprite(CharacterID characterID) {
        return spriteContainer.get(characterID);
    }

    private static void initCharacterSprites(CharacterID characterID) throws Exception {
        CharacterSprite characterSprite = new CharacterSprite(characterID.toString());
        spriteContainer.put(characterID, characterSprite);

        // Getting the saved data from xml-file
        XMLReader reader = new XMLReader(Container.getFile(FileID.XML_CHARACTER_ENEMY));
        CharacterProperties characterProperties = CharacterLoader.loadCharacter(reader.read(), characterSprite.getName());
        propertiesContainer.put(characterID, characterProperties);

        // Creating sprites for every different character state
        for (MovableState characterState : CHARACTER_CHARACTER_STATE) {
            CharacterSpriteProperties characterSpriteProperties = characterProperties.getSpriteProperties(characterState);

            // Spawn and death animations are only made facing one direction (towards the player)
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

                // Walk, Idle and Flinch animations are all drawn in four different directions
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

    static double getCharacterStat(CharacterID characterID, CharacterStat characterStat) {
        return propertiesContainer.get(characterID).getCharacterStat(characterStat);
    }

    static double getTasksDone() {
        return tasksDone;
    }

    static double getTotalTasks() {
        return totalTasks;
    }
}
