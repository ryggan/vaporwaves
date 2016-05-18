package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.assetcontainer.Container;
import edu.chalmers.vaporwave.assetcontainer.FileID;
import edu.chalmers.vaporwave.assetcontainer.Sprite;
import edu.chalmers.vaporwave.assetcontainer.SpriteID;
import edu.chalmers.vaporwave.model.TimerModel;
import edu.chalmers.vaporwave.util.Constants;
import javafx.scene.Group;
import javafx.scene.control.Label;

public class TimerView {

    Sprite timerMessage;
    Label timer;
    Group root;

    public TimerView(Group root){
        this.root = root;

        this.timerMessage = Container.getSprite(SpriteID.HUD_TIMER_MESSAGE);
        this.timerMessage.setPosition(Math.round(Constants.WINDOW_WIDTH / 2.0 - this.timerMessage.getWidth() / 2.0), 30);


        this.timer = new Label();
        this.timer.setFont(Container.getFont(FileID.FONT_BAUHAUS_30));
//        this.timer.setLayoutX();

        root.getChildren().add(timer);
    }

    public void updateTimer(){
        timer.setText(TimerModel.getInstance().getTime());
        this.root.getChildren().remove(timer);
        this.root.getChildren().add(timer);
    }
}
