package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShopRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.DuplicateGroupNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ReservationApplicationServiceTest {

    private static final String A_GROUP_NAME = "Foo";
    private static final String DUPLICATE_GROUP_NAME = "Bar";
    private static final int A_GROUP_SIZE = 2;
    private static final List<Integer> SOME_RESERVED_SEATS_NUMBER = List.of(1, 2);

    private Reservation reservation;
    private ReservationFactory reservationFactory;
    private ReservationRepository reservationRepository;
    private CoffeeShop coffeeShop;
    private CoffeeShopRepository coffeeShopRepository;
    private ReservationApplicationService reservationApplicationService;

    @BeforeEach
    public void setup() {
        setupMocks();

        reservationApplicationService = new ReservationApplicationService(
                reservationFactory,
                reservationRepository,
                coffeeShopRepository);
    }

    private void setupMocks() {
        reservation = mock(Reservation.class);
        reservationFactory = mock(ReservationFactory.class);
        reservationRepository = mock(ReservationRepository.class);
        coffeeShop = mock(CoffeeShop.class);
        coffeeShopRepository = mock(CoffeeShopRepository.class);
        when(reservationFactory.create(A_GROUP_NAME, A_GROUP_SIZE)).thenReturn(reservation);
        when(reservationRepository.exists(DUPLICATE_GROUP_NAME)).thenReturn(true);
        when(coffeeShop.reserveSeats(reservation)).thenReturn(SOME_RESERVED_SEATS_NUMBER);
        when(coffeeShopRepository.find()).thenReturn(coffeeShop);
    }

    @Test
    public void givenReservationInformation_whenAddReservation_thenReservedSeatsIsCalledOnCoffeeShop() {
        reservationApplicationService.addReservation(A_GROUP_NAME, A_GROUP_SIZE);

        verify(coffeeShop).reserveSeats(reservation);
    }

    @Test
    public void givenDuplicateGroupName_whenAddReservation_thenThrowDuplicateGroupNameException() {
        assertThrows(DuplicateGroupNameException.class, () -> {
            reservationApplicationService.addReservation(DUPLICATE_GROUP_NAME, A_GROUP_SIZE);
        });
    }

    @Test
    public void givenDuplicateGroupName_whenAddReservation_thenAddIsNotCalledOnReservationRepository() {
        try {
            reservationApplicationService.addReservation(DUPLICATE_GROUP_NAME, A_GROUP_SIZE);
        } catch (DuplicateGroupNameException ignored) {

        }

        verify(reservationRepository, never()).add(any());
    }

    @Test
    public void givenDuplicateGroupName_whenAddReservation_thenSaveIsNotCalledOnCoffeeShopRepository() {
        try {
            reservationApplicationService.addReservation(DUPLICATE_GROUP_NAME, A_GROUP_SIZE);
        } catch (DuplicateGroupNameException ignored) {

        }

        verify(coffeeShopRepository, never()).save(any());
    }

    @Test
    public void givenReservations_whenGetReservations_thenReservationsFromRepositoryAreReturned() {
        String aReservedGroupName = "Foo";
        String anotherReservedGroupName = "Bar";
        Reservation aReservation = new Reservation(aReservedGroupName, A_GROUP_SIZE);
        Reservation anotherReservation = new Reservation(anotherReservedGroupName, A_GROUP_SIZE);
        List<Reservation> expectedReservations = List.of(aReservation, anotherReservation);
        when(reservationRepository.findAll()).thenReturn(expectedReservations);

        List<Reservation> returnedReservations = reservationApplicationService.getReservations();

        assertEquals(expectedReservations, returnedReservations);
    }
}
