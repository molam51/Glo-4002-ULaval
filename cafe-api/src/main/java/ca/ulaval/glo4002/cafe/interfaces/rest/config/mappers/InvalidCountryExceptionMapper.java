package ca.ulaval.glo4002.cafe.interfaces.rest.config.mappers;

import ca.ulaval.glo4002.cafe.domain.tax.exceptions.InvalidCountryException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InvalidCountryExceptionMapper implements ExceptionMapper<InvalidCountryException> {

    private static final String INVALID_COUNTRY_ERROR_NAME = "INVALID_COUNTRY";
    private static final String INVALID_COUNTRY_DESCRIPTION = "The specified country is invalid.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public InvalidCountryExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(InvalidCountryException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(INVALID_COUNTRY_ERROR_NAME, INVALID_COUNTRY_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
