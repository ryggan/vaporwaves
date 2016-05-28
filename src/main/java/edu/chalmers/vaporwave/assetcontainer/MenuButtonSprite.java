package edu.chalmers.vaporwave.assetcontainer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

/**
 * Almost the same principle as usual sprites, but these doesn't need nearly as much
 * data as Sprite, which is why they came to be a separate class.
 */
public class MenuButtonSprite {

    private Image spriteSheet;
    private int width;
    private int height;
    private Point gridPositionInSheet;
    private Point positionOnCanvas;

    public MenuButtonSprite(Image spriteSheet, int width, int height, Point gridPositionInSheet) {
        this(spriteSheet, width, height, gridPositionInSheet, new Point(0, 0));
    }

    public MenuButtonSprite(Image spriteSheet, int width, int height, Point gridPositionInSheet, Point positionOnCanvas) {
        this.spriteSheet = spriteSheet;
        this.width = width;
        this.height = height;
        this.gridPositionInSheet = gridPositionInSheet;
        this.positionOnCanvas = positionOnCanvas;
    }

    public void render(GraphicsContext menuGC, MenuButtonState menuButtonState) {
        int frameY = 0;
        switch (menuButtonState) {
            case SELECTED:
                frameY = 1;
                break;
            case PRESSED:
                frameY = 2;
                break;
            default:
                break;
        }

        menuGC.drawImage(spriteSheet, gridPositionInSheet.x * width, (3 * gridPositionInSheet.y + frameY) * height,
                width, height, positionOnCanvas.x, positionOnCanvas.y, width, height);
    }

    public void setPositionOnCanvas(Point positionOnCanvas) {
        this.positionOnCanvas = positionOnCanvas;
    }

}
