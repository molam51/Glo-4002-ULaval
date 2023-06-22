package ca.ulaval.glo4002.cafe.interfaces.rest.customer;

import ca.ulaval.glo4002.cafe.interfaces.rest.customer.serializers.DoublePrecisionDoubleSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public class GetBillByCustomerIdResponse {

    public List<String> orders;
    @JsonSerialize(using = DoublePrecisionDoubleSerializer.class)
    public double subtotal;
    @JsonSerialize(using = DoublePrecisionDoubleSerializer.class)
    public double taxes;
    @JsonSerialize(using = DoublePrecisionDoubleSerializer.class)
    public double tip;
    @JsonSerialize(using = DoublePrecisionDoubleSerializer.class)
    public double total;

    @JsonCreator
    public GetBillByCustomerIdResponse(List<String> orders, double subtotal, double taxes, double tip, double total) {
        this.orders = orders;
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.tip = tip;
        this.total = total;
    }
}
