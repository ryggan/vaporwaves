package edu.chalmers.vaporwave.model.gameObjects;

import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.SpriteProperties;
import edu.chalmers.vaporwave.util.CharacterLoader;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.XMLReader;
import edu.chalmers.vaporwave.view.AnimatedSprite;
import edu.chalmers.vaporwave.view.Sprite;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import org.w3c.dom.NodeList;

import java.awt.*;

public class GameCharacter extends DynamicTile {
    private Point currentGridPosition;
    private Point previousGridPosition;
    private Player player;
    private int playerId;

    private Sprite spawnSprite;
    private Sprite[] idleSprite = new Sprite[4];
    private Sprite[] walkSprite = new Sprite[4];
    private Sprite[] flinchSprite = new Sprite[4];
    private Sprite deathSprite;

    public GameCharacter(String name) {

        // Trying out the XML loader
        XMLReader reader = new XMLReader("src/main/resources/configuration/gameCharacters.xml");
        NodeList nl = reader.read();
        System.out.println(nl);
        SpriteProperties[] sp = CharacterLoader.loadCharacter(reader.read(), name);
//        System.out.println(sp[0].getState());


        Image spriteSheet0 = new Image("images/spritesheet-alyssa-respawn-48x128.png");
        Image spriteSheet1 = new Image("images/spritesheet-alyssa-walkidleflinch-48x48.png");
        Image spriteSheet2 = new Image("images/spritesheet-alyssa-death-56x56.png");

        spawnSprite = new AnimatedSprite(spriteSheet0, new Dimension(48, 128), 27, 0.1, new int[] {0, 0});
        spawnSprite.setScale(Constants.GAME_SCALE);
        for (int i = 0; i < 4; i++) {
            idleSprite[i] = new AnimatedSprite(spriteSheet1, new Dimension(48, 48), 1, 0.1, new int[] {i, 4});
            idleSprite[i].setScale(Constants.GAME_SCALE);

            walkSprite[i] = new AnimatedSprite(spriteSheet1, new Dimension(48, 48), 8, 0.1, new int[] {0, i});
            walkSprite[i].setScale(Constants.GAME_SCALE);

            flinchSprite[i] = new AnimatedSprite(spriteSheet1, new Dimension(48, 48), 1, 0.1, new int[] {4+i, 4});
            flinchSprite[i].setScale(Constants.GAME_SCALE);
        }
        deathSprite = new AnimatedSprite(spriteSheet2, new Dimension(56, 56), 28, 0.1, new int[] {0, 0});
        deathSprite.setScale(Constants.GAME_SCALE);

        setGridPosition(new Point(0,0));
        moveRight();

    }


    // Should be rewritten
    public GameCharacter(Point position, Player player) {
        this.currentGridPosition = position;
        this.player = player;
        this.playerId = player.getId();
    }

    // Change this to use enum instead
    public void move(String key) {
        if (key.equals("UP")) {
            moveUp();
        } else if (key.equals("LEFT")) {
            moveLeft();
        } else if (key.equals("DOWN")) {
            moveDown();
        } else if (key.equals("RIGHT")) {
            moveRight();
        }
    }

    public void moveUp() {
        previousGridPosition = currentGridPosition;
        currentGridPosition.setLocation(previousGridPosition.getX(), previousGridPosition.getY() + 1);
        setSprite(walkSprite[3]);
    }
    public void moveDown() {
        previousGridPosition = currentGridPosition;
        currentGridPosition.setLocation(previousGridPosition.getX(), previousGridPosition.getY() - 1);
        setSprite(walkSprite[0]);
    }
    public void moveLeft() {
        previousGridPosition = currentGridPosition;
        currentGridPosition.setLocation(previousGridPosition.getX() - 1, previousGridPosition.getY());
        setSprite(walkSprite[1]);
    }
    public void moveRight() {
        previousGridPosition = currentGridPosition;
        currentGridPosition.setLocation(previousGridPosition.getX() + 1, previousGridPosition.getY());
        setSprite(walkSprite[2]);
    }

    public void setGridPosition(Point gridPosition) {
        this.currentGridPosition = gridPosition;
        this.previousGridPosition = gridPosition;
    }

    public Point getGridPosition() {
        return currentGridPosition;
    }
    public double getXPosition() {
        return currentGridPosition.getX();
    }
    public double getYPosition() {
        return currentGridPosition.getY();
    }
}
