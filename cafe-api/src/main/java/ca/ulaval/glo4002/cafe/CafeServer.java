package ca.ulaval.glo4002.cafe;

import ca.ulaval.glo4002.cafe.application.*;
import ca.ulaval.glo4002.cafe.application.configuration.CoffeeShopConfiguration;
import ca.ulaval.glo4002.cafe.application.configuration.CoffeeShopConfigurationFactory;
import ca.ulaval.glo4002.cafe.domain.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShopFactory;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShopRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientsInventory;
import ca.ulaval.glo4002.cafe.domain.menu.Menu;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemFactory;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;
import ca.ulaval.glo4002.cafe.domain.menu.RecipeFactory;
import ca.ulaval.glo4002.cafe.domain.order.OrderFactory;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.tax.Country;
import ca.ulaval.glo4002.cafe.domain.tax.Province;
import ca.ulaval.glo4002.cafe.domain.tax.State;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRateFactory;
import ca.ulaval.glo4002.cafe.domain.tip.TipRateFactory;
import ca.ulaval.glo4002.cafe.infrastructure.*;
import ca.ulaval.glo4002.cafe.interfaces.rest.CloseResource;
import ca.ulaval.glo4002.cafe.interfaces.rest.config.ConfigResource;
import ca.ulaval.glo4002.cafe.interfaces.rest.config.mappers.InvalidCountryExceptionMapper;
import ca.ulaval.glo4002.cafe.interfaces.rest.config.mappers.InvalidGroupTipRateExceptionMapper;
import ca.ulaval.glo4002.cafe.interfaces.rest.customer.CustomerResource;
import ca.ulaval.glo4002.cafe.interfaces.rest.customer.GetBillByCustomerIdResponseAssembler;
import ca.ulaval.glo4002.cafe.interfaces.rest.customer.GetCustomerResponseAssembler;
import ca.ulaval.glo4002.cafe.interfaces.rest.customer.GetOrdersByCustomerIdResponseAssembler;
import ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers.*;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import ca.ulaval.glo4002.cafe.interfaces.rest.inventory.GetInventoryResponseAssembler;
import ca.ulaval.glo4002.cafe.interfaces.rest.inventory.InventoryResource;
import ca.ulaval.glo4002.cafe.interfaces.rest.inventory.mappers.InsufficientIngredientsExceptionMapper;
import ca.ulaval.glo4002.cafe.interfaces.rest.layout.CubeInGetLayoutResponseAssembler;
import ca.ulaval.glo4002.cafe.interfaces.rest.layout.GetLayoutResponseAssembler;
import ca.ulaval.glo4002.cafe.interfaces.rest.layout.LayoutResource;
import ca.ulaval.glo4002.cafe.interfaces.rest.layout.SeatInGetLayoutResponseAssembler;
import ca.ulaval.glo4002.cafe.interfaces.rest.layout.mappers.InsufficientSeatsExceptionMapper;
import ca.ulaval.glo4002.cafe.interfaces.rest.reservation.GetReservationsResponseAssembler;
import ca.ulaval.glo4002.cafe.interfaces.rest.reservation.ReservationResource;
import ca.ulaval.glo4002.cafe.interfaces.rest.reservation.mappers.DuplicateGroupNameExceptionMapper;
import ca.ulaval.glo4002.cafe.interfaces.rest.reservation.mappers.InvalidGroupReservationMethodExceptionMapper;
import ca.ulaval.glo4002.cafe.interfaces.rest.reservation.mappers.InvalidGroupSizeExceptionMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.List;

public class CafeServer implements Runnable {

    private static final int PORT = 8181;
    private static final String PACKAGE = "ca.ulaval.glo4002.cafe";

    private static final String DEFAULT_COFFEE_SHOP_NAME = "Les 4-Fées";
    private static final int DEFAULT_COFFEE_SHOP_CUBE_SIZE = 4;
    private static final GroupReservationMethod DEFAULT_GROUP_RESERVATION_METHOD = GroupReservationMethod.DEFAULT;
    private static final Country DEFAULT_COUNTRY = Country.None;
    private static final Province DEFAULT_PROVINCE = null;
    private static final State DEFAULT_STATE = null;
    private static final double DEFAULT_GROUP_TIP_RATE = 0;
    private static final IngredientName ESPRESSO = new IngredientName("Espresso");
    private static final IngredientName WATER = new IngredientName("Water");
    private static final IngredientName CHOCOLATE = new IngredientName("Chocolate");
    private static final IngredientName MILK = new IngredientName("Milk");

    public static void main(String[] args) {
        new CafeServer().run();
    }

    public void run() {
        CustomerFactory customerFactory = new CustomerFactory();
        CustomerRepository customerRepository = new InMemoryCustomerRepository();
        ReservationFactory reservationFactory = new ReservationFactory();
        ReservationRepository reservationRepository = new InMemoryReservationRepository();
        ReservationStrategyFactory reservationStrategyFactory = new ReservationStrategyFactory();
        TaxRateFactory taxRateFactory = new TaxRateFactory();
        TipRateFactory tipRateFactory = new TipRateFactory();
        OrderFactory orderFactory = new OrderFactory();
        OrderRepository orderRepository = new InMemoryOrderRepository();
        BillFactory billFactory = new BillFactory();
        BillRepository billRepository = new InMemoryBillRepository();
        Menu menu = initializeInMemoryMenu();
        IngredientsInventory ingredientsInventory = initializeInMemoryIngredientsInventory();
        CoffeeShopFactory coffeeShopFactory = new CoffeeShopFactory(reservationStrategyFactory, taxRateFactory, tipRateFactory);
        CoffeeShopRepository coffeeShopRepository = new InMemoryCoffeeShopRepository();
        CoffeeShopConfigurationFactory coffeeShopConfigurationFactory = new CoffeeShopConfigurationFactory();
        CoffeeShopConfiguration coffeeShopConfiguration = coffeeShopConfigurationFactory.create(
                DEFAULT_COFFEE_SHOP_NAME,
                DEFAULT_COFFEE_SHOP_CUBE_SIZE,
                DEFAULT_GROUP_RESERVATION_METHOD,
                DEFAULT_COUNTRY,
                DEFAULT_PROVINCE,
                DEFAULT_STATE,
                DEFAULT_GROUP_TIP_RATE);

        CoffeeShop coffeeShop = coffeeShopFactory.create(coffeeShopConfiguration);
        coffeeShopRepository.save(coffeeShop);

        CoffeeShopApplicationService coffeeShopApplicationService = new CoffeeShopApplicationService(
                customerRepository,
                reservationRepository,
                coffeeShopRepository,
                orderRepository,
                billRepository,
                ingredientsInventory,
                coffeeShopFactory,
                coffeeShopConfiguration);
        CustomerApplicationService customerApplicationService = new CustomerApplicationService(
                customerFactory,
                customerRepository,
                reservationRepository,
                coffeeShopRepository,
                orderRepository,
                billFactory,
                billRepository);
        ReservationApplicationService reservationApplicationService = new ReservationApplicationService(
                reservationFactory,
                reservationRepository,
                coffeeShopRepository);
        OrderApplicationService orderApplicationService = new OrderApplicationService(
                orderFactory,
                orderRepository,
                customerRepository,
                menu,
                ingredientsInventory);
        BillApplicationService billApplicationService = new BillApplicationService(
                billRepository,
                customerRepository);
        InventoryApplicationService inventoryApplicationService = new InventoryApplicationService(
                ingredientsInventory);

        SeatInGetLayoutResponseAssembler seatAssembler = new SeatInGetLayoutResponseAssembler();
        CubeInGetLayoutResponseAssembler cubeAssembler = new CubeInGetLayoutResponseAssembler(seatAssembler);
        GetLayoutResponseAssembler getLayoutResponseAssembler = new GetLayoutResponseAssembler(cubeAssembler);
        GetCustomerResponseAssembler getCustomerResponseAssembler = new GetCustomerResponseAssembler();
        GetReservationsResponseAssembler getReservationsResponseAssembler = new GetReservationsResponseAssembler();
        GetOrdersByCustomerIdResponseAssembler getOrdersByCustomerIdResponseAssembler =
                new GetOrdersByCustomerIdResponseAssembler();
        GetBillByCustomerIdResponseAssembler getBillByCustomerIdResponseAssembler =
                new GetBillByCustomerIdResponseAssembler();
        GetInventoryResponseAssembler getInventoryResponseAssembler =
                new GetInventoryResponseAssembler();
        ErrorResponseAssembler errorResponseAssembler = new ErrorResponseAssembler();

        LayoutResource layoutResource = new LayoutResource(coffeeShopApplicationService, getLayoutResponseAssembler);
        CustomerResource customerResource = new CustomerResource(
                customerApplicationService,
                orderApplicationService,
                billApplicationService,
                getCustomerResponseAssembler,
                getOrdersByCustomerIdResponseAssembler,
                getBillByCustomerIdResponseAssembler);
        ReservationResource reservationResource = new ReservationResource(reservationApplicationService, getReservationsResponseAssembler);
        CloseResource closeResource = new CloseResource(coffeeShopApplicationService);
        ConfigResource configResource = new ConfigResource(
                coffeeShopApplicationService,
                coffeeShopConfigurationFactory);
        InventoryResource inventoryResource = new InventoryResource(
                inventoryApplicationService,
                getInventoryResponseAssembler);

        InvalidCustomerIdExceptionMapper invalidCustomerIdExceptionMapper =
                new InvalidCustomerIdExceptionMapper(errorResponseAssembler);
        DuplicateCustomerIdExceptionMapper duplicateCustomerIdExceptionMapper =
                new DuplicateCustomerIdExceptionMapper(errorResponseAssembler);
        InsufficientSeatsExceptionMapper insufficientSeatsExceptionMapper =
                new InsufficientSeatsExceptionMapper(errorResponseAssembler);
        DuplicateGroupNameExceptionMapper duplicateGroupNameExceptionMapper =
                new DuplicateGroupNameExceptionMapper(errorResponseAssembler);
        InvalidGroupSizeExceptionMapper invalidGroupSizeExceptionMapper =
                new InvalidGroupSizeExceptionMapper(errorResponseAssembler);
        NoReservationsFoundExceptionMapper noReservationsFoundExceptionMapper =
                new NoReservationsFoundExceptionMapper(errorResponseAssembler);
        NoGroupSeatsExceptionMapper noGroupSeatsExceptionMapper =
                new NoGroupSeatsExceptionMapper(errorResponseAssembler);
        InvalidGroupReservationMethodExceptionMapper invalidGroupReservationMethodExceptionMapper =
                new InvalidGroupReservationMethodExceptionMapper(errorResponseAssembler);
        MenuItemNotFoundExceptionMapper menuItemNotFoundExceptionMapper =
                new MenuItemNotFoundExceptionMapper(errorResponseAssembler);
        BillNotFoundExceptionMapper billNotFoundExceptionMapper =
                new BillNotFoundExceptionMapper(errorResponseAssembler);
        InvalidCountryExceptionMapper invalidCountryExceptionMapper =
                new InvalidCountryExceptionMapper(errorResponseAssembler);
        InvalidGroupTipRateExceptionMapper invalidGroupTipRateExceptionMapper =
                new InvalidGroupTipRateExceptionMapper(errorResponseAssembler);
        InsufficientIngredientsExceptionMapper insufficientIngredientsExceptionMapper =
                new InsufficientIngredientsExceptionMapper(errorResponseAssembler);

        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
        ResourceConfig packageConfig = new ResourceConfig().packages(PACKAGE)
                .register(layoutResource)
                .register(duplicateCustomerIdExceptionMapper)
                .register(insufficientSeatsExceptionMapper)
                .register(customerResource)
                .register(closeResource)
                .register(invalidCustomerIdExceptionMapper)
                .register(duplicateGroupNameExceptionMapper)
                .register(invalidGroupSizeExceptionMapper)
                .register(noReservationsFoundExceptionMapper)
                .register(noGroupSeatsExceptionMapper)
                .register(reservationResource)
                .register(invalidGroupReservationMethodExceptionMapper)
                .register(configResource)
                .register(menuItemNotFoundExceptionMapper)
                .register(billNotFoundExceptionMapper)
                .register(invalidCountryExceptionMapper)
                .register(invalidGroupTipRateExceptionMapper)
                .register(insufficientIngredientsExceptionMapper)
                .register(inventoryResource);

        ServletContainer container = new ServletContainer(packageConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addServlet(servletHolder, "/*");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (server.isRunning()) {
                server.destroy();
            }
        }
    }

    private Menu initializeInMemoryMenu() {
        Menu menu = new InMemoryMenu();
        RecipeFactory recipeFactory = new RecipeFactory();
        MenuItemFactory menuItemFactory = new MenuItemFactory(recipeFactory);

        menu.add(menuItemFactory.create(new MenuItemName("Americano")));
        menu.add(menuItemFactory.create(new MenuItemName("Dark Roast")));
        menu.add(menuItemFactory.create(new MenuItemName("Cappuccino")));
        menu.add(menuItemFactory.create(new MenuItemName("Espresso")));
        menu.add(menuItemFactory.create(new MenuItemName("Flat White")));
        menu.add(menuItemFactory.create(new MenuItemName("Latte")));
        menu.add(menuItemFactory.create(new MenuItemName("Macchiato")));
        menu.add(menuItemFactory.create(new MenuItemName("Mocha")));

        return menu;
    }

    private IngredientsInventory initializeInMemoryIngredientsInventory() {
        IngredientsInventory ingredientsInventory = new InMemoryIngredientsInventory();

        ingredientsInventory.addIngredients(List.of(
                new Ingredient(CHOCOLATE, new Quantity(0)),
                new Ingredient(ESPRESSO, new Quantity(0)),
                new Ingredient(MILK, new Quantity(0)),
                new Ingredient(WATER, new Quantity(0))
        ));

        return ingredientsInventory;
    }
}
