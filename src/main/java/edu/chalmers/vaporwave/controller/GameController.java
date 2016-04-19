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

public class GameController {

    private ArenaView arenaView;
    private ArenaModel arenaModel;

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
        Tile testCharacter = new GameCharacter("Alyssa");

        try {
            arenaModel.setTile(testCharacter, 5, 5);
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Tile out of bounds!");
        }

        XMLReader testReader = new XMLReader("src/main/resources/configuration/gameCharacters.xml");
        testReader.read();

    }

    // This one is called every time the game-timer is updated
    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        // Game logic



        // Updating positions



        // Calls view to update graphics

        arenaView.updateView(arenaModel.getArena(), timeSinceStart, timeSinceLastCall);
    }
}
