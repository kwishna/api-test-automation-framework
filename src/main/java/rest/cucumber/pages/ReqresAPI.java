package rest.cucumber.pages;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReqresAPI extends BaseAPI {

    private static final Logger LOGGER = LogManager.getLogger(ReqresAPI.class);

    public ReqresAPI() {
        super();
    }

    public static class UserAPI {
        public static Response getUser(String index) {
            return getRequester()
                    .makeRequest(getRequestSpecMngr().getSpec(), Method.GET, index);
        }

    }
}
