package ca.ulaval.glo4002.cafe.interfaces.rest.reservation.mappers;

import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InvalidGroupReservationMethodExceptionMapper implements ExceptionMapper<InvalidGroupReservationMethodException> {

    private static final String INVALID_GROUP_RESERVATION_METHOD_ERROR_NAME = "INVALID_GROUP_RESERVATION_METHOD";
    private static final String INVALID_GROUP_RESERVATION_METHOD_DESCRIPTION = "The group reservation method is not supported.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public InvalidGroupReservationMethodExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(InvalidGroupReservationMethodException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(INVALID_GROUP_RESERVATION_METHOD_ERROR_NAME, INVALID_GROUP_RESERVATION_METHOD_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
