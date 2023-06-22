package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.menu.Menu;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;
import ca.ulaval.glo4002.cafe.domain.menu.exceptions.DuplicateMenuItemNameException;
import ca.ulaval.glo4002.cafe.domain.menu.exceptions.MenuItemNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class InMemoryMenu implements Menu {

    private final Set<MenuItem> menuItems = new HashSet<>();

    public void add(MenuItem menuItem) {
        if (!menuItems.add(menuItem)) {
            throw new DuplicateMenuItemNameException(menuItem.getName());
        }
    }

    public MenuItem findByName(MenuItemName menuItemName) {
        return menuItems.stream()
                .filter(menuItem -> menuItem.getName().equals(menuItemName))
                .findFirst()
                .orElseThrow(() -> new MenuItemNotFoundException(menuItemName));
    }
}
