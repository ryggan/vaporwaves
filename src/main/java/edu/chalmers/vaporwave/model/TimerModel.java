package edu.chalmers.vaporwave.model;

import java.util.concurrent.TimeUnit;


public class TimerModel {

    private String time;
    private static TimerModel instance;
//    private double timeLimit;
//    private boolean isFinished;
    private boolean isPaused;

    private TimerModel(){
        this.time="";
        this.isPaused=false;
    }

    public void setPaused(boolean b){
        isPaused=b;
    }

    public boolean isPaused(){
        return isPaused;
    }

    public static synchronized TimerModel getInstance() {
        if (instance == null) {
            instance = new TimerModel();
        }
        return instance;
    }

    public void updateTimer(double time){

        String millis="" + (int)((time*1000)%100);
        String seconds="" + (int)time%60;
        String minutes="" + (int) TimeUnit.SECONDS.toMinutes((long)time);
/*        System.out.println(time);
        System.out.println(millis);*/

        if((int)(time*1000%100)<10){
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

//    public double getTimeLimit(){
//        return timeLimit;
//    }

}
