package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;

public class GetCustomerResponseAssembler {

    public GetCustomerResponse toResponse(Customer customer) {
        return new GetCustomerResponse(customer.getName(), customer.getSeatNumber(), customer.getGroupName());
    }
}
