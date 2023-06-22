package ca.ulaval.glo4002.cafe.domain.coffeeShop;

import ca.ulaval.glo4002.cafe.application.configuration.CoffeeShopConfiguration;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRateFactory;
import ca.ulaval.glo4002.cafe.domain.tip.TipRate;
import ca.ulaval.glo4002.cafe.domain.tip.TipRateFactory;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShopFactory {

    private final ReservationStrategyFactory reservationStrategyFactory;
    private final TaxRateFactory taxRateFactory;
    private final TipRateFactory tipRateFactory;

    public CoffeeShopFactory(ReservationStrategyFactory reservationStrategyFactory, TaxRateFactory taxRateFactory, TipRateFactory tipRateFactory) {
        this.reservationStrategyFactory = reservationStrategyFactory;
        this.taxRateFactory = taxRateFactory;
        this.tipRateFactory = tipRateFactory;
    }

    public CoffeeShop create(CoffeeShopConfiguration coffeeShopConfiguration) {
        List<Cube> cubes = createCubes(
                coffeeShopConfiguration.getCoffeeShopCubesName(),
                coffeeShopConfiguration.getCoffeeShopCubeSize());
        ReservationStrategy reservationStrategy = reservationStrategyFactory.create(
                coffeeShopConfiguration.getGroupReservationMethod());

        TaxRate taxRate = taxRateFactory.create(
                coffeeShopConfiguration.getCountry(),
                coffeeShopConfiguration.getProvince(),
                coffeeShopConfiguration.getState());

        TipRate tipRate = tipRateFactory.create(coffeeShopConfiguration.getGroupTipRate());

        return new CoffeeShop(
                coffeeShopConfiguration.getCoffeeShopName(),
                cubes,
                reservationStrategy,
                new DefaultCheckInStrategy(),
                new DefaultCheckOutStrategy(),
                taxRate,
                tipRate);
    }

    private List<Cube> createCubes(List<String> cubeNames, int cubeSize) {
        List<Cube> cubes = new ArrayList<>();

        for (int cubeNameIndex = 0; cubeNameIndex < cubeNames.size(); cubeNameIndex++) {
            String cubeName = cubeNames.get(cubeNameIndex);
            List<Seat> cubesSeats = createSeats(cubeNameIndex * cubeSize, cubeSize);

            cubes.add(new Cube(cubeName, cubesSeats));
        }

        return cubes;
    }

    private List<Seat> createSeats(int seatNumberOffset, int seatCount) {
        List<Seat> cubesSeats = new ArrayList<>();

        int startSeatNumber = 1 + seatNumberOffset;
        int endSeatNumber = seatCount + seatNumberOffset;
        for (int seatNumber = startSeatNumber; seatNumber <= endSeatNumber; seatNumber++) {
            cubesSeats.add(new Seat(seatNumber));
        }

        return cubesSeats;
    }
}
