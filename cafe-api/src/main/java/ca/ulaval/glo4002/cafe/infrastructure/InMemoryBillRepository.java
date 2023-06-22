package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.bill.exceptions.BillAlreadyInRepositoryException;
import ca.ulaval.glo4002.cafe.domain.bill.exceptions.BillNotFoundException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;

import java.util.ArrayList;
import java.util.List;

public class InMemoryBillRepository implements BillRepository {

    private final List<Bill> bills = new ArrayList<>();

    @Override
    public Bill findByCustomerId(CustomerID customerId) {
        return bills.stream()
                .filter(bill -> bill.getCustomerID().equals(customerId))
                .findFirst()
                .orElseThrow(() -> new BillNotFoundException(customerId));
    }

    @Override
    public void add(Bill bill) {
        if (exists(bill.getCustomerID())) {
            throw new BillAlreadyInRepositoryException(bill.getCustomerID());
        }

        bills.add(bill);
    }

    @Override
    public void clear() {
        bills.clear();
    }

    private boolean exists(CustomerID customerId) {
        return bills.stream()
                .anyMatch(customer -> customer.getCustomerID().equals(customerId));
    }
}
