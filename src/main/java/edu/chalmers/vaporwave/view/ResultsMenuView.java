package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.menu.AbstractMenu;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.GameType;
import javafx.scene.Group;

import java.awt.*;
import java.util.*;
import java.util.List;

import static edu.chalmers.vaporwave.util.GameType.ENEMY_KILLS;

/**
 * A view for presenting the results of the last game. It mainly deals with setting up a
 * scoreboard (same as in game) and showing a picture of the winning character (or random
 * character, if there was a draw).
 */
public class ResultsMenuView extends AbstractMenuView {

    private java.util.List<MenuButtonSprite> menuButtonSpriteList;

    private Sprite winnerSprite;
    private Set<Player> players;
    private GameType gameType;
    private Group root;

    private boolean isTie;
    private ScoreboardView scoreboardView;
    int rand;

    public ResultsMenuView(Group root, AbstractMenu menu) {
        super(root, menu);
        this.root = root;
        setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_RESULT));
        this.gameType = ENEMY_KILLS;
        this.menuButtonSpriteList = new ArrayList<>();
        this.menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_NEXT,
                new Point(Constants.WINDOW_WIDTH - 320, Constants.WINDOW_HEIGHT - 80)));
        this.isTie = false;
        this.rand = (new Random()).nextInt(4);
    }

    public void updateView(List<boolean[]> menuItems, int superSelected, int[] subSelected, int[] remoteSelected,
                           Player player, boolean pressedDown) {
        clearView();
        setActive();

        if (this.players != null) {
            initScoreboard();

                Player winner = getWinner();
                if (!this.isTie) {
                    this.winnerSprite = getSprite(winner);
                } else {
                    this.winnerSprite = getRandomSprite();
                }

            this.winnerSprite.setPosition(0, Constants.WINDOW_HEIGHT / 20);
            this.winnerSprite.render(this.getBackgroundGC(), 0);
        }

        updateButtons(menuItems, superSelected, subSelected, pressedDown, this.menuButtonSpriteList);

    }

    public void setPressed(int superSelected) {
        for (int i = 0; i < this.menuButtonSpriteList.size(); i++) {

            if (superSelected == i) {
                this.menuButtonSpriteList.get(i).render(getBackgroundGC(), MenuButtonState.PRESSED);
            } else {
                this.menuButtonSpriteList.get(i).render(getBackgroundGC(), MenuButtonState.UNSELECTED);
            }
        }
        setActive();
    }

    public Sprite getSprite(Player p) {
        Sprite winnerSprite;
        switch (p.getCharacter().getName().toUpperCase(Locale.ENGLISH)) {
            case "ZYPHER":
                winnerSprite = Container.getSprite(SpriteID.MENU_RESULTS_ZYPHER);
                break;
            case "MEI":
                winnerSprite = Container.getSprite(SpriteID.MENU_RESULTS_MEI);
                break;
            case "CHARLOTTE":
                winnerSprite = Container.getSprite(SpriteID.MENU_RESULTS_CHARLOTTE);
                break;
            case "ALYSSA":
            default:
                winnerSprite = Container.getSprite(SpriteID.MENU_RESULTS_ALYSSA);
        }
        return winnerSprite;
    }

    public Sprite getRandomSprite(){
        SpriteID[] winnerSpriteArray={SpriteID.MENU_RESULTS_MEI, SpriteID.MENU_RESULTS_ALYSSA,
                SpriteID.MENU_RESULTS_ZYPHER, SpriteID.MENU_RESULTS_CHARLOTTE};
        this.winnerSprite = Container.getSprite(winnerSpriteArray[this.rand]);
        return this.winnerSprite;
    }


    public void setPlayers(Set<Player> players) {
        this.players = new HashSet<>();
        for (Player player : players) {
            this.players.add(player);
        }
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Player getWinner() {
            Player winner = this.players.iterator().next();
            switch (this.gameType) {
                case SURVIVAL:
                    for (Player player : this.players) {
                        if (player.getScore() > winner.getScore()) {
                            winner = player;
                            this.isTie = false;
                        } else if (!player.getCharacter().getName().equals(winner.getCharacter().getName()) && player.getScore() == winner.getScore()) {
                            this.isTie = true;
                        }
                    }
                    break;
                case SCORE_LIMIT:
                    break;
                case CHARACTER_KILLS:
                    break;
                case ENEMY_KILLS:
                    break;
                default:
            }
            return winner;
    }

    public void initScoreboard() {
        this.scoreboardView = new ScoreboardView(this.root, this.players, 190, -40, true, this.gameType);
        this.scoreboardView.showScoreboard();
    }

}