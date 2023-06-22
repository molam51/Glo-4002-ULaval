package ca.ulaval.glo4002.cafe.interfaces.rest;

import ca.ulaval.glo4002.cafe.application.CoffeeShopApplicationService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CloseResourceTest {

    private static final int HTTP_OK = Response.Status.OK.getStatusCode();

    private CoffeeShopApplicationService coffeeShopApplicationService;
    private CloseResource closeResource;

    @BeforeEach
    public void setup() {
        coffeeShopApplicationService = mock(CoffeeShopApplicationService.class);
        closeResource = new CloseResource(coffeeShopApplicationService);
    }

    @Test
    public void whenClose_thenResponseHasHttpOkStatusCode() {
        Response response = closeResource.close();

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void whenClose_thenCloseInCoffeeShopApplicationServiceIsCalled() {
        closeResource.close();

        verify(coffeeShopApplicationService).close();
    }
}
