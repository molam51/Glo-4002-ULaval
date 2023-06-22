package ca.ulaval.glo4002.cafe.interfaces.rest.config.deserializers;

import ca.ulaval.glo4002.cafe.domain.tax.State;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class EmptyToNullStateDeserializer extends JsonDeserializer {

    @Override
    public State deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getValueAsString();
        try {
            return State.valueOf(value);
        } catch (Exception ignored) {
            return null;
        }
    }
}
