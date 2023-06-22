package ca.ulaval.glo4002.cafe.interfaces.rest.config.mappers;

import ca.ulaval.glo4002.cafe.domain.tax.exceptions.InvalidCountryException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InvalidCountryExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private InvalidCountryExceptionMapper invalidCountryExceptionMapper;
    private InvalidCountryException invalidCountryException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        invalidCountryExceptionMapper = new InvalidCountryExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        invalidCountryException = mock(InvalidCountryException.class);
    }

    @Test
    public void givenInvalidCountryException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = invalidCountryExceptionMapper.toResponse(invalidCountryException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = invalidCountryExceptionMapper.toResponse(invalidCountryException);

        assertEquals(errorResponse, response.getEntity());
    }
}
