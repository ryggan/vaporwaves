package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileContainer {

    private static FileContainer instance;

    private Map<FileID, File> fileContainer;

    private static double tasksDone = 0;
    private static double totalTasks = 2;

    private FileContainer() {

        // TODO: OBS!!! IF ADDING FILES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        this.fileContainer = new HashMap<>();

        addFile(FileID.XML_CHARACTER_ENEMY, new File(Constants.GAME_CHARACTER_XML_FILE));
        addFile(FileID.VAPORMAP_DEFAULT, new File(Constants.DEFAULT_MAP_FILE));
    }

    private void addFile(FileID fileID, File file) {
        this.fileContainer.put(fileID, file);
        tasksDone++;
    }

    public File getFile(FileID fileID) {
        return this.fileContainer.get(fileID);
    }

    public static FileContainer getInstance() {
        initialize();
        return instance;
    }

    public static void initialize() {
        if (instance == null) {
            instance = new FileContainer();
        }
    }

    public static double getTasksDone() {
        return tasksDone;
    }

    public static double getTotalTasks() {
        return totalTasks;
    }

}
