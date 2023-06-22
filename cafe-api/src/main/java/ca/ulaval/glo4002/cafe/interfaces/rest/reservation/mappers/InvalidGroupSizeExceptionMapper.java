package ca.ulaval.glo4002.cafe.interfaces.rest.reservation.mappers;

import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.InvalidGroupSizeException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InvalidGroupSizeExceptionMapper implements ExceptionMapper<InvalidGroupSizeException> {

    private static final String INVALID_GROUP_SIZE_ERROR_NAME = "INVALID_GROUP_SIZE";
    private static final String INVALID_GROUP_SIZE_DESCRIPTION = "Groups must reserve at least two seats.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public InvalidGroupSizeExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(InvalidGroupSizeException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(INVALID_GROUP_SIZE_ERROR_NAME, INVALID_GROUP_SIZE_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
