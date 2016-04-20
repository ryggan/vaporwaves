package edu.chalmers.vaporwave.controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

/**
 * Created by boob on 2016-04-15.
 */
public class ListenerController {

//    private MainController mc;
    private ArrayList<String> input = new ArrayList<String>();

    public ListenerController(Scene scene) {
//        public ListenerController(Scene scene, MainController mc) {
//        this.mc = mc;

//        ArrayList<String> input = new ArrayList<String>();

        scene.setOnKeyPressed(
            new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    if (!input.contains(code)) {
                        input.add(code);
//                        System.out.println("Key pressed: "+code);
                    }
                }
            });

        scene.setOnKeyReleased(
            new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    input.remove(code);
//                    System.out.println("Key released: "+code);
                }
            });
    }

    public ArrayList<String> getInput() {
        ArrayList<String> inputReturn = new ArrayList<String>();
        for (String s: this.input) {
            inputReturn.add(s);
        }
//        input = new ArrayList<String>();
        return inputReturn;
    }
}
