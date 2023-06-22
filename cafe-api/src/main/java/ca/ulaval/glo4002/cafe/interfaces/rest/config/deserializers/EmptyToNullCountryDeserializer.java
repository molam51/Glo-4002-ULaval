package ca.ulaval.glo4002.cafe.interfaces.rest.config.deserializers;

import ca.ulaval.glo4002.cafe.domain.tax.Country;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class EmptyToNullCountryDeserializer extends JsonDeserializer {

    @Override
    public Country deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getValueAsString();
        try {
            return Country.valueOf(value);
        } catch (Exception ignored) {
            return null;
        }
    }
}
