package ca.ulaval.glo4002.cafe.domain.tax;

import ca.ulaval.glo4002.cafe.domain.tax.exceptions.InvalidCountryException;

public class TaxRateFactory {

    public TaxRate create(Country country, Province province, State state) {
        double countryTaxRate = country.getTaxRate();
        double subTerritoryTaxRate = 0;

        if (hasInvalidSubTerritory(country, province, state)) {
            throw new InvalidCountryException();
        }

        if (province != null && country == Country.CA) {
            subTerritoryTaxRate = province.getTaxRate();
        }

        if (state != null && country == Country.US) {
            subTerritoryTaxRate = state.getTaxRate();
        }

        return new TaxRate(countryTaxRate, subTerritoryTaxRate);
    }

    private boolean hasInvalidSubTerritory(Country country, Province province, State state) {
        return (country == Country.CA && province == null) || (country == Country.US && state == null);
    }
}
