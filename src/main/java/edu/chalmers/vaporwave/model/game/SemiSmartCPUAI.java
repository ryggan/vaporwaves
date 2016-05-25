package edu.chalmers.vaporwave.model.game;

import java.util.Set;

public class SemiSmartCPUAI extends SemiSmartAI implements PlayerAI {

    public SemiSmartCPUAI(Set<GameCharacter> gameCharacterSet) {
        super(gameCharacterSet);
    }
    public boolean shouldPutBomb() {
        /*if(checkValueCurrent(super.getCurrentPosition()) > 170 || checkValueUp(super.getCurrentPosition()) + checkValueRight(super.getCurrentPosition())
                + checkValueLeft(super.getCurrentPosition()) == 0 || checkValueDown(super.getCurrentPosition()) + checkValueLeft(super.getCurrentPosition()) +
                checkValueRight(super.getCurrentPosition()) == 0 || checkValueLeft(super.getCurrentPosition())+ checkValueUp(super.getCurrentPosition()) + checkValueDown(super.getCurrentPosition()) == 0 ||
                checkValueUp(super.getCurrentPosition()) + checkValueRight(super.getCurrentPosition()) + checkValueDown(super.getCurrentPosition()) == 0) {
            return true;
        }*/
        if(checkValueCurrent(super.getCurrentPosition()) > 170 || checkValueCurrent(super.getCurrentPosition()) == 11) {
            return true;
        }
        return false;
    }
}
