package edu.chalmers.vaporwave.model.game;

import java.util.ArrayList;
import java.util.List;

public class HighScore {
    ArrayList<Object[]> singlePlayerScore;
    ArrayList<Object[]> multiPlayerScore;

    public HighScore(){
        singlePlayerScore = new ArrayList<>();
        multiPlayerScore = new ArrayList<>();
    }

    //First int then name
    public void addPlayerHighScore(int score, String name, ArrayList<Object[]> highScoreList) {
        Object[] newScore = {score, name};
        for(int i = 0; i <= highScoreList.size(); i++){
            Object[] intScores = highScoreList.get(i);
            if((int)intScores[0]<score){
                List<Object[]> backup = highScoreList;
                for(int j = i+1;j<=highScoreList.size();j++) {
                    highScoreList.remove(j);
                    highScoreList.add(backup.get(j - 1));
                }
                highScoreList.add(i,newScore);
            } else {
                highScoreList.add(newScore);
            }

        }
    }

    public void addMultiPlayerHighScore(int score, String name){
        addPlayerHighScore(score,name,multiPlayerScore);
    }

    public void addSinglePlayerHighScore(int score, String name){
        addPlayerHighScore(score,name,singlePlayerScore);

    }


}
