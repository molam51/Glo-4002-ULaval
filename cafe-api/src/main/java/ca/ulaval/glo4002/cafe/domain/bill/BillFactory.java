package ca.ulaval.glo4002.cafe.domain.bill;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.domain.tip.TipRate;

import java.util.List;

public class BillFactory {

    public Bill create(Customer customer, List<Order> orders, TaxRate taxRate, TipRate tipRate) {
        Price subtotal = orders.stream()
                .map(order -> order.getMenuItem().getPrice())
                .reduce(new Price(0), Price::add);

        Price taxes = taxRate.calculateTaxes(subtotal);

        Price tip = tipRate.calculateTip(subtotal, customer);

        Price total = subtotal.add(taxes).add(tip);

        return new Bill(customer.getId(), subtotal, taxes, tip, total, orders);
    }
}
