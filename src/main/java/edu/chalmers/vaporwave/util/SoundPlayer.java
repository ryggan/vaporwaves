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

    public SoundPlayer(String fileName){
        Media soundFile = new Media(Paths.get("src/main/resources/sounds/"+fileName).toUri().toString());
        this.sound = new MediaPlayer(soundFile);

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

    public SoundPlayer(String fileName, double volume){
        this(fileName);
        this.sound.setVolume(volume);
    }


    public void playSound(){
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
