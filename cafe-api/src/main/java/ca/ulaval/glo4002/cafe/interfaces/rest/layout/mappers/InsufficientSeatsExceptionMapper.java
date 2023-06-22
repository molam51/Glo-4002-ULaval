package ca.ulaval.glo4002.cafe.interfaces.rest.layout.mappers;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InsufficientSeatsExceptionMapper implements ExceptionMapper<InsufficientSeatsException> {

    private static final String INSUFFICIENT_SEATS_ERROR_NAME = "INSUFFICIENT_SEATS";
    private static final String INSUFFICIENT_SEATS_DESCRIPTION = "There are currently no available seats. Please come back later.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public InsufficientSeatsExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(InsufficientSeatsException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(INSUFFICIENT_SEATS_ERROR_NAME,
                INSUFFICIENT_SEATS_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
