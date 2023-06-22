package ca.ulaval.glo4002.cafe.interfaces.rest.reservation.mappers;

import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.InvalidGroupSizeException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InvalidGroupSizeExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private InvalidGroupSizeExceptionMapper invalidGroupSizeExceptionMapper;
    private InvalidGroupSizeException invalidGroupSizeException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        invalidGroupSizeExceptionMapper = new InvalidGroupSizeExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        invalidGroupSizeException = mock(InvalidGroupSizeException.class);
    }

    @Test
    public void givenInvalidGroupSizeException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = invalidGroupSizeExceptionMapper.toResponse(invalidGroupSizeException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = invalidGroupSizeExceptionMapper.toResponse(invalidGroupSizeException);

        assertEquals(errorResponse, response.getEntity());
    }
}
