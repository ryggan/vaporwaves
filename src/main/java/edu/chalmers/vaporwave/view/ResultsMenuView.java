package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.controller.GameController;
import edu.chalmers.vaporwave.controller.MenuController;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.menu.ResultsMenu;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.GameType;
import javafx.scene.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

import static edu.chalmers.vaporwave.util.GameType.ENEMY_KILLS;
import static edu.chalmers.vaporwave.util.GameType.SURVIVAL;

public class ResultsMenuView extends AbstractMenuView {

    private java.util.List<MenuButtonSprite> menuButtonSpriteList;

    private Sprite winnerSprite;
    private Set<Player> players;
    private GameType gameType;

    public ResultsMenuView(Group root, Set<Player> players) {
        super(root);
        this.players = players;
        this.gameType = ENEMY_KILLS;

        setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_RESULT));

        menuButtonSpriteList = new ArrayList<>();
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_NEXT, new Point(640, 280)));
    }


    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();

        if (getWinner() != null) {
            switch (getWinner().getCharacter().getName().toUpperCase()) {
                case "ZYPHER":
                    winnerSprite = Container.getSprite(SpriteID.MENU_RESULTS_ZYPHER);
                    break;
                case "ALYSSA":
                    winnerSprite = Container.getSprite(SpriteID.MENU_RESULTS_ALYSSA);
                    break;
                case "MEI":
                    winnerSprite = Container.getSprite(SpriteID.MENU_RESULTS_MEI);
                    break;
                case "CHARLOTTE":
                    winnerSprite = Container.getSprite(SpriteID.MENU_RESULTS_CHARLOTTE);
                    break;
                default:
                    winnerSprite = Container.getSprite(SpriteID.MENU_RESULTS_ALYSSA);
            }

            this.winnerSprite.setScale(1);
            this.winnerSprite.setPosition(Constants.WINDOW_WIDTH / 28, Constants.WINDOW_HEIGHT / 9);

            this.winnerSprite.render(this.getBackgroundGC(), 0);
        }
        for (int i = 0; i < menuButtonSpriteList.size(); i++) {
            updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
        }
        setActive();

    }

    public void setPressed(int superSelected) {
        for (int i = 0; i < menuButtonSpriteList.size(); i++) {

            if (superSelected == i) {
                menuButtonSpriteList.get(i).render(getBackgroundGC(), MenuButtonState.PRESSED);
            } else {
                menuButtonSpriteList.get(i).render(getBackgroundGC(), MenuButtonState.UNSELECTED);
            }
        }
        setActive();
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
        System.out.println(""+this.gameType);
    }

    //how is the question //where
    public Player getWinner() {

        Player winner = this.players.iterator().next();
        System.out.println(""+this.gameType);
        switch (this.gameType) {
            case SURVIVAL:
                for (Player player : this.players) {
                    if (player.getScore() > player.getScore()) {
                        winner = player;
                    } else if (player.getCharacter().getName() != winner.getCharacter().getName() && player.getScore() == winner.getScore()) {
                        winner = null;
                    }
                    System.out.println(player.getCharacter().getName() + " " + player.getScore());
                }
                break;
            case SCORE_LIMIT:
                break;
            case CHARACTER_KILLS:
                break;
            case ENEMY_KILLS:
                break;
            default:
                winner = null;
        }

        return winner;


        //characters killed
        //score
        //deathcount
        //enemies killed
        //picture of character (sad/glad)
        //results title
        //highscore screen after?
        //if new highscore
        //winner name
        //victory/defeat
        //rank (noob, ok, pro, hacker)
        //powerups picked up
        //
    }
}
