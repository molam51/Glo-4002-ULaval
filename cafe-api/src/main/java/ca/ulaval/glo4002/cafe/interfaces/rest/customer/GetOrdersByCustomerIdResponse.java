package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class GetOrdersByCustomerIdResponse {

    public List<String> orders;

    @JsonCreator
    public GetOrdersByCustomerIdResponse(List<String> orders) {
        this.orders = orders;
    }
}
