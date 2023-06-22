package ca.ulaval.glo4002.cafe.application.configuration;

import ca.ulaval.glo4002.cafe.domain.reservation.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.domain.tax.Country;
import ca.ulaval.glo4002.cafe.domain.tax.Province;
import ca.ulaval.glo4002.cafe.domain.tax.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoffeeShopConfigurationFactoryTest {

    private static final String A_COFFEE_SHOP_NAME = "Les 4-Fées";
    private static final int A_COFFEE_SHOP_CUBE_SIZE = 4;
    private static final GroupReservationMethod A_GROUP_RESERVATION_METHOD = GroupReservationMethod.DEFAULT;
    private static final Country A_COUNTRY = Country.CA;
    private static final Province A_PROVINCE = Province.QC;
    private static final State A_STATE = State.TX;
    private static final double A_GROUP_TIP_RATE = 0;

    @Test
    public void whenCreate_thenConfigurationIsCreated() {
        CoffeeShopConfigurationFactory coffeeShopConfigurationFactory = new CoffeeShopConfigurationFactory();

        CoffeeShopConfiguration coffeeShopConfiguration = coffeeShopConfigurationFactory.create(
                A_COFFEE_SHOP_NAME,
                A_COFFEE_SHOP_CUBE_SIZE,
                A_GROUP_RESERVATION_METHOD,
                A_COUNTRY,
                A_PROVINCE,
                A_STATE,
                A_GROUP_TIP_RATE);

        assertEquals(A_COFFEE_SHOP_NAME, coffeeShopConfiguration.getCoffeeShopName());
        assertEquals(A_COFFEE_SHOP_CUBE_SIZE, coffeeShopConfiguration.getCoffeeShopCubeSize());
        assertEquals(A_GROUP_RESERVATION_METHOD, coffeeShopConfiguration.getGroupReservationMethod());
        assertEquals(A_COUNTRY, coffeeShopConfiguration.getCountry());
        assertEquals(A_PROVINCE, coffeeShopConfiguration.getProvince());
        assertEquals(A_STATE, coffeeShopConfiguration.getState());
        assertEquals(A_GROUP_TIP_RATE, coffeeShopConfiguration.getGroupTipRate());
    }
}
