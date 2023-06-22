package ca.ulaval.glo4002.cafe.domain.tip;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TipRateTest {

    private static final double A_GROUP_TIP_RATE_VALUE = 0.05;
    private static final Price A_SUBTOTAL = new Price(10.0);
    private static final CustomerID A_CUSTOMER_ID = new CustomerID("abc123");
    private static final String A_CUSTOMER_NAME = "Jack Daniels";
    private static final String A_GROUP_NAME = "Daft Dunk";

    private TipRate tipRate;

    @BeforeEach
    public void setup() {
        tipRate = new TipRate(A_GROUP_TIP_RATE_VALUE);
    }

    @Test
    public void givenSubtotalAndCustomerWithoutGroup_whenCalculateTip_thenTipIsCalculated() {
        Customer customerWithoutGroup = new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, null);

        Price actualTip = tipRate.calculateTip(A_SUBTOTAL, customerWithoutGroup);

        Price expectedTipWithoutGroup = new Price(0);
        assertEquals(expectedTipWithoutGroup, actualTip);
    }

    @Test
    public void givenSubtotalAndCustomerWithGroup_whenCalculateTip_thenTipIsCalculated() {
        Customer customerWithGroup = new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);

        Price actualTip = tipRate.calculateTip(A_SUBTOTAL, customerWithGroup);

        Price expectedTipWithGroup = new Price(0.5);
        assertEquals(expectedTipWithGroup, actualTip);
    }
}
