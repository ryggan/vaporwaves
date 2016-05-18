package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.FileID;
import edu.chalmers.vaporwave.assetcontainer.Sprite;
import edu.chalmers.vaporwave.assetcontainer.SpriteID;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.TimerModel;
import edu.chalmers.vaporwave.model.game.GameCharacter;
import edu.chalmers.vaporwave.util.Constants;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class HUDView {

    private Point[] hudBoxPositions;

    private Group root;
    private Canvas hudCanvas;
    private GraphicsContext hudGC;

    Sprite timerMessage;
    Label timer;

    private Sprite hudBox;
    private Sprite healthbar;
    private Sprite statusbar;
    private Sprite plus;

    private Map<Integer, Integer> playerIDs;

    private Label[] playerNames;
    private Label[] playerScores;

    private double[] currentHealth;
    private int healthChange;

    public HUDView(Group root, Set<Player> players) {

        this.root = root;

        this.hudCanvas = new Canvas(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.root.getChildren().add(hudCanvas);
        this.hudGC = this.hudCanvas.getGraphicsContext2D();

        // TIMER
        this.timerMessage = Container.getSprite(SpriteID.HUD_TIMER_MESSAGE);
        this.timerMessage.setPosition(Math.round(Constants.WINDOW_WIDTH / 2.0 - this.timerMessage.getWidth() / 2.0), 10);
        this.timerMessage.render(this.hudGC, 0);

        this.timer = new Label();
        this.timer.setFont(Container.getFont(FileID.FONT_BAUHAUS_30));
        this.timer.setLayoutX(this.timerMessage.getPositionX() + 47);
        this.timer.setLayoutY(this.timerMessage.getPositionY() + 25);

        root.getChildren().add(timer);

        // HUD BOXES
        this.hudBoxPositions = new Point[] { new Point(20, 122), new Point(926, 122), new Point(20, 422), new Point(926, 422) };
        this.playerNames = new Label[4];
        this.playerScores = new Label[4];
        this.playerIDs = new HashMap<>();

        this.hudBox = Container.getSprite(SpriteID.HUD_BOX);
        this.healthbar = Container.getSprite(SpriteID.HUD_HEALTHBAR_FILLED);
        this.statusbar = Container.getSprite(SpriteID.HUD_STATUSBAR_FILLED);
        this.plus = Container.getSprite(SpriteID.HUD_STATUSBAR_PLUS);

        this.currentHealth = new double[] {0, 0, 0, 0};
        this.healthChange = 2;

        // Setting up individual elements for every active player
        int index = 0;
        for (Player player : players) {

            int id = player.getPlayerID();

            this.playerIDs.put(id, index);

            this.playerNames[index] = new Label();
            this.playerNames[index].setFont(Container.getFont(FileID.FONT_BAUHAUS_18));
            this.playerNames[index].setTextFill(Color.WHITE);
            this.playerNames[index].setLayoutX(this.hudBoxPositions[index].x + 6);
            this.playerNames[index].setLayoutY(this.hudBoxPositions[index].y + 3);
            String name = player.getName().toUpperCase(Locale.ENGLISH);
            if (name.length() > 12) {
                name = name.substring(0, 11) + "..";
            }
            this.playerNames[index].setText(name);
            this.root.getChildren().add(this.playerNames[index]);

            this.playerScores[index] = new Label();
            this.playerScores[index].setFont(Container.getFont(FileID.FONT_BAUHAUS_14));
            this.playerScores[index].setTextFill(Color.valueOf("fd2881"));
            this.playerScores[index].setLayoutX(this.hudBoxPositions[index].x + 8);
            this.playerScores[index].setLayoutY(this.hudBoxPositions[index].y + 26);
            this.playerScores[index].setMinWidth(Control.USE_COMPUTED_SIZE);
            this.playerScores[index].setMaxWidth(118);
            this.playerScores[index].setMinWidth(this.playerScores[index].getMaxWidth());
            this.playerScores[index].setAlignment(Pos.CENTER_RIGHT);
            this.playerScores[index].setText("0");

            this.root.getChildren().add(this.playerScores[index]);

            updateStats(player);

            index++;
        }

    }

    public void updateStats(Set<Player> players) {
        for (Player player : players) {
            updateStats(player);
        }
    }

    public void updateStats(Player player) {

        int index = this.playerIDs.get(player.getPlayerID());

        Point boxPosition = this.hudBoxPositions[index];
        GameCharacter character = player.getCharacter();

//        this.timerMessage.render(this.hudGC, 0);

        this.hudBox.setPosition(boxPosition.x, boxPosition.y);
        this.hudBox.render(this.hudGC, 0);

        this.playerScores[index].setText(""+player.getScore());

        double newHealth = character.getHealth();
        if (this.currentHealth[index] < newHealth - this.healthChange) {
            this.currentHealth[index] += this.healthChange;
        } else if (this.currentHealth[index] > newHealth + this.healthChange) {
            this.currentHealth[index] -= this.healthChange;
        } else {
            this.currentHealth[index] = newHealth;
        }

        this.healthbar.setPosition(boxPosition.x + 6, boxPosition.y + 50);
        this.healthbar.setOffsetDimension((this.healthbar.getWidth() - (this.currentHealth[index] / 100.0) * this.healthbar.getWidth()), 0);
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


    public void updateTimer(){
        timer.setText(TimerModel.getInstance().getTime());
    }
}