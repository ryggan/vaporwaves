package edu.chalmers.vaporwave.util;

import org.junit.Test;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by FEngelbrektsson on 27/05/16.
 */
public class ClonerUtilityTest {

    @Test
    public void testStringArrayCloner() {
        String[] stringArray = new String[2];
        String[] stringArrayClone;
        stringArray[0] = "hej";
        stringArray[1] = "hejdå";

        stringArrayClone = ClonerUtility.stringArrayCloner(stringArray);
        assertTrue(stringArrayClone[0] == "hej");
        assertTrue(stringArrayClone[1] == "hejdå");
    }

    @Test
    public void testintMatrixCloner() {
        int[][] intMatrix = new int[2][2];
        intMatrix[0][0] = 1;
        intMatrix[1][0] = 7;
        intMatrix[0][1] = 3;
        intMatrix[1][1] = 4;

        int[][] intMatrixClone = ClonerUtility.intMatrixCloner(intMatrix);
        assertTrue(intMatrixClone[0][0] == 1);
        assertTrue(intMatrixClone[1][0] == 7);
        assertTrue(intMatrixClone[0][1] == 3);
        assertTrue(intMatrixClone[1][1] == 4);

    }

    @Test
    public void testmapObjectMatrixCloner() {
        MapObject[][] mapObjects = new MapObject[2][2];
        mapObjects[0][1] = MapObject.DESTRUCTIBLE_WALL;
        mapObjects[1][0] = MapObject.INDESTRUCTIBLE_WALL;
        mapObjects[1][1] = MapObject.DESTRUCTIBLE_WALL;
        mapObjects[0][0] = MapObject.EMPTY;

        MapObject[][] mapObjectsClone = ClonerUtility.mapObjectMatrixCloner(mapObjects);
        assertTrue(mapObjectsClone[0][0] == MapObject.EMPTY);
        assertTrue(mapObjectsClone[0][1] == MapObject.DESTRUCTIBLE_WALL);
        assertTrue(mapObjectsClone[1][0] == MapObject.INDESTRUCTIBLE_WALL);
        assertTrue(mapObjectsClone[1][1] == MapObject.DESTRUCTIBLE_WALL);

    }

    @Test
    public void testintArrayCloner() {
        int[] intArray = new int[2];
        intArray[0] = 1;
        intArray[1] = 2;

        int[] intArrayClone = ClonerUtility.intArrayCloner(intArray);
        assertTrue(intArrayClone[0] == 1);
        assertTrue(intArrayClone[1] == 2);
    }

    @Test
    public void testdoubleArrayCloner() {
        double[] doubleArray = new double[2];
        doubleArray[0] = 1.7;
        doubleArray[1] = 2.1;

        double[] doubleArrayClone = ClonerUtility.doubleArrayCloner(doubleArray);
        assertTrue(doubleArrayClone[0] == 1.7);
        assertTrue(doubleArrayClone[1] == 2.1);
    }

}