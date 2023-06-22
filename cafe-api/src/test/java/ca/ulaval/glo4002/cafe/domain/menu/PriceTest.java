package ca.ulaval.glo4002.cafe.domain.menu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceTest {

    @Test
    public void givenPriceAndDecimalPlaces_whenToRoundedUpDouble_thenReturnDoubleRoundedUpToDecimalPlaces() {
        Price price = new Price(1.91001);
        int decimalPlaces = 3;

        double roundedUpPrice = price.toRoundedUpDouble(decimalPlaces);

        double expectedPrice = 1.911;
        assertEquals(expectedPrice, roundedUpPrice);
    }

    @Test
    public void givenTwoPrices_whenAdd_thenReturnSumOfPrices() {
        Price price1 = new Price(1.0);
        Price price2 = new Price(2.0);

        Price sum = price1.add(price2);

        Price expectedPrice = new Price(3.0);
        assertEquals(expectedPrice, sum);
    }

    @Test
    public void givenPriceAndFactor_whenMultiply_thenReturnProductOfPriceAndFactor() {
        Price price = new Price(1.0);
        double factor = 2.0;

        Price product = price.multiply(factor);

        Price expectedPrice = new Price(2.0);
        assertEquals(expectedPrice, product);
    }
}
