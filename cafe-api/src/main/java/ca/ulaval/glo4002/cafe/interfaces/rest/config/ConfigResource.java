package ca.ulaval.glo4002.cafe.interfaces.rest.config;

import ca.ulaval.glo4002.cafe.application.CoffeeShopApplicationService;
import ca.ulaval.glo4002.cafe.application.configuration.CoffeeShopConfiguration;
import ca.ulaval.glo4002.cafe.application.configuration.CoffeeShopConfigurationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.domain.reservation.exceptions.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.domain.tax.Country;
import ca.ulaval.glo4002.cafe.domain.tax.Province;
import ca.ulaval.glo4002.cafe.domain.tax.State;
import ca.ulaval.glo4002.cafe.domain.tax.exceptions.InvalidCountryException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/config")
public class ConfigResource {

    private static final double PERCENT_CONVERSION_FACTOR = 0.01;

    private final CoffeeShopApplicationService coffeeShopApplicationService;
    private final CoffeeShopConfigurationFactory coffeeShopConfigurationFactory;

    public ConfigResource(CoffeeShopApplicationService coffeeShopApplicationService,
                          CoffeeShopConfigurationFactory coffeeShopConfigurationFactory) {
        this.coffeeShopApplicationService = coffeeShopApplicationService;
        this.coffeeShopConfigurationFactory = coffeeShopConfigurationFactory;
    }

    @POST
    public Response configure(ConfigRequest configRequest) {
        GroupReservationMethod groupReservationMethod = configRequest.groupReservationMethod;

        if (groupReservationMethod == null) {
            throw new InvalidGroupReservationMethodException();
        }

        Country country = configRequest.country;
        Province province = configRequest.province;
        State state = configRequest.state;

        if (country == null) {
            throw new InvalidCountryException();
        }

        CoffeeShopConfiguration coffeeShopConfiguration = coffeeShopConfigurationFactory.create(
                configRequest.organizationName,
                configRequest.cubeSize,
                groupReservationMethod,
                country,
                province,
                state,
                convertTipRateToPercent(configRequest.groupTipRate));

        coffeeShopApplicationService.configure(coffeeShopConfiguration);

        return Response.ok().build();
    }

    private double convertTipRateToPercent(double tipRate) {
        return tipRate * PERCENT_CONVERSION_FACTOR;
    }
}
