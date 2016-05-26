package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.util.Constants;
import edu.chalmers.vaporwave.util.GameType;
import javafx.scene.Group;
import java.awt.*;
import java.util.*;

import static edu.chalmers.vaporwave.util.GameType.ENEMY_KILLS;

public class ResultsMenuView extends AbstractMenuView {

    private java.util.List<MenuButtonSprite> menuButtonSpriteList;

    private Sprite winnerSprite;
    private Set<Player> players;
    private GameType gameType;
    private Group root;

    private boolean isTie;
    private ScoreboardView scoreboardView;
    int rand;

    public ResultsMenuView(Group root) {
        super(root);
        this.root = root;
        setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_RESULT));
        this.gameType = ENEMY_KILLS;
        menuButtonSpriteList = new ArrayList<>();
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_NEXT,
                new Point(Constants.WINDOW_WIDTH - 320, Constants.WINDOW_HEIGHT - 100)));
        isTie = false;
        rand = (new Random()).nextInt(4);
    }

    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();
        setActive();

        if(players != null) {
            initScoreboard();

                Player winner = getWinner();
                if (!isTie) {
                    this.winnerSprite = getSprite(winner);
                } else {
                    this.winnerSprite = getRandomSprite();
                }

            this.winnerSprite.setScale(1);
            this.winnerSprite.setPosition(Constants.WINDOW_WIDTH / 28, Constants.WINDOW_HEIGHT / 9);
            this.winnerSprite.render(this.getBackgroundGC(), 0);
        }

        for (int i = 0; i < menuButtonSpriteList.size(); i++) {
            updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
        }

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

    public Sprite getSprite(Player p) {
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
        SpriteID[] winnerSpriteArray={SpriteID.MENU_RESULTS_MEI, SpriteID.MENU_RESULTS_ALYSSA, SpriteID.MENU_RESULTS_ZYPHER, SpriteID.MENU_RESULTS_CHARLOTTE};
        winnerSprite = Container.getSprite(winnerSpriteArray[this.rand]);
        return winnerSprite;
    }


    public void setPlayers(Set<Player> players) {
        this.players = new HashSet<>();
        for (Player player : players) {
            this.players.add(player);
        }
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
//        System.out.println("Set game type: " + this.gameType);
    }

    //how is the question //where
    public Player getWinner() {
// todo: Getting null pointer exception, row 111 and 113. winner gets set to null, in the next iteration we try to access its attribute
            Player winner = this.players.iterator().next();
//            System.out.println("Get winner, gametype: " + this.gameType);
            switch (this.gameType) {
                case SURVIVAL:
                    for (Player player : this.players) {
                        if (player.getScore() > winner.getScore()) {
                            winner = player;
                            isTie=false;
                        } else if (!player.getCharacter().getName().equals(winner.getCharacter().getName()) && player.getScore() == winner.getScore()) {
                            isTie=true;
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
        this.scoreboardView = new ScoreboardView(this.root, this.players, 200, 40);
        this.scoreboardView.showScoreboard();
    }

}