package ca.ulaval.glo4002.cafe.interfaces.rest.reservation;

import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;

import java.util.ArrayList;
import java.util.List;

public class GetReservationsResponseAssembler {

    public List<GetReservationsResponse> toResponse(List<Reservation> reservations) {
        List<GetReservationsResponse> getReservationsResponse = new ArrayList<>();

        for (Reservation reservation : reservations) {
            GetReservationsResponse response = new GetReservationsResponse(reservation.getGroupName(), reservation.getGroupSize());
            getReservationsResponse.add(response);
        }

        return getReservationsResponse;
    }
}
