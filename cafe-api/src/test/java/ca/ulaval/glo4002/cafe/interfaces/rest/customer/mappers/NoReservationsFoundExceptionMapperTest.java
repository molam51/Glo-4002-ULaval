package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.customer.exceptions.NoReservationsFound;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NoReservationsFoundExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private NoReservationsFoundExceptionMapper noReservationsFoundExceptionMapper;
    private NoReservationsFound noReservationsFoundException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        noReservationsFoundExceptionMapper = new NoReservationsFoundExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        noReservationsFoundException = mock(NoReservationsFound.class);
    }

    @Test
    public void givenNoReservationsFoundException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = noReservationsFoundExceptionMapper.toResponse(noReservationsFoundException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = noReservationsFoundExceptionMapper.toResponse(noReservationsFoundException);

        assertEquals(errorResponse, response.getEntity());
    }
}
