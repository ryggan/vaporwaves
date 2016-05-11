package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.assetcontainer.CharacterSpriteContainer;
import edu.chalmers.vaporwave.assetcontainer.FileContainer;
import edu.chalmers.vaporwave.assetcontainer.ImageContainer;
import edu.chalmers.vaporwave.assetcontainer.SoundContainer;

public class LoadingScreen {

    private double percentLoaded;

    public LoadingScreen() {
        this.percentLoaded = 0.0;
    }

    public void updateLoader() {

        double soundLoaded = SoundContainer.getTasksDone();
        double imageLoaded = ImageContainer.getTasksDone();
        double fileLoaded = FileContainer.getTasksDone();
        double spriteLoaded = CharacterSpriteContainer.getTasksDone();
        double totalTasks = SoundContainer.getTotalTasks() + ImageContainer.getTotalTasks()
                + FileContainer.getTotalTasks() + CharacterSpriteContainer.getTotalTasks();

        percentLoaded = (soundLoaded + imageLoaded + fileLoaded + spriteLoaded) / totalTasks;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getPercentLoaded() {
        return Math.round(this.percentLoaded * 1000.0) / 1000.0;
    }

}