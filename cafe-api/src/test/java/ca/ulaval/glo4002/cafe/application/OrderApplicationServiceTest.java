package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientsInventory;
import ca.ulaval.glo4002.cafe.domain.menu.*;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrderFactory;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderApplicationServiceTest {

    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final MenuItemName MENU_ITEM_1_NAME = new MenuItemName("Latte");
    private static final MenuItemName MENU_ITEM_2_NAME = new MenuItemName("Mocha");
    private static final IngredientName AN_INGREDIENT_NAME = new IngredientName("Espresso");
    private static final Price A_PRICE = new Price(10.0f);
    private static final Recipe A_RECIPE = new Recipe(List.of(
            new Ingredient(AN_INGREDIENT_NAME, new Quantity(40))));
    private static final Recipe ANOTHER_RECIPE = new Recipe(List.of(
            new Ingredient(AN_INGREDIENT_NAME, new Quantity(80))));
    private static final MenuItem MENU_ITEM_1 = new MenuItem(MENU_ITEM_1_NAME, A_PRICE, A_RECIPE);
    private static final Order ORDER_FOR_MENU_ITEM_1 = new Order(A_CUSTOMER_ID, MENU_ITEM_1);
    private static final MenuItem MENU_ITEM_2 = new MenuItem(MENU_ITEM_2_NAME, A_PRICE, ANOTHER_RECIPE);
    private static final Order ORDER_FOR_MENU_ITEM_2 = new Order(A_CUSTOMER_ID, MENU_ITEM_2);

    private static Menu menu;
    private static IngredientsInventory ingredientsInventory;

    private OrderFactory orderFactory;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private OrderApplicationService orderApplicationService;

    @BeforeAll
    public static void setupBeforeAll() {
        setupMenu();
        setupIngredientsInventory();
    }

    private static void setupMenu() {
        menu = mock(Menu.class);
        when(menu.findByName(MENU_ITEM_1_NAME)).thenReturn(MENU_ITEM_1);
        when(menu.findByName(MENU_ITEM_2_NAME)).thenReturn(MENU_ITEM_2);
    }

    private static void setupIngredientsInventory() {
        ingredientsInventory = mock(IngredientsInventory.class);
    }

    @BeforeEach
    public void setupBeforeEach() {
        setupOrderFactory();
        setupOrderRepository();
        setupCustomerRepository();
        setupOrderApplicationService();
    }

    private void setupOrderFactory() {
        orderFactory = mock(OrderFactory.class);
        when(orderFactory.create(A_CUSTOMER_ID, MENU_ITEM_1)).thenReturn(ORDER_FOR_MENU_ITEM_1);
        when(orderFactory.create(A_CUSTOMER_ID, MENU_ITEM_2)).thenReturn(ORDER_FOR_MENU_ITEM_2);
    }

    private void setupOrderRepository() {
        orderRepository = mock(OrderRepository.class);
        when(orderRepository.findByCustomerId(A_CUSTOMER_ID))
                .thenReturn(List.of(ORDER_FOR_MENU_ITEM_1, ORDER_FOR_MENU_ITEM_2));
    }

    private void setupCustomerRepository() {
        customerRepository = mock(CustomerRepository.class);
    }

    private void setupOrderApplicationService() {
        orderApplicationService = new OrderApplicationService(
                orderFactory,
                orderRepository,
                customerRepository,
                menu,
                ingredientsInventory);
    }

    @Test
    public void givenCustomerIdAndMenuItemNames_whenOrder_thenAddAllIsCalledOnOrderRepository() {
        when(customerRepository.exists(A_CUSTOMER_ID)).thenReturn(true);

        orderApplicationService.order(A_CUSTOMER_ID, List.of(MENU_ITEM_1_NAME, MENU_ITEM_2_NAME));

        verify(orderRepository).addAll(List.of(ORDER_FOR_MENU_ITEM_1, ORDER_FOR_MENU_ITEM_2));
    }

    @Test
    public void givenMenuItemNames_whenOrder_thenRemoveIngredientsIsCalledOnIngredientInventory() {
        when(customerRepository.exists(A_CUSTOMER_ID)).thenReturn(true);

        orderApplicationService.order(A_CUSTOMER_ID, List.of(MENU_ITEM_1_NAME, MENU_ITEM_2_NAME));

        verify(ingredientsInventory).removeIngredients(List.of(A_RECIPE, ANOTHER_RECIPE));
    }

    @Test
    public void givenNonExistentCustomerId_whenOrder_thenThrowCustomerNotFoundException() {
        when(customerRepository.exists(A_CUSTOMER_ID)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () -> {
            orderApplicationService.order(A_CUSTOMER_ID, List.of());
        });
    }

    @Test
    public void givenNonExistentCustomerId_whenOrder_thenAddAllIsNotCalledOnOrderRepository() {
        when(customerRepository.exists(A_CUSTOMER_ID)).thenReturn(false);

        verify(orderRepository, never()).addAll(any());
    }

    @Test
    public void givenCustomerId_whenGetOrdersByCustomerId_thenOrdersForCustomerIdAreReturned() {
        when(customerRepository.exists(A_CUSTOMER_ID)).thenReturn(true);

        List<Order> orders = orderApplicationService.getOrdersByCustomerId(A_CUSTOMER_ID);

        assertEquals(List.of(ORDER_FOR_MENU_ITEM_1, ORDER_FOR_MENU_ITEM_2), orders);
    }

    @Test
    public void givenNonExistentCustomerId_whenGetOrdersByCustomerId_thenThrowCustomerNotFoundException() {
        when(customerRepository.exists(A_CUSTOMER_ID)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () -> {
            orderApplicationService.getOrdersByCustomerId(A_CUSTOMER_ID);
        });
    }
}
