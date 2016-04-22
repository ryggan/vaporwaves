package edu.chalmers.vaporwave.controller;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.vaporwave.event.GameEventBus;
import edu.chalmers.vaporwave.event.PlaceBombEvent;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.gameObjects.Bomb;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import edu.chalmers.vaporwave.model.gameObjects.Movable;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MapFileReader;
import edu.chalmers.vaporwave.util.MapObject;
import edu.chalmers.vaporwave.util.XMLReader;
import edu.chalmers.vaporwave.view.ArenaView;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

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


        GameEventBus.getInstance().register(this);


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

        List<String> input = ListenerController.getInstance().getInput();


        List<String> pressed = ListenerController.getInstance().getPressed();

        for (int i = 0; i < input.size(); i++) {
            String key = input.get(i);
            switch (key) {
                case "UP":
                case "LEFT":
                case "DOWN":
                case "RIGHT":
                    playerCharacter.move(key);
                    break;
                case "ENTER":
                    playerCharacter.spawn();
                    break;
            }
        }

        for (int i = 0; i < pressed.size(); i++) {
            String key = pressed.get(i);
            switch (key) {
                case "SPACE":
                    playerCharacter.placeBomb();
                    break;
            }
        }



        // Updating positions

        for (Movable movable : arenaModel.getArenaMovables()) {
            movable.updatePosition();
        }

        // Calls view to update graphics

        arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), timeSinceStart, timeSinceLastCall);
    }

    @Subscribe
    public void bombPlaced(PlaceBombEvent placeBombEvent) {
        arenaModel.setTile(new Bomb(this.playerCharacter, 3, 1000), placeBombEvent.getGridPosition());
    }


    public ArenaModel newGame(ArenaMap arenaMap) {

        // Here goes all code for setting up the environment for a new game

        return new ArenaModel(arenaMap);
    }
}
