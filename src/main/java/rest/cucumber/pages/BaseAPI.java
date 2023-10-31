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
    private static final ThreadLocal<RequestSpecsManager> REQ_SPEC_MNGR = ThreadLocal.withInitial(RequestSpecsManager::new);
    private static final ThreadLocal<ResponseSpecsManager> RES_SPEC_MNGR = ThreadLocal.withInitial(ResponseSpecsManager::new);
    private static final ThreadLocal<Requester> REQUESTER = ThreadLocal.withInitial(Requester::new);
    private static final ThreadLocal<Responder> RESPONDER = ThreadLocal.withInitial(Responder::new);

    public static RequestSpecsManager getReqSpecMngr() {
        return REQ_SPEC_MNGR.get();
    }

    public static ResponseSpecsManager getResSpecMngr() {
        return RES_SPEC_MNGR.get();
    }

    public static Requester getRequester() {
        return REQUESTER.get();
    }

    public static Responder getResponder() {
        return RESPONDER.get();
    }

    public static void resetAPI() {
        SerenityRest.reset();
    }

    public static Response getLastResponse() {
        return SerenityRest.lastResponse();
    }
}
