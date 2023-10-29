package rest.cucumber.pages;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.handles.RequestHandler;
import rest.cucumber.specifications.request.places.PlaceRequestSpecs;

public class PlaceAPIEndpoint extends BaseAPIEndpoint {

    private final Logger LOGGER = LogManager.getLogger(PlaceAPIEndpoint.class);

    public PlaceAPIEndpoint() {
        super();
    }

    public Response getAddPlaceResponse(Method verb) {
        PlaceRequestSpecs.prepareAddPlaceSpecs();
        return RequestHandler
                .getResponse(getReqSpecMngr().getSpec(), verb);
    }


}
