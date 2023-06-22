package ca.ulaval.glo4002.cafe.interfaces.rest.config.mappers;

import ca.ulaval.glo4002.cafe.domain.tip.exceptions.InvalidGroupTipRateException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InvalidGroupTipRateExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private InvalidGroupTipRateExceptionMapper invalidGroupTipRateExceptionMapper;
    private InvalidGroupTipRateException invalidGroupTipRateException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        invalidGroupTipRateExceptionMapper = new InvalidGroupTipRateExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        invalidGroupTipRateException = mock(InvalidGroupTipRateException.class);
    }

    @Test
    public void givenInvalidGroupTipRateException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = invalidGroupTipRateExceptionMapper.toResponse(invalidGroupTipRateException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = invalidGroupTipRateExceptionMapper.toResponse(invalidGroupTipRateException);

        assertEquals(errorResponse, response.getEntity());
    }
}
