package edu.chalmers.vaporwave.assetcontainer;

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
    private static CharacterSpriteContainer characterSpriteContainer;
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
        characterSpriteContainer = new CharacterSpriteContainer();
        System.out.println("Character sprites done, timed: "+(System.currentTimeMillis() - time)+" millis");
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

    public static CharacterSprite getCharacterSprite(CharacterSpriteID characterSpriteID) {
        return characterSpriteContainer.getCharacterSprite(characterSpriteID);
    }

    public static Sprite getSprite(SpriteID spriteID) {
        return spriteContainer.getSprite(spriteID);
    }

    public static double getTasksDone() {
        return FileContainer.getTasksDone() + ImageContainer.getTasksDone() + SoundContainer.getTasksDone()
                + CharacterSpriteContainer.getTasksDone() + SpriteContainer.getTasksDone();
    }

    public static double getTotalTasks() {
        return FileContainer.getTotalTasks() + ImageContainer.getTotalTasks() + SoundContainer.getTotalTasks()
                + CharacterSpriteContainer.getTotalTasks() + SpriteContainer.getTotalTasks();
    }
}
