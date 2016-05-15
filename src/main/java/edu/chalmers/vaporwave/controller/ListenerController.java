package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.model.Player;
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

    private List<Controller> gamePads;
    private Map<Controller, List<String>> gamePadInputs;
    private Map<Controller, List<String>> gamePadPressed;
    private Map<Controller, List<String>> gamePadReleased;

    private ListenerController() {
        input = new ArrayList<>();
        pressed = new ArrayList<>();
        released = new ArrayList<>();

        heldDown = new HashMap<>();

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
                    }
                });

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        onKeyReleased(input, released, code);
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
        inputReturn.addAll(this.input);
        return inputReturn;
    }

    public List<String> getPressed() {
        List<String> pressedReturn = new ArrayList<>();
        pressedReturn.addAll(this.pressed);
        return pressedReturn;
    }

    public List<String> getReleased() {
        List<String> releasedReturn = new ArrayList<>();
        releasedReturn.addAll(this.released);
        return releasedReturn;
    }

    public List<Controller> getGamePads() {
        return this.gamePads;
    }

    private List<String[]> getGamePadInputRaw(Controller gamePad) {
        gamePad.poll();
        EventQueue queue = gamePad.getEventQueue();
        Event event = new Event();

        List<String[]> gamePadInput = new ArrayList<>();

        while(queue.getNextEvent(event)) {
            String[] button = new String[2];
            Component comp = event.getComponent();
            button[0] = comp.getName();
            float value = event.getValue();
            if(comp.isAnalog()) {
                button[1] = ""+value;
            } else {
                if(value==1.0f) {
                    button[1] = "On";
                } else {
                    button[1] = "Off";
                }
            }
            gamePadInput.add(button);
//            System.out.println("Button; "+button[0]);
        }

        return gamePadInput;
    }

    public void updateGamePadInputs() {
        for (Controller gamePad : this.gamePads) {

            List<String> input = this.gamePadInputs.get(gamePad);
            List<String> pressed = this.gamePadPressed.get(gamePad);
            List<String> released = this.gamePadReleased.get(gamePad);
            List<String[]> inputRaw = getGamePadInputRaw(gamePad);

            pressed.clear();
            released.clear();

            double x = 100;
            double y = 100;
            boolean xChanged = false;
            boolean yChanged = false;

            for (String[] button : inputRaw) {
                if (button[1].equals("On") || button[1].equals("Off")) {
                    if (button[0].equals("11")) {
                        gamePadOnOffButton(button[1], "DPAD_UP", input, pressed, released);
                    } else if (button[0].equals("12")) {
                        gamePadOnOffButton(button[1], "DPAD_DOWN", input, pressed, released);
                    } else if (button[0].equals("13")) {
                        gamePadOnOffButton(button[1], "DPAD_LEFT", input, pressed, released);
                    } else if (button[0].equals("14")) {
                        gamePadOnOffButton(button[1], "DPAD_RIGHT", input, pressed, released);

                    } else if (button[0].equals("0")) {
                        gamePadOnOffButton(button[1], "BTN_A", input, pressed, released);
                    } else if (button[0].equals("1")) {
                        gamePadOnOffButton(button[1], "BTN_B", input, pressed, released);
                    }
                } else {
                    if (button[0].equals("x")) {
                        x = Double.valueOf(button[1]);
                        xChanged = true;
                    } else if (button[0].equals("y")) {
                        y = Double.valueOf(button[1]);
                        yChanged = true;
                    }
                }
            }

            if (xChanged) {
                if (x > 0.5) {
                    onKeyPressed(input, pressed, "LS_RIGHT");
                    onKeyReleased(input, released, "LS_LEFT");
                } else if (x < -0.5) {
                    onKeyReleased(input, released, "LS_RIGHT");
                    onKeyPressed(input, pressed, "LS_LEFT");
                } else {
                    onKeyReleased(input, released, "LS_RIGHT");
                    onKeyReleased(input, released, "LS_LEFT");
                }
            }

            if (yChanged) {
                if (y > 0.5) {
                    onKeyPressed(input, pressed, "LS_DOWN");
                    onKeyReleased(input, released, "LS_UP");
                } else if (y < -0.5) {
                    onKeyReleased(input, released, "LS_DOWN");
                    onKeyPressed(input, pressed, "LS_UP");
                } else {
                    onKeyReleased(input, released, "LS_DOWN");
                    onKeyReleased(input, released, "LS_UP");
                }
            }
        }
    }

    private void gamePadOnOffButton(String onOff, String key, List<String> input, List<String> pressed, List<String> released) {
        if (onOff.equals("On")) {
            onKeyPressed(input, pressed, key);
//            System.out.println("Pressed: "+pressed);
        } else {
            onKeyReleased(input, released, key);
        }
    }

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

    public List<String> getAllInput(Player player) {
        List<String> allInput = new ArrayList<>();
        allInput.addAll(ListenerController.getInstance().getInput());
        if (player.getGamePad() != null) {
            allInput.addAll(ListenerController.getInstance().getGamePadInput(player.getGamePad()));
        }
        return allInput;
    }

    public List<String> getAllPressed(Player player) {
        List<String> allPressed = new ArrayList<>();
        allPressed.addAll(ListenerController.getInstance().getPressed());
        if (player.getGamePad() != null) {
            allPressed.addAll(ListenerController.getInstance().getGamePadPressed(player.getGamePad()));
        }
        return allPressed;
    }

    public List<String> getAllReleased(Player player) {
        List<String> allReleased = new ArrayList<>();
        allReleased.addAll(ListenerController.getInstance().getReleased());
        if (player.getGamePad() != null) {
            allReleased.addAll(ListenerController.getInstance().getGamePadReleased(player.getGamePad()));
        }
        return allReleased;
    }
}
