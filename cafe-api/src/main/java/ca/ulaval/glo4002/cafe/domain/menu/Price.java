package ca.ulaval.glo4002.cafe.domain.menu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Price {

    private final BigDecimal value;

    public Price(double value) {
        this.value = BigDecimal.valueOf(value);
    }

    public double toRoundedUpDouble(int decimalPlaces) {
        return value.setScale(decimalPlaces, RoundingMode.UP).doubleValue();
    }

    public Price add(Price other) {
        BigDecimal sum = value.add(other.value).setScale(4, RoundingMode.HALF_UP);
        return new Price(sum.doubleValue());
    }

    public Price multiply(double factor) {
        BigDecimal product = value.multiply(BigDecimal.valueOf(factor)).setScale(4, RoundingMode.HALF_UP);
        return new Price(product.doubleValue());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Price price = (Price) other;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
