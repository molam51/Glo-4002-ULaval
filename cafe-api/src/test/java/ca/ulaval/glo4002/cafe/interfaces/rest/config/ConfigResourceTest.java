package ca.ulaval.glo4002.cafe.interfaces.rest.config;

import ca.ulaval.glo4002.cafe.application.CoffeeShopApplicationService;
import ca.ulaval.glo4002.cafe.application.configuration.CoffeeShopConfiguration;
import ca.ulaval.glo4002.cafe.application.configuration.CoffeeShopConfigurationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.domain.tax.Country;
import ca.ulaval.glo4002.cafe.domain.tax.Province;
import ca.ulaval.glo4002.cafe.domain.tax.State;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConfigResourceTest {

    private static final int HTTP_OK = Response.Status.OK.getStatusCode();
    private static final String A_COFFEE_SHOP_NAME = "Foo";
    private static final int A_COFFEE_SHOP_CUBE_SIZE = 12;
    private static final GroupReservationMethod A_PARSED_GROUP_RESERVATION_METHOD = GroupReservationMethod.FULL_CUBES;
    private static final Country A_PARSED_COUNTRY = Country.CA;
    private static final Province A_PARSED_PROVINCE = Province.QC;
    private static final State A_PARSED_STATE = State.TX;
    private static final double A_REQUEST_GROUP_TIP_RATE = 0;

    private CoffeeShopConfiguration coffeeShopConfiguration;
    private ConfigRequest configRequest;
    private CoffeeShopApplicationService coffeeShopApplicationService;
    private CoffeeShopConfigurationFactory coffeeShopConfigurationFactory;
    private ConfigResource configResource;

    @BeforeEach
    void setup() {
        setupConfigRequest();
        setupMocks();

        configResource = new ConfigResource(
                coffeeShopApplicationService,
                coffeeShopConfigurationFactory);
    }

    private void setupConfigRequest() {
        configRequest = new ConfigRequest();
        configRequest.organizationName = A_COFFEE_SHOP_NAME;
        configRequest.cubeSize = A_COFFEE_SHOP_CUBE_SIZE;
        configRequest.groupReservationMethod = A_PARSED_GROUP_RESERVATION_METHOD;
        configRequest.country = A_PARSED_COUNTRY;
        configRequest.province = A_PARSED_PROVINCE;
        configRequest.state = A_PARSED_STATE;
    }

    private void setupMocks() {
        coffeeShopConfiguration = mock(CoffeeShopConfiguration.class);
        coffeeShopApplicationService = mock(CoffeeShopApplicationService.class);
        coffeeShopConfigurationFactory = mock(CoffeeShopConfigurationFactory.class);
        when(coffeeShopConfigurationFactory.create(
                A_COFFEE_SHOP_NAME,
                A_COFFEE_SHOP_CUBE_SIZE,
                A_PARSED_GROUP_RESERVATION_METHOD,
                A_PARSED_COUNTRY,
                A_PARSED_PROVINCE,
                A_PARSED_STATE,
                A_REQUEST_GROUP_TIP_RATE)).thenReturn(coffeeShopConfiguration);
    }

    @Test
    public void whenConfigure_thenResponseHasHttpOkStatusCode() {
        Response response = configResource.configure(configRequest);

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void whenConfigure_thenConfigureInCoffeeShopServiceIsCalled() {
        configResource.configure(configRequest);

        verify(coffeeShopApplicationService).configure(coffeeShopConfiguration);
    }
}
