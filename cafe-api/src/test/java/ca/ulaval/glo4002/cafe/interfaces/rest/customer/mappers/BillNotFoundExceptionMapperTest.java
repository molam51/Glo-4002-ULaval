package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.bill.exceptions.BillNotFoundException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BillNotFoundExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private BillNotFoundExceptionMapper billNotFoundExceptionMapper;
    private BillNotFoundException billNotFoundException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        billNotFoundExceptionMapper = new BillNotFoundExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        billNotFoundException = mock(BillNotFoundException.class);
    }

    @Test
    public void givenBillNotFoundException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = billNotFoundExceptionMapper.toResponse(billNotFoundException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = billNotFoundExceptionMapper.toResponse(billNotFoundException);

        assertEquals(errorResponse, response.getEntity());
    }
}
