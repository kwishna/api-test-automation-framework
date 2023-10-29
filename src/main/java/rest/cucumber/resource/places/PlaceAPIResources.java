package rest.cucumber.resource.places;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlaceAPIResources {

    ADD_PLACE_API("/maps/api/place/add"),
    GET_PLACE_API("/maps/api/place/get"),
    DELETE_PLACE_API("/maps/api/place/delete");

    private String resource;

    public static PlaceAPIResources of(String name) {
        return PlaceAPIResources.valueOf(name);
    }


}
