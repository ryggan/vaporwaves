package edu.chalmers.vaporwave.model.game;

import java.util.Random;
import java.util.Set;

/**
 * This AI is meant to work with GameCharacters, and therefore also need to determine
 * when a Bomb should be placed. Its movement is the same as SemiSmartAI.
 */
public class SemiSmartCPUAI extends SemiSmartAI implements PlayerAI {
    private Random random;

    public SemiSmartCPUAI(Set<GameCharacter> gameCharacterSet) {
        super(gameCharacterSet);
        random = new Random();
    }
    public boolean shouldPutBomb() {
        if(random.nextInt(1200) == 0) {
            return true;
        }
        if(checkValueCurrent(super.getCurrentPosition()) > 150) {
            return true;
        } else if(checkValueCurrent(super.getCurrentPosition()) == 11) {
            return true;
        }
        return false;
    }
}
