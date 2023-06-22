package ca.ulaval.glo4002.cafe.interfaces.rest.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseAssemblerTest {

    private static final String AN_ERROR_NAME = "AN_ERROR_NAME";
    private static final String AN_ERROR_DESCRIPTION = "AN_ERROR_DESCRIPTION";

    @Test
    public void givenError_whenToResponse_thenResponseIsAssembled() {
        ErrorResponseAssembler errorResponseAssembler = new ErrorResponseAssembler();

        ErrorResponse errorResponse = errorResponseAssembler.toResponse(AN_ERROR_NAME, AN_ERROR_DESCRIPTION);

        assertEquals(AN_ERROR_NAME, errorResponse.error);
        assertEquals(AN_ERROR_DESCRIPTION, errorResponse.description);
    }
}
