package ca.ulaval.glo4002.cafe.domain.ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    private Quantity quantity;

    @BeforeEach
    public void setup() {
        quantity = new Quantity(5);
    }

    @Test
    public void givenTwoQuantities_whenAdd_thenReturnSumOfQuantities() {
        Quantity quantityToAdd = new Quantity(3);

        Quantity sum = quantity.add(quantityToAdd);

        Quantity expectedSum = new Quantity(8);
        assertEquals(expectedSum, sum);
    }

    @Test
    public void givenTwoQuantities_whenRemove_thenReturnDifferenceOfQuantities() {
        Quantity quantityToRemove = new Quantity(3);

        Quantity difference = quantity.remove(quantityToRemove);

        Quantity expectedDifference = new Quantity(2);
        assertEquals(expectedDifference, difference);
    }

    @Test
    public void givenGreaterQuantity_whenGreaterThanOrEqual_thenReturnTrue() {
        Quantity greaterQuantity = new Quantity(8);

        boolean greaterThanOrEqual = quantity.greaterOrEqualThan(greaterQuantity);

        assertFalse(greaterThanOrEqual);
    }

    @Test
    public void givenLesserQuantity_whenGreaterThanOrEqual_thenReturnFalse() {
        Quantity greaterQuantity = new Quantity(3);

        boolean greaterThanOrEqual = quantity.greaterOrEqualThan(greaterQuantity);

        assertTrue(greaterThanOrEqual);
    }

    @Test
    public void givenEqualQuantity_whenGreaterThanOrEqual_thenReturnTrue() {
        Quantity equalQuantity = new Quantity(5);

        boolean greaterThanOrEqual = quantity.greaterOrEqualThan(equalQuantity);

        assertTrue(greaterThanOrEqual);
    }
}
