package ca.ulaval.glo4002.cafe.interfaces.rest.inventory.mappers;

import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.InsufficientIngredientsException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InsufficientIngredientsExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private InsufficientIngredientsExceptionMapper insufficientIngredientsExceptionMapper;
    private InsufficientIngredientsException insufficientIngredientsException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        insufficientIngredientsExceptionMapper = new InsufficientIngredientsExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        insufficientIngredientsException = mock(InsufficientIngredientsException.class);
    }

    @Test
    public void givenInsufficientIngredientsException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = insufficientIngredientsExceptionMapper.toResponse(insufficientIngredientsException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = insufficientIngredientsExceptionMapper.toResponse(insufficientIngredientsException);

        assertEquals(errorResponse, response.getEntity());
    }
}
