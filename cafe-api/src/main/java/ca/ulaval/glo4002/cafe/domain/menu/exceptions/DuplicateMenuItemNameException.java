package ca.ulaval.glo4002.cafe.domain.menu.exceptions;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;

public class DuplicateMenuItemNameException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Menu item with name %s already added to the menu.";

    public DuplicateMenuItemNameException(MenuItemName menuItemName) {
        super(String.format(ERROR_MESSAGE, menuItemName.toString()));
    }
}
