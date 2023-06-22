package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.menu.Recipe;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class InMemoryOrderRepositoryTest {

    private static final CustomerID A_CUSTOMER_ID = new CustomerID("foo");
    private static final CustomerID ANOTHER_CUSTOMER_ID = new CustomerID("bar");
    private static final MenuItemName A_MENU_ITEM_NAME = new MenuItemName("latte");
    private static final Price A_PRICE = new Price(10.0f);
    private static final IngredientName AN_INGREDIENT_NAME = new IngredientName("Espresso");
    private static final Recipe A_RECIPE = new Recipe(List.of(
            new Ingredient(AN_INGREDIENT_NAME, new Quantity(80))));

    private OrderRepository orderRepository;

    @BeforeEach
    public void setupRepository() {
        orderRepository = new InMemoryOrderRepository();
    }

    @Test
    public void givenMultipleOrders_whenAddAll_thenOrdersAreFoundInRepository() {
        Order order1 = givenOrderForCustomer(A_CUSTOMER_ID);
        Order order2 = givenOrderForCustomer(ANOTHER_CUSTOMER_ID);
        List<Order> orders = List.of(order1, order2);

        orderRepository.addAll(orders);

        assertCustomerHasOrder(A_CUSTOMER_ID, order1);
        assertCustomerHasOrder(ANOTHER_CUSTOMER_ID, order2);
    }

    @Test
    public void givenMultipleOrdersInRepository_whenFindByCustomerId_thenOrdersForCustomerAreReturned() {
        Order order = givenOrderForCustomerInRepository(A_CUSTOMER_ID);
        givenOrderForCustomerInRepository(ANOTHER_CUSTOMER_ID);

        List<Order> foundOrders = orderRepository.findByCustomerId(A_CUSTOMER_ID);

        List<Order> expectedOrders = List.of(order);
        assertIterableEquals(expectedOrders, foundOrders);
    }

    @Test
    public void givenMultipleOrdersInRepository_whenClear_thenRepositoryIsEmpty() {
        givenOrderForCustomerInRepository(A_CUSTOMER_ID);
        givenOrderForCustomerInRepository(ANOTHER_CUSTOMER_ID);

        orderRepository.clear();

        assertCustomerHasNoOrders(A_CUSTOMER_ID);
        assertCustomerHasNoOrders(ANOTHER_CUSTOMER_ID);
    }

    private MenuItem givenMenuItem() {
        return new MenuItem(A_MENU_ITEM_NAME, A_PRICE, A_RECIPE);
    }

    private Order givenOrderForCustomer(CustomerID customerId) {
        MenuItem menuItem = givenMenuItem();

        return new Order(customerId, menuItem);
    }

    private Order givenOrderForCustomerInRepository(CustomerID customerId) {
        Order order = givenOrderForCustomer(customerId);

        orderRepository.addAll(List.of(order));

        return order;
    }

    private void assertCustomerHasOrder(CustomerID customerId, Order order) {
        List<Order> foundOrders = orderRepository.findByCustomerId(customerId);

        assertIterableEquals(List.of(order), foundOrders);
    }

    private void assertCustomerHasNoOrders(CustomerID customerId) {
        List<Order> expectedOrders = List.of();
        List<Order> foundOrders = orderRepository.findByCustomerId(customerId);

        assertIterableEquals(expectedOrders, foundOrders);
    }
}
