package ca.ulaval.glo4002.cafe.interfaces.rest.layout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SeatInGetLayoutResponse {

    public int number;
    public String status;
    @JsonProperty(value = "customer_id")
    public String customerId;
    @JsonProperty(value = "group_name")
    public String groupName;

    @JsonCreator
    public SeatInGetLayoutResponse(int number, String status, String customerId, String groupName) {
        this.number = number;
        this.status = status;
        this.customerId = customerId;
        this.groupName = groupName;
    }
}
