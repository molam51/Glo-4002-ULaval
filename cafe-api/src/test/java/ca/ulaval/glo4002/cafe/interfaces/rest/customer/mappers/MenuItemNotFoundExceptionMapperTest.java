package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.menu.exceptions.MenuItemNotFoundException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemNotFoundExceptionMapperTest {

    private static final int HTTP_BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private MenuItemNotFoundExceptionMapper menuItemNotFoundExceptionMapper;
    private MenuItemNotFoundException menuItemNotFoundException;
    private ErrorResponseAssembler errorResponseAssembler;

    @BeforeEach
    void setup() {
        setupMocks();

        menuItemNotFoundExceptionMapper = new MenuItemNotFoundExceptionMapper(errorResponseAssembler);
    }

    private void setupMocks() {
        errorResponseAssembler = mock(ErrorResponseAssembler.class);
        menuItemNotFoundException = mock(MenuItemNotFoundException.class);
    }

    @Test
    public void givenMenuItemNotFoundException_whenToResponse_thenResponseHasHttpBadRequestStatusCode() {
        Response response = menuItemNotFoundExceptionMapper.toResponse(menuItemNotFoundException);

        assertEquals(HTTP_BAD_REQUEST, response.getStatus());
    }

    @Test
    public void givenErrorResponse_whenToResponse_thenResponseEntityIsErrorResponse() {
        ErrorResponse errorResponse = mock(ErrorResponse.class);
        when(errorResponseAssembler.toResponse(any(), any())).thenReturn(errorResponse);

        Response response = menuItemNotFoundExceptionMapper.toResponse(menuItemNotFoundException);

        assertEquals(errorResponse, response.getEntity());
    }
}
