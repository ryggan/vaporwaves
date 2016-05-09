package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.util.TimerModel;
import javafx.scene.Group;
import javafx.scene.control.Label;

public class TimerView {

    Label timer;
    Group root;

    public TimerView(Group root){
        timer= new Label();
        this.root=root;
        root.getChildren().add(timer);
    }

    public void updateTimer(){
        timer.setText(TimerModel.getInstance().getTime());
        this.root.getChildren().remove(timer);
        this.root.getChildren().add(timer);
    }
}
