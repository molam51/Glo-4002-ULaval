package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import java.util.List;
import java.util.Objects;

public class Cube {

    private final String name;
    private final List<Seat> seats;

    public Cube(String name, List<Seat> seats) {
        this.name = name;
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public int getSize() {
        return seats.size();
    }

    public List<Seat> findAvailableSeats() {
        return seats.stream()
                .filter(seat -> seat.getState().equals(SeatState.AVAILABLE))
                .toList();
    }

    public List<Seat> findSeatsByGroupName(String groupName) {
        return seats.stream()
                .filter(seat -> Objects.equals(seat.getGroupName(), groupName))
                .toList();
    }

    public List<Seat> findReservedSeatsByGroupName(String groupName) {
        return seats.stream()
                .filter(seat -> seat.getState().equals(SeatState.RESERVED))
                .filter(seat -> Objects.equals(seat.getGroupName(), groupName))
                .toList();
    }

    public boolean isFullyAvailable() {
        return seats.stream()
                .allMatch(seat -> seat.getState().equals(SeatState.AVAILABLE));
    }

    public void clearSeat(int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getNumber() == seatNumber) {
                seat.clear();
            }
        }
    }
}
