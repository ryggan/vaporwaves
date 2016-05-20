package edu.chalmers.vaporwave.model.menu;

public class StartMenu extends AbstractMenu {
    public StartMenu(NewGameEvent newGameEvent) {
        super(new int[]{0, 0}, newGameEvent, 0);
    }

    public MenuState getMenuAction() {
        if (this.getSelectedSuper() == 0) {
            return MenuState.ROOSTER;
        } else if (this.getSelectedSuper() == 1) {
            return MenuState.EXIT_PROGRAM;
        }
        return MenuState.NO_ACTION;
    }

    @Override
    public void performMenuAction(NewGameEvent newGameEvent, int playerID) {
        System.out.println("Performing menu event in characterSelect");
    }
}
