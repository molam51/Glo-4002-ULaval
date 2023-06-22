package ca.ulaval.glo4002.cafe.domain.bill;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.menu.Recipe;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.domain.tip.TipRate;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillFactoryTest {

    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final String A_CUSTOMER_NAME = "John Doe";
    private static final MenuItemName A_MENU_ITEM_NAME = new MenuItemName("Latte");
    private static final Price A_PRICE = new Price(32.43);
    private static final TaxRate A_TAX_RATE = new TaxRate(0.05, 0.15);
    private static final TipRate A_TIP_RATE = new TipRate(0);
    private static final Price EXPECTED_SUBTOTAL = new Price(64.86);

    @Test
    public void whenCreate_thenBillIsCreated() {
        Customer customer = givenCustomer();
        List<Order> orders = List.of(givenOrder(), givenOrder());
        BillFactory billFactory = new BillFactory();

        Bill bill = billFactory.create(customer, orders, A_TAX_RATE, A_TIP_RATE);

        Price expectedTaxes = A_TAX_RATE.calculateTaxes(EXPECTED_SUBTOTAL);
        Price expectedTip = A_TIP_RATE.calculateTip(EXPECTED_SUBTOTAL, customer);
        Price expectedTotal = EXPECTED_SUBTOTAL.add(expectedTaxes).add(expectedTip);
        assertEquals(A_CUSTOMER_ID, bill.getCustomerID());
        assertEquals(EXPECTED_SUBTOTAL, bill.getSubtotal());
        assertEquals(expectedTaxes, bill.getTaxes());
        assertEquals(expectedTip, bill.getTip());
        assertEquals(expectedTotal, bill.getTotal());
        assertEquals(orders, bill.getOrders());
    }

    private Order givenOrder() {
        return new Order(A_CUSTOMER_ID, givenMenuItem());
    }

    private MenuItem givenMenuItem() {
        Recipe menuItemRecipe = new Recipe(Collections.emptyList());

        return new MenuItem(A_MENU_ITEM_NAME, A_PRICE, menuItemRecipe);
    }

    private Customer givenCustomer() {
        return new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, null);
    }
}
