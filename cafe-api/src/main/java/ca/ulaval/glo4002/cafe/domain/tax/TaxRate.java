package ca.ulaval.glo4002.cafe.domain.tax;

import ca.ulaval.glo4002.cafe.domain.menu.Price;

import java.util.Objects;

public class TaxRate {

    private final double countryTaxRate;
    private final double subTerritoryTaxRate;

    public TaxRate(double countryTaxRate, double subTerritoryTaxRate) {
        this.countryTaxRate = countryTaxRate;
        this.subTerritoryTaxRate = subTerritoryTaxRate;
    }

    public Price calculateTaxes(Price subtotal) {
        double taxRateFactor = countryTaxRate + subTerritoryTaxRate;

        return subtotal.multiply(taxRateFactor);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        TaxRate taxRate = (TaxRate) other;
        return Double.compare(taxRate.countryTaxRate, countryTaxRate) == 0 && Double.compare(taxRate.subTerritoryTaxRate, subTerritoryTaxRate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryTaxRate, subTerritoryTaxRate);
    }
}
