package ca.ulaval.glo4002.cafe.domain.customer;

public class Customer {

    private final CustomerID id;
    private final String name;
    private final String groupName;
    private Integer seatNumber;

    public Customer(CustomerID id, String name, String groupName) {
        this.id = id;
        this.name = name;
        this.groupName = groupName;
        this.seatNumber = null;
    }

    public CustomerID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGroupName() {
        return groupName;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public boolean hasGroup() {
        return groupName != null;
    }

    public void assignSeat(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void removeSeat() {
        seatNumber = null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Customer otherCustomer)) {
            return false;
        }

        return id.equals(otherCustomer.getId());
    }
}
