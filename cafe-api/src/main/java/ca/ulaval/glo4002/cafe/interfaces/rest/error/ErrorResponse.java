package ca.ulaval.glo4002.cafe.interfaces.rest.error;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ErrorResponse {

    public final String error;
    public final String description;

    @JsonCreator
    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
