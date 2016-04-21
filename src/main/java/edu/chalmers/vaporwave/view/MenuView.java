package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.model.menu.MenuState;
import edu.chalmers.vaporwave.util.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.*;

/**
 * Created by bob on 2016-04-15.
 */
public class MenuView {

    private Canvas backgroundCanvas;
    private Canvas tileCanvas;
    private GraphicsContext backgroundGC;
    private GraphicsContext tileGC;

    private MenuState menuState;
    private Group root;

    public MenuView(Group root) {
    this.root=root;

    }

    private void createBackground(GraphicsContext backgroundGC) {

        Sprite arenaBackgroundSprite = new Sprite("images/sprite-arenabackground-01.png");
        arenaBackgroundSprite.setPosition(0, 0);
        arenaBackgroundSprite.setScale(Constants.GAME_SCALE);
        arenaBackgroundSprite.render(backgroundGC, -1);

    }

    public void setMenuState(MenuState menuState){
        this.menuState=menuState;
        for(int i=1; i<menuState.getButtonList().length-1; i++) {
            root.getChildren().add(menuState.getButtonList()[i].getUnselected());
        }
        root.getChildren().add(menuState.getButtonList()[0].getSelected());

    }

    public void update(String key){
        if(key=="UP") {
            root.getChildren().remove(menuState.getLastSelectedButton().getSelected());
            root.getChildren().add(menuState.getLastSelectedButton().getUnselected());
            root.getChildren().remove(menuState.getSelectedButton().getUnselected());
            root.getChildren().add(menuState.getSelectedButton().getSelected());
        } else if(key=="DOWN") {
            root.getChildren().remove(menuState.getLastSelectedButton().getSelected());
            root.getChildren().add(menuState.getLastSelectedButton().getUnselected());
            root.getChildren().remove(menuState.getSelectedButton().getUnselected());
            root.getChildren().add(menuState.getSelectedButton().getSelected());
        }

    }
}
