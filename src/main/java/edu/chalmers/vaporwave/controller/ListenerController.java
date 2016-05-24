package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.GamePadDisconnectedEvent;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import net.java.games.input.*;

import java.lang.reflect.Constructor;
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

    // These is used both by above listeners and by gampad-input-update below
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

    // This method updates the list of gamepads connected to the game
    // - OBS!! Even if only ONE of several gamepads has changed (from connected to not, e.g); ALL gamepads in the
    //   updated list will be different and therefor all Player gamepads needs to be re-maped (see MenuController)
    public void updateGamePads() {

        this.gamePads.clear();
        this.gamePadInputs.clear();
        this.gamePadPressed.clear();
        this.gamePadReleased.clear();

        Controller[] controllers = createDefaultEnvironment().getControllers();

        if (controllers != null) {
            for (int i = 0; i < controllers.length; i++) {
                if (controllers[i].getType() == Controller.Type.GAMEPAD || controllers[i].getType() == Controller.Type.STICK) {
                    this.gamePads.add(controllers[i]);
                    List<String> inputList = new ArrayList<>();
                    this.gamePadInputs.put(controllers[i], inputList);
                    List<String> pressedList = new ArrayList<>();
                    this.gamePadPressed.put(controllers[i], pressedList);
                    List<String> releasedList = new ArrayList<>();
                    this.gamePadReleased.put(controllers[i], releasedList);
                } else {
                    System.out.println("Discarded controller: "+controllers[i].getType());
                }
            }
        }

        System.out.println("Updated gamepads, active ones: "+this.gamePads);
    }

    // Method that goes around the fact that a give ControllerEnvironment is a singleton and therefor
    // cannot be re-created, which is necessary when updating the list of gamepads
    private static ControllerEnvironment createDefaultEnvironment() {
        try {
            // Find constructor (class is package private, so we can't access it directly)
            Constructor<ControllerEnvironment> constructor = (Constructor<ControllerEnvironment>)
                    Class.forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructors()[0];
            // Constructor is package private, so we have to deactivate access control checks
            constructor.setAccessible(true);
            // Create object with default constructor
            return constructor.newInstance();

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return null;
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

    // Keyboard-specific input getters
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

    // Gets raw input info from given gamepad and returns it
    private List<String[]> getGamePadInputRaw(Controller gamePad) {
        List<String[]> gamePadInput = new ArrayList<>();
        boolean poll = gamePad.poll();

        if (poll) {
            EventQueue queue = gamePad.getEventQueue();
            Event event = new Event();

            while (queue.getNextEvent(event)) {
                String[] button = new String[2];
                Component comp = event.getComponent();
                button[0] = comp.getName();
                float value = event.getValue();
                if (comp.isAnalog()) {
                    button[1] = "" + value;
                } else {
                    if (value == 1.0f) {
                        button[1] = "On";
                    } else {
                        button[1] = "Off";
                    }
                }
                gamePadInput.add(button);
            }
        }

        return gamePadInput;
    }

    // Polls every gamepad to check if there still is one there, and removes it from the list, if it's not.
    // Returns list of removed ones, if ever needed to adjust other lists.
    private List<Controller> pollAndRemove() {
        List<Controller> removedControllers = new ArrayList<>();
        Iterator<Controller> iterator = this.gamePads.iterator();
        while(iterator.hasNext()) {
            Controller controller = iterator.next();
            if (!controller.poll()) {
                removedControllers.add(controller);
                this.gamePadInputs.remove(controller);
                this.gamePadPressed.remove(controller);
                this.gamePadReleased.remove(controller);
                GameEventBus.getInstance().post(new GamePadDisconnectedEvent(controller));
                iterator.remove();
            }
        }
        return removedControllers;
    }

    // Must run every iteration so that saved input data is up to date.
    // Translates raw input data to key String values, which is interpretable by the rest of the game.
    public void updateGamePadInputs() {

        pollAndRemove();

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

            // Since x and y values are in doubles, i.e the left directional stick has a scaled off/on-state,
            // it has to be manually determined if it points left/right/up/down etc.
            double borderValue = 0.4;

            if (xChanged) {
                if (x > borderValue) {
                    onKeyPressed(input, pressed, "LS_RIGHT");
                    onKeyReleased(input, released, "LS_LEFT");
                } else if (x < -borderValue) {
                    onKeyReleased(input, released, "LS_RIGHT");
                    onKeyPressed(input, pressed, "LS_LEFT");
                } else {
                    onKeyReleased(input, released, "LS_RIGHT");
                    onKeyReleased(input, released, "LS_LEFT");
                }
            }

            if (yChanged) {
                if (y > borderValue) {
                    onKeyPressed(input, pressed, "LS_DOWN");
                    onKeyReleased(input, released, "LS_UP");
                } else if (y < -borderValue) {
                    onKeyReleased(input, released, "LS_DOWN");
                    onKeyPressed(input, pressed, "LS_UP");
                } else {
                    onKeyReleased(input, released, "LS_DOWN");
                    onKeyReleased(input, released, "LS_UP");
                }
            }
        }
    }

    // All press-down-buttons need to go through this step
    private void gamePadOnOffButton(String onOff, String key, List<String> input, List<String> pressed, List<String> released) {
        if (onOff.equals("On")) {
            onKeyPressed(input, pressed, key);
        } else {
            onKeyReleased(input, released, key);
        }
    }

    // Gamepad-specific input-getters
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

    // Gamepad AND keyboard (at the same time) input getters
    // Should generally allways be these that is used
    public List<String> getAllInput(Player player) {
        List<String> allInput = new ArrayList<>();
        allInput.addAll(getInput());
        if (player.getGamePad() != null) {
            allInput.addAll(getGamePadInput(player.getGamePad()));
        }
        return allInput;
    }

    public List<String> getAllPressed(Player player) {
        List<String> allPressed = new ArrayList<>();
        allPressed.addAll(getPressed());
        if (player.getGamePad() != null) {
            allPressed.addAll(getGamePadPressed(player.getGamePad()));
        }
        return allPressed;
    }

    public List<String> getAllReleased(Player player) {
        List<String> allReleased = new ArrayList<>();
        allReleased.addAll(getReleased());
        if (player.getGamePad() != null) {
            allReleased.addAll(getGamePadReleased(player.getGamePad()));
        }
        return allReleased;
    }

    // Classic static singleton methods
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
