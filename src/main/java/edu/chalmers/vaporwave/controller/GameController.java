package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.gameObjects.DynamicTile;
import edu.chalmers.vaporwave.model.gameObjects.Tile;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MapObject;
import edu.chalmers.vaporwave.util.XMLReader;
import edu.chalmers.vaporwave.view.ArenaView;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
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

        //comment this out for only menu
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
            arenaModel.setTile(playerCharacter, 5, 5);
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Tile out of bounds!");
        }

        XMLReader testReader = new XMLReader("src/main/resources/configuration/gameCharacters.xml");
        testReader.read();

    }

    // This one is called every time the game-timer is updated
    public void timerUpdate(double timeSinceStart, double timeSinceLastCall, ArrayList<String> input) {

        // Game logic

        // Input handling:

//        if (input.size() > 0)
//            System.out.println(input);

        for (int i = 0; i < input.size(); i++) {
            String key = input.get(i);
            if (key.equals("UP") || key.equals("LEFT") || key.equals("DOWN") || key.equals("RIGHT"))
                playerCharacter.move(key);
        }

        // Updating positions

        ArrayList<Tile>[][] arena = arenaModel.getArena();

        for (int i = 0; i < arena.length; i++) {
            for (int j = 0; j < arena[0].length; j++) {
                for (Tile t : arena[i][j]) {
                    if (t instanceof DynamicTile) {
                        ((DynamicTile)t).updatePosition();
                    }
                }
            }
        }

        // Calls view to update graphics

        arenaView.updateView(arenaModel.getArena(), timeSinceStart, timeSinceLastCall);
    }


    public ArenaModel newGame(ArenaMap arenaMap) {

        // Here goes all code for setting up the environment for a new game

        return new ArenaModel(arenaMap);
    }
}
