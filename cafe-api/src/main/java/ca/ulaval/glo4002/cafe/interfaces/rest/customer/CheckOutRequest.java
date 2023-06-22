package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckOutRequest {

    @JsonProperty("customer_id")
    public String customerIdValue;
}
