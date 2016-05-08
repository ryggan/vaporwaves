package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.util.SoundPlayer;

/**
 * Created by bob on 2016-05-08.
 */
public class SoundController {

    private static SoundController instance;

    private SoundPlayer placeBomb;
    private SoundPlayer explosion;
    private SoundPlayer gameBackgroundMusic;

    private SoundController() {
        this.placeBomb = new SoundPlayer("placebomb.wav");
        this.explosion = new SoundPlayer("explosion.wav");
        this.gameBackgroundMusic = new SoundPlayer("bg1.mp3", 0.5);
    }

    public static SoundController getInstance() {
        initialize();
        return instance;
    }

    public static void initialize() {
        if (instance == null) {
            instance = new SoundController();
        }
    }

    public SoundPlayer getSound(String soundName) {
        switch (soundName) {
            case "placeBomb":
                return placeBomb;
            case "explosion":
                return explosion;
            case "gameBackgroundMusic":
                return gameBackgroundMusic;
        }
        return null;
    }

}
