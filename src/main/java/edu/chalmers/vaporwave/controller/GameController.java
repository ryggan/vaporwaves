package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.SpriteProperties;
import edu.chalmers.vaporwave.model.gameObjects.Tile;
import edu.chalmers.vaporwave.util.CharacterLoader;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.XMLReader;
import edu.chalmers.vaporwave.view.AnimatedSprite;
import edu.chalmers.vaporwave.view.ArenaView;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.Group;
import javafx.scene.image.Image;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.util.ArrayList;

public class GameController {

    private ArenaView arenaView;
    private ArenaModel arenaModel;

    private GameCharacter playerCharacter;

    public GameController(Group root) {

        // Initiates view

        arenaView = new ArenaView(root);
        arenaModel = new ArenaModel(Constants.DEFAULT_GRID_WIDTH, Constants.DEFAULT_GRID_HEIGHT);

        // Setting up background



        // Setting up tiles



        // Trying out the XML loader
        XMLReader reader = new XMLReader("src/main/resources/configuration/gameCharacters.xml");
        NodeList nl = reader.read();
        System.out.println(nl);
        SpriteProperties[] sp = CharacterLoader.loadCharacters(reader.read());
        System.out.println(sp[0].getName());



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

        if (input.size() > 0)
            System.out.println(input);

        for (int i = 0; i < input.size(); i++) {
            String key = input.get(i);
            if (key.equals("UP")) {
                playerCharacter.moveUp();
            } else if (key.equals("LEFT")) {
                playerCharacter.moveLeft();
            } else if (key.equals("DOWN")) {
                playerCharacter.moveDown();
            } else if (key.equals("RIGHT")) {
                playerCharacter.moveRight();
            }
        }

        // Updating positions



        // Calls view to update graphics

        arenaView.updateView(arenaModel.getArena(), timeSinceStart, timeSinceLastCall);
    }
}
