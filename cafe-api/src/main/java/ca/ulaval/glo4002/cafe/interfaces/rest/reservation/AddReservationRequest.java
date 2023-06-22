package ca.ulaval.glo4002.cafe.interfaces.rest.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddReservationRequest {

    @JsonProperty("group_name")
    public String groupName;

    @JsonProperty("group_size")
    public int groupSize;
}
