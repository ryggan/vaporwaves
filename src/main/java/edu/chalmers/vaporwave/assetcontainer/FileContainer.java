package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

class FileContainer {

    private static Map<FileID, File> fileContainer;

    private static Map<FileID, Font> fontContainer;

    private static Map<FileID, Color> colorContainer;

    private static double tasksDone;
    private static final double totalTasks = 2 + 3 + 1;

    public static void initFileContainer() {
        // TODO: OBS!!! IF ADDING FILES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        fileContainer = new HashMap<>();
        fontContainer = new HashMap<>();
        colorContainer = new HashMap<>();

        // Misc files (2)
        addFile(FileID.XML_CHARACTER_ENEMY, new File(Constants.GAME_CHARACTER_XML_FILE));
        addFile(FileID.VAPORMAP_DEFAULT, new File(Constants.DEFAULT_MAP_FILE));

        // Fonts (3)
        try {

            Font font = Font.loadFont(new FileInputStream(new File(Constants.FONT_FILE_BAUHAUS)), 14);
            addFont(FileID.FONT_BAUHAUS_14, font);

            font = Font.loadFont(new FileInputStream(new File(Constants.FONT_FILE_BAUHAUS)), 18);
            addFont(FileID.FONT_BAUHAUS_18, font);

            font = Font.loadFont(new FileInputStream(new File(Constants.FONT_FILE_BAUHAUS)), 30);
            addFont(FileID.FONT_BAUHAUS_30, font);

            Color color = Color.web(Constants.COLORNO_VAPEPINK);
            addColor(FileID.COLOR_VAPEPINK, color);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Fonts (3)
    }

    private static void addFile(FileID fileID, File file) {
        fileContainer.put(fileID, file);
        tasksDone++;
    }

    private static void addFont(FileID fileID, Font font) {
        if (!fileID.toString().substring(0, 4).equals("FONT")) {
            throw new IllegalArgumentException();
        }
        fontContainer.put(fileID, font);
        tasksDone++;
    }

    private static void addColor(FileID fileID, Color color) {
        if (!fileID.toString().substring(0, 5).equals("COLOR")) {
            throw new IllegalArgumentException();
        }
        colorContainer.put(fileID, color);
        tasksDone++;
    }

    static File getFile(FileID fileID) {
        return fileContainer.get(fileID);
    }

    static Font getFont(FileID fileID) {
        return fontContainer.get(fileID);
    }

    static Color getColor(FileID fileID) {
        return colorContainer.get(fileID);
    }

    static double getTasksDone() {
        return tasksDone;
    }

    static double getTotalTasks() {
        return totalTasks;
    }

}
