package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.application.configuration.CoffeeShopConfiguration;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShopFactory;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShopRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientsInventory;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.cafe.domain.tax.Country;
import ca.ulaval.glo4002.cafe.domain.tax.Province;
import ca.ulaval.glo4002.cafe.domain.tax.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CoffeeShopApplicationServiceTest {

    private static final String CONFIG_COFFEE_SHOP_NAME = "Foo";
    private static final List<String> CONFIG_COFFEE_SHOP_CUBES_NAME = List.of("Allô", "Bye");
    private static final int CONFIG_COFFEE_SHOP_CUBE_SIZE = 5;
    private static final GroupReservationMethod CONFIG_GROUP_RESERVATION_METHOD = GroupReservationMethod.DEFAULT;
    private static final Country CONFIG_COUNTRY = Country.CA;
    private static final Province CONFIG_PROVINCE = Province.QC;
    private static final State CONFIG_STATE = State.TX;
    private static final double CONFIG_GROUP_TIP_RATE = 0;

    private CustomerRepository customerRepository;
    private ReservationRepository reservationRepository;
    private CoffeeShopRepository coffeeShopRepository;
    private OrderRepository orderRepository;
    private BillRepository billRepository;
    private IngredientsInventory ingredientsInventory;
    private CoffeeShopFactory coffeeShopFactory;
    private CoffeeShop coffeeShop;
    private CoffeeShopApplicationService coffeeShopApplicationService;
    private CoffeeShopConfiguration coffeeShopConfiguration;

    @BeforeEach
    public void setup() {
        setupMocks();

        coffeeShopConfiguration = new CoffeeShopConfiguration(
                CONFIG_COFFEE_SHOP_NAME,
                CONFIG_COFFEE_SHOP_CUBES_NAME,
                CONFIG_COFFEE_SHOP_CUBE_SIZE,
                CONFIG_GROUP_RESERVATION_METHOD,
                CONFIG_COUNTRY,
                CONFIG_PROVINCE,
                CONFIG_STATE,
                CONFIG_GROUP_TIP_RATE);
        coffeeShopApplicationService = new CoffeeShopApplicationService(
                customerRepository,
                reservationRepository,
                coffeeShopRepository,
                orderRepository,
                billRepository,
                ingredientsInventory,
                coffeeShopFactory,
                coffeeShopConfiguration);
    }

    private void setupMocks() {
        customerRepository = mock(CustomerRepository.class);
        reservationRepository = mock(ReservationRepository.class);
        coffeeShopRepository = mock(CoffeeShopRepository.class);
        orderRepository = mock(OrderRepository.class);
        billRepository = mock(BillRepository.class);
        ingredientsInventory = mock(IngredientsInventory.class);
        coffeeShopFactory = mock(CoffeeShopFactory.class);
        coffeeShop = mock(CoffeeShop.class);
    }

    @Test
    public void whenClose_thenCoffeeShopIsClosed() {
        when(coffeeShopFactory.create(coffeeShopConfiguration)).thenReturn(coffeeShop);

        coffeeShopApplicationService.close();

        verifyCoffeeShopIsClosed();
    }

    @Test
    public void givenCoffeeShopInRepository_whenGetCoffeeShop_thenCoffeeShopFromRepositoryIsReturned() {
        when(coffeeShopRepository.find()).thenReturn(coffeeShop);

        CoffeeShop actualCoffeeShop = coffeeShopApplicationService.getCoffeeShop();

        CoffeeShop expectedCoffeeShop = coffeeShop;
        assertEquals(expectedCoffeeShop, actualCoffeeShop);
    }

    @Test
    public void givenConfiguration_whenConfigure_thenCoffeeShopIsClosed() {
        when(coffeeShopFactory.create(coffeeShopConfiguration)).thenReturn(coffeeShop);

        coffeeShopApplicationService.configure(coffeeShopConfiguration);

        verifyCoffeeShopIsClosed();
    }

    private void verifyCoffeeShopIsClosed() {
        verify(customerRepository).clear();
        verify(reservationRepository).clear();
        verify(orderRepository).clear();
        verify(billRepository).clear();
        verify(ingredientsInventory).clear();
        verify(coffeeShopRepository).save(coffeeShop);
    }
}
