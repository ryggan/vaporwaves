package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.util.FileContainer;
import edu.chalmers.vaporwave.util.SoundContainer;
import edu.chalmers.vaporwave.view.ImageContainer;

public class LoadingScreen {

    private double percentLoaded;

    public LoadingScreen() {
        this.percentLoaded = 0.0;
    }

    public void updateLoader() {
        double soundLoaded = (double) SoundContainer.getTasksDone() / (double) SoundContainer.getTotalTasks();
        if (Double.isNaN(soundLoaded)) {
            soundLoaded = 0.0;
        }
        double imageLoaded = ImageContainer.getTasksDone() / ImageContainer.getTotalTasks();
        if (Double.isNaN(imageLoaded)) {
            imageLoaded = 0.0;
        }
        double fileLoaded = FileContainer.getTasksDone() / FileContainer.getTotalTasks();
        if (Double.isNaN(fileLoaded)) {
            fileLoaded = 0.0;
        }

        if (Math.floor(imageLoaded) == 1) {
            imageLoaded = 0.1;
        } else {
            imageLoaded = 0.0;
        }
        if (Math.floor(fileLoaded) == 1) {
            fileLoaded = 0.1;
        } else {
            fileLoaded = 0.0;
        }

//        percentLoaded = (soundLoaded + imageLoaded + fileLoaded) / 3.0;
        percentLoaded = (soundLoaded + imageLoaded + fileLoaded) / 1.2;
//        System.out.println("Loading! "+percentLoaded);

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