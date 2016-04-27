package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.model.menu.MenuButtonState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public class CharacterSelectView {
    private Image spriteSheet;
    private Point position;

    public CharacterSelectView(Image spriteSheet, Point position) {
        this.spriteSheet = spriteSheet;
        this.position = position;
    }

    public void render(GraphicsContext menuGC, int selectedCharacter) {
        menuGC.drawImage(spriteSheet, selectedCharacter * 500, 0, 500, 406, position.x, position.y, 500, 406);
    }

}
