package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaTheme;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MapObject;
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

    private List<MenuButtonSprite> menuButtonSpriteList;

    private Sprite mark;
    private Sprite smallIndestructible;
    private Sprite smallDestructible;

    private Sprite arenaBackground;
    private Sprite bigIndestructible;
    private Sprite bigDestructible;

    private List<ArenaMap> arenaMaps;
    private ArenaTheme theme;

    int bigx;
    int bigy;
    int smallx;
    int smally;
    int smalli;
    int smallDim;

    public MapSelectMenuView(Group root, List<ArenaMap> arenaMaps) {
        super(root);

        this.bigx = 204 / (int)Constants.GAME_SCALE;
        this.bigy = 44 / (int)Constants.GAME_SCALE;
        this.smallx = 346;
        this.smally = 545;
        this.smalli = 141;
        this.smallDim = 5;

        this.arenaMaps = arenaMaps;
        this.setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_MAPSELECT));

        // The good ol' usual buttons
        this.menuButtonSpriteList = new ArrayList<>();
        this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_SMALL_BACK, new Point(4, 4)));
        this.menuButtonSpriteList.add(null);
        this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_START_GAME,
                new Point(Constants.WINDOW_WIDTH - 320, Constants.WINDOW_HEIGHT - 80)));

        // Setting up sprites
        this.mark = Container.getSprite(SpriteID.MENU_MAPSELECT_MARK);
        this.mark.setPosition(479, 537);

        this.smallIndestructible = Container.getSprite(SpriteID.MENU_MAPSELECT_INDESTRUCTIBLE);
        this.smallDestructible = Container.getSprite(SpriteID.MENU_MAPSELECT_DESTRUCTIBLE);

        setTheme(ArenaTheme.BEACH);
    }

    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();

        // Updating buttons
        for (int i = 0; i < this.menuButtonSpriteList.size(); i++) {
            if (this.menuButtonSpriteList.get(i) != null) {
                updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
            }
        }

        // Mark on map selector
        if (superSelected == 1) {
            this.mark.render(getBackgroundGC(), 0);
        }

        // Rendering all preview maps
        renderBigPreviewMap(this.arenaMaps.get(subSelected[1]));

        int previusSelected = subSelected[1] - 1;
        if (previusSelected < 0) {
            previusSelected = this.arenaMaps.size() - 1;
        }
        renderSmallPreviewMap(this.arenaMaps.get(previusSelected), 0);

        renderSmallPreviewMap(this.arenaMaps.get(subSelected[1]), 1);

        int nextSelected = subSelected[1] + 1;
        if (nextSelected > this.arenaMaps.size() - 1) {
            nextSelected = 0;
        }
        renderSmallPreviewMap(this.arenaMaps.get(nextSelected), 2);

        setActive();
    }

    private void renderBigPreviewMap(ArenaMap arenaMap) {
        this.arenaBackground.setPosition(this.bigx, this.bigy);
        this.arenaBackground.render(getBackgroundGC(), 0);

        MapObject[][] mapObjects = arenaMap.getMapObjects();
        for (int i = 0; i < mapObjects.length; i++) {
            for (int j = 0; j < mapObjects[i].length; j++) {
                renderBigMapObject(mapObjects[i][j], i, j);
            }
        }
    }

    private void renderBigMapObject(MapObject mapObject, int x, int y) {
        Sprite sprite;
        switch (mapObject) {
            case INDESTRUCTIBLE_WALL:
                sprite = this.bigIndestructible;
                break;
            case DESTRUCTIBLE_WALL:
                sprite = this.bigDestructible;
                break;
            default:
                sprite = null;
        }

        if (sprite != null) {
            sprite.setPosition(this.bigx + x * Constants.DEFAULT_TILE_WIDTH, this.bigy + y * Constants.DEFAULT_TILE_HEIGHT);
            sprite.render(getBackgroundGC(), 0);
        }
    }

    private void renderSmallPreviewMap(ArenaMap arenaMap, int index) {
        MapObject[][] mapObjects = arenaMap.getMapObjects();
        for (int i = 0; i < mapObjects.length; i++) {
            for (int j = 0; j < mapObjects[i].length; j++) {
                renderSmallMapObject(mapObjects[i][j], i, j, index);
            }
        }
    }

    private void renderSmallMapObject(MapObject mapObject, int x, int y, int index) {
        Sprite sprite;
        switch (mapObject) {
            case INDESTRUCTIBLE_WALL:
                sprite = this.smallIndestructible;
                break;
            case DESTRUCTIBLE_WALL:
                sprite = this.smallDestructible;
                break;
            default:
                sprite = null;
        }

        if (sprite != null) {
            sprite.setPosition(this.smallx + x * this.smallDim + index * this.smalli, this.smally + y * this.smallDim);
            sprite.render(getBackgroundGC(), 0);
        }
    }

    public void setTheme(ArenaTheme theme) {
        this.theme = theme;

        switch (theme) {
            default:
            case DIGITAL:
            case BEACH:
                this.arenaBackground = Container.getSprite(SpriteID.GAME_BACKGROUND_1);
                this.bigIndestructible = Container.getSprite(SpriteID.WALL_INDESTR_BEACHSTONE);
                this.bigDestructible = Container.getSprite(SpriteID.WALL_DESTR_PARASOL);
                break;
        }
    }
}
