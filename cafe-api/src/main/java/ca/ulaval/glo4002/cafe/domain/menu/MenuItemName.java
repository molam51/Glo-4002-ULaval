package ca.ulaval.glo4002.cafe.domain.menu;

import java.util.Objects;

public class MenuItemName {

    private final String value;

    public MenuItemName(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MenuItemName otherMenuItemName)) {
            return false;
        }

        return Objects.equals(value, otherMenuItemName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
