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

    public static Object[][] matrixCloner(Object[][] objectMatrix) {
        Object[][] temporary = new Object[objectMatrix.length][objectMatrix[0].length];
        for(int i = 0; i < objectMatrix[0].length; i++) {
            for (int j = 0; j < objectMatrix.length; j++) {
                temporary[j][i] = objectMatrix[j][i];
            }
        }
        return temporary;
    }

    public static int[][] intMatrixCloner(int[][] intMatrix) {
        int[][] temporary = new int[intMatrix.length][intMatrix[0].length];
        for(int i = 0; i < intMatrix[0].length; i++) {
            for (int j = 0; j < intMatrix.length; j++) {
                temporary[j][i] = intMatrix[j][i];
            }
        }
        return temporary;
    }

    public static MapObject[][] mapObjectMatrixCloner(MapObject[][] mapObjectMatrix) {
        MapObject[][] temporary = new MapObject[mapObjectMatrix.length][mapObjectMatrix[0].length];
        for(int i = 0; i < mapObjectMatrix[0].length; i++) {
            for (int j = 0; j < mapObjectMatrix.length; j++) {
                temporary[j][i] = mapObjectMatrix[j][i];
            }
        }
        return temporary;
    }

    public static Object[][] objectMatrixCloner(Object[][] objectMatrix) {
        Object[][] temporary = new Object[objectMatrix.length][objectMatrix[0].length];
        for(int i = 0; i < objectMatrix[0].length; i++) {
            for (int j = 0; j < objectMatrix.length; j++) {
                temporary[j][i] = objectMatrix[j][i];
            }
        }
        return temporary;
    }

    public static int[] intArrayCloner(int[] intArray) {
        int[] temporary = new int[intArray.length];
        for(int i = 0; i < intArray.length; i++) {
            temporary[i] = intArray[i];
        }
        return temporary;
    }

    public static double[] doubleArrayCloner(double[] doubleArray) {
        double[] temporary = new double[doubleArray.length];
        for (int i = 0; i < doubleArray.length; i++) {
            temporary[i] = doubleArray[i];
        }
        return temporary;
    }
}
