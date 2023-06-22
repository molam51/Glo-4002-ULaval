package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientsInventory;
import ca.ulaval.glo4002.cafe.domain.menu.Menu;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;
import ca.ulaval.glo4002.cafe.domain.menu.Recipe;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrderFactory;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderApplicationService {

    private final OrderFactory orderFactory;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final Menu menu;
    private final IngredientsInventory ingredientsInventory;

    public OrderApplicationService(OrderFactory orderFactory,
                                   OrderRepository orderRepository,
                                   CustomerRepository customerRepository,
                                   Menu menu,
                                   IngredientsInventory ingredientsInventory) {
        this.orderFactory = orderFactory;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.menu = menu;
        this.ingredientsInventory = ingredientsInventory;
    }

    public void order(CustomerID customerId, List<MenuItemName> menuItemNames) {
        if (!customerRepository.exists(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        List<Order> orders = createOrdersFromMenuItemNames(customerId, menuItemNames);
        List<Recipe> recipes = getRecipesFromMenuItemNames(menuItemNames);

        ingredientsInventory.removeIngredients(recipes);
        orderRepository.addAll(orders);
    }

    public List<Order> getOrdersByCustomerId(CustomerID customerId) {
        if (!customerRepository.exists(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        return orderRepository.findByCustomerId(customerId);
    }

    private List<Order> createOrdersFromMenuItemNames(CustomerID customerId, List<MenuItemName> menuItemNames) {
        List<Order> orders = new ArrayList<>();

        for (MenuItemName menuItemName : menuItemNames) {
            MenuItem menuItem = menu.findByName(menuItemName);
            Order order = orderFactory.create(customerId, menuItem);
            orders.add(order);
        }

        return orders;
    }

    public List<Recipe> getRecipesFromMenuItemNames(List<MenuItemName> menuItemNames) {
        List<Recipe> recipes = new ArrayList<>();

        for (MenuItemName menuItemName : menuItemNames) {
            MenuItem menuItem = menu.findByName(menuItemName);
            Recipe recipe = menuItem.getRecipe();
            recipes.add(recipe);
        }

        return recipes;
    }
}
