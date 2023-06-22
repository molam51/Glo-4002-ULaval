package ca.ulaval.glo4002.cafe.domain.customer;

import java.util.Objects;

public class CustomerID {

    private final String value;

    public CustomerID(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CustomerID otherCustomerID)) {
            return false;
        }

        return value.equals(otherCustomerID.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
