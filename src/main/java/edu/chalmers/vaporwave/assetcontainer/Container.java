package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.CharacterStat;
import edu.chalmers.vaporwave.util.SoundPlayer;
import javafx.scene.image.Image;

import java.io.File;

/**
 * Created by bob on 2016-05-13.
 */
public class Container {

    private static FileContainer fileContainer;
    private static ImageContainer imageContainer;
    private static SoundContainer soundContainer;
    private static CharacterContainer characterContainer;
    private static SpriteContainer spriteContainer;

    public static void initialize() {
        double time = System.currentTimeMillis();
        imageContainer = new ImageContainer();
        System.out.println("Image loading done, timed: "+(System.currentTimeMillis() - time)+" millis");
        time = System.currentTimeMillis();
        fileContainer = new FileContainer();
        System.out.println("File loading done, timed: "+(System.currentTimeMillis() - time)+" millis");
        time = System.currentTimeMillis();
        soundContainer = new SoundContainer();
        System.out.println("Sound loading done, timed: "+(System.currentTimeMillis() - time)+" millis");
        time = System.currentTimeMillis();
        characterContainer = new CharacterContainer();
        System.out.println("Characters done, timed: "+(System.currentTimeMillis() - time)+" millis");
        time = System.currentTimeMillis();
        spriteContainer = new SpriteContainer();
        System.out.println("Sprites done, timed: "+(System.currentTimeMillis() - time)+" millis");
    }

    public static Image getImage(ImageID imageID) {
        return imageContainer.getImage(imageID);
    }

    public static File getFile(FileID fileID) {
        return fileContainer.getFile(fileID);
    }

    public static SoundPlayer getSound(SoundID soundID) {
        return soundContainer.getSound(soundID);
    }

    public static void playSound(SoundID soundID) {
        soundContainer.playSound(soundID);
    }

    public static void stopSound(SoundID soundID) {
        soundContainer.stopSound(soundID);
    }

    public static CharacterSprite getCharacterSprite(CharacterID characterID) {
        return characterContainer.getCharacterSprite(characterID);
    }

    public static double getCharacterHealth(CharacterID characterID) {
        return characterContainer.getCharacterStat(characterID, CharacterStat.HEALTH);
    }
    public static double getCharacterSpeed(CharacterID characterID) {
        return characterContainer.getCharacterStat(characterID, CharacterStat.SPEED);
    }
    public static int getCharacterBombRange(CharacterID characterID) {
        return (int)characterContainer.getCharacterStat(characterID, CharacterStat.BOMB_RANGE);
    }
    public static int getCharacterBombCount(CharacterID characterID) {
        return (int)characterContainer.getCharacterStat(characterID, CharacterStat.BOMB_COUNT);
    }
    public static double getCharacterDamage(CharacterID characterID) {
        return characterContainer.getCharacterStat(characterID, CharacterStat.DAMAGE);
    }

    public static Sprite getSprite(SpriteID spriteID) {
        return spriteContainer.getSprite(spriteID);
    }

    public static double getTasksDone() {
        return FileContainer.getTasksDone() + ImageContainer.getTasksDone() + SoundContainer.getTasksDone()
                + CharacterContainer.getTasksDone() + SpriteContainer.getTasksDone();
    }

    public static double getTotalTasks() {
        return FileContainer.getTotalTasks() + ImageContainer.getTotalTasks() + SoundContainer.getTotalTasks()
                + CharacterContainer.getTotalTasks() + SpriteContainer.getTotalTasks();
    }
}
