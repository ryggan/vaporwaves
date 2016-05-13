package edu.chalmers.vaporwave.util;

import java.io.*;

public class MapFileReader {
    private MapObject[][] mapObjects;
    private BufferedReader reader;

    public MapFileReader(File file) {
//        public MapFileReader(String filename) {
        try {
//            reader = new BufferedReader(new FileReader(filename));
            reader = new BufferedReader(new FileReader(file));
            String widthString = reader.readLine();
            int width;
            if (widthString != null) {
                width = widthString.replace(" ", "").length();
            } else {
                throw new IOException();
            }
            LineNumberReader numbers = new LineNumberReader(reader);
            numbers.skip(Integer.MAX_VALUE);
            int height = (numbers.getLineNumber() + 2);
            this.mapObjects = new MapObject[width][height];
            reader = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            while((line = reader.readLine()) != null) {
                line = line.replace(" ", "");
                for (int j = 0; j < width; j++) {
                    switch (line.charAt(j)) {
                        case '1':
                            mapObjects[j][i] = MapObject.PLAYER1;
                            break;
                        case '2':
                            mapObjects[j][i] = MapObject.PLAYER2;
                            break;
                        case '3':
                            mapObjects[j][i] = MapObject.PLAYER3;
                            break;
                        case '4':
                            mapObjects[j][i] = MapObject.PLAYER4;
                            break;
                        case 'E':
                            mapObjects[j][i] = MapObject.ENEMY;
                            break;
                        case 'X':
                            mapObjects[j][i] = MapObject.INDESTRUCTIBLE_WALL;
                            break;
                        case 'D':
                            mapObjects[j][i] = MapObject.DESTRUCTIBLE_WALL;
                            break;
                        default:
                            mapObjects[j][i] = MapObject.EMPTY;
                    }
                }
                i++;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MapObject[][] getMapObjects() {
        return ArrayCloner.mapObjectMatrixCloner(this.mapObjects);
    }
}
