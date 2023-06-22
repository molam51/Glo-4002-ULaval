package ca.ulaval.glo4002.cafe.interfaces.rest.config.mappers;

import ca.ulaval.glo4002.cafe.domain.tip.exceptions.InvalidGroupTipRateException;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponse;
import ca.ulaval.glo4002.cafe.interfaces.rest.error.ErrorResponseAssembler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InvalidGroupTipRateExceptionMapper implements ExceptionMapper<InvalidGroupTipRateException> {

    private static final String INVALID_GROUP_TIP_RATE_ERROR_NAME = "INVALID_GROUP_TIP_RATE";
    private static final String INVALID_GROUP_TIP_RATE_DESCRIPTION = "The group tip rate must be set to a value between 0 to 100.";

    private final ErrorResponseAssembler errorResponseAssembler;

    public InvalidGroupTipRateExceptionMapper(ErrorResponseAssembler errorResponseAssembler) {
        this.errorResponseAssembler = errorResponseAssembler;
    }

    @Override
    public Response toResponse(InvalidGroupTipRateException e) {
        ErrorResponse errorResponse = errorResponseAssembler.toResponse(INVALID_GROUP_TIP_RATE_ERROR_NAME, INVALID_GROUP_TIP_RATE_DESCRIPTION);

        return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorResponse).build();
    }
}
