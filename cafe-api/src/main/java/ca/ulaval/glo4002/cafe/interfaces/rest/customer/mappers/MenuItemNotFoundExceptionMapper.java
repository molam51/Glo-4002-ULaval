package ca.ulaval.glo4002.cafe.interfaces.rest.customer.mappers;

import ca.ulaval.glo4002.cafe.domain.menu.exceptions.MenuItemNotFoundException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class MenuItemNotFoundExceptionMapper implements ExceptionMapper<MenuItemNotFoundException> {

    private static final String MENU_ITEM_NOT_FOUND_ERROR_NAME = "INVALID_MENU_ORDER";
    private static final String MENU_ITEM_NOT_FOUND_DESCRIPTION = "An item ordered is not on the menu.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public MenuItemNotFoundExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(MenuItemNotFoundException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(MENU_ITEM_NOT_FOUND_ERROR_NAME, MENU_ITEM_NOT_FOUND_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
