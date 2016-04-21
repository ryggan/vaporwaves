package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import edu.chalmers.vaporwave.model.gameObjects.Movable;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MapFileReader;
import edu.chalmers.vaporwave.util.MapObject;
import edu.chalmers.vaporwave.util.XMLReader;
import edu.chalmers.vaporwave.view.ArenaView;
import javafx.scene.Group;

import java.util.ArrayList;

public class GameController {

    private ArenaView arenaView;
    private ArenaModel arenaModel;

    private GameCharacter playerCharacter;

    public GameController(Group root) {

        // Initiates view

        this.arenaView = new ArenaView(root);

        ArenaMap arenaMap = new ArenaMap("default", (new MapFileReader(Constants.DEFAULT_MAP_FILE)).getMapObjects());

        // Starting new game
        this.arenaModel = newGame(arenaMap);

        arenaView.initArena();


        // Setting up background



        // Setting up tiles



        // TEST TILES
        playerCharacter = new GameCharacter("ALYSSA");

        try {
            arenaModel.addMovable(playerCharacter);
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Tile out of bounds!");
        }



    }

    // This one is called every time the game-timer is updated
    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        // Game logic

        // Input handling:

        ArrayList<String> input = ListenerController.getInstance().getInput();

        for (int i = 0; i < input.size(); i++) {
            String key = input.get(i);
            if (key.equals("UP") || key.equals("LEFT") || key.equals("DOWN") || key.equals("RIGHT")) {
                playerCharacter.move(key);
            } else if (key.equals("ENTER")) {
                playerCharacter.spawn();
            }
        }

        // Updating positions

        for (Movable movable : arenaModel.getArenaMovables()) {
            movable.updatePosition();
        }

        // Calls view to update graphics

        arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), timeSinceStart, timeSinceLastCall);
    }


    public ArenaModel newGame(ArenaMap arenaMap) {

        // Here goes all code for setting up the environment for a new game

        return new ArenaModel(arenaMap);
    }
}
