package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions.SeatUnavailableException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;

import java.util.Objects;

public class Seat {

    private final int number;
    private CustomerID customerId;
    private String groupName;

    public Seat(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public CustomerID getCustomerId() {
        return customerId;
    }

    public String getGroupName() {
        return groupName;
    }

    public SeatState getState() {
        if (isOccupied()) {
            return SeatState.OCCUPIED;
        }

        if (isReserved()) {
            return SeatState.RESERVED;
        }

        return SeatState.AVAILABLE;
    }

    private boolean isOccupied() {
        return this.customerId != null;
    }

    private boolean isReserved() {
        return this.groupName != null;
    }

    public void occupy(CustomerID customerId, String groupName) {
        if (isOccupied() || !Objects.equals(this.groupName, groupName)) {
            throw new SeatUnavailableException(number);
        }

        this.customerId = customerId;
    }

    public void reserve(String groupName) {
        if (isOccupied() || isReserved()) {
            throw new SeatUnavailableException(number);
        }

        this.groupName = groupName;
    }

    public void clear() {
        this.customerId = null;
        this.groupName = null;
    }
}
