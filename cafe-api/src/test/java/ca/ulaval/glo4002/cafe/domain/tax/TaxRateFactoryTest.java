package ca.ulaval.glo4002.cafe.domain.tax;

import ca.ulaval.glo4002.cafe.domain.tax.exceptions.InvalidCountryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaxRateFactoryTest {

    private static final Province A_PROVINCE = Province.QC;
    private static final State A_STATE = State.NY;

    private TaxRateFactory taxRateFactory;

    @BeforeEach
    public void setup() {
        taxRateFactory = new TaxRateFactory();
    }

    @Test
    public void givenCountryIsUSAndStateIsNull_whenCreate_thenThrowInvalidCountryException() {
        assertThrows(InvalidCountryException.class, () -> taxRateFactory.create(Country.US, A_PROVINCE, null));
    }

    @Test
    public void givenCountryIsCanadaAndProvinceIsNull_whenCreate_thenThrowInvalidCountryException() {
        assertThrows(InvalidCountryException.class, () -> taxRateFactory.create(Country.CA, null, A_STATE));
    }

    @Test
    public void givenCountryIsNone_whenCreate_thenTaxIsCreatedWithoutSubTerritoryTax() {
        TaxRate actualTaxRate = taxRateFactory.create(Country.None, A_PROVINCE, A_STATE);

        TaxRate expectedTaxRate = new TaxRate(Country.None.getTaxRate(), 0);
        assertEquals(expectedTaxRate, actualTaxRate);
    }

    @Test
    public void givenCountryIsChile_whenCreate_thenTaxIsCreatedWithoutSubTerritoryTax() {
        TaxRate actualTaxRate = taxRateFactory.create(Country.CL, A_PROVINCE, A_STATE);

        TaxRate expectedTaxRate = new TaxRate(Country.CL.getTaxRate(), 0);
        assertEquals(expectedTaxRate, actualTaxRate);
    }

    @Test
    public void givenCountryIsCanada_whenCreate_thenTaxIsCreatedWithProvinceTax() {
        TaxRate actualTaxRate = taxRateFactory.create(Country.CA, A_PROVINCE, A_STATE);

        TaxRate expectedTaxRate = new TaxRate(Country.CA.getTaxRate(), A_PROVINCE.getTaxRate());
        assertEquals(expectedTaxRate, actualTaxRate);
    }

    @Test
    public void givenCountryIsUS_whenCreate_thenTaxIsCreatedWithStateTax() {
        TaxRate actualTaxRate = taxRateFactory.create(Country.US, A_PROVINCE, A_STATE);

        TaxRate expectedTaxRate = new TaxRate(Country.US.getTaxRate(), A_STATE.getTaxRate());
        assertEquals(expectedTaxRate, actualTaxRate);
    }
}
