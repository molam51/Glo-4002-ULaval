package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.customer.exceptions.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NoGroupSeatsExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private NoGroupSeatsExceptionMapper noGroupSeatsExceptionMapper;
    private NoGroupSeatsException noGroupSeatsException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        noGroupSeatsExceptionMapper = new NoGroupSeatsExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        noGroupSeatsException = mock(NoGroupSeatsException.class);
    }

    @Test
    public void givenNoGroupSeatsException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = noGroupSeatsExceptionMapper.toResponse(noGroupSeatsException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = noGroupSeatsExceptionMapper.toResponse(noGroupSeatsException);

        assertEquals(errorResponse, response.getEntity());
    }
}
