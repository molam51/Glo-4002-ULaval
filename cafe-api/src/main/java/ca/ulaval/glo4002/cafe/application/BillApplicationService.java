package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.CustomerNotFoundException;

public class BillApplicationService {

    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;

    public BillApplicationService(BillRepository billRepository,
                                  CustomerRepository customerRepository) {
        this.billRepository = billRepository;
        this.customerRepository = customerRepository;
    }

    public Bill getBillByCustomerId(CustomerID customerId) {
        if (!customerRepository.exists(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }

        return billRepository.findByCustomerId(customerId);
    }
}
