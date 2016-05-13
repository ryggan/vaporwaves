package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MovableState;
import edu.chalmers.vaporwave.util.XMLReader;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bob on 2016-05-11.
 */
public class CharacterSpriteContainer {

    private static CharacterSpriteContainer instance;

    private Map<CharacterSpriteID, CharacterSprite> spriteContainer;

    private static double tasksDone = 0;
    private static double totalTasks = 14 * 5;

    private CharacterSpriteContainer() {

        spriteContainer = new HashMap<>();

        // TODO: OBS!!! IF ADDING CHARACTER SPRITES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        initCharacterSprites(CharacterSpriteID.ALYSSA);
        initCharacterSprites(CharacterSpriteID.CHARLOTTE);
        initCharacterSprites(CharacterSpriteID.ZYPHER);
        initCharacterSprites(CharacterSpriteID.MEI);

        initCharacterSprites(CharacterSpriteID.PCCHAN);
    }

    public CharacterSprite getCharacterSprite(CharacterSpriteID characterSpriteID) {
        return this.spriteContainer.get(characterSpriteID);
    }

    private void initCharacterSprites(CharacterSpriteID characterSpriteID) {
        CharacterSprite characterSprite = new CharacterSprite(characterSpriteID.toString());
        this.spriteContainer.put(characterSpriteID, characterSprite);

        XMLReader reader = new XMLReader(FileContainer.getInstance().getFile(FileID.XML_CHARACTER_ENEMY));
        CharacterProperties characterProperties = CharacterLoader.loadCharacter(reader.read(), characterSprite.getName());

        for (MovableState characterState : Constants.CHARACTER_CHARACTER_STATE) {
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
                        }

                        tasksDone++;
                        startIndexX += characterSpriteProperties.getFrames();
                    }
                    break;
            }
        }

    }

    public static CharacterSpriteContainer getInstance() {
        initialize();
        return instance;
    }

    public static void initialize() {
        if (instance == null) {
            instance = new CharacterSpriteContainer();
        }
    }

    public static double getTasksDone() {
        return tasksDone;
    }

    public static double getTotalTasks() {
        return totalTasks;
    }
}
