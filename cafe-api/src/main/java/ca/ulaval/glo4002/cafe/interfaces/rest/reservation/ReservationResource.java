package ca.ulaval.glo4002.cafe.interfaces.rest.reservation;

import ca.ulaval.glo4002.cafe.application.ReservationApplicationService;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {

    public final ReservationApplicationService reservationApplicationService;
    public final GetReservationsResponseAssembler getReservationsResponseAssembler;

    public ReservationResource(ReservationApplicationService reservationApplicationService, GetReservationsResponseAssembler getReservationsResponseAssembler) {
        this.reservationApplicationService = reservationApplicationService;
        this.getReservationsResponseAssembler = getReservationsResponseAssembler;
    }

    @GET
    public Response getReservations() {
        List<Reservation> reservations = reservationApplicationService.getReservations();
        List<GetReservationsResponse> getReservationsResponse = getReservationsResponseAssembler.toResponse(reservations);
        return Response.ok(getReservationsResponse).build();
    }

    @POST
    public Response addReservation(AddReservationRequest addReservationRequest) {
        reservationApplicationService.addReservation(addReservationRequest.groupName, addReservationRequest.groupSize);
        return Response.ok().build();
    }
}
