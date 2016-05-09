package edu.chalmers.vaporwave.view;
;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.ImageID;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;
import java.awt.*;

public class HUDView {

    private int width;
    private int height;
    private int firstFrameX;
    private Point position;
    private Label stats;
    private Label healthPercentage;


    private ImageView healthBarEmpty;
    private ImageView healthBarFilled;
    private ImageView scoreBarFilled;
    private ImageView scoreBarEmpty;


    private ImageView emptyBar;
    private Group root;
    private Canvas hudCanvas;

    private PixelReader reader;
    private WritableImage newImage;

    private Font bauhaus10;

    public HUDView(Group root) {

        this.root = root;
        this.healthBarEmpty = new ImageView(ImageContainer.getInstance().getImage(ImageID.HUD_HEALTHBAR_EMPTY));
        this.scoreBarFilled = new ImageView(ImageContainer.getInstance().getImage(ImageID.HUD_SCOREBAR_FILLED));
        this.scoreBarEmpty = new ImageView(ImageContainer.getInstance().getImage(ImageID.HUD_SCOREBAR_EMPTY));

        Image filled = ImageContainer.getInstance().getImage(ImageID.HUD_HEALTHBAR_FILLED);
        reader = filled.getPixelReader();
        newImage = new WritableImage(reader, 0, 0, 300, 17);
        this.healthBarFilled = new ImageView(newImage);

        this.healthBarEmpty.setLayoutX(140);
        this.healthBarEmpty.setLayoutY(-10);
        this.healthBarFilled.setLayoutX(184);
        this.healthBarFilled.setLayoutY(38);
        this.scoreBarEmpty.setLayoutX(594);
        this.scoreBarEmpty.setLayoutY(34);
        this.scoreBarFilled.setLayoutX(598);
        this.scoreBarFilled.setLayoutY(38);

        this.stats = new Label();

        stats.getStylesheets().add("css/style.css");
        stats.getStyleClass().add("healthPercentage");

        hudCanvas = new Canvas(Constants.GAME_WIDTH + (Constants.DEFAULT_TILE_WIDTH * 4 * Constants.GAME_SCALE), ((Constants.GAME_HEIGHT + Constants.GRID_OFFSET_Y) * Constants.GAME_SCALE));
        this.root.getChildren().add(hudCanvas);
        this.root.getChildren().add(healthBarEmpty);
        this.root.getChildren().add(scoreBarEmpty);
        this.root.getChildren().add(scoreBarFilled);

        resetHealthBar();
    }

    public void updateStats(double health, double speed, int range, int bombCount) {
        int printHealth = (int) health;
        double printSpeed = (double) (speed);
        //50
        updateHealthBar((int) ((health / 100) * 300));
        stats.setText("Health: " + printHealth + "\nBombs: " + bombCount + "\nSpeed: " + printSpeed + "\nRange: " + range);

        stats.setLayoutX(920);
        stats.setLayoutY(152);

        this.root.getChildren().remove(stats);
        this.root.getChildren().add(stats);

    }

    public void updateHealthBar(int health) {
        newImage = new WritableImage(reader, 0, 0, health, 17);
        ImageView healthBarFilledNew = new ImageView(newImage);
        healthBarFilledNew.setLayoutX(184);
        healthBarFilledNew.setLayoutY(38);
        this.root.getChildren().remove(healthBarFilled);
        this.root.getChildren().add(healthBarFilledNew);
        this.healthBarFilled = healthBarFilledNew;
    }

    public void setZeroHealthBar(){
        this.root.getChildren().remove(healthBarFilled);
    }

    public void resetHealthBar(){

        updateHealthBar(300);
    }
}