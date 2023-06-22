package ca.ulaval.glo4002.cafe.interfaces.rest.inventory;

import ca.ulaval.glo4002.cafe.application.InventoryApplicationService;
import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
public class InventoryResource {

    private final InventoryApplicationService inventoryApplicationService;
    private final GetInventoryResponseAssembler getInventoryResponseAssembler;

    public InventoryResource(InventoryApplicationService inventoryApplicationService,
                             GetInventoryResponseAssembler getInventoryResponseAssembler) {
        this.inventoryApplicationService = inventoryApplicationService;
        this.getInventoryResponseAssembler = getInventoryResponseAssembler;
    }

    @GET
    public Response getInventory() {
        List<Ingredient> ingredients = inventoryApplicationService.getInventory();

        GetInventoryResponse getInventoryResponse =
                getInventoryResponseAssembler.toResponse(ingredients);

        return Response.ok(getInventoryResponse).build();
    }

    @PUT
    public Response addIngredientsToInventory(AddIngredientsToInventoryRequest addIngredientsToInventoryRequest) {
        Ingredient chocolateIngredient = new Ingredient(new IngredientName("Chocolate"),
                new Quantity(addIngredientsToInventoryRequest.chocolate));
        Ingredient espressoIngredient = new Ingredient(new IngredientName("Espresso"),
                new Quantity(addIngredientsToInventoryRequest.espresso));
        Ingredient milkIngredient = new Ingredient(new IngredientName("Milk"),
                new Quantity(addIngredientsToInventoryRequest.milk));
        Ingredient waterIngredient = new Ingredient(new IngredientName("Water"),
                new Quantity(addIngredientsToInventoryRequest.water));

        List<Ingredient> ingredients = List.of(chocolateIngredient,
                espressoIngredient,
                milkIngredient,
                waterIngredient);

        inventoryApplicationService.addIngredientsToInventory(ingredients);

        return Response.ok().build();
    }
}
