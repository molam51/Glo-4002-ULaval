package ca.ulaval.glo4002.cafe.domain.ingredient;

public class Ingredient {

    private final IngredientName ingredientName;
    private Quantity quantity;

    public Ingredient(IngredientName ingredientName, Quantity quantity) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    public IngredientName getIngredientName() {
        return ingredientName;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void addQuantity(Quantity quantityToAdd) {
        quantity = quantity.add(quantityToAdd);
    }

    public void removeQuantity(Quantity quantityToRemove) {
        quantity = quantity.remove(quantityToRemove);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Ingredient otherIngredient)) {
            return false;
        }

        return ingredientName.equals(otherIngredient.getIngredientName());
    }

    public Ingredient copy() {
        return new Ingredient(ingredientName, quantity);
    }
}
