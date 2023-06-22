package ca.ulaval.glo4002.cafe.interfaces.rest.config.deserializers;

import ca.ulaval.glo4002.cafe.domain.tax.Province;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class EmptyToNullProvinceDeserializer extends JsonDeserializer {

    @Override
    public Province deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getValueAsString();
        try {
            return Province.valueOf(value);
        } catch (Exception ignored) {
            return null;
        }
    }
}
