package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.*;
import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.controller.GameController;
import edu.chalmers.vaporwave.controller.MenuController;
import edu.chalmers.vaporwave.model.Player;
import edu.chalmers.vaporwave.model.menu.ResultsMenu;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class ResultsMenuView extends AbstractMenuView {

    private java.util.List<MenuButtonSprite> menuButtonSpriteList;

    private Sprite winnerSprite;
    private Set<Player> players;

    public ResultsMenuView(Group root, Set<Player> players){
        super(root);
        this.players=players;

        setBackgroundImage(Container.getImage(ImageID.MENU_BACKGROUND_RESULT));


        menuButtonSpriteList = new ArrayList<>();
        menuButtonSpriteList.add(Container.getButton(MenuButtonID.BUTTON_NEXT, new Point(640, 280)));
//        menuButtonSpriteList.add(new MenuButtonSprite(Container.getImage(ImageID.BUTTON_EXIT), 308, 66, new Point(0, 0), new Point(640, 280)));
    }


    public void updateView(int superSelected, int[] subSelected, int[] remoteSelected, Player player, boolean pressedDown) {
        clearView();

        switch (getWinner().toUpperCase()) {
            case "ZYPHER":
                winnerSprite=Container.getSprite(SpriteID.MENU_RESULTS_ZYPHER);
                break;
            case "ALYSSA":
                winnerSprite=Container.getSprite(SpriteID.MENU_RESULTS_ALYSSA);
                break;
            case "MEI":
                winnerSprite=Container.getSprite(SpriteID.MENU_RESULTS_MEI);
                break;
            case "CHARLOTTE":
                winnerSprite=Container.getSprite(SpriteID.MENU_RESULTS_CHARLOTTE);
                break;
            default :
                winnerSprite=Container.getSprite(SpriteID.MENU_RESULTS_ALYSSA);
        }

        this.winnerSprite.setScale(1);
        this.winnerSprite.setPosition(Constants.WINDOW_WIDTH/28,Constants.WINDOW_HEIGHT/9);

        this.winnerSprite.render(this.getBackgroundGC(), 0);
        for (int i = 0; i < menuButtonSpriteList.size(); i++) {
            updateButton(menuButtonSpriteList.get(i), superSelected == i, pressedDown);
        }
        setActive();

    }

    public void setPressed(int superSelected){
        for (int i = 0; i < menuButtonSpriteList.size(); i++) {

            if (superSelected == i) {
                menuButtonSpriteList.get(i).render(getBackgroundGC(), MenuButtonState.PRESSED);
            } else {
                menuButtonSpriteList.get(i).render(getBackgroundGC(), MenuButtonState.UNSELECTED);
            }
        }
        setActive();
    }

    //how is the question //where
    public String getWinner(){

        String winner = "";
        Player first=this.players.iterator().next();
        for (Player player : this.players) {
            if(player.getScore()>first.getScore()){
                winner=player.getCharacter().getName();
            } else if(player.getScore()==first.getScore()){
                winner="TIE";
            }
            System.out.println(player.getCharacter().getName()+" "+player.getScore());
        }
        System.out.println(winner);
        return winner;
    }



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
