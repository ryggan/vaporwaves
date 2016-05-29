package edu.chalmers.vaporwave.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is for reading in a map from text file, interpreting it and returning an grid
 *
 */
public class MapFileReader {
    private MapObject[][] mapObjects;
    private BufferedReader reader;

    public MapFileReader(File file) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF8");
            reader = new BufferedReader(inputStreamReader);
            String widthString = reader.readLine();
            int width;
            if (widthString != null) {
                width = widthString.replace(" ", "").length();
            } else {
                throw new IOException();
            }
            LineNumberReader numbers = new LineNumberReader(reader);
            long skippedLines = numbers.skip(Integer.MAX_VALUE);
            if (Debug.PRINT_LOG) {
                System.out.println("Skipped lines in MapFileReader: " + skippedLines);
            }
            int height = (numbers.getLineNumber() + 2);
            this.mapObjects = new MapObject[width][height];

            Map<Character, MapObject> mapCharacterToMapObject = new HashMap<>();
            mapCharacterToMapObject.put('1', MapObject.PLAYER1);
            mapCharacterToMapObject.put('2', MapObject.PLAYER2);
            mapCharacterToMapObject.put('3', MapObject.PLAYER3);
            mapCharacterToMapObject.put('4', MapObject.PLAYER4);
            mapCharacterToMapObject.put('E', MapObject.ENEMY);
            mapCharacterToMapObject.put('X', MapObject.INDESTRUCTIBLE_WALL);
            mapCharacterToMapObject.put('D', MapObject.DESTRUCTIBLE_WALL);

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            String line;
            int i = 0;
            while((line = reader.readLine()) != null) {
                line = line.replace(" ", "");
                for (int j = 0; j < width; j++) {
                    switch (line.charAt(j)) {
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case 'E':
                        case 'X':
                        case 'D':
                            mapObjects[j][i] = mapCharacterToMapObject.get(line.charAt(j));
                            break;
                        default:
                            mapObjects[j][i] = MapObject.EMPTY;
                    }
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            ErrorMessage.show(e);
        }  catch (IOException e) {
            ErrorMessage.show(e);
        }
    }

    public MapObject[][] getMapObjects() {
        return ClonerUtility.mapObjectMatrixCloner(this.mapObjects);
    }
}
