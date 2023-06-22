package ca.ulaval.glo4002.cafe.domain.tip;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.menu.Price;

public class TipRate {

    private static final Price DEFAULT_TIP = new Price(0);

    private final double groupTipRateValue;

    public TipRate(double groupTipRateValue) {
        this.groupTipRateValue = groupTipRateValue;
    }

    public Price calculateTip(Price subtotal, Customer customer) {
        if (customer.hasGroup()) {
            return subtotal.multiply(groupTipRateValue);
        } else {
            return DEFAULT_TIP;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        TipRate tipRate = (TipRate) other;
        return Double.compare(tipRate.groupTipRateValue, groupTipRateValue) == 0;
    }
}
