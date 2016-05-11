package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.controller.MainController;
import edu.chalmers.vaporwave.assetcontainer.SoundContainer;

public class LoadingScreen {

    private MainController mainController;

    private double percentLoaded;

    private int hasLoaded;

    public LoadingScreen(MainController mainController) {
        this.mainController = mainController;
        this.percentLoaded = 0;
        this.hasLoaded = 0;
    }

    public void updateLoader() {
        percentLoaded = (double)SoundContainer.getTasksDone() / (double)SoundContainer.getTotalTasks();
        System.out.println("Loading! "+percentLoaded);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getPercentLoaded() {
        return this.percentLoaded;
    }

}