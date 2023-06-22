package ca.ulaval.glo4002.cafe.domain.coffeeShop;

public enum SeatState {
    AVAILABLE {
        @Override
        public String toString() {
            return "Available";
        }
    },
    OCCUPIED {
        @Override
        public String toString() {
            return "Occupied";
        }
    },
    RESERVED {
        @Override
        public String toString() {
            return "Reserved";
        }
    }
}
