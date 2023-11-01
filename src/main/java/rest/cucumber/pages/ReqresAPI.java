package rest.cucumber.pages;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.pojo.reqres.users.SimpleUser;

import java.util.Map;

public class ReqresAPI extends BaseAPI {

    private static final Logger LOGGER = LogManager.getLogger(ReqresAPI.class);

    public ReqresAPI() {
        super();
    }

    public static class UserAPI {

        private static Response response;

        public static Response getUser(String index) {
            return getRequester()
                    .makeRequest(getRequestSpecMngr().getSpec(), Method.GET, index);
        }

        public static void prepareBasicRequestSpecToCreateUser() {
            getRequestSpecMngr().setBasicRequestSpecs();
        }

        public static void setHeaderToCreateUserRequest(Map<String, String> headers) {
            getRequestSpecMngr().setHeaders(headers);
        }

        public static void setBodyToCreateUserRequest(Object body) {
            getRequestSpecMngr().setBody(body);
        }

        public static Response postCreateUserRequest(String path) {
            UserAPI.response = ReqresAPI
                    .getRequester()
                    .makeRequest(
                            path,
                            Method.POST
                    );
            return UserAPI.response;
        }

        public static int getResponseStatusCodeForCreateUser() {
            return getResponder().getStatusCode(UserAPI.response);
        }

        public static String getResponseHeaderValue(String headerName) {
            return ReqresAPI
                    .getResponder()
                    .getHeaderValue(UserAPI.response, headerName);
        }

        public static SimpleUser getCreatedUser() {
            return getResponder()
                    .getBodyAs(UserAPI.response, SimpleUser.class);
        }

        public static void logResponse() {
            ReqresAPI.logResponse(UserAPI.response);
        }
    }
}
