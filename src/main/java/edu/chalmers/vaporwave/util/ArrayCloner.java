package edu.chalmers.vaporwave.util;

import edu.chalmers.vaporwave.model.game.StaticTile;

import java.util.ArrayList;
import java.util.List;

public class ArrayCloner {

    public static Object[] arrayCloner(Object[] objectArray) {
        Object[] temporary = new Object[objectArray.length];
        for(int i = 0; i < objectArray.length; i++) {
            temporary[i] = objectArray[i];
        }
        return temporary;
    }

    public static String[] stringArrayCloner(String[] objectArray) {
        String[] temporary = new String[objectArray.length];
        for(int i = 0; i < objectArray.length; i++) {
            temporary[i] = objectArray[i];
        }
        return temporary;
    }


    public static List<Object> listCloner(List<Object> objectList) {
        List<Object> temporary = new ArrayList<>();
        for(int i = 0; i < objectList.size(); i++) {
            temporary.add(objectList.get(i));
        }
        return temporary;
    }

    public static Object[][] matriceCloner(Object[][] objectMatrice) {
        Object[][] temporary = new Object[objectMatrice.length][objectMatrice[0].length];
        for(int i = 0; i < objectMatrice[0].length; i++) {
            for (int j = 0; j < objectMatrice.length; j++) {
                temporary[j][i] = objectMatrice[j][i];
            }
        }
        return temporary;
    }

    public static int[][] intMatriceCloner(int[][] intMatrice) {
        int[][] temporary = new int[intMatrice.length][intMatrice[0].length];
        for(int i = 0; i < intMatrice[0].length; i++) {
            for (int j = 0; j < intMatrice.length; j++) {
                temporary[j][i] = intMatrice[j][i];
            }
        }
        return temporary;
    }

    public static MapObject[][] mapObjectMatriceCloner(MapObject[][] mapObjectMatrice) {
        MapObject[][] temporary = new MapObject[mapObjectMatrice.length][mapObjectMatrice[0].length];
        for(int i = 0; i < mapObjectMatrice[0].length; i++) {
            for (int j = 0; j < mapObjectMatrice.length; j++) {
                temporary[j][i] = mapObjectMatrice[j][i];
            }
        }
        return temporary;
    }

    public static StaticTile[][] staticTileMatriceCloner(StaticTile[][] staticTileMatrice) {
        StaticTile[][] temporary = new StaticTile[staticTileMatrice.length][staticTileMatrice[0].length];
        for(int i = 0; i < staticTileMatrice[0].length; i++) {
            for (int j = 0; j < staticTileMatrice.length; j++) {
                temporary[j][i] = staticTileMatrice[j][i];
            }
        }
        return temporary;
    }

}
