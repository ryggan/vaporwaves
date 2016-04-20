package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.view.MenuView;
import javafx.scene.Group;

import java.util.ArrayList;

/**
 * Created by bob on 2016-04-15.
 */
public class MenuController {

    private MenuView mv;

    public MenuController(Group root) {
        mv = new MenuView(root);
    }

    public void timerUpdate(double timeSinceStart, double timeSinceLastCall, ArrayList<String> input) {

        for (int i = 0; i < input.size(); i++) {
            String key = input.get(i);
            if (key.equals("UP") || key.equals("DOWN"))
                mv.changeSelected(key);
        }
        
//        av.updateView(timeSinceStart);
    } //menustate


}
