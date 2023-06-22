package ca.ulaval.glo4002.cafe.domain.ingredient;

import java.util.Objects;

public class Quantity {

    private final int value;

    public Quantity(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }

    public Quantity add(Quantity other) {
        int sum = value + other.value;
        return new Quantity(sum);
    }

    public Quantity remove(Quantity other) {
        int difference = value - other.value;
        return new Quantity(difference);
    }

    public boolean greaterOrEqualThan(Quantity quantity) {
        return value >= quantity.value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Quantity otherQuantity)) {
            return false;
        }

        return Objects.equals(value, otherQuantity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
