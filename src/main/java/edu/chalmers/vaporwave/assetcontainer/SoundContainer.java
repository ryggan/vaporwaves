package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.SoundPlayer;

import java.util.HashMap;
import java.util.Map;

class SoundContainer {

    private static Map<SoundID, SoundPlayer[]> soundContainer;

    private static double soundVolume;
    private static double musicVolume;

    private static final int NR_OF_PLACEBOMB = 10;
    private static final int NR_OF_EXPLOSION = 20;
    private static final int NR_OF_POWERUP = 10;
    private static final int NR_OF_BACKGROUND = 1;

    private static double tasksDone;
    private static final double totalTasks = NR_OF_PLACEBOMB + NR_OF_EXPLOSION + NR_OF_POWERUP + NR_OF_BACKGROUND;

    SoundContainer() {
        initSoundContainer();
    }

    private static void initSoundContainer() {
        soundVolume = 1.0;
        musicVolume = 0.5;

        // TODO: OBS!!! IF ADDING SOUNDS; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        soundContainer = new HashMap<>();
        SoundPlayer[] soundPlayer;

        soundPlayer = new SoundPlayer[NR_OF_PLACEBOMB];
        setUpSoundArray(soundPlayer, NR_OF_PLACEBOMB, "placebomb.mp3");
        soundContainer.put(SoundID.PLACE_BOMB, soundPlayer);

        soundPlayer = new SoundPlayer[NR_OF_EXPLOSION];
        setUpSoundArray(soundPlayer, NR_OF_EXPLOSION, "explosion.mp3");
        soundContainer.put(SoundID.EXPLOSION, soundPlayer);

        soundPlayer = new SoundPlayer[NR_OF_POWERUP];
        setUpSoundArray(soundPlayer, NR_OF_POWERUP, "powerup1.mp3", 0.8);
        soundContainer.put(SoundID.POWERUP, soundPlayer);

//        soundPlayer = new SoundPlayer[4];
//        setUpSoundArray(soundPlayer, 4, "girl_moan4.mp3");
//        this.soundContainer.put(SoundID.CHARACTER_FLINCH, soundPlayer);

        setUpBackgroundSound(soundPlayer);
    }

    private static void setUpBackgroundSound(SoundPlayer[] soundPlayer) {
        soundPlayer = new SoundPlayer[1];
        soundPlayer[0] = new SoundPlayer("bg3.mp3", 0.5);
        soundPlayer[0].loopSound(true);
        soundContainer.put(SoundID.GAME_MUSIC, soundPlayer);
        tasksDone++;
    }

    private static void setUpSoundArray(SoundPlayer[] array, int numberOfSounds, String fileName, double volume) {
        array[0] = new SoundPlayer(fileName, volume);
        tasksDone++;
        for (int i = 1; i < numberOfSounds; i++) {
            array[i] = new SoundPlayer(array[0]);
            tasksDone++;
        }
    }

    private static void setUpSoundArray(SoundPlayer[] array, int numberOfSounds, String fileName) {
        setUpSoundArray(array, numberOfSounds, fileName, 1.0);
    }

    public static SoundPlayer[] getSoundPlayers(SoundID soundID) {
        return soundContainer.get(soundID);
    }

    public static SoundPlayer getSound(SoundID soundID) {
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

    public static void playSound(SoundID soundID) {
        SoundPlayer player = getSound(soundID);
        if (player != null) {
            double masterVolume = soundVolume;
            if (soundID == SoundID.GAME_MUSIC) {
                masterVolume = musicVolume;
            }
            player.playSound(masterVolume);
        }
    }

    public static void stopSound(SoundID soundID) {
        SoundPlayer player = getSound(soundID);
        if (player != null) {
            player.stopSound();
        }
    }

    public static double getTasksDone() {
        return tasksDone;
    }

    public static double getTotalTasks() {
        return totalTasks;
    }
}
