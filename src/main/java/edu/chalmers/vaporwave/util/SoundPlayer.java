package edu.chalmers.vaporwave.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class SoundPlayer {

    private MediaPlayer sound;
    private double volume;

    public SoundPlayer(MediaPlayer sound) {
        this.sound = sound;
        this.volume = 1.0;

        // Stops the sound when done:
        this.sound.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (sound.getCycleCount() != MediaPlayer.INDEFINITE) {
                    sound.stop();
                }
            }
        });
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

    public SoundPlayer clone() {
        return new SoundPlayer(this);
    }

}
