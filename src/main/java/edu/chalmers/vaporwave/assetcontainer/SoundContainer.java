package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.SoundID;
import edu.chalmers.vaporwave.util.SoundPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bob on 2016-05-08.
 */
public class SoundContainer {

    private static SoundContainer instance;

    private Map<SoundID, SoundPlayer[]> soundContainer;

    private double soundVolume;
    private double musicVolume;

    private static int tasksDone;
    private static int totalTasks;

    private SoundContainer() {

        tasksDone = 0;
        totalTasks = 10 + 30 + 10 + 1;

        this.soundVolume = 1.0;
//        this.musicVolume = 1.0;

//        this.soundVolume = 0.0;
        this.musicVolume = 0.0;

        // TODO: OBS!!! IF ADDING SOUNDS; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        this.soundContainer = new HashMap<>();
        SoundPlayer[] soundPlayer;

        soundPlayer = new SoundPlayer[10];
        setUpSoundArray(soundPlayer, 10, "placebomb.wav");
        this.soundContainer.put(SoundID.PLACE_BOMB, soundPlayer);

        soundPlayer = new SoundPlayer[30];
        setUpSoundArray(soundPlayer, 30, "explosion.wav");
        this.soundContainer.put(SoundID.EXPLOSION, soundPlayer);

        soundPlayer = new SoundPlayer[10];
        setUpSoundArray(soundPlayer, 10, "powerup1.wav", 0.8);
        this.soundContainer.put(SoundID.POWERUP, soundPlayer);

//        soundPlayer = new SoundPlayer[4];
//        setUpSoundArray(soundPlayer, 4, "girl_moan4.wav");
//        this.soundContainer.put(SoundID.CHARACTER_FLINCH, soundPlayer);

        soundPlayer = new SoundPlayer[1];
        soundPlayer[0] = new SoundPlayer("bg1.mp3", 0.5);
        soundPlayer[0].loopSound(true);
        this.soundContainer.put(SoundID.GAME_MUSIC, soundPlayer);
        tasksDone++;
    }

    private void setUpSoundArray(SoundPlayer[] array, int numberOfSounds, String fileName, double volume) {
        array[0] = new SoundPlayer(fileName, volume);
        tasksDone++;
        for (int i = 1; i < numberOfSounds; i++) {
            array[i] = new SoundPlayer(array[0]);
            tasksDone++;
        }
    }

    private void setUpSoundArray(SoundPlayer[] array, int numberOfSounds, String fileName) {
        setUpSoundArray(array, numberOfSounds, fileName, 1.0);
    }

    public static SoundContainer getInstance() {
        initialize();
        return instance;
    }

    public static void initialize() {
        if (instance == null) {
            instance = new SoundContainer();
        }
    }

    public SoundPlayer[] getSoundPlayers(SoundID soundID) {
        return this.soundContainer.get(soundID);
    }

    public SoundPlayer getSound(SoundID soundID) {
        SoundPlayer[] soundPlayers = getSoundPlayers(soundID);

        if (soundPlayers == null) {
            return null;
        }

        for (int i = 0; i < soundPlayers.length; i++) {
            if (!soundPlayers[i].isPlaying()) {
                return soundPlayers[i];
            }
        }
        return null;
    }

    public void playSound(SoundID soundID) {
        SoundPlayer player = getSound(soundID);
        if (player != null) {
            double masterVolume = soundVolume;
            if (soundID == SoundID.GAME_MUSIC) {
                masterVolume = musicVolume;
            }
            player.playSound(masterVolume);
        }
    }

    public void stopSound(SoundID soundID) {
        SoundPlayer player = getSound(soundID);
        if (player != null) {
            player.stopSound();
        }
    }

    public static int getTasksDone() {
        return tasksDone;
    }

    public static int getTotalTasks() {
        return totalTasks;
    }
}
