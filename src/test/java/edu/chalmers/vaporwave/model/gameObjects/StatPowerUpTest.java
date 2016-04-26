package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.util.PowerUpState;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 26/04/16.
 */
public class StatPowerUpTest {

    List<PowerUpState> pList = new ArrayList<PowerUpState>();

    @Test
    public void testPowerUps() {
        pList.add(PowerUpState.BOMB_COUNT);
        pList.add(PowerUpState.HEALTH);
        pList.add(PowerUpState.RANGE);
        pList.add(PowerUpState.SPEED);

        StatPowerUp stat = new StatPowerUp(pList);

        System.out.println("The state is: " + stat.getPowerUpState());
        assertTrue(stat.getPowerUpState().equals(PowerUpState.HEALTH) || stat.getPowerUpState().equals(PowerUpState.BOMB_COUNT)
        || stat.getPowerUpState().equals(PowerUpState.RANGE) || stat.getPowerUpState().equals(PowerUpState.SPEED));

    }

}