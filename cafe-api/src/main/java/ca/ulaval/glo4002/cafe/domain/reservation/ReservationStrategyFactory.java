package ca.ulaval.glo4002.cafe.domain.reservation;

public class ReservationStrategyFactory {

    public ReservationStrategy create(GroupReservationMethod groupReservationMethod) {
        return switch (groupReservationMethod) {
            case DEFAULT -> new DefaultReservationStrategy();
            case FULL_CUBES -> new FullCubesReservationStrategy();
            case NO_LONERS -> new NoLonersReservationStrategy();
        };
    }
}
