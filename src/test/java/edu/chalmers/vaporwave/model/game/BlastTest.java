package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.BlastState;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 26/04/16.
 */
public class BlastTest {
    GameCharacter pirre = new GameCharacter("pirre", 0);
    Point posish = pirre.getGridPosition();

    @Test
    public void testGetPosition() throws Exception {
        Explosive bombish = new Bomb(pirre, 1, 1, 1, 30);
        Blast bMan = new Blast(bombish, BlastState.CENTER, null, 0);
//        assertTrue(bMan.getPosition().equals(pirre.getGridPosition()));
    }

    @Test
    public void testGetRange() throws Exception {
        Explosive bombish = new Bomb(pirre, 1, 1, 1, 30);
        Blast bMan = new Blast(bombish, BlastState.CENTER, null, 0);
//        assertTrue(bMan.getRange() == 1);
    }
}