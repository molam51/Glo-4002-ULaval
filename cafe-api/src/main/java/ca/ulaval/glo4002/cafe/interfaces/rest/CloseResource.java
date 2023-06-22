package ca.ulaval.glo4002.cafe.interfaces.rest;

import ca.ulaval.glo4002.cafe.application.CoffeeShopApplicationService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/close")
@Produces(MediaType.APPLICATION_JSON)
public class CloseResource {

    private final CoffeeShopApplicationService coffeeShopApplicationService;

    public CloseResource(CoffeeShopApplicationService coffeeShopApplicationService) {
        this.coffeeShopApplicationService = coffeeShopApplicationService;
    }

    @POST
    public Response close() {
        coffeeShopApplicationService.close();
        return Response.ok().build();
    }
}
