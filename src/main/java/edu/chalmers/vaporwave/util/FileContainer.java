package edu.chalmers.vaporwave.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bob on 2016-05-09.
 */
public class FileContainer {

    private static FileContainer instance;

    private Map<FileID, File> fileContainer;

    private FileContainer() {

        this.fileContainer = new HashMap<>();

        this.fileContainer.put(FileID.XML_CHARACTER_ENEMY, new File(Constants.GAME_CHARACTER_XML_FILE));

        this.fileContainer.put(FileID.VAPORMAP_DEFAULT, new File(Constants.DEFAULT_MAP_FILE));

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

}
