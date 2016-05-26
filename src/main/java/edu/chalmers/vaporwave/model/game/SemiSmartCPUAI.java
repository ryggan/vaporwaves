package edu.chalmers.vaporwave.model.game;

import java.util.Set;
import java.util.Timer;

public class SemiSmartCPUAI extends SemiSmartAI implements PlayerAI {

    public SemiSmartCPUAI(Set<GameCharacter> gameCharacterSet) {
        super(gameCharacterSet);
    }
    public boolean shouldPutBomb() {
        if(checkValueCurrent(super.getCurrentPosition()) > 150) {
            return true;
        } else if(checkValueCurrent(super.getCurrentPosition()) == 11) {
            return true;
        }
        return false;
    }
}
