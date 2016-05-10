package edu.chalmers.vaporwave.model.menu;

public class LoadingScreen extends AbstractMenu {

    public LoadingScreen() {
        super(new int[0]);
    }

    public MenuAction getMenuAction() {
        return MenuAction.NO_ACTION;
    }

}