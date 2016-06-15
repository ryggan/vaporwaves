package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.model.ArenaMap;
import edu.chalmers.vaporwave.model.ArenaTheme;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.RandomArenaMap;
import edu.chalmers.vaporwave.model.menu.AbstractMenu;
import edu.chalmers.vaporwave.model.menu.MapSelectMenu;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.MapObject;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.util.List;

/**
 * Displayes the rooster buttons and changes them depending on which item is selected.
 * Nothing over-exciting.
 */
public class MapSelectMenuView extends AbstractMenuView {

    private Sprite mark;
    private Sprite smallIndestructible;
    private Sprite smallDestructible;

    private Sprite arenaBackground;
    private Sprite bigIndestructible;
    private Sprite bigDestructible;

    private Sprite arrowRight;
    private Sprite arrowLeft;
    private boolean rightPressed;
    private boolean leftPressed;

    private Sprite[] playerStart;

    private Label questionMark;
    private Label randomText;
    private boolean showQuestionMark;

    private List<ArenaMap> arenaMaps;
    private ArenaTheme theme;

    int bigx;
    int bigy;
    int smallx;
    int smally;
    int smalli;
    int smallDim;

    public MapSelectMenuView(Group root, AbstractMenu menu) {
        super(root, menu);

        this.bigx = 204 / (int)Constants.GAME_SCALE;
        this.bigy = 44 / (int)Constants.GAME_SCALE;
        this.smallx = 346;
        this.smally = 545;
        this.smalli = 141;
        this.smallDim = 5;

        // Setting up text labels
        this.questionMark = new Label("?");
        this.questionMark.setFont(Container.getFont(FileID.FONT_BAUHAUS_60));
        this.questionMark.setTextFill(Color.web(Constants.COLORNO_MAPGREY));
        this.questionMark.setLayoutY(this.smally + 5);
        this.questionMark.setAlignment(Pos.CENTER);
        this.questionMark.setTextAlignment(TextAlignment.CENTER);
        this.questionMark.setVisible(false);

        this.showQuestionMark = false;

        this.randomText = new Label("Random map!");
        this.randomText.setFont(Container.getFont(FileID.FONT_BAUHAUS_60));
        this.randomText.setTextFill(Color.web(Constants.COLORNO_VAPEPINK));
        this.randomText.setLayoutX(this.bigx + Constants.DEFAULT_GRID_WIDTH * Constants.DEFAULT_TILE_WIDTH * Constants.GAME_SCALE / 2 - 70);
        this.randomText.setLayoutY(this.bigy + Constants.DEFAULT_GRID_HEIGHT * Constants.DEFAULT_TILE_HEIGHT * Constants.GAME_SCALE / 2 - 15);
        this.randomText.setAlignment(Pos.CENTER);
        this.randomText.setTextAlignment(TextAlignment.CENTER);
        this.randomText.setVisible(false);

        this.arenaMaps = ((MapSelectMenu) menu).getArenaMaps();
        this.setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_MAPSELECT));

        // Setting up sprites
        this.mark = Container.getSprite(SpriteID.MENU_MAPSELECT_MARK);
        this.mark.setPosition(479, 537);

        this.smallIndestructible = Container.getSprite(SpriteID.MENU_MAPSELECT_INDESTRUCTIBLE);
        this.smallDestructible = Container.getSprite(SpriteID.MENU_MAPSELECT_DESTRUCTIBLE);

        this.arrowRight = Container.getSprite(SpriteID.MENU_MAPSELECT_ARROW_RIGHT);
        this.arrowRight.setPosition(761, 569);
        this.arrowLeft = Container.getSprite(SpriteID.MENU_MAPSELECT_ARROW_LEFT);
        this.arrowLeft.setPosition(302, 569);

        this.rightPressed = false;
        this.leftPressed = false;

        this.playerStart = new Sprite[4];
        this.playerStart[0] = Container.getSprite(SpriteID.MENU_MAPSELECT_P1);
        this.playerStart[1] = Container.getSprite(SpriteID.MENU_MAPSELECT_P2);
        this.playerStart[2] = Container.getSprite(SpriteID.MENU_MAPSELECT_P3);
        this.playerStart[3] = Container.getSprite(SpriteID.MENU_MAPSELECT_P4);

        // The good ol' usual buttons
        setButton(Container.getButton(MenuButtonID.BUTTON_SMALL_BACK, new Point(5, 5)), 0, 0);
        setButton(Container.getButton(MenuButtonID.BUTTON_CHANGE_THEME,
                new Point(386, Constants.WINDOW_HEIGHT - 80)), 2, 1);
        setButton(Container.getButton(MenuButtonID.BUTTON_NEXT,
                new Point(Constants.WINDOW_WIDTH - 320, Constants.WINDOW_HEIGHT - 80)), 2, 0);

        setTheme(ArenaTheme.BEACH);
    }

    public void updateView(List<boolean[]> menuItems, int superSelected, int[] subSelected, int[] remoteSelected,
                           Player player, boolean pressedDown) {
        clearView();

        updateButtons(menuItems, superSelected, subSelected, pressedDown);

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

        if (superSelected == 1) {
            if (this.rightPressed) {
                this.arrowRight.render(getBackgroundGC(), 0);
            }
            if (this.leftPressed) {
                this.arrowLeft.render(getBackgroundGC(), 0);
            }
        }

        setActive();

        getRoot().getChildren().remove(this.randomText);
        getRoot().getChildren().add(this.randomText);

        getRoot().getChildren().remove(this.questionMark);
        getRoot().getChildren().add(this.questionMark);

        this.showQuestionMark = false;
    }

    private void renderBigPreviewMap(ArenaMap arenaMap) {
        if (arenaMap instanceof RandomArenaMap) {
            this.randomText.setVisible(true);

        } else {
            this.randomText.setVisible(false);

            this.arenaBackground.setPosition(this.bigx, this.bigy);
            this.arenaBackground.render(getBackgroundGC(), 0);

            MapObject[][] mapObjects = arenaMap.getMapObjects();
            for (int i = 0; i < mapObjects.length; i++) {
                for (int j = 0; j < mapObjects[i].length; j++) {
                    renderBigMapObject(mapObjects[i][j], i, j);
                }
            }
        }
    }

    private void renderBigMapObject(MapObject mapObject, int x, int y) {
        Sprite sprite;
        switch (mapObject) {
            case PLAYER1:
            case PLAYER2:
            case PLAYER3:
            case PLAYER4:
                String mapObjStr = mapObject.toString();
                int playerID = Integer.valueOf(mapObjStr.substring(mapObjStr.length()-1, mapObjStr.length())) - 1;
                sprite = this.playerStart[playerID];
                break;
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
        if (arenaMap instanceof RandomArenaMap) {
            this.questionMark.setVisible(true);
            this.questionMark.setLayoutX(this.smallx + 35 + index * this.smalli);
            this.showQuestionMark = true;

        } else {
            if (!this.showQuestionMark) {
                this.questionMark.setVisible(false);
            }

            MapObject[][] mapObjects = arenaMap.getMapObjects();
            for (int i = 0; i < mapObjects.length; i++) {
                for (int j = 0; j < mapObjects[i].length; j++) {
                    renderSmallMapObject(mapObjects[i][j], i, j, index);
                }
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

    public void setArrowPressed(boolean leftPressed, boolean rightPressed) {
        this.leftPressed = leftPressed;
        this.rightPressed = rightPressed;
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
