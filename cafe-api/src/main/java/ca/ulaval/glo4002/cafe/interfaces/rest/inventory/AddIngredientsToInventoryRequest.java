package ca.ulaval.glo4002.cafe.interfaces.rest.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddIngredientsToInventoryRequest {

    @JsonProperty(value = "Chocolate")
    public int chocolate;

    @JsonProperty(value = "Espresso")
    public int espresso;

    @JsonProperty(value = "Milk")
    public int milk;

    @JsonProperty(value = "Water")
    public int water;
}
