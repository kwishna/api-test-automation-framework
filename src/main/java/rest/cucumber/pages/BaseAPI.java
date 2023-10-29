package rest.cucumber.pages;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import rest.cucumber.managers.RequestSpecsManager;
import rest.cucumber.managers.ResponseSpecsManager;

public class BaseAPI {

    private static final ThreadLocal<RequestSpecsManager> reqSpecMngr = ThreadLocal.withInitial(RequestSpecsManager::new);
    private static final ThreadLocal<ResponseSpecsManager> respSpecMngr = ThreadLocal.withInitial(ResponseSpecsManager::new);

    public static RequestSpecsManager getReqSpecMngr() {
        return reqSpecMngr.get();
    }

    public static ResponseSpecsManager getRespSpecMngr() {
        return respSpecMngr.get();
    }

    public static void resetAPI() {
        SerenityRest.reset();
    }

    public static Response getLastResponse() {
        return SerenityRest.lastResponse();
    }
}
