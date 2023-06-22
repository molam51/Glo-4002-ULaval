package ca.ulaval.glo4002.cafe.interfaces.rest.layout;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.Cube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetLayoutResponseAssemblerTest {

    private static final String A_COFFEE_SHOP_NAME = "Foo";
    private static final String A_CUBE_NAME = "Foo";
    private static final String ANOTHER_CUBE_NAME = "Bar";

    private GetLayoutResponseAssembler getLayoutResponseAssembler;
    private CubeInGetLayoutResponseAssembler cubeInGetLayoutResponseAssembler;

    @BeforeEach
    public void setup() {
        cubeInGetLayoutResponseAssembler = mock(CubeInGetLayoutResponseAssembler.class);

        getLayoutResponseAssembler = new GetLayoutResponseAssembler(cubeInGetLayoutResponseAssembler);
    }


    @Test
    public void givenCoffeeShop_whenToResponse_thenResponseIsAssembled() {
        Cube aCube = new Cube(A_CUBE_NAME, List.of());
        Cube anotherCube = new Cube(ANOTHER_CUBE_NAME, List.of());
        CoffeeShop coffeeShop = givenCoffeeShop(aCube, anotherCube);
        CubeInGetLayoutResponse aCubeInGetLayoutResponse = givenCubeInGetLayoutResponse(aCube);
        CubeInGetLayoutResponse anotherCubeInGetLayoutResponse = givenCubeInGetLayoutResponse(anotherCube);

        GetLayoutResponse getLayoutResponse = getLayoutResponseAssembler.toResponse(coffeeShop);

        assertEquals(A_COFFEE_SHOP_NAME, getLayoutResponse.name);
        assertTrue(getLayoutResponse.cubes.containsAll(List.of(aCubeInGetLayoutResponse, anotherCubeInGetLayoutResponse)));
    }

    private CubeInGetLayoutResponse givenCubeInGetLayoutResponse(Cube cube) {
        CubeInGetLayoutResponse cubeInGetLayoutResponse = mock(CubeInGetLayoutResponse.class);
        when(cubeInGetLayoutResponseAssembler.toResponse(cube)).thenReturn(cubeInGetLayoutResponse);

        return cubeInGetLayoutResponse;
    }

    private CoffeeShop givenCoffeeShop(Cube... cubes) {
        CoffeeShop coffeeShop = mock(CoffeeShop.class);
        when(coffeeShop.getName()).thenReturn(A_COFFEE_SHOP_NAME);
        when(coffeeShop.getCubes()).thenReturn(List.of(cubes));

        return coffeeShop;
    }
}
