package ca.ulaval.glo4002.cafe.domain.bill;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;

public interface BillRepository {

    Bill findByCustomerId(CustomerID customerId);

    void add(Bill bill);

    void clear();
}
