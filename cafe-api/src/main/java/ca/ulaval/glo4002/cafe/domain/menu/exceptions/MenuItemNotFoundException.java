package ca.ulaval.glo4002.cafe.domain.menu.exceptions;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;

public class MenuItemNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Unable to find menu item with name %s.";

    public MenuItemNotFoundException(MenuItemName menuItemName) {
        super(String.format(ERROR_MESSAGE, menuItemName.toString()));
    }
}
