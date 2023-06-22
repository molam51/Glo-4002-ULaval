package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.application.configuration.CoffeeShopConfiguration;
import ca.ulaval.glo4002.cafe.domain.reservation.DefaultReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.tax.*;
import ca.ulaval.glo4002.cafe.domain.tip.TipRate;
import ca.ulaval.glo4002.cafe.domain.tip.TipRateFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoffeeShopFactoryTest {

    private static final String A_COFFEE_SHOP_NAME = "Abstract Connoisseurs";
    private static final List<String> A_COFFEE_SHOP_CUBES_NAME = List.of("Hello", "Bye");
    private static final int A_COFFEE_SHOP_CUBE_SIZE = 5;
    private static final GroupReservationMethod A_GROUP_RESERVATION_METHOD = GroupReservationMethod.DEFAULT;
    private static final Country A_COUNTRY = Country.CA;
    private static final Province A_PROVINCE = Province.QC;
    private static final State A_STATE = State.TX;
    private static final double A_GROUP_TIP_RATE = 0;
    private static final ReservationStrategy A_RESERVATION_STRATEGY = new DefaultReservationStrategy();
    private static final TaxRate A_TAX_RATE = new TaxRate(0.05, 0.10);
    private static final TipRate A_TIP_RATE = new TipRate(0);

    private static CoffeeShopConfiguration coffeeShopConfiguration;
    private static CoffeeShopFactory coffeeShopFactory;

    @BeforeAll
    public static void setupBeforeAll() {
        ReservationStrategyFactory reservationStrategyFactory = mock(ReservationStrategyFactory.class);
        TaxRateFactory taxRateFactory = mock(TaxRateFactory.class);
        TipRateFactory tipRateFactory = mock(TipRateFactory.class);

        when(reservationStrategyFactory.create(A_GROUP_RESERVATION_METHOD)).thenReturn(A_RESERVATION_STRATEGY);
        when(taxRateFactory.create(A_COUNTRY, A_PROVINCE, A_STATE)).thenReturn(A_TAX_RATE);
        when(tipRateFactory.create(A_GROUP_TIP_RATE)).thenReturn(A_TIP_RATE);

        coffeeShopConfiguration = new CoffeeShopConfiguration(
                A_COFFEE_SHOP_NAME,
                A_COFFEE_SHOP_CUBES_NAME,
                A_COFFEE_SHOP_CUBE_SIZE,
                A_GROUP_RESERVATION_METHOD,
                A_COUNTRY,
                A_PROVINCE,
                A_STATE,
                A_GROUP_TIP_RATE);
        coffeeShopFactory = new CoffeeShopFactory(reservationStrategyFactory, taxRateFactory, tipRateFactory);
    }

    @Test
    public void givenCoffeeShopConfiguration_whenCreate_thenCoffeeShopIsCreatedBasedOnConfiguration() {
        CoffeeShop coffeeShop = coffeeShopFactory.create(coffeeShopConfiguration);

        List<Integer> expectedCoffeeShopCubesSize = List.of(A_COFFEE_SHOP_CUBE_SIZE, A_COFFEE_SHOP_CUBE_SIZE);
        assertEquals(A_COFFEE_SHOP_NAME, coffeeShop.getName());
        assertIterableEquals(A_COFFEE_SHOP_CUBES_NAME, getCoffeeShopCubesName(coffeeShop));
        assertIterableEquals(expectedCoffeeShopCubesSize, getCoffeeShopCubesSize(coffeeShop));
        assertEquals(A_RESERVATION_STRATEGY, coffeeShop.getReservationStrategy());
        assertEquals(A_TAX_RATE, coffeeShop.getTaxRate());
        assertEquals(A_TIP_RATE, coffeeShop.getTipRate());
    }

    private List<String> getCoffeeShopCubesName(CoffeeShop coffeeShop) {
        return coffeeShop.getCubes().stream()
                .map(Cube::getName)
                .toList();
    }

    private List<Integer> getCoffeeShopCubesSize(CoffeeShop coffeeShop) {
        return coffeeShop.getCubes().stream()
                .map(Cube::getSize)
                .toList();
    }
}
