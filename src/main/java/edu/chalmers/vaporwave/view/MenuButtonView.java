package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class MenuButtonView {

    private Image spriteSheet;
    private int width;
    private int height;
    private int firstFrameX;
    private Point position;

    public MenuButtonView(Image spriteSheet, int width, int height, int firstFrameX, Point position) {
        this.spriteSheet = spriteSheet;
        this.width = width;
        this.height = height;
        this.firstFrameX = firstFrameX;
        this.position = position;
    }

    public void render(GraphicsContext menuGC, MenuButtonState menuButtonState) {
        int firstFrameY = 0;
        switch (menuButtonState) {
            case UNSELECTED:
                firstFrameY = 0;
                break;
            case SELECTED:
                firstFrameY = 1;
                break;
            case PRESSED:
                firstFrameY = 2;
                break;
        }

        menuGC.drawImage(spriteSheet, firstFrameX * width, firstFrameY * height, width, height, position.x, position.y, width, height);
    }

}
