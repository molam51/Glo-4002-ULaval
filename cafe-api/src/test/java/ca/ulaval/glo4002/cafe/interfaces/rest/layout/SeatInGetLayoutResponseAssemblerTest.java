package ca.ulaval.glo4002.cafe.interfaces.rest.layout;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.Seat;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.SeatState;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SeatInGetLayoutResponseAssemblerTest {

    private static final Random RANDOM = new Random();
    private static final CustomerID A_CUSTOMER_ID = new CustomerID("1234");
    private static final String A_GROUP_NAME = "Runtime Terror";

    private SeatInGetLayoutResponseAssembler seatInGetLayoutResponseAssembler;

    @BeforeEach
    public void setup() {
        seatInGetLayoutResponseAssembler = new SeatInGetLayoutResponseAssembler();
    }

    @Test
    public void givenSeatWithCustomer_whenToResponse_thenResponseIsAssembled() {
        Seat givenSeatWithCustomer = givenSeatWithCustomer();

        SeatInGetLayoutResponse seatInGetLayoutResponse = seatInGetLayoutResponseAssembler.toResponse(givenSeatWithCustomer);

        assertEquals(givenSeatWithCustomer.getNumber(), seatInGetLayoutResponse.number);
        assertEquals(A_CUSTOMER_ID.getValue(), seatInGetLayoutResponse.customerId);
        assertEquals(A_GROUP_NAME, seatInGetLayoutResponse.groupName);
        assertEquals(SeatState.OCCUPIED.toString(), seatInGetLayoutResponse.status);
    }

    @Test
    public void givenSeatWithoutCustomer_whenToResponse_thenResponseCustomerIdIsNull() {
        Seat seatWithoutACustomer = givenSeatWithoutCustomer();

        SeatInGetLayoutResponse seatInGetLayoutResponse = seatInGetLayoutResponseAssembler.toResponse(seatWithoutACustomer);

        assertNull(seatInGetLayoutResponse.customerId);
    }

    @Test
    public void givenSeatWithoutGroupName_whenToResponse_thenResponseGroupNameIsNull() {
        Seat seatWithoutGroupName = givenSeatWithoutGroupName();

        SeatInGetLayoutResponse seatInGetLayoutResponse = seatInGetLayoutResponseAssembler.toResponse(seatWithoutGroupName);

        assertNull(seatInGetLayoutResponse.groupName);
    }

    private Seat givenSeatWithCustomer() {
        Seat seatWithCustomer = new Seat(RANDOM.nextInt());
        seatWithCustomer.reserve(A_GROUP_NAME);
        seatWithCustomer.occupy(A_CUSTOMER_ID, A_GROUP_NAME);
        return seatWithCustomer;
    }

    private Seat givenSeatWithoutCustomer() {
        Seat seatWithoutCustomer = new Seat(RANDOM.nextInt());
        seatWithoutCustomer.reserve(A_GROUP_NAME);
        return seatWithoutCustomer;
    }

    private Seat givenSeatWithoutGroupName() {
        Seat seatWithoutGroupName = new Seat(RANDOM.nextInt());
        seatWithoutGroupName.occupy(A_CUSTOMER_ID, null);
        return seatWithoutGroupName;
    }
}
