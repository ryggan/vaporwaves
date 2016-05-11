package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.FileID;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileContainer {

    private static FileContainer instance;

    private Map<FileID, File> fileContainer;

    private static double tasksDone;
    private static double totalTasks;

    private FileContainer() {

        tasksDone = 0.0;
        totalTasks = (double)(2) / 10.0;

        // TODO: OBS!!! IF ADDING FILES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        this.fileContainer = new HashMap<>();

        addFile(FileID.XML_CHARACTER_ENEMY, new File(Constants.GAME_CHARACTER_XML_FILE));
        addFile(FileID.VAPORMAP_DEFAULT, new File(Constants.DEFAULT_MAP_FILE));
    }

    private void addFile(FileID fileID, File file) {
        this.fileContainer.put(fileID, file);
        tasksDone += 0.1;
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
