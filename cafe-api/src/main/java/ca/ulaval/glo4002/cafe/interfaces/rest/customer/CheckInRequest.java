package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckInRequest {

    @JsonProperty("customer_id")
    public String customerId;

    @JsonProperty("customer_name")
    public String customerName;

    @JsonProperty("group_name")
    public String groupName;
}
