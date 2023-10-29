package rest.cucumber.specifications.request.places;

import rest.cucumber.resource.places.PlaceAPIResources;
import rest.cucumber.specifications.request.common.CommonRequestSpecs;

/**
 * Spec bulder for '/place' endpoint.
 */
public class PlaceRequestSpecs extends CommonRequestSpecs {
    public static void prepareAddPlaceSpecs() {
        setBasicRequestSpecs();
        reqSpecManager()
                .setBasePath(PlaceAPIResources.ADD_PLACE_API.getResource());
    }
}
