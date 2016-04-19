package edu.chalmers.vaporwave.model.menu;

import edu.chalmers.vaporwave.model.AlyssaConfig;
import edu.chalmers.vaporwave.model.characterConfig;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


/**
 * Created by bob on 2016-04-15.
 */
public class CharacterSelect implements MenuState {

    Label speed;
    Label bombRange;
    Label health;
    Label bombCount;

    ImageView background;

    ImageView goButton;
    ImageView backButton;

    characterConfig[] characters;

    private static CharacterSelect instance = new CharacterSelect();

    private CharacterSelect(){
        characters=new characterConfig[4];
        characters[0]=AlyssaConfig.getInstance();
    }

    public static CharacterSelect getInstance(){
        return instance;
    }


    
}
