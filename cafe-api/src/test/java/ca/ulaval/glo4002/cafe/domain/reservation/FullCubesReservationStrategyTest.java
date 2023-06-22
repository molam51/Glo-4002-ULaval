package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.Cube;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.Seat;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.SeatState;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions.InsufficientSeatsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FullCubesReservationStrategyTest {

    private static final Random RANDOM = new Random();
    private static final String A_GROUP_NAME = "RUNTIME_TERROR";
    private static final int A_GROUP_SIZE = 2;
    private static final String A_CUBE_NAME = "foo";

    private Reservation reservation;
    private ReservationStrategy reservationStrategy;

    @BeforeEach
    public void setup() {
        reservation = new Reservation(A_GROUP_NAME, A_GROUP_SIZE);
        reservationStrategy = new FullCubesReservationStrategy();
    }

    @Test
    void givenNoFullyAvailableCubes_whenReserveSeats_thenThrowInsufficientSeatsException() {
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes();

        assertThrows(InsufficientSeatsException.class, () -> {
            reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());
        });
    }

    @Test
    void givenFullyAvailableCubeWithoutEnoughSeats_whenReserveSeats_thenThrowInsufficientSeatsException() {
        Cube fullyAvailableCube = givenCubeWithSeats(givenAvailableSeat());
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(fullyAvailableCube);

        assertThrows(InsufficientSeatsException.class, () -> {
            reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());
        });
    }

    @Test
    void givenJustEnoughFullyAvailableCube_whenReserveSeats_thenReturnSeatsInCube() {
        Seat firstAvailableSeat = givenAvailableSeat();
        Seat secondAvailableSeat = givenAvailableSeat();
        Cube fullyAvailableCube = givenCubeWithSeats(firstAvailableSeat, secondAvailableSeat);
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(fullyAvailableCube);

        List<Integer> reservedSeatsNumber = reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        List<Integer> expectedSeatsNumber = List.of(firstAvailableSeat.getNumber(), secondAvailableSeat.getNumber());
        assertIterableEquals(expectedSeatsNumber, reservedSeatsNumber);
    }

    @Test
    void givenMoreThanEnoughFullyAvailableCubes_whenReserveSeats_thenReturnOnlySeatsFromNeededCubes() {
        Seat firstAvailableSeat = givenAvailableSeat();
        Seat secondAvailableSeat = givenAvailableSeat();
        Cube firstFullyAvailableCube = givenCubeWithSeats(firstAvailableSeat, secondAvailableSeat);
        Cube secondFullyAvailableCube = givenCubeWithSeats(givenAvailableSeat(), givenAvailableSeat());
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(firstFullyAvailableCube, secondFullyAvailableCube);

        List<Integer> reservedSeatsNumber = reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        List<Integer> expectedSeatsNumber = List.of(firstAvailableSeat.getNumber(), secondAvailableSeat.getNumber());
        assertEquals(expectedSeatsNumber, reservedSeatsNumber);
    }

    @Test
    public void givenSeats_whenReserveSeats_thenSeatsAreReserved() {
        Seat firstAvailableSeat = givenAvailableSeat();
        Seat secondAvailableSeat = givenAvailableSeat();
        Cube fullyAvailableCube = givenCubeWithSeats(firstAvailableSeat, secondAvailableSeat);
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(fullyAvailableCube);

        reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        assertEquals(SeatState.RESERVED, firstAvailableSeat.getState());
        assertEquals(SeatState.RESERVED, secondAvailableSeat.getState());
    }

    private CoffeeShop givenCoffeeShopWithCubes(Cube... cubes) {
        CoffeeShop coffeeShop = mock(CoffeeShop.class);
        when(coffeeShop.getCubes()).thenReturn(Arrays.stream(cubes).toList());

        return coffeeShop;
    }

    private Cube givenCubeWithSeats(Seat... seats) {
        return new Cube(A_CUBE_NAME, Arrays.stream(seats).toList());
    }

    private Seat givenAvailableSeat() {
        return new Seat(RANDOM.nextInt());
    }
}
