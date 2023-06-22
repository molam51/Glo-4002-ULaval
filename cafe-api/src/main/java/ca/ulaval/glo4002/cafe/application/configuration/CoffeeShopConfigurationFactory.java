package ca.ulaval.glo4002.cafe.application.configuration;

import ca.ulaval.glo4002.cafe.domain.reservation.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.domain.tax.Country;
import ca.ulaval.glo4002.cafe.domain.tax.Province;
import ca.ulaval.glo4002.cafe.domain.tax.State;

import java.util.List;

public class CoffeeShopConfigurationFactory {

    private static final List<String> DEFAULT_COFFEE_SHOP_CUBES_NAME = List.of(
            "Bloom",
            "Merryweather",
            "Tinker Bell",
            "Wanda");

    public CoffeeShopConfiguration create(String coffeeShopName,
                                          int coffeeShopCubeSize,
                                          GroupReservationMethod groupReservationMethod,
                                          Country country,
                                          Province province,
                                          State state,
                                          double groupTipRate) {
        return new CoffeeShopConfiguration(
                coffeeShopName,
                DEFAULT_COFFEE_SHOP_CUBES_NAME,
                coffeeShopCubeSize,
                groupReservationMethod,
                country,
                province,
                state,
                groupTipRate);
    }
}
