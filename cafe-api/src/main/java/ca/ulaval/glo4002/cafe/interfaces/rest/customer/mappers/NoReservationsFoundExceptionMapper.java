package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.customer.exceptions.NoReservationsFound;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class NoReservationsFoundExceptionMapper implements ExceptionMapper<NoReservationsFound> {

    private static final String NO_RESERVATIONS_FOUND_ERROR_NAME = "NO_RESERVATIONS_FOUND";
    private static final String NO_RESERVATIONS_FOUND_DESCRIPTION = "No reservations were made today for that group.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public NoReservationsFoundExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(NoReservationsFound noReservationsFound) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(NO_RESERVATIONS_FOUND_ERROR_NAME, NO_RESERVATIONS_FOUND_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
