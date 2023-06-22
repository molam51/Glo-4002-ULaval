package ca.ulaval.glo4002.cafe.domain.bill.exceptions;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;

public class BillAlreadyInRepositoryException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Bill with customer id %s already exists.";

    public BillAlreadyInRepositoryException(CustomerID customerId) {
        super(String.format(ERROR_MESSAGE, customerId.getValue()));
    }
}
