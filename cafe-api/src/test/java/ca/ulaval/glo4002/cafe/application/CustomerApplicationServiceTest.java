package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShopRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.DuplicateCustomerIdException;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.NoReservationsFound;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.domain.tip.TipRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerApplicationServiceTest {

    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final CustomerID DUPLICATE_CUSTOMER_ID = new CustomerID("5678");
    private static final String A_CUSTOMER_NAME = "John Doe";
    private static final String A_GROUP_NAME = "Avenged Sevenfold";
    private static final String NON_EXISTENT_GROUP_NAME = "Potatoes";
    private static final int A_RESERVED_SEAT_NUMBER = 10;

    private CoffeeShop coffeeShop;
    private CustomerFactory customerFactory;
    private CustomerRepository customerRepository;
    private ReservationRepository reservationRepository;
    private CoffeeShopRepository coffeeShopRepository;
    private BillFactory billFactory;
    private BillRepository billRepository;
    private OrderRepository orderRepository;
    private CustomerApplicationService customerApplicationService;

    @BeforeEach
    public void setup() {
        setupMocks();

        customerApplicationService = new CustomerApplicationService(
                customerFactory,
                customerRepository,
                reservationRepository,
                coffeeShopRepository,
                orderRepository,
                billFactory,
                billRepository);
    }

    private void setupMocks() {
        coffeeShop = mock(CoffeeShop.class);
        customerFactory = mock(CustomerFactory.class);
        customerRepository = mock(CustomerRepository.class);
        reservationRepository = mock(ReservationRepository.class);
        coffeeShopRepository = mock(CoffeeShopRepository.class);
        orderRepository = mock(OrderRepository.class);
        billFactory = mock(BillFactory.class);
        billRepository = mock(BillRepository.class);
        orderRepository = mock(OrderRepository.class);
        coffeeShopRepository = mock(CoffeeShopRepository.class);

        when(customerRepository.exists(DUPLICATE_CUSTOMER_ID)).thenReturn(true);
        when(coffeeShopRepository.find()).thenReturn(coffeeShop);
        when(reservationRepository.exists(A_GROUP_NAME)).thenReturn(true);
        when(reservationRepository.exists(NON_EXISTENT_GROUP_NAME)).thenReturn(false);
    }

    @Test
    public void givenCustomerCheckInInformation_whenCheckIn_thenSeatIsAssignedToCustomer() {
        Customer customer = givenCustomer();

        customerApplicationService.checkIn(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);

        assertEquals(A_RESERVED_SEAT_NUMBER, customer.getSeatNumber());
    }

    @Test
    public void givenCustomerCheckInInformation_whenCheckIn_thenCoffeeShopRepositoryIsUpdated() {
        givenCustomer();

        customerApplicationService.checkIn(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);

        verify(coffeeShopRepository).save(coffeeShop);
    }

    @Test
    public void givenCustomerCheckInInformation_whenCheckIn_thenCustomerRepositoryIsUpdated() {
        Customer customer = givenCustomer();

        customerApplicationService.checkIn(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);

        verify(customerRepository).save(customer);
    }

    @Test
    public void givenDuplicateCustomerId_whenCheckIn_thenThrowDuplicateCustomerIdException() {
        assertThrows(DuplicateCustomerIdException.class, () -> {
            customerApplicationService.checkIn(DUPLICATE_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);
        });
    }

    @Test
    public void givenDuplicateCustomerId_whenCheckIn_thenSaveIsNotCalledOnCustomerRepository() {
        try {
            customerApplicationService.checkIn(DUPLICATE_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);
        } catch (Exception ignored) {

        }

        verify(customerRepository, never()).save(any());
    }

    @Test
    public void givenDuplicateCustomerId_whenCheckIn_thenSaveIsNotCalledOnCoffeeShopRepository() {
        try {
            customerApplicationService.checkIn(DUPLICATE_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);
        } catch (Exception ignored) {

        }

        verify(coffeeShopRepository, never()).save(any());
    }

    @Test
    public void givenNonExistentGroupName_whenCheckIn_thenThrowNoReservationsFoundException() {
        assertThrows(NoReservationsFound.class, () -> {
            customerApplicationService.checkIn(A_CUSTOMER_ID, A_CUSTOMER_NAME, NON_EXISTENT_GROUP_NAME);
        });
    }

    @Test
    public void givenNonExistentGroupName_whenCheckIn_thenSaveIsNotCalledOnCoffeeShopRepository() {
        try {
            customerApplicationService.checkIn(A_CUSTOMER_ID, A_CUSTOMER_NAME, NON_EXISTENT_GROUP_NAME);
        } catch (Exception ignored) {

        }

        verify(coffeeShopRepository, never()).save(any());
    }

    @Test
    public void givenNonExistentGroupName_whenCheckIn_thenSaveIsNotCalledOnCustomerRepository() {
        try {
            customerApplicationService.checkIn(A_CUSTOMER_ID, A_CUSTOMER_NAME, NON_EXISTENT_GROUP_NAME);
        } catch (Exception ignored) {

        }

        verify(customerRepository, never()).save(any());
    }

    @Test
    public void givenCheckedInCustomer_whenGetCustomer_thenCustomerIsReturned() {
        Customer checkedInCustomer = givenCheckedInCustomer();

        Customer returnedCustomer = customerApplicationService.getCustomer(A_CUSTOMER_ID);

        assertEquals(checkedInCustomer, returnedCustomer);
    }

    @Test
    public void givenCheckedInCustomer_whenCheckOut_thenCheckOutIsCalledOnCoffeeShop() {
        Customer checkedInCustomer = givenCheckedInCustomer();

        customerApplicationService.checkOut(A_CUSTOMER_ID);

        verify(coffeeShop).checkOut(checkedInCustomer);
    }

    @Test
    public void givenCheckedInCustomer_whenCheckOut_thenSeatIsRemovedFromCustomer() {
        Customer checkedInCustomer = givenCheckedInCustomer();

        customerApplicationService.checkOut(A_CUSTOMER_ID);

        assertNull(checkedInCustomer.getSeatNumber());
    }

    @Test
    public void givenCheckedInCustomer_whenCheckOut_thenCustomerRepositoryIsUpdated() {
        Customer checkedInCustomer = givenCheckedInCustomer();

        customerApplicationService.checkOut(A_CUSTOMER_ID);

        verify(customerRepository).save(checkedInCustomer);
    }

    @Test
    public void givenCheckedInCustomer_whenCheckOut_thenCoffeeShopRepositoryIsUpdated() {
        givenCheckedInCustomer();

        customerApplicationService.checkOut(A_CUSTOMER_ID);

        verify(coffeeShopRepository).save(coffeeShop);
    }

    @Test
    public void givenCheckedInCustomer_whenCheckOut_thenBillIsAddedToBillRepository() {
        Customer checkedInCustomer = givenCheckedInCustomer();
        TaxRate taxRate = mock(TaxRate.class);
        TipRate tipRate = mock(TipRate.class);
        Bill bill = mock(Bill.class);
        List<Order> orders = new ArrayList<>();
        when(coffeeShop.getTaxRate()).thenReturn(taxRate);
        when(coffeeShop.getTipRate()).thenReturn(tipRate);
        when(orderRepository.findByCustomerId(A_CUSTOMER_ID)).thenReturn(orders);
        when(billFactory.create(checkedInCustomer, orders, taxRate, tipRate)).thenReturn(bill);

        customerApplicationService.checkOut(A_CUSTOMER_ID);

        verify(billRepository).add(bill);
    }

    private Customer givenCustomer() {
        Customer customer = new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);
        when(customerFactory.create(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME)).thenReturn(customer);
        when(coffeeShop.assignCustomerToSeat(customer)).thenReturn(A_RESERVED_SEAT_NUMBER);

        return customer;
    }

    private Customer givenCheckedInCustomer() {
        Customer checkedInCustomer = new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME, A_GROUP_NAME);
        when(customerRepository.findById(A_CUSTOMER_ID)).thenReturn(checkedInCustomer);

        return checkedInCustomer;
    }
}
