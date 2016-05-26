package edu.chalmers.vaporwave.model.game;

import java.util.Random;
import java.util.Set;
import java.util.Timer;

public class SemiSmartCPUAI extends SemiSmartAI implements PlayerAI {
    private Random random;

    public SemiSmartCPUAI(Set<GameCharacter> gameCharacterSet) {
        super(gameCharacterSet);
        random = new Random();
    }
    public boolean shouldPutBomb() {
        if(random.nextInt(1200) == 0) {
            //System.out.println("the bomb was random god damnit");
            return true;
        }
        if(checkValueCurrent(super.getCurrentPosition()) > 150) {
            //System.out.println("putting bomb at value " + checkValueCurrent(super.getCurrentPosition()));

            return true;
        } else if(checkValueCurrent(super.getCurrentPosition()) == 11) {
            //System.out.println("putting bomb at value " + checkValueCurrent(super.getCurrentPosition()));
            return true;
        }
        return false;
    }
}
