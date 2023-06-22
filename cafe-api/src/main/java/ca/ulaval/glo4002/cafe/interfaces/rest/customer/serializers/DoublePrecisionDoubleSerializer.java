package ca.ulaval.glo4002.cafe.interfaces.rest.customer.serializers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class DoublePrecisionDoubleSerializer extends JsonSerializer<Double> {

    @Override
    public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonGenerationException {
        if (null == value) {
            jsonGenerator.writeNull();
        } else {
            final String pattern = "0.00";
            final DecimalFormat formatter = new DecimalFormat(pattern);
            final String output = formatter.format(value);
            jsonGenerator.writeNumber(output);
        }
    }
}
