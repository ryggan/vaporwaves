package edu.chalmers.vaporwave.view;

import javafx.scene.Group;
import javafx.scene.control.Label;

/**
 * Created by Lina on 4/29/2016.
 */
public class TimerView {

    Label timer;
    Group root;

    public TimerView(Group root){
        timer= new Label("00:00:00");
        this.root=root;
        root.getChildren().add(timer);
    }

    public void updateTimer(int time){
        String str = ""+time;
        int fullInt = Integer.parseInt(str);


        //realised i failed continue later

        if(str.length()==0){
            timer.setText("00:00:00");
        } else if(str.length()==1){
            timer.setText("00:00:0"+time);
        } else if(str.length()==2){
            timer.setText("00:00:"+time);
        } else if(str.length()==3){
            String chartwo = str.substring(0,1);
            String charthree = str.substring(1,3);
            timer.setText("00:0"+chartwo+":"+charthree);
        } else if(str.length()==4){
            String chartwo = str.substring(0,2);
            String charthree = str.substring(2,4);
            timer.setText("00:"+chartwo+":"+charthree);
        } else if(str.length()==5){
            String charone = str.substring(0,1);
            String chartwo = str.substring(1,3);
            String charthree = str.substring(3,5);
            timer.setText("0"+charone+":"+chartwo+":"+charthree);
        } else if(str.length()==6){
            String charone = str.substring(0,2);
            String chartwo = str.substring(2,4);
            String charthree = str.substring(4,6);
            timer.setText(charone+":"+chartwo+":"+charthree);
        }

        this.root.getChildren().remove(timer);
        this.root.getChildren().add(timer);
    }
}
