package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShopRepository;

public class InMemoryCoffeeShopRepository implements CoffeeShopRepository {

    private CoffeeShop coffeeShop;

    @Override
    public void save(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @Override
    public CoffeeShop find() {
        return coffeeShop;
    }
}
