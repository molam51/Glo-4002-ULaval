package ca.ulaval.glo4002.cafe.domain.ingredient;

import java.util.Objects;

public class IngredientName {

    private final String value;

    public IngredientName(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IngredientName otherIngredientName)) {
            return false;
        }

        return value.equals(otherIngredientName.value);
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
