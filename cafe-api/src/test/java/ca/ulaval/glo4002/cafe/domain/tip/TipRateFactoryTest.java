package ca.ulaval.glo4002.cafe.domain.tip;

import ca.ulaval.glo4002.cafe.domain.tip.exceptions.InvalidGroupTipRateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TipRateFactoryTest {

    private static final double A_GROUP_TIP_RATE_VALUE_LOWER_THAN_0 = -1;
    private static final double A_GROUP_TIP_RATE_VALUE_HIGHER_THAN_1 = 1.01;
    private static final double A_GROUP_TIP_RATE_VALUE_BETWEEN_0_AND_1 = 0.50;

    private TipRateFactory tipRateFactory;

    @BeforeEach
    public void setup() {
        tipRateFactory = new TipRateFactory();
    }

    @Test
    public void givenGroupTipRateLowerThan0_whenCreate_thenThrowInvalidGroupTipRateException() {
        assertThrows(InvalidGroupTipRateException.class, () -> tipRateFactory.create(A_GROUP_TIP_RATE_VALUE_LOWER_THAN_0));
    }

    @Test
    public void givenGroupTipRateHigherThan1_whenCreate_thenThrowInvalidGroupTipRateException() {
        assertThrows(InvalidGroupTipRateException.class, () -> tipRateFactory.create(A_GROUP_TIP_RATE_VALUE_HIGHER_THAN_1));
    }

    @Test
    public void givenGroupTipRateBetween0And1_whenCreate_thenTipIsCreated() {
        TipRate tipRate = tipRateFactory.create(A_GROUP_TIP_RATE_VALUE_BETWEEN_0_AND_1);

        TipRate expectedTipRate = new TipRate(A_GROUP_TIP_RATE_VALUE_BETWEEN_0_AND_1);
        assertEquals(expectedTipRate, tipRate);
    }
}
