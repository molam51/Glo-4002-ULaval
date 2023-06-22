package ca.ulaval.glo4002.cafe.domain.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationStrategyFactoryTest {

    private ReservationStrategyFactory reservationStrategyFactory;

    @BeforeEach
    public void setup() {
        reservationStrategyFactory = new ReservationStrategyFactory();
    }

    @Test
    public void givenMethodIsDefault_whenCreate_thenReturnInstanceOfDefaultReservationStrategy() {
        ReservationStrategy instance = reservationStrategyFactory.create(GroupReservationMethod.DEFAULT);

        assertEquals(DefaultReservationStrategy.class, instance.getClass());
    }

    @Test
    public void givenMethodIsFullCubes_whenCreate_thenReturnInstanceOfFullCubesReservationStrategy() {
        ReservationStrategy instance = reservationStrategyFactory.create(GroupReservationMethod.FULL_CUBES);

        assertEquals(FullCubesReservationStrategy.class, instance.getClass());
    }

    @Test
    public void givenMethodIsNoLoners_whenCreate_thenReturnInstanceOfNoLonersReservationStrategy() {
        ReservationStrategy instance = reservationStrategyFactory.create(GroupReservationMethod.NO_LONERS);

        assertEquals(NoLonersReservationStrategy.class, instance.getClass());
    }
}
