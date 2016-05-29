package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * This container deals with all miscellaneous files, such as XML or fonts.
 */
class FileContainer {

    private final static Map<FileID, File> fileContainer = new HashMap<>();

    private final static Map<FileID, Font> fontContainer = new HashMap<>();

    private final static Map<FileID, Color> colorContainer = new HashMap<>();

    private static int tasksDone;
    private static final double totalTasks = 1 + 6 + 3;

    // Called from Container
    public static void initFileContainer() throws Exception {

        // Misc files (1)
        addFile(FileID.XML_CHARACTER_ENEMY, new File(Constants.GAME_CHARACTER_XML_FILE));

        // Map files (6)
        addFile(FileID.VAPORMAP_DEFAULT, new File(Constants.DEFAULT_MAP_FILE));

        addFile(FileID.VAPORMAP_TEST, new File("src/main/resources/maps/test.vapormap"));
        addFile(FileID.VAPORMAP_SCARCE, new File("src/main/resources/maps/scarce.vapormap"));
        addFile(FileID.VAPORMAP_EMPTY, new File("src/main/resources/maps/empty.vapormap"));

        addFile(FileID.VAPORMAP_CLOSE, new File("src/main/resources/maps/close.vapormap"));
        addFile(FileID.VAPORMAP_LABYRINTH, new File("src/main/resources/maps/labyrinth.vapormap"));

        // Fonts (3)
        Font font = Font.loadFont(new FileInputStream(new File(Constants.FONT_FILE_BAUHAUS)), 14);
        addFont(FileID.FONT_BAUHAUS_14, font);

        font = Font.loadFont(new FileInputStream(new File(Constants.FONT_FILE_BAUHAUS)), 18);
        addFont(FileID.FONT_BAUHAUS_18, font);

        font = Font.loadFont(new FileInputStream(new File(Constants.FONT_FILE_BAUHAUS)), 30);
        addFont(FileID.FONT_BAUHAUS_30, font);
    }

    // Every add-method is used to keep track of how many of the total tasks that has been accomplished
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
