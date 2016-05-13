package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.assetcontainer.Container;

public class LoadingScreen {

    private double percentLoaded;

    public LoadingScreen() {
        this.percentLoaded = 0.0;
    }

    public void updateLoader() {

        percentLoaded = Container.getTasksDone() / Container.getTotalTasks();

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