package ca.ulaval.glo4002.cafe.interfaces.rest.inventory;

import ca.ulaval.glo4002.cafe.domain.ingredient.Ingredient;

import java.util.List;

public class GetInventoryResponseAssembler {

    public GetInventoryResponse toResponse(List<Ingredient> ingredients) {
        int chocolate = 0;
        int espresso = 0;
        int milk = 0;
        int water = 0;

        for (Ingredient ingredient: ingredients) {
            if (ingredient.getIngredientName().toString().equals("Chocolate")) {
                chocolate = ingredient.getQuantity().toInt();
            } else if (ingredient.getIngredientName().toString().equals("Espresso")) {
                espresso = ingredient.getQuantity().toInt();
            } else if (ingredient.getIngredientName().toString().equals("Milk")) {
                milk = ingredient.getQuantity().toInt();
            } else if (ingredient.getIngredientName().toString().equals("Water")) {
                water = ingredient.getQuantity().toInt();
            }
        }

        return new GetInventoryResponse(
                chocolate,
                espresso,
                milk,
                water);
    }
}
