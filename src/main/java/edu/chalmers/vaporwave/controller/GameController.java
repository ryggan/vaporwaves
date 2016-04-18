package edu.chalmers.vaporwave.controller;

import edu.chalmers.vaporwave.model.ArenaModel;
import edu.chalmers.vaporwave.model.gameObjects.Tile;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.view.ArenaView;
import edu.chalmers.vaporwave.model.gameObjects.GameCharacter;
import javafx.scene.Group;

import java.awt.*;

public class GameController {

    private ArenaView arenaView;
    private ArenaModel arenaModel;

    public GameController(Group root) {

        // Initiates view

        arenaView = new ArenaView(root);
        arenaModel = new ArenaModel(Constants.DEFAULT_GRID_WIDTH, Constants.DEFAULT_GRID_HEIGHT);

        Tile testCharacter = new GameCharacter(new Point(50, 50));
        try {
            arenaModel.setTile(testCharacter, 5, 5);
        } catch(ArrayIndexOutOfBoundsException e) {

        }

    }

    // This one is called every time the game-timer is updated
    public void timerUpdate(double timeSinceStart, double timeSinceLastCall) {

        // Calls view to update graphics
        arenaView.updateView(timeSinceStart, timeSinceLastCall);

//        System.out.println(timeSinceLastCall);

        // aaaall the game logicz
    }
}
