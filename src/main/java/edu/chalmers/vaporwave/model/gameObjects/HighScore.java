package edu.chalmers.vaporwave.model.gameObjects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HighScore {
    ArrayList<Object[]> singlePlayerScore;
    ArrayList<Object[]> multiPlayerScore;


    //First int then name
    public HighScore(){
        singlePlayerScore = new ArrayList<Object[]>();
        multiPlayerScore = new ArrayList<Object[]>();
    }


    public void addSinglePlayerHighScore(int score, String name) {
        Object[] newScore = {score, name};
        for(int i = 0; i <= singlePlayerScore.size(); i++){
            Object[] intScores = singlePlayerScore.get(i);
            if((int)intScores[0]<score){
                List<Object[]> backup = singlePlayerScore;
                for(int j = i+1;j<=singlePlayerScore.size();j++) {
                    singlePlayerScore.remove(j);
                    singlePlayerScore.add(backup.get(j - 1));
                }
                singlePlayerScore.add(i,newScore);
            } else {
                singlePlayerScore.add(newScore);
            }

        }
    }

    public void addMultiPlayerHighScore(int score, String name){

    }


}
