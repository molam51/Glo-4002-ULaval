package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.order.Order;

import java.util.List;

public class GetBillByCustomerIdResponseAssembler {

    public GetBillByCustomerIdResponse toResponse(Bill bill) {
        List<String> orderItemNames = bill.getOrders().stream()
                .map(Order::getItemNameAsString)
                .toList();

        return new GetBillByCustomerIdResponse(
                orderItemNames,
                bill.getSubtotal().toRoundedUpDouble(2),
                bill.getTaxes().toRoundedUpDouble(2),
                bill.getTip().toRoundedUpDouble(2),
                bill.getTotal().toRoundedUpDouble(2));
    }
}
