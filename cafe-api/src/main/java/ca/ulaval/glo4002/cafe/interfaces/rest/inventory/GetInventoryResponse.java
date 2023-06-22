package ca.ulaval.glo4002.cafe.interfaces.rest.inventory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetInventoryResponse {

    @JsonProperty(value = "Chocolate")
    public int chocolate;
    @JsonProperty(value = "Espresso")
    public int espresso;
    @JsonProperty(value = "Milk")
    public int milk;
    @JsonProperty(value = "Water")
    public int water;

    @JsonCreator
    public GetInventoryResponse(int chocolate, int espresso, int milk, int water) {
        this.chocolate = chocolate;
        this.espresso = espresso;
        this.milk = milk;
        this.water = water;
    }
}
