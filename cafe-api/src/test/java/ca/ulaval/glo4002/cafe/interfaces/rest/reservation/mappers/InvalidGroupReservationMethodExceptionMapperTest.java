package ca.ulaval.glo4002.cafe.interfaces.rest.reservation.mappers;

import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InvalidGroupReservationMethodExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private InvalidGroupReservationMethodExceptionMapper invalidGroupReservationMethodExceptionMapper;
    private InvalidGroupReservationMethodException invalidGroupReservationMethodException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        invalidGroupReservationMethodExceptionMapper = new InvalidGroupReservationMethodExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        invalidGroupReservationMethodException = mock(InvalidGroupReservationMethodException.class);
    }

    @Test
    public void givenInvalidGroupReservationMethodException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = invalidGroupReservationMethodExceptionMapper.toResponse(invalidGroupReservationMethodException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = invalidGroupReservationMethodExceptionMapper.toResponse(invalidGroupReservationMethodException);

        assertEquals(errorResponse, response.getEntity());
    }
}
