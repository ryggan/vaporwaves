package edu.chalmers.vaporwave.view;

import javafx.scene.image.ImageView;

/**
 * Created by Lina on 4/21/2016.
 */
public class GameMenuButton {

    private ImageView selected;
    private ImageView unselected;
    private ImageView pressed;

    public GameMenuButton(ImageView unselected, ImageView selected, ImageView pressed){
        this.selected=selected;
        this.unselected=unselected;
        this.pressed=pressed;
    }

    public ImageView getUnselected(){
        return unselected;
    }

    public ImageView getSelected(){
        return  selected;
    }

    public ImageView getPressed(){
        return pressed;
    }
}
