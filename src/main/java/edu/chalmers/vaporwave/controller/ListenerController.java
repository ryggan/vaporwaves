package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.util.Constants;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.*;

public class ListenerController {

    private static ListenerController instance;

    private List<String> input = new ArrayList<>();
    private List<String> pressed = new ArrayList<>();
    private List<String> released = new ArrayList<>();

    private Map<String, Integer> heldDown = new HashMap<>();

    private ListenerController() { }

    public void initiateListener(Scene scene) {

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        if (!input.contains(code)) {
                            input.add(code);
                            pressed.add(code);
                        }


                    }
                });

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        input.remove(code);
                        released.add(code);
                    }
                });
    }

    public void clearPressed() {
        for (String key : this.pressed) {
            this.heldDown.put(key, 0);
        }
        this.pressed.clear();
    }

    public void clearReleased() {
        for (String key : this.released) {
            if(this.heldDown.keySet().contains(key)) {
                this.heldDown.remove(key);
            }
        }

        Set<String> keysToRemove = new HashSet<>();
        for (String key : this.heldDown.keySet()) {
            if (this.heldDown.get(key).equals(new Integer(Constants.FRAMES_HELD_KEYS_UPDATE))) {
                this.pressed.add(key);
                keysToRemove.add(key);
            } else {
                this.heldDown.put(key, this.heldDown.get(key) + 1);
            }
        }
        for (String key : keysToRemove) {
            this.heldDown.remove(key);
        }

        this.released.clear();
    }

    public static synchronized ListenerController getInstance() {
        if (instance == null) {
            instance = new ListenerController();
        }
        return instance;
    }

    public List<String> getInput() {
        List<String> inputReturn = new ArrayList<>();
        for (String s: this.input) {
            inputReturn.add(s);
        }
        return inputReturn;
    }

    public List<String> getPressed() {
        List<String> pressedReturn = new ArrayList<>();
        for (String s: this.pressed) {
            pressedReturn.add(s);
        }
        return pressedReturn;
    }

    public List<String> getReleased() {
        List<String> releasedReturn = new ArrayList<>();
        for (String s: this.released) {
            releasedReturn.add(s);
        }
        return releasedReturn;
    }
}
