package ca.ulaval.glo4002.cafe.interfaces.rest.error;

public class ErrorResponseAssembler {

    public ErrorResponse toResponse(String error, String description) {
        return new ErrorResponse(error, description);
    }
}
