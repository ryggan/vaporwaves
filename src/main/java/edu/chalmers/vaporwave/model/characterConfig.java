package edu.chalmers.vaporwave.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Lina on 4/19/2016.
 */
public abstract class characterConfig {

    //hej

    int health;
    int bombCount;
    int bombRange;
    int speed;

    String name;

    ImageView frameSelected;
    ImageView frame;
    ImageView characterFrameView;
    ImageView characterName;
    ImageView selectedCharacterView;

    public characterConfig(int health, int bombCount, int bombRange, int speed, String name, ImageView characterFrameView,
                           ImageView frameSelected, ImageView frame, ImageView characterName, ImageView selectedCharacterView){
        this.health=health;
        this.bombCount=bombCount;
        this.bombRange=bombRange;
        this.speed=speed;

        this.frame=frame;
        this.frameSelected=frameSelected;
        this.characterFrameView=characterFrameView;
        this.characterName=characterName;
        this.selectedCharacterView=selectedCharacterView;
    }

    public int getHealth(){
        return health;
    }
    public int getBombCount() {
        return bombCount;
    }
    public int getBombRange(){
        return bombRange;
    }
    public int getSpeed(){
        return speed;
    }

    public ImageView getFrame(){
        return frame;
    }
    public ImageView getFrameSelected(){
        return frameSelected;
    }
    public ImageView getCharacterFrameView(){
        return characterFrameView;
    }
    public ImageView getCharacterName(){
        return characterName;
    }
    public ImageView getSelectedCharacterView(){
        return selectedCharacterView;
    }

    public String getName(){
        return name;
    }
}
