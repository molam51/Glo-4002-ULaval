package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.domain.tip.TipRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

class CoffeeShopTest {

    private static final Random RANDOM = new Random();
    private static final String A_COFFEE_SHOP_NAME = "foo";
    private static final TaxRate A_TAX_RATE = new TaxRate(0.05, 0.10);
    private static final TipRate A_TIP_RATE = new TipRate(0);
    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final String A_CUSTOMER_NAME = "Mark";
    private static final String A_GROUP_NAME = "The Avengers";
    private static final int A_GROUP_SIZE = 2;

    private ReservationStrategy reservationStrategy;
    private CheckInStrategy checkInStrategy;
    private CheckOutStrategy checkOutStrategy;

    @BeforeEach
    public void setup() {
        reservationStrategy = mock(ReservationStrategy.class);
        checkInStrategy = mock(CheckInStrategy.class);
        checkOutStrategy = mock(CheckOutStrategy.class);
    }

    @Test
    public void givenCustomer_whenAssignCustomerToSeat_thenReturnCheckedInSeatNumber() {
        CoffeeShop coffeeShop = givenCoffeeShop();
        Customer customer = givenCustomer();
        int checkInSeatNumber = RANDOM.nextInt();
        when(checkInStrategy.checkIn(customer, coffeeShop.getCubes())).thenReturn(checkInSeatNumber);

        int returnedSeatNumber = coffeeShop.assignCustomerToSeat(customer);

        assertEquals(checkInSeatNumber, returnedSeatNumber);
    }

    @Test
    public void givenReservationAndAvailableSeats_whenReserveSeats_thenReturnReservedSeatsNumber() {
        Reservation reservation = givenReservation();
        CoffeeShop coffeeShop = givenCoffeeShop();
        int reservedSeatNumber1 = RANDOM.nextInt();
        int reservedSeatNumber2 = RANDOM.nextInt();
        when(reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes()))
                .thenReturn(List.of(reservedSeatNumber1, reservedSeatNumber2));

        List<Integer> returnedSeatsNumber = coffeeShop.reserveSeats(reservation);

        List<Integer> expectedSeatsNumber = List.of(reservedSeatNumber1, reservedSeatNumber2);
        assertIterableEquals(expectedSeatsNumber, returnedSeatsNumber);
    }

    @Test
    public void givenCustomer_whenCheckOut_thenCheckOutIsCalledOnCheckOutStrategy() {
        CoffeeShop coffeeShop = givenCoffeeShop();
        Customer customer = givenCustomer();

        coffeeShop.checkOut(customer);

        verify(checkOutStrategy).checkOut(customer, coffeeShop.getCubes());
    }

    private Reservation givenReservation() {
        return new Reservation(A_GROUP_NAME, A_GROUP_SIZE);
    }

    private CoffeeShop givenCoffeeShop() {
        return new CoffeeShop(
                A_COFFEE_SHOP_NAME,
                Collections.emptyList(),
                reservationStrategy,
                checkInStrategy,
                checkOutStrategy,
                A_TAX_RATE,
                A_TIP_RATE);
    }

    private Customer givenCustomer() {
        return new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);
    }
}
