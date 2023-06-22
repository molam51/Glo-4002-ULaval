package ca.ulaval.glo4002.cafe.interfaces.rest.reservation.mappers;

import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DuplicateGroupNameExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private DuplicateGroupNameExceptionMapper duplicateGroupNameExceptionMapper;
    private DuplicateGroupNameException duplicateGroupNameException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        duplicateGroupNameExceptionMapper = new DuplicateGroupNameExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        duplicateGroupNameException = mock(DuplicateGroupNameException.class);
    }

    @Test
    public void givenDuplicateGroupNameException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = duplicateGroupNameExceptionMapper.toResponse(duplicateGroupNameException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = duplicateGroupNameExceptionMapper.toResponse(duplicateGroupNameException);

        assertEquals(errorResponse, response.getEntity());
    }
}
