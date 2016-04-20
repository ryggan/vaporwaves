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

    public StatPowerUp(Sprite sprite, double canvasPositionX, double canvasPositionY, Point gridPosition, ArrayList<PowerUpState> list) {
        super.setSprite(sprite);
        super.setCanvasPosition(canvasPositionX, canvasPositionY);
        super.setGridPosition(gridPosition);
        enabledPowerUpList = list;

        if(list.size() > 0) {
            int randomMaxValue = 0;
            for (int i = 0; i < list.size(); i++) {
                randomMaxValue = randomMaxValue + list.get(i).getSpawnChance();
            }

            Random r = new Random();
            int randomValue = r.nextInt(randomMaxValue);
            int j = 0;
            for (int i = 0; i < list.size(); i++) {
                j = j + list.get(i).getSpawnChance();
                if(j <= randomValue) {
                    powerUpState = list.get(i);
                }
            }

        }
    }
}
