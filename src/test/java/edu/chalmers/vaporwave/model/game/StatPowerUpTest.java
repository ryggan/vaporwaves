package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.PowerUpType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by FEngelbrektsson on 26/04/16.
 */
public class StatPowerUpTest {

    List<PowerUpType> pList = new ArrayList<PowerUpType>();

    @Test
    public void testPowerUps() {
        pList.add(PowerUpType.BOMB_COUNT);
        pList.add(PowerUpType.HEALTH);
        pList.add(PowerUpType.RANGE);
        pList.add(PowerUpType.SPEED);

        StatPowerUp stat = new StatPowerUp(pList);

        System.out.println("The state is: " + stat.getPowerUpType());
        assertTrue(stat.getPowerUpType().equals(PowerUpType.HEALTH) || stat.getPowerUpType().equals(PowerUpType.BOMB_COUNT)
        || stat.getPowerUpType().equals(PowerUpType.RANGE) || stat.getPowerUpType().equals(PowerUpType.SPEED));

    }

}