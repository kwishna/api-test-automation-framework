package rest.cucumber.specifications.response.places;

import io.restassured.specification.ResponseSpecification;
import rest.cucumber.specifications.response.common.CommonResponseSpecs;

public class PlaceResponseSpecs extends CommonResponseSpecs{
    public static ResponseSpecification addPlaceReponseSpec() {
        setBasicResponseSpecs();
        return respSpecManager().getSpec();
    }
}
