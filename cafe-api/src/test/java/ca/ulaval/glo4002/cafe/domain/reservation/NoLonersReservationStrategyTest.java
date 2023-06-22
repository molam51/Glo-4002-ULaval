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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NoLonersReservationStrategyTest {

    private static final Random RANDOM = new Random();
    private static final String A_GROUP_NAME = "blah blah blah";
    private static final int GROUP_SIZE_OF_TWO = 2;
    private static final int NOT_SPLITTABLE_GROUP_SIZE = 3;
    private static final int SPLITTABLE_GROUP_SIZE = 4;
    private static final String A_CUBE_NAME = "Tinker Bell";

    private ReservationStrategy reservationStrategy;

    @BeforeEach
    public void setup() {
        reservationStrategy = new NoLonersReservationStrategy();
    }

    @Test
    public void givenTwoCubesAndGroupFitsInOnlyOneCube_whenReserveSeats_thenReturnSeatsFromFirstCube() {
        Reservation reservation = new Reservation(A_GROUP_NAME, GROUP_SIZE_OF_TWO);
        Cube firstCube = givenCubeWithSeats(givenAvailableSeat(), givenAvailableSeat());
        Cube secondCube = givenCubeWithSeats(givenAvailableSeat(), givenAvailableSeat());
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(firstCube, secondCube);

        List<Integer> reservedSeatsNumber = reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        List<Integer> expectedSeatsNumber = getSeatsNumber(firstCube.getSeats().toArray(new Seat[0]));
        assertIterableEquals(expectedSeatsNumber, reservedSeatsNumber);
    }

    @Test
    public void givenCubeHasOneAvailableSeat_whenReserveSeats_thenReturnSeatsFromNextCube() {
        Reservation reservation = new Reservation(A_GROUP_NAME, GROUP_SIZE_OF_TWO);
        Cube cubeWithOneAvailableSeat = givenCubeWithSeats(givenAvailableSeat());
        Cube nextCube = givenCubeWithSeats(givenAvailableSeat(), givenAvailableSeat());
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(cubeWithOneAvailableSeat, nextCube);

        List<Integer> reservedSeatsNumber = reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        List<Integer> expectedSeatsNumber = getSeatsNumber(nextCube.getSeats().toArray(new Seat[0]));
        assertIterableEquals(expectedSeatsNumber, reservedSeatsNumber);
    }

    @Test
    public void givenCubeWillLeaveOneCustomerAloneAndNextCubeDoesNotHaveEnoughAvailableSeats_whenReserveSeats_thenThrowInsufficientSeatsException() {
        Reservation reservation = new Reservation(A_GROUP_NAME, NOT_SPLITTABLE_GROUP_SIZE);
        Cube firstCube = givenCubeWithSeats(givenAvailableSeat(), givenAvailableSeat());
        Cube secondCube = givenCubeWithSeats(givenAvailableSeat());
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(firstCube, secondCube);

        assertThrows(InsufficientSeatsException.class, () -> {
            reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());
        });
    }

    @Test
    public void givenTwoCubesAndGroupDoesNotFitInOnlyOneCube_whenReserveSeats_thenReturnSeatsFromBothCubes() {
        Reservation reservation = new Reservation(A_GROUP_NAME, SPLITTABLE_GROUP_SIZE);
        Cube firstCube = givenCubeWithSeats(givenAvailableSeat(), givenAvailableSeat());
        Cube secondCube = givenCubeWithSeats(givenAvailableSeat(), givenAvailableSeat());
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(firstCube, secondCube);

        List<Integer> reservedSeatsNumber = reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        List<Seat> expectedSeats = Stream.concat(firstCube.getSeats().stream(), secondCube.getSeats().stream()).toList();
        List<Integer> expectedSeatsNumber = getSeatsNumber(expectedSeats.toArray(new Seat[0]));
        assertIterableEquals(expectedSeatsNumber, reservedSeatsNumber);
    }

    @Test
    public void givenCoffeeShopDoesNotHaveEnoughAvailableSeats_whenReserveSeats_thenThrowInsufficientSeatsException() {
        Reservation reservation = new Reservation(A_GROUP_NAME, NOT_SPLITTABLE_GROUP_SIZE);
        Cube cube = givenCubeWithSeats(givenAvailableSeat(), givenAvailableSeat());
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(cube);

        assertThrows(InsufficientSeatsException.class, () -> {
            reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());
        });
    }

    @Test
    public void givenCubeWillLeaveOneCustomerAloneAndNotSplittableGroup_whenReserveSeats_thenReturnSeatsFromNextCube() {
        Reservation reservation = new Reservation(A_GROUP_NAME, NOT_SPLITTABLE_GROUP_SIZE);
        Cube firstCube = givenCubeWithSeats(givenAvailableSeat(), givenAvailableSeat());
        Cube nextCube = givenCubeWithSeats(givenAvailableSeat(), givenAvailableSeat(), givenAvailableSeat());
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(firstCube, nextCube);

        List<Integer> reservedSeatsNumber = reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        List<Integer> expectedSeatsNumber = getSeatsNumber(nextCube.getSeats().toArray(new Seat[0]));
        assertIterableEquals(expectedSeatsNumber, reservedSeatsNumber);
    }

    @Test
    public void givenCubeWillLeaveOneCustomerAloneAndSplittableGroup_whenReserveSeats_thenReturnsSeatsFromBothCubes() {
        Reservation reservation = new Reservation(A_GROUP_NAME, SPLITTABLE_GROUP_SIZE);
        Seat seat1 = givenAvailableSeat();
        Seat seat2 = givenAvailableSeat();
        Seat seat4 = givenAvailableSeat();
        Seat seat5 = givenAvailableSeat();
        Cube firstCube = givenCubeWithSeats(seat1, seat2, givenAvailableSeat());
        Cube secondCube = givenCubeWithSeats(seat4, seat5);
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(firstCube, secondCube);

        List<Integer> reservedSeatsNumber = reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        List<Integer> expectedSeatsNumber = getSeatsNumber(seat1, seat2, seat4, seat5);
        assertIterableEquals(expectedSeatsNumber, reservedSeatsNumber);
    }

    @Test
    public void givenCubeWithMoreThanEnoughAvailableSeats_whenReserveSeats_thenReturnJustEnoughSeats() {
        Reservation reservation = new Reservation(A_GROUP_NAME, GROUP_SIZE_OF_TWO);
        Seat seat1 = givenAvailableSeat();
        Seat seat2 = givenAvailableSeat();
        Cube cube = givenCubeWithSeats(seat1, seat2, givenAvailableSeat());
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(cube);

        List<Integer> reservedSeatsNumber = reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        List<Integer> expectedSeatsNumber = getSeatsNumber(seat1, seat2);
        assertIterableEquals(expectedSeatsNumber, reservedSeatsNumber);
    }

    @Test
    public void givenSeats_whenReserveSeats_thenSeatsAreReserved() {
        Reservation reservation = new Reservation(A_GROUP_NAME, GROUP_SIZE_OF_TWO);
        Seat seat1 = givenAvailableSeat();
        Seat seat2 = givenAvailableSeat();
        Cube cube = givenCubeWithSeats(seat1, seat2);
        CoffeeShop coffeeShop = givenCoffeeShopWithCubes(cube);

        reservationStrategy.reserveSeats(reservation, coffeeShop.getCubes());

        assertEquals(SeatState.RESERVED, seat1.getState());
        assertEquals(SeatState.RESERVED, seat2.getState());
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

    private List<Integer> getSeatsNumber(Seat... seats) {
        return Arrays.stream(seats)
                .map(Seat::getNumber)
                .toList();
    }
}
