package ca.ulaval.glo4002.cafe.interfaces.rest.layout.mappers;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.exceptions.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InsufficientSeatsExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private InsufficientSeatsExceptionMapper insufficientSeatsExceptionMapper;
    private InsufficientSeatsException insufficientSeatsException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        insufficientSeatsExceptionMapper = new InsufficientSeatsExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        insufficientSeatsException = mock(InsufficientSeatsException.class);
    }

    @Test
    public void givenInsufficientSeatsException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = insufficientSeatsExceptionMapper.toResponse(insufficientSeatsException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = insufficientSeatsExceptionMapper.toResponse(insufficientSeatsException);

        assertEquals(errorResponse, response.getEntity());
    }
}
