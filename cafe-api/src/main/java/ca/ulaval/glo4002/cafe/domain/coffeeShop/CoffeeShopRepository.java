package ca.ulaval.glo4002.cafe.domain.coffeeShop;

public interface CoffeeShopRepository {

    void save(CoffeeShop coffeeShop);

    CoffeeShop find();
}
