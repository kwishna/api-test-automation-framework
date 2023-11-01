package rest.cucumber.pages;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.handles.Requester;
import rest.cucumber.handles.Responder;
import rest.cucumber.managers.RequestSpecsManager;
import rest.cucumber.managers.ResponseSpecsManager;

public class BaseAPI {

    private static final Logger LOGGER = LogManager.getLogger(BaseAPI.class);
    private static final ThreadLocal<RequestSpecsManager> REQUEST_SPECIFICATION_MNGR = ThreadLocal.withInitial(RequestSpecsManager::new);
    private static final ThreadLocal<ResponseSpecsManager> RESPONSE_SPECIFICATION_MANAGER = ThreadLocal.withInitial(ResponseSpecsManager::new);
    private static final ThreadLocal<Requester> REQUESTER = ThreadLocal.withInitial(Requester::new);
    private static final ThreadLocal<Responder> RESPONDER = ThreadLocal.withInitial(Responder::new);

    public static RequestSpecsManager getRequestSpecMngr() {
        return REQUEST_SPECIFICATION_MNGR.get();
    }

    public static ResponseSpecsManager getResponseSpecMngr() {
        return RESPONSE_SPECIFICATION_MANAGER.get();
    }

    public static Requester getRequester() {
        return REQUESTER.get();
    }

    public static Responder getResponder() {
        return RESPONDER.get();
    }

    public static void resetAPI() {
        LOGGER.warn("Resetting rest api");
        SerenityRest.reset();
    }

    public static void logResponse(Response response) {
        getResponder().logResponse(response);
    }

    public static void logResponseBody(Response response) {
        getResponder().logBody(response);
    }
}
