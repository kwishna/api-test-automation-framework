package rest.cucumber.pages;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.handles.Requester;

public class ReqresAPI extends BaseAPI {

    private final Logger LOGGER = LogManager.getLogger(ReqresAPI.class);

    public ReqresAPI() {
        super();
    }

    public static class UserAPI {
        public static Response getUser(String index) {
            return Requester
                    .getResponse(getReqSpecMngr().getSpec(), Method.GET, index);
        }

    }
}
