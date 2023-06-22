package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import jakarta.ws.rs.PathParam;

public class GetCustomerRequest {

    @PathParam("customer_id")
    public String customerId;
}
