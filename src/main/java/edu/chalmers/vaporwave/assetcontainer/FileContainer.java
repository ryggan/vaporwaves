package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.Pair;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This container deals with all miscellaneous files, such as XML or fonts.
 */
class FileContainer {

    private static Set<Pair<FileID, String>> fileSet = new HashSet<>();
    private static Set<Pair<FileID, String>> mapSet = new HashSet<>();
    private static Set<Pair<FileID, String>> fontSet = new HashSet<>();

    private final static Map<FileID, File> fileContainer = new HashMap<>();
    private final static Map<FileID, File> mapContainer = new HashMap<>();
    private final static Map<FileID, Font> fontContainer = new HashMap<>();

    private static int tasksDone = 0;
    private static int totalTasks = 0;

    // Called from Container
    static void prepare() throws Exception {

        // Misc files (1)
        prepareFileLoad(FileID.XML_CHARACTER_ENEMY, Constants.GAME_CHARACTER_XML_FILE);

        // Map files (6)
        prepareMapLoad(FileID.VAPORMAP_DEFAULT, Constants.DEFAULT_MAP_FILE);

//        prepareMapLoad(FileID.VAPORMAP_TEST, "src/main/resources/maps/test.vapormap");
//        prepareMapLoad(FileID.VAPORMAP_SCARCE, "src/main/resources/maps/scarce.vapormap");
//        prepareMapLoad(FileID.VAPORMAP_EMPTY, "src/main/resources/maps/empty.vapormap");

        prepareMapLoad(FileID.VAPORMAP_CLOSE, "src/main/resources/maps/close.vapormap");
        prepareMapLoad(FileID.VAPORMAP_LABYRINTH, "src/main/resources/maps/labyrinth.vapormap");
        prepareMapLoad(FileID.VAPORMAP_ARENA, "src/main/resources/maps/arena.vapormap");

        // Fonts (3)
        prepareFontLoad(FileID.FONT_BAUHAUS_14, Constants.FONT_FILE_BAUHAUS);
        prepareFontLoad(FileID.FONT_BAUHAUS_18, Constants.FONT_FILE_BAUHAUS);
        prepareFontLoad(FileID.FONT_BAUHAUS_30, Constants.FONT_FILE_BAUHAUS);
        prepareFontLoad(FileID.FONT_BAUHAUS_60, Constants.FONT_FILE_BAUHAUS);
    }

    static void init() throws Exception {
        addFiles();
        addMaps();
        addFonts();
    }

    private static void prepareFileLoad(FileID fileID, String fileName) {
        fileSet.add(new Pair(fileID, fileName));
        totalTasks++;
    }

    private static void prepareMapLoad(FileID fileID, String fileName) {
        mapSet.add(new Pair(fileID, fileName));
        totalTasks++;
    }

    private static void prepareFontLoad(FileID fileID, String fileName) {
        fontSet.add(new Pair(fileID, fileName));
        totalTasks++;
    }

    private static void addFiles() {
        for (Pair<FileID, String> pair : fileSet) {
            addFile(pair.getFirst(), new File(pair.getSecond()));
        }
    }

    private static void addMaps() {
        for (Pair<FileID, String> pair : mapSet) {
            addMap(pair.getFirst(), new File(pair.getSecond()));
        }
    }

    private static void addFonts() throws Exception {
        for (Pair<FileID, String> pair : fontSet) {
            String fontString = String.valueOf(pair.getFirst());
            int fontSize = Integer.valueOf(fontString.substring(fontString.length() - 2));
            Font font = Font.loadFont(new FileInputStream(new File(pair.getSecond())), fontSize);
            addFont(pair.getFirst(), font);
        }
    }

    // Every add-method is used to keep track of how many of the total tasks that has been accomplished
    private static void addFile(FileID fileID, File file) {
        fileContainer.put(fileID, file);
        tasksDone++;
    }

    private static void addMap(FileID fileID, File file) {
        mapContainer.put(fileID, file);
        tasksDone++;
    }

    private static void addFont(FileID fileID, Font font) {
        if (!fileID.toString().substring(0, 4).equals("FONT")) {
            throw new IllegalArgumentException();
        }
        fontContainer.put(fileID, font);
        tasksDone++;
    }

    static File getFile(FileID fileID) {
        return fileContainer.get(fileID);
    }

    static File getMap(FileID fileID) {
        return mapContainer.get(fileID);
    }

    static Map<FileID, File> getAllMaps() {
        return mapContainer;
    }

    static Font getFont(FileID fileID) {
        return fontContainer.get(fileID);
    }

    static double getTasksDone() {
        return tasksDone;
    }

    static double getTotalTasks() {
        return totalTasks;
    }

}
