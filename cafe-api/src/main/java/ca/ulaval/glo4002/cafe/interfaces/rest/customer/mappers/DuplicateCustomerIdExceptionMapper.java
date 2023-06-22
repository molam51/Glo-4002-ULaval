package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.customer.exceptions.DuplicateCustomerIdException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class DuplicateCustomerIdExceptionMapper implements ExceptionMapper<DuplicateCustomerIdException> {

    private static final String DUPLICATE_CUSTOMER_ID_ERROR_NAME = "DUPLICATE_CUSTOMER_ID";
    private static final String DUPLICATE_CUSTOMER_ID_DESCRIPTION = "The customer cannot visit the café multiple times in the same day.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public DuplicateCustomerIdExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(DuplicateCustomerIdException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(DUPLICATE_CUSTOMER_ID_ERROR_NAME,
                DUPLICATE_CUSTOMER_ID_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
