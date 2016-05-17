package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.ImageID;
import edu.chalmers.vaporwave.assetcontainer.Sprite;
import edu.chalmers.vaporwave.assetcontainer.SpriteID;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;

;import java.awt.*;
import java.util.Set;

public class HUDView {

//    private Label stats;

    private Point[] hudBoxPositions;

//    private ImageView healthBarEmpty;
//    private ImageView healthBarFilled;
//    private ImageView scoreBarFilled;
//    private ImageView scoreBarEmpty;

//    private ImageView emptyBar;
    private Group root;
    private Canvas hudCanvas;
    private GraphicsContext hudGC;

    private Sprite hudBox;
    private Sprite healthbar;
    private Sprite statusbar;
    private Sprite plus;

//    private PixelReader reader;
//    private WritableImage newImage;

//    private int[] newHealth;
    private double[] currentHealth;
    private int healthChange;

//    private Font bauhaus10;

    public HUDView(Group root, Set<Player> players) {

        this.root = root;

        this.hudCanvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.root.getChildren().add(hudCanvas);
        this.hudGC = this.hudCanvas.getGraphicsContext2D();

        this.hudBoxPositions = new Point[] { new Point(20, 122), new Point(926, 122), new Point(20, 422), new Point(926, 422) };

        this.hudBox = Container.getSprite(SpriteID.HUD_BOX);
        this.healthbar = Container.getSprite(SpriteID.HUD_HEALTHBAR_FILLED);
        this.statusbar = Container.getSprite(SpriteID.HUD_STATUSBAR_FILLED);
        this.plus = Container.getSprite(SpriteID.HUD_STATUSBAR_PLUS);

        this.currentHealth = new double[] {0, 0, 0, 0};
//        this.newHealth = new int[4];
        this.healthChange = 2;
//
        for (Player player : players) {
////            System.out.println("Setting up hud for player: "+player);
//            Point boxPosition = this.hudBoxPositions[player.getPlayerID()];
//
//            hudBox.setPosition(boxPosition.x, boxPosition.y);
//            hudBox.render(this.hudGC, 0);
            updateStats(player);
        }

//        WritableImage writableImage = new WritableImage(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
//        this.hudBackground = hudCanvas.snapshot(new SnapshotParameters(), writableImage);

//        this.healthBarEmpty = new ImageView(Container.getImage(ImageID.HUD_HEALTHBAR_EMPTY));
//        Image filled = Container.getImage(ImageID.HUD_HEALTHBAR_FILLED);
//        reader = filled.getPixelReader();
//        newImage = new WritableImage(reader, 0, 0, 300, 17);
//        this.healthBarFilled = new ImageView(newImage);

//        this.healthBarEmpty.setLayoutX(140);
//        this.healthBarEmpty.setLayoutY(-10);
//        this.healthBarFilled.setLayoutX(184);
//        this.healthBarFilled.setLayoutY(38);

//        root.getChildren().add(healthBarEmpty);
//        root.getChildren().add(healthBarFilled);

//        this.scoreBarFilled = new ImageView(Container.getImage(ImageID.HUD_SCOREBAR_FILLED));
//        this.scoreBarEmpty = new ImageView(Container.getImage(ImageID.HUD_SCOREBAR_EMPTY));

//        this.scoreBarEmpty.setLayoutX(594);
//        this.scoreBarEmpty.setLayoutY(34);
//        this.scoreBarFilled.setLayoutX(598);
//        this.scoreBarFilled.setLayoutY(38);

//        this.stats = new Label();
//
//        stats.getStylesheets().add("css/style.css");
//        stats.getStyleClass().add("healthPercentage");

//        hudCanvas = new Canvas(Constants.GAME_WIDTH + (Constants.DEFAULT_TILE_WIDTH * 4 * Constants.GAME_SCALE), ((Constants.GAME_HEIGHT + Constants.GRID_OFFSET_Y) * Constants.GAME_SCALE));
//        this.root.getChildren().add(hudCanvas);

//        this.root.getChildren().add(scoreBarEmpty);
//        this.root.getChildren().add(scoreBarFilled);

    }

    public void updateStats(Set<Player> players) {
        for (Player player : players) {
            updateStats(player);
        }
    }

    public void updateStats(Player player) {

        int id = player.getPlayerID();
        Point boxPosition = this.hudBoxPositions[id];
        GameCharacter character = player.getCharacter();

        this.hudBox.setPosition(boxPosition.x, boxPosition.y);
        this.hudBox.render(this.hudGC, 0);

        double newHealth = character.getHealth();
        if (this.currentHealth[id] < newHealth - this.healthChange) {
            this.currentHealth[id] += this.healthChange;
        } else if (this.currentHealth[id] > newHealth + this.healthChange) {
            this.currentHealth[id] -= this.healthChange;
        } else {
            this.currentHealth[id] = newHealth;
        }

        this.healthbar.setPosition(boxPosition.x + 6, boxPosition.y + 50);
        this.healthbar.setOffsetDimension((this.healthbar.getWidth() - (this.currentHealth[id] / 100.0) * this.healthbar.getWidth()), 0);
        this.healthbar.render(this.hudGC, 0);

        this.statusbar.setPosition(boxPosition.x + 40, boxPosition.y + 79);
        int bombs = Math.min(character.getCurrentBombCount(), 9);
        this.statusbar.setOffsetDimension((9 - bombs) * 10, 0);
        this.statusbar.render(this.hudGC, 0);

        if (character.getCurrentBombCount() > 9) {
            this.plus.setPosition(boxPosition.x + 120, boxPosition.y + 79);
            this.plus.render(this.hudGC, 0);
        }

        this.statusbar.setPosition(boxPosition.x + 40, boxPosition.y + 115);
        int range = Math.min(character.getBombRange(), 9);
        this.statusbar.setOffsetDimension((9 - range) * 10, 0);
        this.statusbar.render(this.hudGC, 0);

        if (character.getBombRange() > 9) {
            this.plus.setPosition(boxPosition.x + 120, boxPosition.y + 115);
            this.plus.render(this.hudGC, 0);
        }

        this.statusbar.setPosition(boxPosition.x + 40, boxPosition.y + 151);
        double gain = Constants.DEFAULT_POWERUP_SPEED_GAIN;
        int speed = (int)Math.min((character.getSpeed() - gain * 6) / gain, 9);
        this.statusbar.setOffsetDimension((9 - speed) * 10, 0);
        this.statusbar.render(this.hudGC, 0);

        if (character.getSpeed() > gain * (6 + 9 + 1)) {
            this.plus.setPosition(boxPosition.x + 120, boxPosition.y + 151);
            this.plus.render(this.hudGC, 0);
        }
    }

//    public void updateHealth(int playerID, double newHealth) {
//        if (this.currentHealth[playerID] < newHealth - this.healthChange) {
//            this.currentHealth[playerID] += this.healthChange;
//        } else if (this.currentHealth[playerID] > newHealth + this.healthChange) {
//            this.currentHealth[playerID] -= this.healthChange;
//        } else {
//            this.currentHealth[playerID] = newHealth;
//        }
//    }

//    public void updateStats(double health, double speed, int range, int bombCount) {
//        double newSpeed = Math.floor(speed * 10)/ 10;
//        int newHealth = (int) health;
//
//        stats.setText("Health: " + newHealth + "\nBombs: " + bombCount + "\nSpeed: " + newSpeed + "\nRange: " + range);
//
//        stats.setLayoutX(920);
//        stats.setLayoutY(152);
//
//        this.root.getChildren().remove(stats);
//        this.root.getChildren().add(stats);
//
//    }

//    public void updateHealthBar(int health) {
//
//        if (health * 3 == 0) {
////            setZeroHealthBar();
//
//        } else {
////            newImage = new WritableImage(reader, 0, 0, health * 3, 17);
////            ImageView healthBarFilledNew = new ImageView(newImage);
////            healthBarFilledNew.setLayoutX(184);
////            healthBarFilledNew.setLayoutY(38);
////            this.root.getChildren().remove(healthBarFilled);
////            this.root.getChildren().add(healthBarFilledNew);
////            this.healthBarFilled = healthBarFilledNew;
//        }
//    }

//    public void setZeroHealthBar(){
//        this.root.getChildren().remove(healthBarFilled);
//    }

}