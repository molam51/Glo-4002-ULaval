package ca.ulaval.glo4002.cafe.interfaces.rest.reservation;

import ca.ulaval.glo4002.cafe.application.ReservationApplicationService;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReservationResourceTest {

    private static final int HTTP_OK = Response.Status.OK.getStatusCode();
    private static final String A_GROUP_NAME = "Foo";
    private static final int A_GROUP_SIZE = 12;

    private List<Reservation> reservations;
    private AddReservationRequest addReservationRequest;
    private ReservationApplicationService reservationApplicationService;
    private GetReservationsResponseAssembler getReservationsResponseAssembler;
    private ReservationResource reservationResource;

    @BeforeEach
    void setup() {
        setupAddReservationRequest();
        setupReservations();
        setupMocks();

        reservationResource = new ReservationResource(reservationApplicationService, getReservationsResponseAssembler);
    }

    private void setupAddReservationRequest() {
        addReservationRequest = new AddReservationRequest();
        addReservationRequest.groupName = A_GROUP_NAME;
        addReservationRequest.groupSize = A_GROUP_SIZE;
    }

    private void setupReservations() {
        Reservation reservation1 = new Reservation(A_GROUP_NAME, A_GROUP_SIZE);
        Reservation reservation2 = new Reservation(A_GROUP_NAME, A_GROUP_SIZE);
        reservations = List.of(reservation1, reservation2);
    }

    private void setupMocks() {
        reservationApplicationService = mock(ReservationApplicationService.class);
        getReservationsResponseAssembler = mock(GetReservationsResponseAssembler.class);
        when(reservationApplicationService.getReservations()).thenReturn(reservations);
    }

    @Test
    public void whenAddReservation_thenResponseHasHttpOkStatusCode() {
        Response response = reservationResource.addReservation(addReservationRequest);

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void whenAddReservation_thenAddReservationInReservationServiceIsCalled() {
        reservationResource.addReservation(addReservationRequest);

        verify(reservationApplicationService).addReservation(
                addReservationRequest.groupName,
                addReservationRequest.groupSize);
    }

    @Test
    public void whenGetReservations_thenResponseHasHttpOkStatusCode() {
        Response response = reservationResource.getReservations();

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void givenGetReservationsResponse_whenGetReservations_thenResponseEntityIsGetReservationsResponse() {
        List<GetReservationsResponse> getReservationsResponse = List.of(mock(GetReservationsResponse.class));
        when(getReservationsResponseAssembler.toResponse(reservations)).thenReturn(getReservationsResponse);

        Response response = reservationResource.getReservations();

        assertEquals(getReservationsResponse, response.getEntity());
    }
}
