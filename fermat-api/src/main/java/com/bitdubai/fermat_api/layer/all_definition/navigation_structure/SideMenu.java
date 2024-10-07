package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatSideMenu;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Matias Furszyfer on 2015.07.17..
 */

public class SideMenu implements FermatSideMenu {

    /**
     * SideMenu class member variables
     */
    List<MenuItem> menuItems = new ArrayList<>();
    private String backgroundColor;
    private String backgroudColor;
    private String navigationIconColor;

    /**
     * SideMenu class constructors
     */
    public SideMenu() {
    }

    public SideMenu(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItem (MenuItem menuItem){
        menuItems.add(menuItem);
    }

    /**
     * SideMenu class getters
     */

    public List<MenuItem> getMenuItems () {
        return menuItems;
    }

    /**
     * SideMenu class setters
     */
    public void setMenuItems (List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroudColor() {
        return backgroudColor;
    }

    public void setNavigationIconColor(String navigationIconColor) {
        this.navigationIconColor = navigationIconColor;
    }

    public String getNavigationIconColor() {
        return navigationIconColor;
    }
}
