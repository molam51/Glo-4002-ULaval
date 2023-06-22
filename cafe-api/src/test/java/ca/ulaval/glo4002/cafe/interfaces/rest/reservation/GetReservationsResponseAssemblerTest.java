package ca.ulaval.glo4002.cafe.interfaces.rest.reservation;

import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class GetReservationsResponseAssemblerTest {

    private static final String A_GROUP_NAME_1 = "P'tit Dej.";
    private static final int A_GROUP_SIZE_1 = 13;
    private static final String A_GROUP_NAME_2 = "LES MANGE-OEUFS DE DEJEUNER";
    private static final int A_GROUP_SIZE_2 = 7;

    private List<Reservation> reservations;
    private GetReservationsResponseAssembler getReservationsResponseAssembler;

    @BeforeEach
    void setup() {
        setupReservations();

        getReservationsResponseAssembler = new GetReservationsResponseAssembler();
    }

    private void setupReservations() {
        Reservation reservation1 = new Reservation(A_GROUP_NAME_1, A_GROUP_SIZE_1);
        Reservation reservation2 = new Reservation(A_GROUP_NAME_2, A_GROUP_SIZE_2);
        reservations = List.of(reservation1, reservation2);
    }

    @Test
    public void givenMultipleReservations_whenToResponse_thenResponseIsAssembled() {
        List<GetReservationsResponse> responses = getReservationsResponseAssembler.toResponse(reservations);

        List<String> groupsName = responses.stream().map(response -> response.groupName).toList();
        List<String> expectedGroupsName = List.of(A_GROUP_NAME_1, A_GROUP_NAME_2);
        List<Integer> groupsSize = responses.stream().map(response -> response.groupSize).toList();
        List<Integer> expectedGroupsSize = List.of(A_GROUP_SIZE_1, A_GROUP_SIZE_2);
        assertIterableEquals(expectedGroupsName, groupsName);
        assertIterableEquals(expectedGroupsSize, groupsSize);
    }
}
