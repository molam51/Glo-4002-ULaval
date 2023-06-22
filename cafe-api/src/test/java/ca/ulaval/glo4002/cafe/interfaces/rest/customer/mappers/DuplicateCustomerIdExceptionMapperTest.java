package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.customer.exceptions.DuplicateCustomerIdException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DuplicateCustomerIdExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private DuplicateCustomerIdExceptionMapper duplicateCustomerIdExceptionMapper;
    private DuplicateCustomerIdException duplicateCustomerIdException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        duplicateCustomerIdExceptionMapper = new DuplicateCustomerIdExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        duplicateCustomerIdException = mock(DuplicateCustomerIdException.class);
    }

    @Test
    public void givenDuplicateCustomerIdException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = duplicateCustomerIdExceptionMapper.toResponse(duplicateCustomerIdException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

   @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = duplicateCustomerIdExceptionMapper.toResponse(duplicateCustomerIdException);

        assertEquals(errorResponse, response.getEntity());
    }
}
