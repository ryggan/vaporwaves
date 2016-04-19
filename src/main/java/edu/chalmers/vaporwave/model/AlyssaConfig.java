package edu.chalmers.vaporwave.model;

import javafx.scene.image.ImageView;

/**
 * Created by Lina on 4/19/2016.
 */
public class AlyssaConfig extends characterConfig {

    private static AlyssaConfig instance = new AlyssaConfig();

    private AlyssaConfig(){
        super(10,10,10,10,"Alyssa",new ImageView(), new ImageView(), new ImageView(), new ImageView(), new ImageView());
    }

    public static AlyssaConfig getInstance(){
        return instance;
    }


}
