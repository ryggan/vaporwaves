package edu.chalmers.vaporwave.model.gameObjects;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 26/04/16.
 */
public class ExplosiveTest {

    @Test
    public void explosiveTest() {
        GameCharacter gameChar = new GameCharacter("pirre");
        Explosive expls = new Bomb(gameChar, 10, 10, 30);
        System.out.println("gamechar: " + gameChar.getGridPosition());
        System.out.println("expl: " + expls.getPosition());
        assertTrue(gameChar.getGridPosition().equals(expls.getPosition()));
    }

}