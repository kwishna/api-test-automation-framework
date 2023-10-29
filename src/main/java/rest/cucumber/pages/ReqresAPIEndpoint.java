package rest.cucumber.pages;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.handles.RequestHandler;

public class ReqresAPIEndpoint extends BaseAPIEndpoint {

    private final Logger LOGGER = LogManager.getLogger(ReqresAPIEndpoint.class);

    public ReqresAPIEndpoint() {
        super();
    }

    public static class UserAPI {
        public static Response getUserRequest(String index) {
            return RequestHandler
                    .getResponse(getReqSpecMngr().getSpec(), Method.GET, index);
        }
    }
}
