package ca.ulaval.glo4002.cafe.domain.customer.exceptions;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;

public class CustomerNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Unable to find customer with id %s.";

    public CustomerNotFoundException(CustomerID customerId) {
        super(String.format(ERROR_MESSAGE, customerId.getValue()));
    }
}
