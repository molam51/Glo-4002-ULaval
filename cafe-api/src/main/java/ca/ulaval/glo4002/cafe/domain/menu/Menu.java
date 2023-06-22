package ca.ulaval.glo4002.cafe.domain.menu;

public interface Menu {

    void add(MenuItem menuItem);

    MenuItem findByName(MenuItemName menuItemName);
}
