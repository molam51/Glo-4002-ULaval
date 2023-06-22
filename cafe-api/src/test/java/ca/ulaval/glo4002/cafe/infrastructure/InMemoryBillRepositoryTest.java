package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.bill.exceptions.BillAlreadyInRepositoryException;
import ca.ulaval.glo4002.cafe.domain.bill.exceptions.BillNotFoundException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryBillRepositoryTest {

    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final CustomerID ANOTHER_CUSTOMER_ID = new CustomerID("5678");
    private static final Price A_BILL_PRICE = new Price(13.50);
    private static final Price A_BILL_TAX_PRICE = new Price(0.67);
    private static final Price A_BILL_TIP_PRICE = new Price(1.35);
    private static final Price A_BILL_TOTAL_PRICE = new Price(40.15);

    private BillRepository billRepository;

    @BeforeEach
    public void setup() {
        billRepository = new InMemoryBillRepository();
    }

    @Test
    public void givenEmptyRepository_whenFindByCustomerId_thenThrowsBillNotFoundException() {
        assertThrows(BillNotFoundException.class, () -> {
            billRepository.findByCustomerId(A_CUSTOMER_ID);
        });
    }

    @Test
    public void givenBill_whenAdd_thenBillIsFoundInRepository() {
        Bill bill = givenBill();

        billRepository.add(bill);

        Bill foundBill = billRepository.findByCustomerId(bill.getCustomerID());
        assertEquals(bill, foundBill);
    }

    @Test
    public void givenMultipleBillsInRepository_whenFindByCustomerId_thenReturnTheRightBill() {
        Bill bill = givenBill();
        Bill anotherBill = givenAnotherBill();
        billRepository.add(bill);
        billRepository.add(anotherBill);

        Bill actualBill = billRepository.findByCustomerId(bill.getCustomerID());

        assertEquals(bill, actualBill);
    }

    @Test
    public void givenBillAlreadyInRepository_whenAdd_thenThrowBillAlreadyInRepositoryException() {
        Bill bill = givenBill();
        billRepository.add(bill);

        assertThrows(BillAlreadyInRepositoryException.class, () -> {
            billRepository.add(bill);
        });
    }

    @Test
    public void givenMultipleBillsInRepository_whenClear_thenRepositoryIsEmpty() {
        Bill bill = givenBill();
        Bill anotherBill = givenAnotherBill();
        billRepository.add(bill);
        billRepository.add(anotherBill);

        billRepository.clear();

        assertThrows(BillNotFoundException.class, () -> billRepository.findByCustomerId(bill.getCustomerID()));
        assertThrows(BillNotFoundException.class, () -> billRepository.findByCustomerId(anotherBill.getCustomerID()));
    }

    private Bill givenBill() {
        return new Bill(A_CUSTOMER_ID, A_BILL_PRICE, A_BILL_TIP_PRICE, A_BILL_TAX_PRICE, A_BILL_TOTAL_PRICE, List.of());
    }

    private Bill givenAnotherBill() {
        return new Bill(ANOTHER_CUSTOMER_ID, A_BILL_PRICE, A_BILL_TIP_PRICE, A_BILL_TAX_PRICE, A_BILL_TOTAL_PRICE, List.of());
    }
}
