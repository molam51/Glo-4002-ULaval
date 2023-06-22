package ca.ulaval.glo4002.cafe.interfaces.rest.config.deserializers;

import ca.ulaval.glo4002.cafe.domain.reservation.GroupReservationMethod;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class EmptyToNullGroupReservationMethodDeserializer extends JsonDeserializer {

    @Override
    public GroupReservationMethod deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getValueAsString();
        return switch (value) {
            case "Default" -> GroupReservationMethod.DEFAULT;
            case "Full Cubes" -> GroupReservationMethod.FULL_CUBES;
            case "No Loners" -> GroupReservationMethod.NO_LONERS;
            default -> null;
        };
    }
}
