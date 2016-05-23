package edu.chalmers.vaporwave.model.game;

import java.util.Set;

public class SemiSmartCPUAI extends SemiSmartAI implements PlayerAI {

    public SemiSmartCPUAI(Set<GameCharacter> gameCharacterSet) {
        super(gameCharacterSet);
    }
    public boolean shouldPutBomb() {
        if(checkValueCurrent(super.getCurrentPosition()) > 170) {
            return true;
        }
        return false;
    }
}
