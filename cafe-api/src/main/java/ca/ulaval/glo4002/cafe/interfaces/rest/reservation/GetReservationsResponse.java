package ca.ulaval.glo4002.cafe.interfaces.rest.reservation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetReservationsResponse {

    @JsonProperty(value = "group_name")
    public final String groupName;
    @JsonProperty(value = "group_size")
    public final int groupSize;

    @JsonCreator
    public GetReservationsResponse(String groupName, int groupSize) {
        this.groupName = groupName;
        this.groupSize = groupSize;
    }
}
