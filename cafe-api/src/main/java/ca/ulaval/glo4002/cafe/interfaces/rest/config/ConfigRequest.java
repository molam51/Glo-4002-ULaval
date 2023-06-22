package ca.ulaval.glo4002.cafe.interfaces.rest.config;

import ca.ulaval.glo4002.cafe.domain.reservation.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.domain.tax.Country;
import ca.ulaval.glo4002.cafe.domain.tax.Province;
import ca.ulaval.glo4002.cafe.domain.tax.State;
import ca.ulaval.glo4002.cafe.interfaces.rest.config.deserializers.EmptyToNullCountryDeserializer;
import ca.ulaval.glo4002.cafe.interfaces.rest.config.deserializers.EmptyToNullGroupReservationMethodDeserializer;
import ca.ulaval.glo4002.cafe.interfaces.rest.config.deserializers.EmptyToNullProvinceDeserializer;
import ca.ulaval.glo4002.cafe.interfaces.rest.config.deserializers.EmptyToNullStateDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigRequest {

    @JsonProperty("group_reservation_method")
    @JsonDeserialize(using = EmptyToNullGroupReservationMethodDeserializer.class)
    public GroupReservationMethod groupReservationMethod;

    @JsonProperty("organization_name")
    public String organizationName;

    @JsonProperty("cube_size")
    public int cubeSize;

    @JsonProperty("country")
    @JsonDeserialize(using = EmptyToNullCountryDeserializer.class)
    public Country country;

    @JsonProperty("province")
    @JsonDeserialize(using = EmptyToNullProvinceDeserializer.class)
    public Province province;

    @JsonProperty("state")
    @JsonDeserialize(using = EmptyToNullStateDeserializer.class)
    public State state;

    @JsonProperty("group_tip_rate")
    public double groupTipRate;
}
