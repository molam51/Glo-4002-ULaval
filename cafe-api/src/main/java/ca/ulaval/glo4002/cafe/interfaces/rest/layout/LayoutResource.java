package ca.ulaval.glo4002.cafe.interfaces.rest.layout;

import ca.ulaval.glo4002.cafe.application.CoffeeShopApplicationService;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/layout")
@Produces(MediaType.APPLICATION_JSON)
public class LayoutResource {

    private final CoffeeShopApplicationService coffeeShopApplicationService;
    private final GetLayoutResponseAssembler getLayoutResponseAssembler;

    public LayoutResource(CoffeeShopApplicationService coffeeShopApplicationService, GetLayoutResponseAssembler getLayoutResponseAssembler) {
        this.coffeeShopApplicationService = coffeeShopApplicationService;
        this.getLayoutResponseAssembler = getLayoutResponseAssembler;
    }

    @GET
    public Response getLayout() {
        CoffeeShop coffeeShop = coffeeShopApplicationService.getCoffeeShop();
        GetLayoutResponse response = getLayoutResponseAssembler.toResponse(coffeeShop);
        return Response.ok(response).build();
    }
}
