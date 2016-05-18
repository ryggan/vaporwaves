package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

class FileContainer {

    private Map<FileID, File> fileContainer;

    private Map<FileID, Font> fontContainer;

    private static double tasksDone;
    private static final double totalTasks = 2 + 2;

    FileContainer() {

        // TODO: OBS!!! IF ADDING FILES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        this.fileContainer = new HashMap<>();
        this.fontContainer = new HashMap<>();

        // Misc files (2)
        addFile(FileID.XML_CHARACTER_ENEMY, new File(Constants.GAME_CHARACTER_XML_FILE));
        addFile(FileID.VAPORMAP_DEFAULT, new File(Constants.DEFAULT_MAP_FILE));

        // Fonts (2)
        try {

            Font font = Font.loadFont(new FileInputStream(new File(Constants.FONT_FILE_BAUHAUS)), 18);
            addFont(FileID.FONT_BAUHAUS_18, font);

            font = Font.loadFont(new FileInputStream(new File(Constants.FONT_FILE_BAUHAUS)), 14);
            addFont(FileID.FONT_BAUHAUS_14, font);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addFile(FileID fileID, File file) {
        this.fileContainer.put(fileID, file);
        tasksDone++;
    }

    private void addFont(FileID fileID, Font font) {
        if (!fileID.toString().substring(0, 4).equals("FONT")) {
            throw new IllegalArgumentException();
        }
        this.fontContainer.put(fileID, font);
        tasksDone++;
    }

    File getFile(FileID fileID) {
        return this.fileContainer.get(fileID);
    }

    Font getFont(FileID fileID) {
        return this.fontContainer.get(fileID);
    }

    static double getTasksDone() {
        return tasksDone;
    }

    static double getTotalTasks() {
        return totalTasks;
    }

}
