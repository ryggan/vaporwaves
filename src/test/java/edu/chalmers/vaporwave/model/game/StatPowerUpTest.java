package edu.chalmers.vaporwave.model.game;

import edu.chalmers.vaporwave.util.PowerUpType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class StatPowerUpTest {

    List<PowerUpType> pList = new ArrayList<>();

    @Test
    public void testPowerUps() {
        pList.add(PowerUpType.BOMB_COUNT);
        pList.add(PowerUpType.HEALTH);
        pList.add(PowerUpType.RANGE);
        pList.add(PowerUpType.SPEED);

        PowerUp stat = new PowerUp(pList);

        assertTrue(stat.getPowerUpType().equals(PowerUpType.HEALTH) || stat.getPowerUpType().equals(PowerUpType.BOMB_COUNT)
        || stat.getPowerUpType().equals(PowerUpType.RANGE) || stat.getPowerUpType().equals(PowerUpType.SPEED));

    }

}