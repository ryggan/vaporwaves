package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.controller.MainController;
import edu.chalmers.vaporwave.util.CharacterStat;
import edu.chalmers.vaporwave.util.SoundPlayer;
import javafx.scene.image.Image;
import javafx.scene.text.Font;


import java.awt.*;
import java.io.File;

public class Container {

    public static void initialize() {
        try {
            double time = System.currentTimeMillis();
            ImageContainer.initImageContainer();
            System.out.println("Image loading done, timed: " + (System.currentTimeMillis() - time) + " millis");
            time = System.currentTimeMillis();
            FileContainer.initFileContainer();
            System.out.println("File loading done, timed: " + (System.currentTimeMillis() - time) + " millis");
            time = System.currentTimeMillis();
            SoundContainer.initSoundContainer();
            System.out.println("Sound loading done, timed: " + (System.currentTimeMillis() - time) + " millis");
            time = System.currentTimeMillis();
            CharacterContainer.initCharacterContainer();
            System.out.println("Characters done, timed: " + (System.currentTimeMillis() - time) + " millis");
            time = System.currentTimeMillis();
            SpriteContainer.initSpriteContainer();
            System.out.println("Sprites done, timed: " + (System.currentTimeMillis() - time) + " millis");
            time = System.currentTimeMillis();
            MenuButtonContainer.initMenuButtonContainer();
            System.out.println("Menu buttons done, timed: " + (System.currentTimeMillis() - time) + " millis");

        } catch (Exception e) {
            e.printStackTrace();
            MainController.showError();
        }
    }

    public static Image getImage(ImageID imageID) {
        return ImageContainer.getImage(imageID);
    }

    public static File getFile(FileID fileID) {
        return FileContainer.getFile(fileID);
    }

    public static Font getFont(FileID fileID) {
        return FileContainer.getFont(fileID);
    }

    public static javafx.scene.paint.Color getColor(FileID fileID) {
        return FileContainer.getColor(fileID);
    }

    public static SoundPlayer getSound(SoundID soundID) {
        return SoundContainer.getSound(soundID);
    }

    public static void playSound(SoundID soundID) {
        SoundContainer.playSound(soundID);
    }

    public static void stopSound(SoundID soundID) {
        SoundContainer.stopSound(soundID);
    }

    public static CharacterSprite getCharacterSprite(CharacterID characterID) {
        return CharacterContainer.getCharacterSprite(characterID);
    }

    public static double getCharacterHealth(CharacterID characterID) {
        return CharacterContainer.getCharacterStat(characterID, CharacterStat.HEALTH);
    }
    public static double getCharacterSpeed(CharacterID characterID) {
        return CharacterContainer.getCharacterStat(characterID, CharacterStat.SPEED);
    }
    public static int getCharacterBombRange(CharacterID characterID) {
        return (int)CharacterContainer.getCharacterStat(characterID, CharacterStat.BOMB_RANGE);
    }
    public static int getCharacterBombCount(CharacterID characterID) {
        return (int)CharacterContainer.getCharacterStat(characterID, CharacterStat.BOMB_COUNT);
    }
    public static double getCharacterDamage(CharacterID characterID) {
        return CharacterContainer.getCharacterStat(characterID, CharacterStat.DAMAGE);
    }

    public static Sprite getSprite(SpriteID spriteID) {
        return SpriteContainer.getSprite(spriteID);
    }

    public static MenuButtonSprite getButton(MenuButtonID menuButtonID) {
        return MenuButtonContainer.getButton(menuButtonID);
    }

    public static MenuButtonSprite getButton(MenuButtonID menuButtonID, Point positionOnCanvas) {
        return MenuButtonContainer.getButton(menuButtonID, positionOnCanvas);
    }

    public static double getTasksDone() {
        return FileContainer.getTasksDone() + ImageContainer.getTasksDone() + SoundContainer.getTasksDone()
                + CharacterContainer.getTasksDone() + SpriteContainer.getTasksDone() + MenuButtonContainer.getTasksDone();
    }

    public static double getTotalTasks() {
        return FileContainer.getTotalTasks() + ImageContainer.getTotalTasks() + SoundContainer.getTotalTasks()
                + CharacterContainer.getTotalTasks() + SpriteContainer.getTotalTasks() + MenuButtonContainer.getTotalTasks();
    }
}
