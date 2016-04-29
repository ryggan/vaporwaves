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
    }

    public void playSound(){
        this.sound.play();
        System.out.println("playing");
    }

    public void stopSound(){
        this.sound.stop();
    }

    public void loopSound(){

    }
}
