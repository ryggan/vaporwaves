package edu.chalmers.vaporwave.controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boob on 2016-04-15.
 */
public class ListenerController {

    private static ListenerController instance;

    private ArrayList<String> input = new ArrayList<String>();

    private ListenerController() { }

    public void initiateListener(Scene scene) {
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        if (!input.contains(code)) {
                            input.add(code);
                        }
                    }
                });

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        input.remove(code);
                    }
                });
    }

    public static ListenerController getInstance() {
        if (instance == null) {
            instance = new ListenerController();
        }
        return instance;
    }

    public ArrayList<String> getInput() {
        ArrayList<String> inputReturn = new ArrayList<String>();
        for (String s: this.input) {
            inputReturn.add(s);
        }

        return inputReturn;
    }
}
