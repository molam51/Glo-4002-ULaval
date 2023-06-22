package ca.ulaval.glo4002.cafe.domain.tip;

import ca.ulaval.glo4002.cafe.domain.tip.exceptions.InvalidGroupTipRateException;

public class TipRateFactory {

    private static final double MINIMUM_GROUP_TIP_RATE_VALUE = 0;
    private static final double MAXIMUM_GROUP_TIP_RATE_VALUE = 1;

    public TipRate create(double groupTipRateValue) {
        if (groupTipRateValue < MINIMUM_GROUP_TIP_RATE_VALUE || groupTipRateValue > MAXIMUM_GROUP_TIP_RATE_VALUE) {
            throw new InvalidGroupTipRateException();
        }

        return new TipRate(groupTipRateValue);
    }
}
