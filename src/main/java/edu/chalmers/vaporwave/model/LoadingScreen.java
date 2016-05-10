package edu.chalmers.vaporwave.model;

import edu.chalmers.vaporwave.controller.MainController;

public class LoadingScreen implements Runnable {

    private MainController mainController;

    private double percentLoaded;

    public LoadingScreen(MainController mainController) {
        this.mainController = mainController;
        this.percentLoaded = 0;
    }

    public void run() {
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