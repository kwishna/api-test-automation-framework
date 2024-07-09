package devils.dare.apis.base;

import devils.dare.apis.handles.Requester;
import devils.dare.apis.handles.Responder;
import devils.dare.apis.managers.RequestSpecsManager;
import devils.dare.apis.managers.ResponseSpecsManager;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseEndpoint {

    private static final Logger LOGGER = LogManager.getLogger(BaseEndpoint.class);
    private static final ThreadLocal<RequestSpecsManager> REQUEST_SPECIFICATION_MANAGER = ThreadLocal.withInitial(RequestSpecsManager::new);
    private static final ThreadLocal<ResponseSpecsManager> RESPONSE_SPECIFICATION_MANAGER = ThreadLocal.withInitial(ResponseSpecsManager::new);
    private static final ThreadLocal<Requester> REQUESTER = ThreadLocal.withInitial(Requester::new);
    private static final ThreadLocal<Responder> RESPONDER = ThreadLocal.withInitial(Responder::new);

    public static RequestSpecsManager getRequestSpecMngr() {
        if (REQUEST_SPECIFICATION_MANAGER.get() == null) {
            REQUEST_SPECIFICATION_MANAGER.set(new RequestSpecsManager());
        }
        return REQUEST_SPECIFICATION_MANAGER.get();
    }

    public static ResponseSpecsManager getResponseSpecMngr() {
        if (RESPONSE_SPECIFICATION_MANAGER.get() == null) {
            RESPONSE_SPECIFICATION_MANAGER.set(new ResponseSpecsManager());
        }
        return RESPONSE_SPECIFICATION_MANAGER.get();
    }

    public static Requester getRequester() {
        return REQUESTER.get();
    }

    public static Responder getResponder() {
        return RESPONDER.get();
    }

    public static void clearAPI() {
        LOGGER.warn("Clearing Rest API.");
        SerenityRest.clear();
        SerenityRest.reset();
        REQUEST_SPECIFICATION_MANAGER.remove();
        RESPONSE_SPECIFICATION_MANAGER.remove();
    }

    public static void logResponse(Response response) {
        getResponder().logResponse(response);
    }

    public static void logResponseBody(Response response) {
        getResponder().logBody(response);
    }
}
