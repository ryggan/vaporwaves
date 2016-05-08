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
    private SoundPlayer[] powerUp;
    private SoundPlayer[] characterFlinch;
    private SoundPlayer gameBackgroundMusic;

    private double soundVolume;
    private double musicVolume;

    private SoundController() {

        this.soundVolume = 1.0;
//        this.musicVolume = 1.0;

//        this.soundVolume = 0;
        this.musicVolume = 0;

        this.placeBomb = new SoundPlayer[10];
        setUpSoundArray(this.placeBomb, 10, "placebomb.wav");

        this.explosion = new SoundPlayer[30];
        setUpSoundArray(this.explosion, 30, "explosion.wav");

        this.powerUp = new SoundPlayer[10];
        setUpSoundArray(this.powerUp, 10, "powerup1.wav", 0.8);

        this.characterFlinch = new SoundPlayer[4];
        setUpSoundArray(this.characterFlinch, 4, "girl_moan4.wav");

        this.gameBackgroundMusic = new SoundPlayer("bg1.mp3", 0.5);
        this.gameBackgroundMusic.loopSound(true);
    }

    private void setUpSoundArray(SoundPlayer[] array, int numberOfSounds, String fileName, double volume) {
        array[0] = new SoundPlayer(fileName, volume);
        for (int i = 1; i < numberOfSounds; i++) {
            array[i] = new SoundPlayer(array[0]);
        }
    }

    private void setUpSoundArray(SoundPlayer[] array, int numberOfSounds, String fileName) {
        setUpSoundArray(array, numberOfSounds, fileName, 1.0);
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
            case POWERUP:
                for (int i = 0; i < powerUp.length; i++) {
                    if (!powerUp[i].isPlaying()) {
                        return powerUp[i];
                    }
                }
                break;
            case CHARACTER_FLINCH:
                for (int i = 0; i < characterFlinch.length; i++) {
                    if (!characterFlinch[i].isPlaying()) {
                        return characterFlinch[i];
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
            double masterVolume = soundVolume;
            if (sound == Sound.GAME_MUSIC) {
                masterVolume = musicVolume;
            }
            player.playSound(masterVolume);
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
