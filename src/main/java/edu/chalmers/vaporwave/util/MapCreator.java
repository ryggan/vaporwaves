/*package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.model.gameObjects.DestructibleWall;
import edu.chalmers.vaporwave.model.gameObjects.Enemy;
import edu.chalmers.vaporwave.model.gameObjects.IndestructibleWall;
import edu.chalmers.vaporwave.model.gameObjects.Tile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

*
 * Created by FEngelbrektsson on 20/04/16.

public class MapCreator {
    private File mapFile;
    private ArrayList<Tile> mapArray;




    public ArrayList<Tile> createMapArray(char[] charKeywords) {
        for(int i = 0; i <= charKeywords.length; i++) {
            if(isDestructibleWall(charKeywords[i])) {
                mapArray.get(i) = new DestructibleWall();
            } else if(isInDestructibleWall(charKeywords[i])) {
                mapArray.get(i) = new IndestructibleWall();
            } else if(isEnemySpawn(charKeywords[i])) {
                mapArray.get(i) = new Enemy();
            } else if(isCharacterSpawn(charKeywords[i])) {
                mapArray.get(i) = new Character();
            }
        }
    }

    public Boolean isDestructibleWall(char c) {
        if(c == 'X') {
            return true;
        }
        return false;
    }

    public Boolean isInDestructibleWall(char c) {
        if(c == 'O') {
            return true;
        }
        return false;
    }

    public Boolean isEnemySpawn(char c) {
        if(c == 'E') {
            return true;
        }
        return false;
    }

    public Boolean isCharacterSpawn(char c) {
        if(c == 'C') {
            return true;
        }
        return false;
    }*/

//}
