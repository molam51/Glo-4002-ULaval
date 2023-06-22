package ca.ulaval.glo4002.cafe.interfaces.rest.reservation.mappers;

import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class DuplicateGroupNameExceptionMapper implements ExceptionMapper<DuplicateGroupNameException> {

    private static final String DUPLICATE_GROUP_NAME_ERROR_NAME = "DUPLICATE_GROUP_NAME";
    private static final String DUPLICATE_GROUP_NAME_DESCRIPTION = "The specified group already made a reservation today.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public DuplicateGroupNameExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(DuplicateGroupNameException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(DUPLICATE_GROUP_NAME_ERROR_NAME, DUPLICATE_GROUP_NAME_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
