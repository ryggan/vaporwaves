package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Pair;
import edu.chalmers.vaporwave.util.Quad;
import edu.chalmers.vaporwave.util.SoundPlayer;
import edu.chalmers.vaporwave.util.Triple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    private static double tasksDone = 0;
    private static double totalTasks = 0;
    private static Set<Quad<SoundID, String, Integer, Double>> soundSet = new HashSet<>();
    private static Set<Triple<SoundID, String, Double>> musicSet = new HashSet<>();

    static void prepare() throws Exception {
        soundVolume = 1.0;
        musicVolume = 0.5;

        soundContainer = new HashMap<>();

        // Game sounds (+0)
        prepareSoundLoad(SoundID.PLACE_BOMB, "placebomb.mp3", NR_OF_PLACEBOMB);
        prepareSoundLoad(SoundID.EXPLOSION, "explosion.mp3", NR_OF_EXPLOSION);
        prepareSoundLoad(SoundID.POWERUP, "powerup1.mp3", NR_OF_POWERUP, 0.8);

        // Menu sounds (+3)
        prepareSoundLoad(SoundID.MENU_FORWARD_CLICK, "menu-forward-click.mp3", NR_OF_FORWARD_CLICK, 0.4);
        prepareSoundLoad(SoundID.MENU_BACKWARD_CLICK, "menu-backward-click.mp3", NR_OF_BACKWARD_CLICK, 0.4);
        prepareSoundLoad(SoundID.MENU_STARTUP, "menu-startup.mp3", 1);
        prepareSoundLoad(SoundID.MENU_EXIT, "menu-exit.mp3", 1);
        prepareSoundLoad(SoundID.MENU_SUCCESS, "menu_success1.mp3", 1, 0.4);

        // Speech files (7)
        prepareSoundLoad(SoundID.MENU_ALYSSA, "menu-alyssa.mp3", 1);
        prepareSoundLoad(SoundID.MENU_MEI, "menu-mei.mp3", 1);
        prepareSoundLoad(SoundID.MENU_CHARLOTTE, "menu-charlotte.mp3", 1);
        prepareSoundLoad(SoundID.MENU_ZYPHER, "menu-zypher.mp3", 1);
        prepareSoundLoad(SoundID.START_GAME, "game-start.mp3", 1);
        prepareSoundLoad(SoundID.SELECT_CHARACTER, "menu-selectcharacter.mp3", 1);
        prepareSoundLoad(SoundID.TIME_UP, "menu-timeup.mp3", 1);

        // background music (2)
        prepareMusicLoad(SoundID.MENU_BGM_1, "menu-bgm-1.mp3", 1.0);
        prepareMusicLoad(SoundID.GAME_MUSIC, "bg3.mp3", 1.0);
    }

    static void init() throws Exception {
        addSounds();
        addMusic();
    }

    private static void addSounds() throws Exception {
        for (Quad<SoundID, String, Integer, Double> quad : soundSet) {
            SoundPlayer[] soundPlayer = new SoundPlayer[quad.getThird()];
            setUpSoundArray(soundPlayer, quad.getThird(), quad.getSecond(), quad.getFourth());
            soundContainer.put(quad.getFirst(), soundPlayer);
        }
    }

    private static void addMusic() throws Exception {
        for (Triple<SoundID, String, Double> trip : musicSet) {
            SoundPlayer[] soundPlayer = new SoundPlayer[1];
            setUpBackgroundMusic(soundPlayer, trip.getSecond(), trip.getThird());
            soundContainer.put(trip.getFirst(), soundPlayer);
        }
    }

    private static void prepareSoundLoad(SoundID soundID, String fileName, int numberOfSounds, double volume) {
        soundSet.add(new Quad<>(soundID, fileName, numberOfSounds, volume));
        totalTasks += numberOfSounds;
    }

    private static void prepareSoundLoad(SoundID soundID, String fileName, int numberOfSounds) {
        prepareSoundLoad(soundID, fileName, numberOfSounds, 1.0);
    }

    private static void prepareMusicLoad(SoundID soundID, String fileName, double volume) {
        musicSet.add(new Triple<>(soundID, fileName, volume));
        totalTasks++;
    }

    // First creates a sound, and if number of sounds is more than one, duplicates this sound that many times
    private static void setUpSoundArray(SoundPlayer[] soundPlayers, int numberOfSounds, String fileName, double volume) throws Exception {
        soundPlayers[0] = new SoundPlayer(fileName, volume);
        tasksDone++;
        for (int i = 1; i < numberOfSounds; i++) {
            soundPlayers[i] = new SoundPlayer(soundPlayers[0]);
            tasksDone++;
        }
    }

    // Separate method for creating background music, since it allways should be one soundfile and allways loops
    private static void setUpBackgroundMusic(SoundPlayer[] soundPlayers, String fileName, double volume) throws Exception {
        soundPlayers[0] = new SoundPlayer(fileName, volume);
        soundPlayers[0].loopSound(true);
        tasksDone++;
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
            if (!soundPlayers[i].isPlaying() || soundID == SoundID.GAME_MUSIC || soundID == SoundID.MENU_BGM_1) {
                return soundPlayers[i];
            }
        }
        return null;
    }

    // Not only is it possible to get each sound via Container, but also to play sounds directly
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
