package edu.chalmers.vaporwave.view;

import edu.chalmers.vaporwave.util.ImageID;
import javafx.scene.Group;
import javafx.scene.image.Image;

/**
 * Created by Lina on 5/10/2016.
 */
public class ResultsMenuView extends AbstractMenuView {

    public ResultsMenuView(Group root){
        super(root);
        setBackgroundImage(ImageContainer.getInstance().getImage(ImageID.BUTTON_EXIT));
        setActive();

    }

    @Override
    public void updateView(int superSelected, int[] subSelected) {

    }
}
