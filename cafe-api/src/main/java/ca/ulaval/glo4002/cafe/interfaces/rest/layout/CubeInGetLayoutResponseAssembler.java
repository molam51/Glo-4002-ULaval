package ca.ulaval.glo4002.cafe.interfaces.rest.layout;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.Cube;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.Seat;

import java.util.ArrayList;
import java.util.List;

public class CubeInGetLayoutResponseAssembler {

    private final SeatInGetLayoutResponseAssembler seatInGetLayoutResponseAssembler;

    public CubeInGetLayoutResponseAssembler(SeatInGetLayoutResponseAssembler seatInGetLayoutResponseAssembler) {
        this.seatInGetLayoutResponseAssembler = seatInGetLayoutResponseAssembler;
    }

    public CubeInGetLayoutResponse toResponse(Cube cube) {
        List<SeatInGetLayoutResponse> seats = createSeatsInGetLayoutResponse(cube.getSeats());

        return new CubeInGetLayoutResponse(cube.getName(), seats);
    }

    private List<SeatInGetLayoutResponse> createSeatsInGetLayoutResponse(List<Seat> seats) {
        List<SeatInGetLayoutResponse> seatInGetLayoutResponseArrayList = new ArrayList<>();

        for (Seat seat : seats) {
            SeatInGetLayoutResponse seatInGetLayoutResponse = seatInGetLayoutResponseAssembler.toResponse(seat);
            seatInGetLayoutResponseArrayList.add(seatInGetLayoutResponse);
        }

        return seatInGetLayoutResponseArrayList;
    }
}
