package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.util.Constants;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import net.java.games.input.*;

import java.util.*;

public class ListenerController {

    private static ListenerController instance;

    private List<String> input;
    private List<String> pressed;
    private List<String> released;

    private Map<String, Integer> heldDown;

//    private Map<Integer, Controller> gamePads;
    private List<Controller> gamePads;
    private Map<Controller, List<String>> gamePadInputs;
    private Map<Controller, List<String>> gamePadPressed;
    private Map<Controller, List<String>> gamePadReleased;

    private ListenerController() {
        input = new ArrayList<>();
        pressed = new ArrayList<>();
        released = new ArrayList<>();

        heldDown = new HashMap<>();
//        gamePads = new HashMap<>();
        gamePads = new ArrayList<>();
        gamePadInputs = new HashMap<>();
        gamePadPressed = new HashMap<>();
        gamePadReleased = new HashMap<>();
    }

    public void initiateListener(Scene scene) {

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        onKeyPressed(input, pressed, code);
//                        if (!input.contains(code)) {
//                            input.add(code);
//                            pressed.add(code);
//                        }


                    }
                });

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        onKeyReleased(input, released, code);
//                        input.remove(code);
//                        released.add(code);
                    }
                });

        updateGamePads();
    }

    private void onKeyPressed(List<String> input, List<String> pressed, String code) {
        if (!input.contains(code)) {
            input.add(code);
            pressed.add(code);
        }
    }

    private void onKeyReleased(List<String> input, List<String> released, String code) {
        input.remove(code);
        released.add(code);
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
                List<String> inputList = new ArrayList<>();
                this.gamePadInputs.put(controllers[i], inputList);
                List<String> pressedList = new ArrayList<>();
                this.gamePadPressed.put(controllers[i], pressedList);
                List<String> releasedList = new ArrayList<>();
                this.gamePadReleased.put(controllers[i], releasedList);
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

//    public Controller getGamePad(int id) {
//        return this.gamePads.get(id);
//    }

//    public int getGamePadID(Controller gamePad) {
//        int id = -1;
//        for (Map.Entry<Integer, Controller> entry : this.gamePads.entrySet()) {
//            if (entry.getValue() == gamePad) {
//                id = entry.getKey();
//            }
//        }
//        return id;
//    }

    private List<String[]> getGamePadInputRaw(Controller gamePad) {
        gamePad.poll();
        EventQueue queue = gamePad.getEventQueue();
        Event event = new Event();

        List<String[]> gamePadInput = new ArrayList<>();

        while(queue.getNextEvent(event)) {
            StringBuffer buffer = new StringBuffer(gamePad.getName());
            String[] button = new String[2];
            buffer.append(" at ");
            buffer.append(event.getNanos()).append(", ");
            Component comp = event.getComponent();
            buffer.append(comp.getName()).append(" changed to ");
            button[0] = comp.getName();
            float value = event.getValue();
            if(comp.isAnalog()) {
                buffer.append(value);
                button[1] = ""+value;
            } else {
                if(value==1.0f) {
                    buffer.append("On");
                    button[1] = "On";
                } else {
                    buffer.append("Off");
                    button[1] = "Off";
                }
            }
//            System.out.println(buffer.toString());
            gamePadInput.add(button);
        }

        return gamePadInput;
    }

    public void updateGamePadInputs() {
        for (Controller gamePad : this.gamePads) {
            List<String> input = this.gamePadInputs.get(gamePad);
            List<String> pressed = this.gamePadPressed.get(gamePad);
            List<String> released = this.gamePadReleased.get(gamePad);
            List<String[]> inputRaw = getGamePadInputRaw(gamePad);

            double x = 0;
            double y = 0;

            for (String[] button : inputRaw) {
                if (button[1].equals("On") || button[1].equals("Off")) {
                    if (button[0].equals("11")) {
                        if (button[1].equals("On")) {
                            onKeyPressed(input, pressed, "UP");
                        } else {
                            onKeyReleased(input, released, "UP");
                        }
                    } else if (button[0].equals("12")) {
                        if (button[1].equals("On")) {
                            onKeyPressed(input, pressed, "DOWN");
                        } else {
                            onKeyReleased(input, released, "DOWN");
                        }
                    } else if (button[0].equals("13")) {
                        if (button[1].equals("On")) {
                            onKeyPressed(input, pressed, "LEFT");
                        } else {
                            onKeyReleased(input, released, "LEFT");
                        }
                    } else if (button[0].equals("14")) {
                        if (button[1].equals("On")) {
                            onKeyPressed(input, pressed, "RIGHT");
                        } else {
                            onKeyReleased(input, released, "RIGHT");
                        }
                    }
                } else {
                    if (button[0].equals("x")) {
                        x = Double.valueOf(button[1]);
                    } else if (button[0].equals("y")) {
                        y = Double.valueOf(button[1]);
                    }
                }
            }

            if (Math.abs(x) > 0.5 || Math.abs(y) > 0.5) {
                if (Math.abs(x) > Math.abs(y)) {
                    if (x > 0.5) {
                        onKeyPressed(input, pressed, "LS_RIGHT");
                        onKeyReleased(input, released, "LS_LEFT");
                    } else if (x < -0.5){
                        onKeyPressed(input, pressed, "LS_LEFT");
                        onKeyReleased(input, released, "LS_RIGHT");
                    }
                } else {
                    if (y > 0.5) {
                        onKeyPressed(input, pressed, "LS_DOWN");
                        onKeyReleased(input, released, "LS_UP");
                    } else if (y < -0.5){
                        onKeyPressed(input, pressed, "LS_UP");
                        onKeyReleased(input, released, "LS_DOWN");
                    }
                }
            }
            if (Math.abs(x) <= 0.5) {
                onKeyReleased(input, released, "LS_RIGHT");
                onKeyReleased(input, released, "LS_LEFT");
            }
            if (Math.abs(y) <= 0.5) {
                onKeyReleased(input, released, "LS_UP");
                onKeyReleased(input, released, "LS_DOWN");
            }
        }
    }

//    public String getGamePadWalkDirection(Controller gamePad) {
//        String walkDirection = "";
//        List<String[]> input = getGamePadInput(gamePad);
//        List<String> quedButtons = new ArrayList<>();
//        double x = 0;
//        double y = 0;
//
//        for (String[] button : input) {
//            if (button[1].equals("On")) {
//                if (button[0].equals("11")) {
//                    quedButtons.add("UP");
//                } else if (button[0].equals("12")) {
//                    quedButtons.add("DOWN");
//                } else if (button[0].equals("13")) {
//                    quedButtons.add("LEFT");
//                } else if (button[0].equals("14")) {
//                    quedButtons.add("RIGHT");
//                }
//            } else {
//                if (button[0].equals("x")) {
//                    x = Double.valueOf(button[1]);
//                } else if (button[0].equals("y")) {
//                    y = Double.valueOf(button[1]);
//                }
//            }
//        }
//
//        if (Math.abs(x) > 0.5 || Math.abs(y) > 0.5) {
//            if (Math.abs(x) > Math.abs(y)) {
//                if (x > 0) {
//                    quedButtons.add("RIGHT");
//                }
//            } else {
//
//            }
//        }
//
//        if (quedButtons.size() > 0) {
//            System.out.println("All buttons pressed: " + quedButtons);
//        }
//        return walkDirection;
//    }



    public List<String> getGamePadInput(Controller gamePad) {
        List<String> inputReturn = new ArrayList<>();
        inputReturn.addAll(this.gamePadInputs.get(gamePad));
        return inputReturn;
    }

    public List<String> getGamePadPressed(Controller gamePad) {
        List<String> pressedReturn = new ArrayList<>();
        pressedReturn.addAll(this.gamePadPressed.get(gamePad));
        return pressedReturn;
    }

    public List<String> getGamePadReleased(Controller gamePad) {
        List<String> releasedReturn = new ArrayList<>();
        releasedReturn.addAll(this.gamePadReleased.get(gamePad));
        return releasedReturn;
    }

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
