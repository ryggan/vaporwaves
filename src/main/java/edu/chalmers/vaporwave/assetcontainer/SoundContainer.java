package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.SoundPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * This container deals with all the sounds.
 * It is handled a bit different from the other containers, since there is needed a
 * buffert of several identical sounds, when for example more than one bomb is placed almost
 * at the same time, so that overlapping sounds can be played.
 */
class SoundContainer {

    private static Map<SoundID, SoundPlayer[]> soundContainer;

    private static double soundVolume;
    private static double musicVolume;

    private static final int NR_OF_PLACEBOMB = 10;
    private static final int NR_OF_EXPLOSION = 20;
    private static final int NR_OF_POWERUP = 10;
    private static final int NR_OF_FORWARD_CLICK = 4;
    private static final int NR_OF_BACKWARD_CLICK = 4;

    private static double tasksDone;
    private static final double totalTasks = 3 + 7 + 2 +
            NR_OF_BACKWARD_CLICK + NR_OF_FORWARD_CLICK +
            NR_OF_PLACEBOMB + NR_OF_EXPLOSION + NR_OF_POWERUP;

    public static void initSoundContainer() throws Exception {
        soundVolume = 1.0;
        musicVolume = 0.5;

        // TODO: OBS!!! IF ADDING SOUNDS; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        soundContainer = new HashMap<>();
        SoundPlayer[] soundPlayer;

        // Game sounds (+0)
        soundPlayer = new SoundPlayer[NR_OF_PLACEBOMB];
        setUpSoundArray(soundPlayer, NR_OF_PLACEBOMB, "placebomb.mp3");
        soundContainer.put(SoundID.PLACE_BOMB, soundPlayer);

        soundPlayer = new SoundPlayer[NR_OF_EXPLOSION];
        setUpSoundArray(soundPlayer, NR_OF_EXPLOSION, "explosion.mp3");
        soundContainer.put(SoundID.EXPLOSION, soundPlayer);

        soundPlayer = new SoundPlayer[NR_OF_POWERUP];
        setUpSoundArray(soundPlayer, NR_OF_POWERUP, "powerup1.mp3", 0.8);
        soundContainer.put(SoundID.POWERUP, soundPlayer);

        // Menu sounds (+3)
        soundPlayer = new SoundPlayer[NR_OF_FORWARD_CLICK];
        setUpSoundArray(soundPlayer, NR_OF_FORWARD_CLICK, "menu-forward-click.mp3", 0.4);
        soundContainer.put(SoundID.MENU_FORWARD_CLICK, soundPlayer);

        soundPlayer = new SoundPlayer[NR_OF_BACKWARD_CLICK];
        setUpSoundArray(soundPlayer, NR_OF_BACKWARD_CLICK, "menu-backward-click.mp3", 0.4);
        soundContainer.put(SoundID.MENU_BACKWARD_CLICK, soundPlayer);

        soundPlayer = new SoundPlayer[1];
        setUpSoundArray(soundPlayer, 1, "menu-startup.mp3");
        soundContainer.put(SoundID.MENU_STARTUP, soundPlayer);

        soundPlayer = new SoundPlayer[1];
        setUpSoundArray(soundPlayer, 1, "menu-exit.mp3");
        soundContainer.put(SoundID.MENU_EXIT, soundPlayer);

        soundPlayer = new SoundPlayer[1];
        setUpSoundArray(soundPlayer, 1, "menu_success1.mp3", 0.4);
        soundContainer.put(SoundID.MENU_SUCCESS, soundPlayer);

        // Speech files (7)
        soundPlayer = new SoundPlayer[1];
        setUpSoundArray(soundPlayer, 1, "menu-alyssa.mp3");
        soundContainer.put(SoundID.MENU_ALYSSA, soundPlayer);
        soundPlayer = new SoundPlayer[1];
        setUpSoundArray(soundPlayer, 1, "menu-mei.mp3");
        soundContainer.put(SoundID.MENU_MEI, soundPlayer);
        soundPlayer = new SoundPlayer[1];
        setUpSoundArray(soundPlayer, 1, "menu-charlotte.mp3");
        soundContainer.put(SoundID.MENU_CHARLOTTE, soundPlayer);
        soundPlayer = new SoundPlayer[1];
        setUpSoundArray(soundPlayer, 1, "menu-zypher.mp3");
        soundContainer.put(SoundID.MENU_ZYPHER, soundPlayer);
        soundPlayer = new SoundPlayer[1];
        setUpSoundArray(soundPlayer, 1, "game-start.mp3");
        soundContainer.put(SoundID.START_GAME, soundPlayer);
        soundPlayer = new SoundPlayer[1];
        setUpSoundArray(soundPlayer, 1, "menu-selectcharacter.mp3");
        soundContainer.put(SoundID.SELECT_CHARACTER, soundPlayer);
        soundPlayer = new SoundPlayer[1];
        setUpSoundArray(soundPlayer, 1, "menu-timeup.mp3");
        soundContainer.put(SoundID.TIME_UP, soundPlayer);

        // background music (2)
        soundPlayer = new SoundPlayer[1];
        setUpBackgroundMusic(soundPlayer, "menu-bgm-1.mp3", 0.5);
        soundContainer.put(SoundID.MENU_BGM_1, soundPlayer);

        soundPlayer = new SoundPlayer[1];
        setUpBackgroundMusic(soundPlayer, "bg3.mp3", 0.5);
        soundContainer.put(SoundID.GAME_MUSIC, soundPlayer);
    }

    private static void setUpBackgroundMusic(SoundPlayer[] soundPlayers, String fileName, double volume) throws Exception {
        soundPlayers[0] = new SoundPlayer(fileName, volume);
        soundPlayers[0].loopSound(true);
        tasksDone++;
    }

    private static void setUpSoundArray(SoundPlayer[] soundPlayers, int numberOfSounds, String fileName, double volume) throws Exception {
        soundPlayers[0] = new SoundPlayer(fileName, volume);
        tasksDone++;
        for (int i = 1; i < numberOfSounds; i++) {
            soundPlayers[i] = new SoundPlayer(soundPlayers[0]);
            tasksDone++;
        }
    }

    private static void setUpSoundArray(SoundPlayer[] array, int numberOfSounds, String fileName) throws Exception {
        setUpSoundArray(array, numberOfSounds, fileName, soundVolume);
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
            if (soundID == SoundID.GAME_MUSIC || soundID == SoundID.MENU_BGM_1) {
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
