package ca.ulaval.glo4002.cafe.interfaces.rest.inventory;

import ca.ulaval.glo4002.cafe.application.InventoryApplicationService;
import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;
import ca.ulaval.glo4002.cafe.domain.ingredient.IngredientName;
import ca.ulaval.glo4002.cafe.domain.ingredient.Quantity;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InventoryResourceTest {

    private static final int HTTP_OK = Response.Status.OK.getStatusCode();
    private static final int REQUEST_INGREDIENT_QUANTITY = 12;
    private static final IngredientName AN_INGREDIENT_NAME = new IngredientName("chocolate");
    private static final Quantity A_QUANTITY = new Quantity(5);

    private List<Ingredient> ingredients;
    private AddIngredientsToInventoryRequest addIngredientsToInventoryRequest;
    private InventoryApplicationService inventoryApplicationService;
    private GetInventoryResponseAssembler getInventoryResponseAssembler;
    private InventoryResource inventoryResource;

    @BeforeEach
    void setup() {
        setupRequestObjects();
        setupIngredients();
        setupMocks();

        inventoryResource = new InventoryResource(inventoryApplicationService, getInventoryResponseAssembler);
    }

    private void setupRequestObjects() {
        addIngredientsToInventoryRequest = new AddIngredientsToInventoryRequest();
        addIngredientsToInventoryRequest.chocolate = REQUEST_INGREDIENT_QUANTITY;
        addIngredientsToInventoryRequest.espresso = REQUEST_INGREDIENT_QUANTITY;
        addIngredientsToInventoryRequest.milk = REQUEST_INGREDIENT_QUANTITY;
        addIngredientsToInventoryRequest.water = REQUEST_INGREDIENT_QUANTITY;
    }

    private void setupIngredients() {
        Ingredient ingredient1 = new Ingredient(AN_INGREDIENT_NAME, A_QUANTITY);
        Ingredient ingredient2 = new Ingredient(AN_INGREDIENT_NAME, A_QUANTITY);
        ingredients = List.of(ingredient1, ingredient2);
    }

    private void setupMocks() {
        inventoryApplicationService = mock(InventoryApplicationService.class);
        getInventoryResponseAssembler = mock(GetInventoryResponseAssembler.class);
        when(inventoryApplicationService.getInventory()).thenReturn(ingredients);
    }

    @Test
    public void whenAddIngredientsToInventory_thenResponseHasHttpOkStatusCode() {
        Response response = inventoryResource.addIngredientsToInventory(addIngredientsToInventoryRequest);

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void whenAddIngredientsToInventory_thenAddIngredientsToInventoryIsCalledOnInventoryApplicationService() {
        inventoryResource.addIngredientsToInventory(addIngredientsToInventoryRequest);

        verify(inventoryApplicationService).addIngredientsToInventory(any());
    }

    @Test
    public void whenGetInventory_thenResponseHasHttpOkStatusCode() {
        Response response = inventoryResource.getInventory();

        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void givenGetInventoryResponse_whenGetInventory_thenResponseEntityIsGetInventoryResponse() {
        GetInventoryResponse getInventoryResponse = mock(GetInventoryResponse.class);
        when(getInventoryResponseAssembler.toResponse(ingredients)).thenReturn(getInventoryResponse);

        Response response = inventoryResource.getInventory();

        assertEquals(getInventoryResponse, response.getEntity());
    }
}
