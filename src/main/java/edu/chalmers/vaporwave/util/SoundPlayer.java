package edu.chalmers.vaporwave.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class SoundPlayer {

    private MediaPlayer sound;
    private double volume;

    public SoundPlayer(MediaPlayer sound) {
        this.sound = sound;
        this.volume = 1.0;

        // Stops the sound when done:
        this.sound.setOnEndOfMedia(new EndOfMediaRunnable(this.sound));
    }

    public MediaPlayer getSound(){
        return sound;
    }
    private static class EndOfMediaRunnable implements Runnable {
        private MediaPlayer sound;

        EndOfMediaRunnable (MediaPlayer sound) {
            this.sound = sound;
        }

        @Override
        public void run() {
            if (this.sound.getCycleCount() != MediaPlayer.INDEFINITE) {
                this.sound.stop();
            }
        }
    }

    public SoundPlayer(String fileName){
        this(new MediaPlayer(new Media(Paths.get("src/main/resources/sounds/"+fileName).toUri().toString())));
    }

    public SoundPlayer(String fileName, double volume){
        this(fileName);
        this.volume = volume;
    }

    public SoundPlayer(SoundPlayer otherPlayer) {
        this(new MediaPlayer(otherPlayer.sound.getMedia()));
    }


    public void playSound(){
        this.sound.setVolume(volume);
        this.sound.play();
    }

    public void playSound(double masterVolume) {
        this.sound.setVolume(masterVolume * volume);
        this.sound.play();
    }

    public void stopSound(){
        this.sound.stop();
    }

    public void loopSound(boolean loop){
        if (loop) {
            this.sound.setCycleCount(MediaPlayer.INDEFINITE);
        } else {
            this.sound.setCycleCount(0);
        }
    }

    public boolean isPlaying() {
        return this.sound.getStatus() == MediaPlayer.Status.PLAYING;
    }

}
