package ca.ulaval.glo4002.cafe.application;

import ca.ulaval.glo4002.cafe.application.configuration.CoffeeShopConfiguration;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShopFactory;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShopRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientsInventory;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;

public class CoffeeShopApplicationService {

    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final CoffeeShopRepository coffeeShopRepository;
    private final OrderRepository orderRepository;
    private final BillRepository billRepository;
    private final IngredientsInventory ingredientsInventory;
    private final CoffeeShopFactory coffeeShopFactory;
    private CoffeeShopConfiguration coffeeShopConfiguration;

    public CoffeeShopApplicationService(CustomerRepository customerRepository,
                                        ReservationRepository reservationRepository,
                                        CoffeeShopRepository coffeeShopRepository,
                                        OrderRepository orderRepository,
                                        BillRepository billRepository,
                                        IngredientsInventory ingredientsInventory,
                                        CoffeeShopFactory coffeeShopFactory,
                                        CoffeeShopConfiguration coffeeShopConfiguration) {
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.coffeeShopRepository = coffeeShopRepository;
        this.orderRepository = orderRepository;
        this.billRepository = billRepository;
        this.ingredientsInventory = ingredientsInventory;
        this.coffeeShopFactory = coffeeShopFactory;
        this.coffeeShopConfiguration = coffeeShopConfiguration;
    }

    public void close() {
        customerRepository.clear();
        reservationRepository.clear();
        orderRepository.clear();
        billRepository.clear();
        ingredientsInventory.clear();

        CoffeeShop coffeeShop = coffeeShopFactory.create(coffeeShopConfiguration);
        coffeeShopRepository.save(coffeeShop);
    }

    public CoffeeShop getCoffeeShop() {
        return coffeeShopRepository.find();
    }

    public void configure(CoffeeShopConfiguration coffeeShopConfiguration) {
        this.coffeeShopConfiguration = coffeeShopConfiguration;

        close();
    }
}
