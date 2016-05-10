package edu.chalmers.vaporwave.model.menu;

// TODO: Obsolete code? Doesn't seem to be used anywhere, should be deleted maybe?

public class MenuOld {
    private static MenuOld instance;
    private MenuCategory activeMenu;
    private int activeSuperMenu;
    private int activeSubMenu;

    private MenuOld() {}

    public static synchronized MenuOld getInstance() {
        if (instance == null) {
            instance = new MenuOld();
        }
        return instance;
    }

    public void setActiveMenu(MenuCategory activeMenu) {
        this.activeMenu = activeMenu;
    }

    public MenuCategory getActiveMenu() {
        return this.getActiveMenu();
    }

    public void setActiveSuperMenu(int activeSuperMenu) {
        this.activeSuperMenu = activeSuperMenu;
    }

    public int getActiveSuperMenu() {
        return this.activeSuperMenu;
    }

    public void setActiveSubMenu(int activeSubMenu) {
        this.activeSubMenu = activeSubMenu;
    }

    public int getActiveSubMenu() {
        return this.activeSubMenu;
    }
}
