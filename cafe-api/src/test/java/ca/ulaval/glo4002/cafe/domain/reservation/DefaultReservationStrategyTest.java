package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.Cube;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.Seat;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.SeatState;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions.InsufficientSeatsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultReservationStrategyTest {

    private static final Random RANDOM = new Random();
    private static final int A_GROUP_SIZE = 2;
    private static final String A_GROUP_NAME = "The Avengers";
    private static final String A_CUBE_NAME = "Cube1";

    private Reservation reservation;
    private ReservationStrategy reservationStrategy;

    @BeforeEach
    public void setup() {
        reservation = new Reservation(A_GROUP_NAME, A_GROUP_SIZE);
        reservationStrategy = new DefaultReservationStrategy();
    }

    @Test
    public void givenReservationAndEnoughAvailableSeats_whenReserveSeats_thenReturnAvailableSeatsNumber() {
        Seat firstSeat = givenAvailableSeat();
        Seat secondSeat = givenAvailableSeat();
        CoffeeShop coffeeShop = givenCoffeeShopWithAvailableSeats(firstSeat, secondSeat);

        List<Integer> reservedSeatsNumber = reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        List<Integer> expectedSeatsNumber = List.of(firstSeat.getNumber(), secondSeat.getNumber());
        assertIterableEquals(expectedSeatsNumber, reservedSeatsNumber);
    }

    @Test
    public void givenReservationAndMoreThanEnoughAvailableSeats_whenReserveSeats_thenReturnJustEnoughAvailableSeats() {
        Seat firstSeat = givenAvailableSeat();
        Seat secondSeat = givenAvailableSeat();
        CoffeeShop coffeeShop = givenCoffeeShopWithAvailableSeats(firstSeat, secondSeat, givenAvailableSeat());

        List<Integer> reservedSeatsNumber = reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        List<Integer> expectedSeatsNumber = List.of(firstSeat.getNumber(), secondSeat.getNumber());
        assertIterableEquals(expectedSeatsNumber, reservedSeatsNumber);
    }

    @Test
    public void givenReservationAndNotEnoughAvailableSeats_whenReserveSeats_thenThrowInsufficientSeatsException() {
        CoffeeShop coffeeShop = givenCoffeeShopWithAvailableSeats(givenAvailableSeat());

        assertThrows(InsufficientSeatsException.class, () -> {
            reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());
        });
    }

    @Test
    public void givenSeats_whenReserveSeats_thenSeatsAreReserved() {
        Seat firstSeat = givenAvailableSeat();
        Seat secondSeat = givenAvailableSeat();
        CoffeeShop coffeeShop = givenCoffeeShopWithAvailableSeats(firstSeat, secondSeat);

        reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        assertEquals(SeatState.RESERVED, firstSeat.getState());
        assertEquals(SeatState.RESERVED, secondSeat.getState());
    }

    private CoffeeShop givenCoffeeShopWithAvailableSeats(Seat... availableSeats) {
        CoffeeShop coffeeShop = mock(CoffeeShop.class);
        Cube cube = new Cube(A_CUBE_NAME, List.of(availableSeats));
        List<Cube> cubes = List.of(cube);
        when(coffeeShop.getCubes()).thenReturn(cubes);

        return coffeeShop;
    }

    private Seat givenAvailableSeat() {
        return new Seat(RANDOM.nextInt());
    }
}
