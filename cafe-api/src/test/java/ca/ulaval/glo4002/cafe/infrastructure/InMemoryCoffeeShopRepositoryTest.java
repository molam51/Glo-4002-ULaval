package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.*;
import ca.ulaval.glo4002.cafe.domain.reservation.DefaultReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.domain.tip.TipRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

public class InMemoryCoffeeShopRepositoryTest {

    private static final String A_COFFEE_SHOP_NAME = "Teatime Chads";
    private static final ReservationStrategy A_RESERVATION_STRATEGY = new DefaultReservationStrategy();
    private static final CheckInStrategy A_CHECK_IN_STRATEGY = new DefaultCheckInStrategy();
    private static final CheckOutStrategy A_CHECK_OUT_STRATEGY = new DefaultCheckOutStrategy();
    private static final TaxRate A_TAX_RATE = new TaxRate(0.05, 0.10);
    private static final TipRate A_TIP_RATE = new TipRate(0);

    private CoffeeShopRepository coffeeShopRepository;

    @BeforeEach
    public void setup() {
        coffeeShopRepository = new InMemoryCoffeeShopRepository();
    }

    @Test
    public void givenCoffeeShop_whenSave_thenCoffeeShopIsAddedToRepository() {
        CoffeeShop coffeeShop = givenCoffeeShop();

        coffeeShopRepository.save(coffeeShop);

        CoffeeShop foundCoffeeShop = coffeeShopRepository.find();
        assertSame(coffeeShop, foundCoffeeShop);
    }

    @Test
    public void givenUpdatedCoffeeShop_whenSave_thenCoffeeShopIsUpdatedInRepository() {
        CoffeeShop coffeeShop = givenCoffeeShop();
        CoffeeShop updatedCoffeeShop = givenCoffeeShop();
        coffeeShopRepository.save(coffeeShop);

        coffeeShopRepository.save(updatedCoffeeShop);

        CoffeeShop foundCoffeeShop = coffeeShopRepository.find();
        assertSame(updatedCoffeeShop, foundCoffeeShop);
    }

    private CoffeeShop givenCoffeeShop() {
        return new CoffeeShop(A_COFFEE_SHOP_NAME, List.of(), A_RESERVATION_STRATEGY, A_CHECK_IN_STRATEGY,
                A_CHECK_OUT_STRATEGY, A_TAX_RATE, A_TIP_RATE);
    }
}
