package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import ca.ulaval.glo4002.cafe.application.BillApplicationService;
import ca.ulaval.glo4002.cafe.application.CustomerApplicationService;
import ca.ulaval.glo4002.cafe.application.OrderApplicationService;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerID;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemName;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private static final String LOCATION = "/customers/%s";
    private static final String BILL_LOCATION = "/customers/%s/bill";
    private final CustomerApplicationService customerApplicationService;
    private final OrderApplicationService orderApplicationService;
    private final BillApplicationService billApplicationService;
    private final GetCustomerResponseAssembler getCustomerResponseAssembler;
    private final GetOrdersByCustomerIdResponseAssembler getOrdersByCustomerIdResponseAssembler;
    private final GetBillByCustomerIdResponseAssembler getBillByCustomerIdResponseAssembler;

    public CustomerResource(CustomerApplicationService customerApplicationService,
                            OrderApplicationService orderApplicationService,
                            BillApplicationService billApplicationService,
                            GetCustomerResponseAssembler getCustomerResponseAssembler,
                            GetOrdersByCustomerIdResponseAssembler getOrdersByCustomerIdResponseAssembler,
                            GetBillByCustomerIdResponseAssembler getBillByCustomerIdResponseAssembler) {
        this.customerApplicationService = customerApplicationService;
        this.orderApplicationService = orderApplicationService;
        this.billApplicationService = billApplicationService;
        this.getCustomerResponseAssembler = getCustomerResponseAssembler;
        this.getOrdersByCustomerIdResponseAssembler = getOrdersByCustomerIdResponseAssembler;
        this.getBillByCustomerIdResponseAssembler = getBillByCustomerIdResponseAssembler;
    }

    @GET
    @Path("/customers/{customer_id}")
    public Response getCustomer(@BeanParam GetCustomerRequest getCustomerRequest) {
        CustomerID customerId = new CustomerID(getCustomerRequest.customerId);

        Customer customer = customerApplicationService.getCustomer(customerId);

        GetCustomerResponse getCustomerResponse = getCustomerResponseAssembler.toResponse(customer);

        return Response.ok(getCustomerResponse).build();
    }

    @POST
    @Path("/check-in")
    public Response checkIn(CheckInRequest checkInRequest) {
        CustomerID customerId = new CustomerID(checkInRequest.customerId);

        if (checkInRequest.groupName == null) {
            customerApplicationService.checkIn(customerId, checkInRequest.customerName);
        } else {
            customerApplicationService.checkIn(customerId, checkInRequest.customerName, checkInRequest.groupName);
        }

        URI customerLocation = URI.create(String.format(LOCATION, checkInRequest.customerId));

        return Response.created(customerLocation).build();
    }

    @POST
    @Path("/checkout")
    public Response checkOut(CheckOutRequest checkOutRequest) {
        CustomerID customerId = new CustomerID(checkOutRequest.customerIdValue);

        customerApplicationService.checkOut(customerId);

        URI customerLocation = URI.create(String.format(BILL_LOCATION, checkOutRequest.customerIdValue));

        return Response.created(customerLocation).build();
    }

    @PUT
    @Path("/customers/{customer_id}/orders")
    public Response order(@PathParam("customer_id") String customerIdValue, OrderRequest orderRequest) {
        CustomerID customerId = new CustomerID(customerIdValue);

        List<MenuItemName> menuItemNames = orderRequest.orders.stream()
                .map(MenuItemName::new)
                .toList();

        orderApplicationService.order(customerId, menuItemNames);

        return Response.ok().build();
    }

    @GET
    @Path("/customers/{customer_id}/orders")
    public Response getOrdersByCustomerId(@PathParam("customer_id") String customerIdValue) {
        CustomerID customerId = new CustomerID(customerIdValue);

        List<Order> orders = orderApplicationService.getOrdersByCustomerId(customerId);

        GetOrdersByCustomerIdResponse getOrdersByCustomerIdResponse =
                getOrdersByCustomerIdResponseAssembler.toResponse(orders);

        return Response.ok(getOrdersByCustomerIdResponse).build();
    }

    @GET
    @Path("/customers/{customer_id}/bill")
    public Response getBillByCustomerId(@PathParam("customer_id") String customerIdValue) {
        CustomerID customerId = new CustomerID(customerIdValue);

        Bill bill = billApplicationService.getBillByCustomerId(customerId);

        GetBillByCustomerIdResponse getBillByCustomerIdResponse =
                getBillByCustomerIdResponseAssembler.toResponse(bill);

        return Response.ok(getBillByCustomerIdResponse).build();
    }
}
