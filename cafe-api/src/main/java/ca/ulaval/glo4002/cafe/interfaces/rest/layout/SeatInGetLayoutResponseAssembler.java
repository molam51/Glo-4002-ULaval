package ca.ulaval.glo4002.cafe.interfaces.rest.layout;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.Seat;

public class SeatInGetLayoutResponseAssembler {

    public SeatInGetLayoutResponse toResponse(Seat seat) {
        String status = seat.getState().toString();
        String customerId = seat.getCustomerId() != null ? seat.getCustomerId().getValue() : null;
        String groupName = seat.getGroupName();

        return new SeatInGetLayoutResponse(seat.getNumber(), status, customerId, groupName);
    }
}
