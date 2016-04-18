package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.view.MenuView;
import javafx.scene.Group;

/**
 * Created by bob on 2016-04-15.
 */
public class MenuController {

    private MenuView mv;

    public MenuController(Group root) {
        mv = new MenuView(root);
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {
        
//        av.updateView(timeSinceStart);
    } //menustate


}
