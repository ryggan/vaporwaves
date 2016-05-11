package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.assetcontainer.*;

public class LoadingScreen {

    private double percentLoaded;

    public LoadingScreen() {
        this.percentLoaded = 0.0;
    }

    public void updateLoader() {

        double soundLoaded = SoundContainer.getTasksDone();
        double imageLoaded = ImageContainer.getTasksDone();
        double fileLoaded = FileContainer.getTasksDone();
        double characterSpriteLoaded = CharacterSpriteContainer.getTasksDone();
        double spriteLoaded = SpriteContainer.getTasksDone();
        double totalTasks = SoundContainer.getTotalTasks() + ImageContainer.getTotalTasks()
                + FileContainer.getTotalTasks() + CharacterSpriteContainer.getTotalTasks() + SpriteContainer.getTotalTasks();

        percentLoaded = (soundLoaded + imageLoaded + fileLoaded + characterSpriteLoaded + spriteLoaded) / totalTasks;
//        System.out.println(percentLoaded);

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