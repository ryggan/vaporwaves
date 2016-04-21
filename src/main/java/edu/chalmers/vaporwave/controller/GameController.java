package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import edu.chalmers.vaporwave.model.gameObjects.Movable;
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


        // Starting new game
        this.arenaModel = newGame(new ArenaMap("default", new MapObject[0][0]));
        arenaView.initArena();


        // Setting up background



        // Setting up tiles



        // Trying out the XML loader
//        XMLReader reader = new XMLReader("src/main/resources/configuration/gameCharacters.xml");
//        NodeList nl = reader.read();
//        System.out.println(nl);
//        CharacterSpriteProperties[] sp = CharacterLoader.loadCharacters(reader.read());
//        System.out.println(sp[0].getName());

        // TEST TILES
        playerCharacter = new GameCharacter("Alyssa");

        try {
            arenaModel.addMovable(playerCharacter);
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Tile out of bounds!");
        }

        XMLReader testReader = new XMLReader("src/main/resources/configuration/gameCharacters.xml");
        testReader.read();

    }

    // This one is called every time the game-timer is updated
    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        // Game logic

        // Input handling:

//        if (input.size() > 0)
//            System.out.println(input);

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

//        ArrayList<Movable> arenaMovables = arenaModel.getArenaMovables();

        for (Movable movable : arenaModel.getArenaMovables()) {
            movable.updatePosition();
        }

//        for (int i = 0; i < arena.length; i++) {
//            for (int j = 0; j < arena[0].length; j++) {
//                for (Tile t : arena[i][j]) {
//                    if (t instanceof DynamicTile) {
//                        ((DynamicTile)t).updatePosition();
//                    }
//                }
//            }
//        }

        // Calls view to update graphics

        arenaView.updateView(arenaModel.getArenaMovables(), arenaModel.getArenaTiles(), timeSinceStart, timeSinceLastCall);
    }


    public ArenaModel newGame(ArenaMap arenaMap) {

        // Here goes all code for setting up the environment for a new game

        return new ArenaModel(arenaMap);
    }
}
