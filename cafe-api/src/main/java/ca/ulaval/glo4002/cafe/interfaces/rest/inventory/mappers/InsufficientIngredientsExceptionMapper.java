package ca.ulaval.glo4002.cafe.interfaces.rest.inventory.mappers;

import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.InsufficientIngredientsException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InsufficientIngredientsExceptionMapper implements ExceptionMapper<InsufficientIngredientsException> {

    private static final String INSUFFICIENT_INGREDIENTS_ERROR_NAME = "INSUFFICIENT_INGREDIENTS";
    private static final String INSUFFICIENT_INGREDIENTS_DESCRIPTION = "We lack the necessary number of ingredients to fulfill your order.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public InsufficientIngredientsExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(InsufficientIngredientsException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(INSUFFICIENT_INGREDIENTS_ERROR_NAME, INSUFFICIENT_INGREDIENTS_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
