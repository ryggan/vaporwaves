package edu.chalmers.vaporwave.model.gameObjects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HighScore {
    List<Integer> singlePlayerScore;
    List<Integer> multiPlayerScore;


    //First time
    public HighScore(){
        singlePlayerScore = new ArrayList<Integer>();
        multiPlayerScore = new ArrayList<Integer>();
    }

    //Maybe when they are saved?
    public HighScore(List<Integer> single, List<Integer> multiple){
        singlePlayerScore = single;
        multiPlayerScore = multiple;
    }


}
