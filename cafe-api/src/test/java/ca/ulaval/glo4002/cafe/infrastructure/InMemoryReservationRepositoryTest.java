package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.ReservationAlreadyInRepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryReservationRepositoryTest {

    private static final String A_GROUP_NAME = "Runtime Terror";
    private static final int A_GROUP_SIZE = 3;
    private static final String ANOTHER_GROUP_NAME = "Abstract Connoisseurs";

    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setup() {
        reservationRepository = new InMemoryReservationRepository();
    }

    @Test
    public void givenReservation_whenAdd_thenReservationIsAddedToRepository() {
        Reservation reservation = givenReservation();

        reservationRepository.add(reservation);

        assertTrue(reservationRepository.exists(reservation.getGroupName()));
    }

    @Test
    public void givenReservationAlreadyInRepository_whenAdd_thenThrowReservationAlreadyInRepositoryException() {
        Reservation reservation = givenReservation();
        reservationRepository.add(reservation);

        assertThrows(ReservationAlreadyInRepositoryException.class, () -> {
            reservationRepository.add(reservation);
        });
    }

    @Test
    public void givenReservationAlreadyInRepository_whenExists_thenReturnTrue() {
        Reservation reservation = givenReservation();
        reservationRepository.add(reservation);

        boolean exists = reservationRepository.exists(A_GROUP_NAME);

        assertTrue(exists);
    }

    @Test
    public void givenReservationNotInRepository_whenExists_thenReturnFalse() {
        Reservation anotherReservation = givenAnotherReservation();
        reservationRepository.add(anotherReservation);

        boolean exists = reservationRepository.exists(A_GROUP_NAME);

        assertFalse(exists);
    }

    @Test
    public void givenMultipleReservationsInRepository_whenClear_thenRepositoryIsEmpty() {
        Reservation reservation = givenReservation();
        Reservation anotherReservation = givenAnotherReservation();
        reservationRepository.add(reservation);
        reservationRepository.add(anotherReservation);

        reservationRepository.clear();

        assertFalse(reservationRepository.exists(reservation.getGroupName()));
        assertFalse(reservationRepository.exists(anotherReservation.getGroupName()));
    }

    @Test
    public void givenEmptyRepository_whenFindAll_thenReturnNoReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        assertEquals(List.of(), reservations);
    }

    @Test
    public void givenMultipleReservationsInRepository_whenFindAll_thenReturnAllReservations() {
        Reservation reservation = givenReservation();
        Reservation anotherReservation = givenAnotherReservation();
        reservationRepository.add(reservation);
        reservationRepository.add(anotherReservation);

        List<Reservation> reservations = reservationRepository.findAll();

        assertEquals(List.of(reservation, anotherReservation), reservations);
    }

    private Reservation givenReservation() {
        return new Reservation(A_GROUP_NAME, A_GROUP_SIZE);
    }

    private Reservation givenAnotherReservation() {
        return new Reservation(ANOTHER_GROUP_NAME, A_GROUP_SIZE);
    }
}
