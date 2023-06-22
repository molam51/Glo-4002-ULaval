package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.customer.exceptions.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidCustomerIdExceptionMapper implements ExceptionMapper<CustomerNotFoundException> {

    private static final String INVALID_CUSTOMER_ID_ERROR_NAME = "INVALID_CUSTOMER_ID";
    private static final String INVALID_CUSTOMER_ID_DESCRIPTION = "The customer does not exist.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public InvalidCustomerIdExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(CustomerNotFoundException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(INVALID_CUSTOMER_ID_ERROR_NAME, INVALID_CUSTOMER_ID_DESCRIPTION);

        return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(errorResponse).build();
    }
}
