package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Displayes the rooster buttons and changes them depending on which item is selected.
 * Nothing over-exciting.
 */
public class MapSelectMenuView extends AbstractMenuView {

//    private Canvas bigPreviewCanvas;
    private Canvas[] smallPreviewCanvas;

//    private GraphicsContext bigPreviewGC;
    private GraphicsContext[] smallPreviewGC;

    private List<MenuButtonSprite> menuButtonSpriteList;

    private Sprite mark;
    private Sprite indestructible;
    private Sprite destructible;

    private List<ArenaMap> arenaMaps;

    public MapSelectMenuView(Group root, List<ArenaMap> arenaMaps) {
        super(root);

        this.arenaMaps = arenaMaps;
        this.setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_MAPSELECT));

        // Setting up canvas
//        this.bigPreviewCanvas = new Canvas(Constants.DEFAULT_GRID_WIDTH * Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE,
//                Constants.DEFAULT_GRID_HEIGHT * Constants.DEFAULT_TILE_HEIGHT * Constants.GAME_SCALE);
//        this.bigPreviewGC = this.bigPreviewCanvas.getGraphicsContext2D();

        int size = 5;
        this.smallPreviewCanvas = new Canvas[3];
        this.smallPreviewGC= new GraphicsContext[3];
        for (int i = 0; i < this.smallPreviewCanvas.length; i++) {
            this.smallPreviewCanvas[i] = new Canvas(Constants.DEFAULT_GRID_WIDTH * size, Constants.DEFAULT_GRID_HEIGHT * size);
            this.smallPreviewGC[i] = this.smallPreviewCanvas[i].getGraphicsContext2D();
        }

        // The good ol' usual buttons
        this.menuButtonSpriteList = new ArrayList<>();
        this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_SMALL_BACK, new Point(4, 4)));
        this.menuButtonSpriteList.add(null);
        this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_START_GAME,
                new Point(Constants.WINDOW_WIDTH - 320, Constants.WINDOW_HEIGHT - 80)));

        // Setting up sprites
        this.mark = Container.getSprite(SpriteID.MENU_MAPSELECT_MARK);
        this.mark.setPosition(479, 537);

        this.indestructible = Container.getSprite(SpriteID.MENU_MAPSELECT_INDESTRUCTIBLE);
        this.destructible = Container.getSprite(SpriteID.MENU_MAPSELECT_DESTRUCTIBLE);
    }

    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();

        for (int i = 0; i < this.menuButtonSpriteList.size(); i++) {
            if (this.menuButtonSpriteList.get(i) != null) {
                updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
            }
        }

        if (superSelected == 1) {
            this.mark.render(getBackgroundGC(), 0);
        }

        setActive();

//        getRoot().getChildren().add(this.bigPreviewCanvas);
        for (int i = 0; i < this.smallPreviewCanvas.length; i++) {
            getRoot().getChildren().add(this.smallPreviewCanvas[i]);
        }
    }
}
