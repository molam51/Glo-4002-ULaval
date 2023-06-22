package ca.ulaval.glo4002.cafe.interfaces.rest.layout;

import ca.ulaval.glo4002.cafe.domain.coffeeShop.CoffeeShop;
import ca.ulaval.glo4002.cafe.domain.coffeeShop.Cube;

import java.util.ArrayList;
import java.util.List;

public class GetLayoutResponseAssembler {

    private final CubeInGetLayoutResponseAssembler cubeInGetLayoutResponseAssembler;

    public GetLayoutResponseAssembler(CubeInGetLayoutResponseAssembler cubeInGetLayoutResponseAssembler) {
        this.cubeInGetLayoutResponseAssembler = cubeInGetLayoutResponseAssembler;
    }

    public GetLayoutResponse toResponse(CoffeeShop coffeeShop) {
        List<CubeInGetLayoutResponse> layoutResponse = createCubesInGetLayoutResponse(coffeeShop.getCubes());

        return new GetLayoutResponse(coffeeShop.getName(), layoutResponse);
    }

    private List<CubeInGetLayoutResponse> createCubesInGetLayoutResponse(List<Cube> cubes) {
        List<CubeInGetLayoutResponse> cubeInGetLayoutResponseArrayList = new ArrayList<>();

        for (Cube cube : cubes) {
            CubeInGetLayoutResponse cubeInGetLayoutResponse = cubeInGetLayoutResponseAssembler.toResponse(cube);
            cubeInGetLayoutResponseArrayList.add(cubeInGetLayoutResponse);
        }

        return cubeInGetLayoutResponseArrayList;
    }
}
