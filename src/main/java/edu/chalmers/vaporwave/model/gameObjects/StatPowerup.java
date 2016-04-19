package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.view.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by FEngelbrektsson on 15/04/16.
 */
public class StatPowerUp extends PowerUp {
    private ArrayList<PowerUpState> enabledPowerUpList;
    private PowerUpState powerUpState;

    public StatPowerUp(Sprite s, Point cPos, Point gPos, ArrayList<PowerUpState> l) {
        super.setSprite(s);
        super.setCanvasPosition(cPos);
        super.setGridPosition(gPos);
        enabledPowerUpList = l;

        if(l.size() > 0) {
            int randomMaxValue = 0;
            for (int i = 0; i < l.size(); i++) {
                randomMaxValue = randomMaxValue + l.get(i).getSpawnChance();
            }

            Random r = new Random();
            int randomValue = r.nextInt(randomMaxValue);
            int j = 0;
                for (int i = 0; i < l.size(); i++) {
                    j = j + l.get(i).getSpawnChance();
                    if(j <= randomValue) {
                       powerUpState = l.get(i);
                    }
                }

        }
    }
}
