package edu.chalmers.vaporwave.assetcontainer;

import edu.chalmers.vaporwave.util.Constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

class FileContainer {

    private Map<FileID, File> fileContainer;

    private static double tasksDone = 0;
    private static double totalTasks = 2;

    FileContainer() {

        // TODO: OBS!!! IF ADDING FILES; REMEMBER TO ALTER TOTAL TASKS ABOVE!!

        this.fileContainer = new HashMap<>();

        addFile(FileID.XML_CHARACTER_ENEMY, new File(Constants.GAME_CHARACTER_XML_FILE));
        addFile(FileID.VAPORMAP_DEFAULT, new File(Constants.DEFAULT_MAP_FILE));
    }

    private void addFile(FileID fileID, File file) {
        this.fileContainer.put(fileID, file);
        tasksDone++;
    }

    File getFile(FileID fileID) {
        return this.fileContainer.get(fileID);
    }

    static double getTasksDone() {
        return tasksDone;
    }

    static double getTotalTasks() {
        return totalTasks;
    }

}
