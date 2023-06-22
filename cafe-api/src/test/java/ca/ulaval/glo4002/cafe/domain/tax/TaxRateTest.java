package ca.ulaval.glo4002.cafe.domain.tax;

import ca.ulaval.glo4002.cafe.domain.menu.Price;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaxRateTest {

    private static final double A_COUNTRY_TAX_RATE = 0.05;
    private static final double A_SUB_TERRITORY_TAX_RATE = 0.10;
    private static final Price A_SUBTOTAL = new Price(10.0);
    private static final Price EXPECTED_TAXES = new Price(1.5);

    @Test
    public void givenSubtotal_whenCalculateTaxes_thenTaxesAreCalculated() {
        TaxRate taxRate = new TaxRate(A_COUNTRY_TAX_RATE, A_SUB_TERRITORY_TAX_RATE);

        Price actualTaxes = taxRate.calculateTaxes(A_SUBTOTAL);

        assertEquals(EXPECTED_TAXES, actualTaxes);
    }
}
