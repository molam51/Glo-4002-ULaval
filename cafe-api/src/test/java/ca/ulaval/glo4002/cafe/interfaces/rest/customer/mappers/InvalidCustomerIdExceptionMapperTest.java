package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.customer.exceptions.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InvalidCustomerIdExceptionMapperTest {

    private static final int HTTP_NOT_FOUND = Response.Status.NOT_FOUND.getStatusCode();

    private InvalidCustomerIdExceptionMapper invalidCustomerIdExceptionMapper;
    private CustomerNotFoundException customerNotFoundException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        invalidCustomerIdExceptionMapper = new InvalidCustomerIdExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        customerNotFoundException = mock(CustomerNotFoundException.class);
    }

    @Test
    public void givenException_whenToResponse_thenResponseHasHttpNotFoundStatusCode() {
        Response response = invalidCustomerIdExceptionMapper.toResponse(customerNotFoundException);

        assertEquals(HTTP_NOT_FOUND, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = invalidCustomerIdExceptionMapper.toResponse(customerNotFoundException);

        assertEquals(errorResponse, response.getEntity());
    }
}
