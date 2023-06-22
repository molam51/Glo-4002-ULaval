package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.bill.exceptions.BillNotFoundException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class BillNotFoundExceptionMapper implements ExceptionMapper<BillNotFoundException> {

    private static final String BILL_NOT_FOUND_ERROR_NAME = "NO_BILL";
    private static final String BILL_NOT_FOUND_DESCRIPTION = "The customer needs to do a checkout before receiving his bill.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public BillNotFoundExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(BillNotFoundException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(BILL_NOT_FOUND_ERROR_NAME, BILL_NOT_FOUND_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
