package ca.ulaval.glo4002.cafe.interfaces.rest.layout;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

public class GetLayoutResponse {

    public final String name;
    public final List<CubeInGetLayoutResponse> cubes;

    @JsonCreator
    public GetLayoutResponse(String name, List<CubeInGetLayoutResponse> cubes) {
        this.name = name;
        this.cubes = cubes;
    }
}
