package ca.ulaval.glo4002.cafe.domain.reservation;

public class Reservation {

    private final String groupName;
    private final int groupSize;

    public Reservation(String groupName, int groupSize) {
        this.groupName = groupName;
        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reservation otherReservation)) {
            return false;
        }

        return groupName.equals(otherReservation.getGroupName());
    }
}
