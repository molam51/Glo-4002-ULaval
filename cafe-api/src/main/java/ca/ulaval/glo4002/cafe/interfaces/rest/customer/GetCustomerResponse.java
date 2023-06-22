package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetCustomerResponse {

    @JsonProperty(value = "name")
    public final String name;
    @JsonProperty(value = "seat_number")
    public final int seatNumber;
    @JsonProperty(value = "group_name")
    public final String groupName;

    @JsonCreator
    public GetCustomerResponse(String name, int seatNumber, String groupName) {
        this.name = name;
        this.seatNumber = seatNumber;
        this.groupName = groupName;
    }
}
