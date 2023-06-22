package ca.ulaval.glo4002.cafe.interfaces.rest.layout;

import ca.ulaval.glo4002.cafe.application.CoffeeShopApplicationService;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LayoutResourceTest {

    private static final int HTTP_OK = Response.Status.OK.getStatusCode();

    private CoffeeShop coffeeShop;
    private CoffeeShopApplicationService coffeeShopApplicationService;
    private GetLayoutResponseAssembler getLayoutResponseAssembler;
    private LayoutResource layoutResource;

    @BeforeEach
    public void setup() {
        setupMocks();

        layoutResource = new LayoutResource(coffeeShopApplicationService, getLayoutResponseAssembler);
    }

    private void setupMocks() {
        coffeeShop = mock(CoffeeShop.class);
        coffeeShopApplicationService = mock(CoffeeShopApplicationService.class);
        getLayoutResponseAssembler = mock(GetLayoutResponseAssembler.class);
        when(coffeeShopApplicationService.getCoffeeShop()).thenReturn(coffeeShop);
    }

    @Test
    public void whenGetLayout_thenResponseHasHttpOkStatusCode() {
        Response response = layoutResource.getLayout();

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void givenGetLayoutResponse_whenGetLayout_thenResponseEntityIsGetLayoutResponse() {
        GetLayoutResponse getLayoutResponse = mock(GetLayoutResponse.class);
        when(getLayoutResponseAssembler.toResponse(coffeeShop)).thenReturn(getLayoutResponse);

        Response response = layoutResource.getLayout();

        assertEquals(getLayoutResponse, response.getEntity());
    }
}
