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

import java.util.List;

public class CustomerApplicationService {

    private final CustomerFactory customerFactory;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final CoffeeShopRepository coffeeShopRepository;
    private final OrderRepository orderRepository;
    private final BillFactory billFactory;
    private final BillRepository billRepository;

    public CustomerApplicationService(CustomerFactory customerFactory,
                                      CustomerRepository customerRepository,
                                      ReservationRepository reservationRepository,
                                      CoffeeShopRepository coffeeShopRepository,
                                      OrderRepository orderRepository,
                                      BillFactory billFactory,
                                      BillRepository billRepository) {
        this.customerFactory = customerFactory;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.coffeeShopRepository = coffeeShopRepository;
        this.orderRepository = orderRepository;
        this.billFactory = billFactory;
        this.billRepository = billRepository;
    }

    public void checkIn(CustomerID customerId, String customerName) {
        this.executeCheckIn(customerId, customerName, null);
    }

    public void checkIn(CustomerID customerId, String customerName, String groupName) {
        if (!reservationRepository.exists(groupName)) {
            throw new NoReservationsFound();
        }

        this.executeCheckIn(customerId, customerName, groupName);
    }

    private void executeCheckIn(CustomerID customerId, String customerName, String groupName) {

        if (customerRepository.exists(customerId)) {
            throw new DuplicateCustomerIdException();
        }

        Customer customer = customerFactory.create(customerId, customerName, groupName);
        CoffeeShop coffeeShop = coffeeShopRepository.find();

        int reservedSeat = coffeeShop.assignCustomerToSeat(customer);
        customer.assignSeat(reservedSeat);

        coffeeShopRepository.save(coffeeShop);
        customerRepository.save(customer);
    }

    public Customer getCustomer(CustomerID customerId) {
        return customerRepository.findById(customerId);
    }

    public void checkOut(CustomerID customerId) {
        Customer customer = customerRepository.findById(customerId);
        CoffeeShop coffeeShop = coffeeShopRepository.find();

        coffeeShop.checkOut(customer);
        customer.removeSeat();

        List<Order> orders = orderRepository.findByCustomerId(customerId);
        Bill bill = billFactory.create(customer, orders, coffeeShop.getTaxRate(), coffeeShop.getTipRate());

        billRepository.add(bill);
        coffeeShopRepository.save(coffeeShop);
        customerRepository.save(customer);
    }
}
