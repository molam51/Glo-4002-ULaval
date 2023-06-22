package ca.ulaval.glo4002.cafe.domain.bill.exceptions;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;

public class BillNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Unable to find bill with customer id %s.";

    public BillNotFoundException(CustomerID customerId) {
        super(String.format(ERROR_MESSAGE, customerId.getValue()));
    }
}
