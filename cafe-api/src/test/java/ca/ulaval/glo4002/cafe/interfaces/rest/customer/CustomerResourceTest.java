package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import ca.ulaval.glo4002.cafe.application.BillApplicationService;
import ca.ulaval.glo4002.cafe.application.CustomerApplicationService;
import ca.ulaval.glo4002.cafe.application.OrderApplicationService;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerResourceTest {

    private static final int HTTP_OK = Response.Status.OK.getStatusCode();
    private static final int HTTP_CREATED = Response.Status.CREATED.getStatusCode();
    private static final String A_CUSTOMER_NAME = "Foo";
    private static final String A_CUSTOMER_ID_VALUE = "abc-123";
    private static final String A_CUSTOMER_LOCATION = String.format("/customers/%s", A_CUSTOMER_ID_VALUE);
    private static final String A_CUSTOMER_BILL_LOCATION = String.format("/customers/%s/bill", A_CUSTOMER_ID_VALUE);
    private static final String A_GROUP_NAME = "P'tit Dej.";
    private static final String AN_ORDER_NAME = "Baba-o-rum";
    private static final String ANOTHER_ORDER_NAME = "Biscuits Breton";
    private static final CustomerID A_CUSTOMER_ID = new CustomerID(A_CUSTOMER_ID_VALUE);

    private Customer customer;
    private GetCustomerRequest getCustomerRequest;
    private CheckInRequest checkInRequestWithGroupName;
    private CheckOutRequest checkOutRequest;
    private OrderRequest orderRequest;
    private CustomerApplicationService customerApplicationService;
    private OrderApplicationService orderApplicationService;
    private BillApplicationService billApplicationService;
    private GetCustomerResponseAssembler getCustomerResponseAssembler;
    private GetOrdersByCustomerIdResponseAssembler getOrdersByCustomerIdResponseAssembler;
    private GetBillByCustomerIdResponseAssembler getBillByCustomerIdResponseAssembler;
    private CustomerResource customerResource;

    @BeforeEach
    void setup() {
        setupRequestObjects();
        setupMocks();

        customerResource = new CustomerResource(
                customerApplicationService,
                orderApplicationService,
                billApplicationService,
                getCustomerResponseAssembler,
                getOrdersByCustomerIdResponseAssembler,
                getBillByCustomerIdResponseAssembler);
    }

    private void setupRequestObjects() {
        getCustomerRequest = new GetCustomerRequest();
        getCustomerRequest.customerId = A_CUSTOMER_ID_VALUE;

        checkInRequestWithGroupName = new CheckInRequest();
        checkInRequestWithGroupName.customerId = A_CUSTOMER_ID_VALUE;
        checkInRequestWithGroupName.customerName = A_CUSTOMER_NAME;
        checkInRequestWithGroupName.groupName = A_GROUP_NAME;

        orderRequest = new OrderRequest();
        orderRequest.orders = List.of(AN_ORDER_NAME, ANOTHER_ORDER_NAME);

        checkOutRequest = new CheckOutRequest();
        checkOutRequest.customerIdValue = A_CUSTOMER_ID_VALUE;
    }

    private void setupMocks() {
        customer = mock(Customer.class);
        customerApplicationService = mock(CustomerApplicationService.class);
        orderApplicationService = mock(OrderApplicationService.class);
        billApplicationService = mock(BillApplicationService.class);
        getCustomerResponseAssembler = mock(GetCustomerResponseAssembler.class);
        getOrdersByCustomerIdResponseAssembler = mock(GetOrdersByCustomerIdResponseAssembler.class);
        getBillByCustomerIdResponseAssembler = mock(GetBillByCustomerIdResponseAssembler.class);
    }

    @Test
    public void whenGetCustomer_thenResponseHasHttpOkStatusCode() {
        Response response = customerResource.getCustomer(getCustomerRequest);

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void givenGetCustomerResponse_whenGetCustomer_thenResponseIsGetCustomerResponse() {
        GetCustomerResponse getCustomerResponse = mock(GetCustomerResponse.class);
        when(customerApplicationService.getCustomer(A_CUSTOMER_ID)).thenReturn(customer);
        when(getCustomerResponseAssembler.toResponse(customer)).thenReturn(getCustomerResponse);

        Response response = customerResource.getCustomer(getCustomerRequest);

        assertEquals(getCustomerResponse, response.getEntity());
    }

    @Test
    public void whenCheckIn_thenResponseHasHttpCreatedStatusCode() {
        Response response = customerResource.checkIn(checkInRequestWithGroupName);

        assertEquals(HTTP_CREATED, response.getStatus());
    }

    @Test
    public void givenCheckInRequestWithGroupName_whenCheckIn_thenCheckInWithGroupNameInCustomerServiceIsCalled() {
        customerResource.checkIn(checkInRequestWithGroupName);

        verify(customerApplicationService).checkIn(
                A_CUSTOMER_ID,
                checkInRequestWithGroupName.customerName,
                checkInRequestWithGroupName.groupName);
    }

    @Test
    public void givenCheckInRequestWithNoGroupName_whenCheckIn_thenCheckInWithNoGroupNameInCustomerServiceIsCalled() {
        CheckInRequest checkInRequestWithNoGroupName = new CheckInRequest();
        checkInRequestWithNoGroupName.customerId = A_CUSTOMER_ID_VALUE;
        checkInRequestWithNoGroupName.customerName = A_CUSTOMER_NAME;

        customerResource.checkIn(checkInRequestWithNoGroupName);

        verify(customerApplicationService).checkIn(A_CUSTOMER_ID, checkInRequestWithNoGroupName.customerName);
    }

    @Test
    public void givenCustomerId_whenCheckIn_thenLocationHeaderContainsCustomerLocation() {
        Response response = customerResource.checkIn(checkInRequestWithGroupName);

        String actualLocationHeader = response.getLocation().toString();
        assertEquals(A_CUSTOMER_LOCATION, actualLocationHeader);
    }

    @Test
    public void whenOrder_thenResponseHasHttpOkStatusCode() {
        Response response = customerResource.order(A_CUSTOMER_ID_VALUE, orderRequest);

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void givenCustomerIdAndMenuItemNames_whenOrder_thenOrderIsCalledInOrderApplicationService() {
        MenuItemName aMenuItemName = new MenuItemName(AN_ORDER_NAME);
        MenuItemName anotherMenuItemName = new MenuItemName(ANOTHER_ORDER_NAME);
        List<MenuItemName> menuItemNames = List.of(aMenuItemName, anotherMenuItemName);

        customerResource.order(A_CUSTOMER_ID_VALUE, orderRequest);

        verify(orderApplicationService).order(A_CUSTOMER_ID, menuItemNames);
    }

    @Test
    public void whenGetOrdersByCustomerId_thenResponseHasHttpOkStatusCode() {
        Response response = customerResource.getOrdersByCustomerId(A_CUSTOMER_ID_VALUE);

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void givenGetOrdersByCustomerIdResponse_whenGetOrdersByCustomerId_thenResponseIsGetOrdersByCustomerIdResponse() {
        GetOrdersByCustomerIdResponse getOrdersByCustomerIdResponse = mock(GetOrdersByCustomerIdResponse.class);
        List<Order> orders = List.of(mock(Order.class));
        when(orderApplicationService.getOrdersByCustomerId(A_CUSTOMER_ID)).thenReturn(orders);
        when(getOrdersByCustomerIdResponseAssembler.toResponse(orders)).thenReturn(getOrdersByCustomerIdResponse);

        Response response = customerResource.getOrdersByCustomerId(A_CUSTOMER_ID_VALUE);

        assertEquals(getOrdersByCustomerIdResponse, response.getEntity());
    }

    @Test
    public void whenGetBillByCustomerId_thenResponseHasHttpOkStatusCode() {
        Response response = customerResource.getBillByCustomerId(A_CUSTOMER_ID_VALUE);

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void givenGetBillByCustomerIdResponse_whenGetBillByCustomerId_thenResponseIsGetBillByCustomerIdResponse() {
        GetBillByCustomerIdResponse getBillByCustomerIdResponse = mock(GetBillByCustomerIdResponse.class);
        Bill bill = mock(Bill.class);
        when(billApplicationService.getBillByCustomerId(A_CUSTOMER_ID)).thenReturn(bill);
        when(getBillByCustomerIdResponseAssembler.toResponse(bill)).thenReturn(getBillByCustomerIdResponse);

        Response response = customerResource.getBillByCustomerId(A_CUSTOMER_ID_VALUE);

        assertEquals(getBillByCustomerIdResponse, response.getEntity());
    }

    @Test
    public void whenCheckOut_thenResponseHasHttpCreatedStatusCode() {
        Response response = customerResource.checkOut(checkOutRequest);

        assertEquals(HTTP_CREATED, response.getStatus());
    }

    @Test
    public void givenCheckOutRequest_whenCheckOut_thenCheckOutInCustomerServiceIsCalled() {
        customerResource.checkOut(checkOutRequest);

        verify(customerApplicationService).checkOut(A_CUSTOMER_ID);
    }

    @Test
    public void givenCustomerId_whenCheckOut_thenLocationHeaderContainsCustomerBillLocation() {
        Response response = customerResource.checkOut(checkOutRequest);

        String actualLocationHeader = response.getLocation().toString();
        assertEquals(A_CUSTOMER_BILL_LOCATION, actualLocationHeader);
    }
}
