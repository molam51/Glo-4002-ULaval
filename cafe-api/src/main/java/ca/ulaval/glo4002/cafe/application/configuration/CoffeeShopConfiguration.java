package ca.ulaval.glo4002.cafe.application.configuration;

import ca.ulaval.glo4002.cafe.domain.reservation.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.domain.tax.Country;
import ca.ulaval.glo4002.cafe.domain.tax.Province;
import ca.ulaval.glo4002.cafe.domain.tax.State;

import java.util.List;

public class CoffeeShopConfiguration {

    private final String coffeeShopName;
    private final List<String> coffeeShopCubesName;
    private final int coffeeShopCubeSize;
    private final GroupReservationMethod groupReservationMethod;
    private final Country country;
    private final Province province;
    private final State state;
    private final double groupTipRate;

    public CoffeeShopConfiguration(String coffeeShopName,
                                   List<String> coffeeShopCubesName,
                                   int coffeeShopCubeSize,
                                   GroupReservationMethod groupReservationMethod,
                                   Country country,
                                   Province province,
                                   State state,
                                   double groupTipRate) {
        this.coffeeShopName = coffeeShopName;
        this.coffeeShopCubesName = coffeeShopCubesName;
        this.coffeeShopCubeSize = coffeeShopCubeSize;
        this.groupReservationMethod = groupReservationMethod;
        this.country = country;
        this.province = province;
        this.state = state;
        this.groupTipRate = groupTipRate;
    }

    public String getCoffeeShopName() {
        return coffeeShopName;
    }

    public List<String> getCoffeeShopCubesName() {
        return coffeeShopCubesName;
    }

    public int getCoffeeShopCubeSize() {
        return coffeeShopCubeSize;
    }

    public GroupReservationMethod getGroupReservationMethod() {
        return groupReservationMethod;
    }

    public Country getCountry() {
        return country;
    }

    public Province getProvince() {
        return province;
    }

    public State getState() {
        return state;
    }

    public double getGroupTipRate() {
        return groupTipRate;
    }
}
