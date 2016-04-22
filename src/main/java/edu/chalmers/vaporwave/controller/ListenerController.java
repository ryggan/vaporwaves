package edu.chalmers.vaporwave.controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class ListenerController {

    private static ListenerController instance;

    private List<String> input = new ArrayList<String>();
    private List<String> pressed = new ArrayList<String>();
    private List<String> released = new ArrayList<String>();

    private ListenerController() { }

    public void initiateListener(Scene scene) {
        released.add("UP");
        released.add("DOWN");

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        if (!input.contains(code)) {
                            input.add(code);
                        }

                        if (pressed.contains(code)) {
                            pressed.remove(code);
                        } else if (input.contains(code) && !pressed.contains(code) && released.contains(code)) {
                            pressed.add(code);
                            released.remove(code);
                        }
                    }
                });

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        input.remove(code);
                        pressed.remove(code);
                        released.add(code);
                    }
                });
    }

    public static synchronized ListenerController getInstance() {
        if (instance == null) {
            instance = new ListenerController();
        }
        return instance;
    }

    public List<String> getInput() {
        List<String> inputReturn = new ArrayList<String>();
        for (String s: this.input) {
            inputReturn.add(s);
        }
        return inputReturn;
    }

    public List<String> getPressed() {
        List<String> pressedReturn = new ArrayList<String>();
        for (String s: this.pressed) {
            pressedReturn.add(s);
        }
        return pressedReturn;
    }
}
