package edu.chalmers.vaporwave.model.game;

import java.util.Random;

public class StupidPlayerAI extends StupidAI implements PlayerAI {

    @Override
    public boolean shouldPutBomb(){
        Random random = new Random();
        int nextRandom = random.nextInt(1000);
        if(nextRandom==1){
            return true;
        } else {
            return false;
        }
    }
}
