package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.customer.exceptions.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class NoGroupSeatsExceptionMapper implements ExceptionMapper<NoGroupSeatsException> {

    private static final String NO_GROUP_SEATS_ERROR_NAME = "NO_GROUP_SEATS";
    private static final String NO_GROUP_SEATS_DESCRIPTION = "There are no more seats reserved for that group.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public NoGroupSeatsExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(NoGroupSeatsException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(NO_GROUP_SEATS_ERROR_NAME, NO_GROUP_SEATS_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
