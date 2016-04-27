package edu.chalmers.vaporwave.model.menu;

public class Menu {
    private static Menu instance;
    private MenuCategory activeMenu;
    private int activeSuperMenu;
    private int activeSubMenu;

    private Menu() {}

    public static synchronized Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
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
