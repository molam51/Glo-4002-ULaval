package ca.ulaval.glo4002.cafe.interfaces.rest.layout;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.Cube;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CubeInGetLayoutResponseAssemblerTest {

    private static final String A_CUBE_NAME = "Foo";
    private static final int A_SEAT_NUMBER_1 = 1;
    private static final int A_SEAT_NUMBER_2 = 2;

    private Seat aSeat;
    private Seat anotherSeat;
    private Cube cube;
    private SeatInGetLayoutResponseAssembler seatInGetLayoutResponseAssembler;
    private CubeInGetLayoutResponseAssembler cubeInGetLayoutResponseAssembler;

    @BeforeEach
    public void setup() {
        setupCube();

        seatInGetLayoutResponseAssembler = mock(SeatInGetLayoutResponseAssembler.class);
        cubeInGetLayoutResponseAssembler = new CubeInGetLayoutResponseAssembler(seatInGetLayoutResponseAssembler);
    }

    private void setupCube() {
        aSeat = new Seat(A_SEAT_NUMBER_1);
        anotherSeat = new Seat(A_SEAT_NUMBER_2);
        cube = new Cube(A_CUBE_NAME, List.of(aSeat, anotherSeat));
    }

    @Test
    public void givenCube_whenToResponse_thenResponseIsAssembled() {
        CubeInGetLayoutResponse cubeInGetLayoutResponse = cubeInGetLayoutResponseAssembler.toResponse(cube);

        assertEquals(A_CUBE_NAME, cubeInGetLayoutResponse.name);
        assertEquals(cube.getSeats().size(), cubeInGetLayoutResponse.seats.size());
    }

    @Test
    public void givenCube_whenToResponse_thenToResponseIsCalledOnSeatAssemblerForEachSeat() {
        cubeInGetLayoutResponseAssembler.toResponse(cube);

        verify(seatInGetLayoutResponseAssembler).toResponse(aSeat);
        verify(seatInGetLayoutResponseAssembler).toResponse(anotherSeat);
    }
}
