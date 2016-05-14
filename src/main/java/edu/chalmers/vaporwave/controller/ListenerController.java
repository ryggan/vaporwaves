package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.util.Constants;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.*;

public class ListenerController {

    private static ListenerController instance;

    private List<String> input;
    private List<String> pressed;
    private List<String> released;

    private Map<String, Integer> heldDown;

//    private Map<Integer, Controller> gamePads;
    private List<Controller> gamePads;

    private ListenerController() {
        input = new ArrayList<>();
        pressed = new ArrayList<>();
        released = new ArrayList<>();

        heldDown = new HashMap<>();
//        gamePads = new HashMap<>();
        gamePads = new ArrayList<>();
    }

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

        updateGamePads();
    }

    private void updateGamePads() {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for(int i =0;i < controllers.length;i++) {
//            if (controllers[i].getType() == Controller.Type.GAMEPAD && !this.gamePads.values().contains(controllers[i])) {
//                Random generator = new Random();
//                Integer id;
//                do {
//                    id = new Integer(Math.abs(generator.nextInt()));
//                } while (this.gamePads.keySet().contains(id));
//                this.gamePads.put(id, controllers[i]);
//            }

            if (controllers[i].getType() == Controller.Type.GAMEPAD && !this.gamePads.contains(controllers[i])) {
                this.gamePads.add(controllers[i]);
            }
        }

        System.out.println("Active gamepads: "+this.gamePads);
//        if (this.gamePads.size() > 1) {
//            System.out.println("Is the same? pad1 == pad1: "+(this.gamePads.get(0) == this.gamePads.get(0)));
//            System.out.println("Is the same? pad1 == pad2: "+(this.gamePads.get(0) == this.gamePads.get(1)));
//        }
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
        for (Map.Entry<String, Integer> entry : this.heldDown.entrySet()) {
            if (this.heldDown.get(entry.getKey()).equals(Integer.valueOf(Constants.FRAMES_HELD_KEYS_UPDATE))) {
                this.pressed.add(entry.getKey());
                keysToRemove.add(entry.getKey());
            } else {
                this.heldDown.put(entry.getKey(), this.heldDown.get(entry.getKey()) + 1);
            }
        }

        for (String key : keysToRemove) {
            this.heldDown.remove(key);
        }

        this.released.clear();
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

//    public Map<Integer, Controller> getGamePads() {
//        return this.gamePads;
//    }

    public List<Controller> getGamePads() {
        return this.gamePads;
    }

    public Controller getGamePad(int id) {
        return this.gamePads.get(id);
    }

//    public int getGamePadID(Controller gamePad) {
//        int id = -1;
//        for (Map.Entry<Integer, Controller> entry : this.gamePads.entrySet()) {
//            if (entry.getValue() == gamePad) {
//                id = entry.getKey();
//            }
//        }
//        return id;
//    }

    public static synchronized ListenerController getInstance() {
        initialize();
        return instance;
    }

    public static synchronized void initialize() {
        if (instance == null) {
            instance = new ListenerController();
        }
    }
}
