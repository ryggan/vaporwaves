package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.controller.MainController;

public class LoadingScreen {

    private MainController mainController;

    private double percentLoaded;

    public LoadingScreen(MainController mainController) {
        this.mainController = mainController;
        this.percentLoaded = 0;
    }

    public void updateLoader() {
        while(true) {
            System.out.println("Loading! "+percentLoaded);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}