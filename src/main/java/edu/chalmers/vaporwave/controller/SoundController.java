package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.util.Sound;
import edu.chalmers.vaporwave.util.SoundPlayer;

/**
 * Created by bob on 2016-05-08.
 */
public class SoundController {

    private static SoundController instance;

    private SoundPlayer[] placeBomb;
    private SoundPlayer[] explosion;
    private SoundPlayer gameBackgroundMusic;

    private SoundController() {

        int amountOfSounds= 10;
        this.placeBomb = new SoundPlayer[amountOfSounds];
        for (int i = 0; i < amountOfSounds; i++) {
            this.placeBomb[i] = new SoundPlayer("placebomb.wav");
        }

        amountOfSounds = 30;
        this.explosion = new SoundPlayer[amountOfSounds];
        for (int i = 0; i < amountOfSounds; i++) {
            this.explosion[i] = new SoundPlayer("explosion.wav");
        }

        this.gameBackgroundMusic = new SoundPlayer("bg1.mp3", 0.5);
        this.gameBackgroundMusic.loopSound(true);
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

    public SoundPlayer getSound(Sound sound) {
        switch (sound) {
            case PLACE_BOMB:
                for (int i = 0; i < placeBomb.length; i++) {
                    if (!placeBomb[i].isPlaying()) {
                        return placeBomb[i];
                    }
                }
                break;
            case EXPLOSION:
                for (int i = 0; i < explosion.length; i++) {
                    if (!explosion[i].isPlaying()) {
                        return explosion[i];
                    }
                }
                break;
            case GAME_MUSIC:
                return gameBackgroundMusic;
        }
        return null;
    }

    public void playSound(Sound sound) {
        SoundPlayer player = getSound(sound);
        if (player != null) {
            player.playSound();
        }
    }

    public void stopSound(Sound sound) {
        SoundPlayer player = getSound(sound);
        if (player != null) {
            player.stopSound();
        }
    }

//    public void loopSound(Sound sound, boolean loop) {
//        SoundPlayer player = getSound(sound);
//        if (player != null) {
//            player.loopSound(loop);
//        }
//    }

}
