package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.view.TimerView;
import javafx.scene.Group;
import javafx.scene.control.Label;

import java.util.concurrent.TimeUnit;


public class TimerModel {

    private String time;
    private static TimerModel instance;
    private double timeLimit;

    private TimerModel(){
        this.time="";
    }

    public static TimerModel getInstance(){
        if (instance == null)
            instance = new TimerModel();

        return instance;
    }

    public void updateTimer(double time){

        String millis="" + (int)((time*1000)%60);
        String seconds="" + (int)time%60;
        String minutes="" + (int) TimeUnit.SECONDS.toMinutes((long)time);

        if((int)(time*1000%60)<10){
            millis="0"+ millis;
        }
        if(((int)time%60)<10){
            seconds="0"+ seconds;
        }
        if(((int)TimeUnit.SECONDS.toMinutes((long)time))<10){
            minutes="0"+ minutes;
        }
        this.time = minutes+":"+seconds + ":"+millis;
    }

    public String getTime(){
        return time;
    }

    public double getTimeLimit(){
        return timeLimit;
    }

}
