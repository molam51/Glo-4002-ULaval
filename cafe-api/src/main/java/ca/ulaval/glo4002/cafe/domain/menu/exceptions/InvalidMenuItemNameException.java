package ca.ulaval.glo4002.cafe.domain.menu.exceptions;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;

public class InvalidMenuItemNameException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Menu item name %s is invalid.";

    public InvalidMenuItemNameException(MenuItemName menuItemName) {
        super(String.format(ERROR_MESSAGE, menuItemName.toString()));
    }
}
