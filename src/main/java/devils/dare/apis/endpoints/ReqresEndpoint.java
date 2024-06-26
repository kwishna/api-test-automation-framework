package devils.dare.apis.endpoints;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import devils.dare.apis.base.BaseEndpoint;
import devils.dare.apis.pojo.reqres.users.SimpleUser;

import java.util.Map;
import java.util.Objects;

public class ReqresEndpoint extends BaseEndpoint {

    private static final Logger LOGGER = LogManager.getLogger(ReqresEndpoint.class);

    public ReqresEndpoint() {
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
            getRequestSpecMngr().setHeaders(headers).setLog(LogDetail.ALL);
        }

        public static void setBodyToCreateUserRequest(Object body) {
            getRequestSpecMngr().setBody(body);
        }

        public static Response postCreateUserRequest(String path) {
            UserAPI.response = ReqresEndpoint
                    .getRequester()
                    .makeRequest(
                            path,
                            Method.POST
                    );
            Objects.requireNonNull(UserAPI.response, "'postCreateUserRequest' Response is null.");
            return UserAPI.response;
        }

        public static int getResponseStatusCodeForCreateUser() {
            return getResponder().getStatusCode(UserAPI.response);
        }

        public static String getResponseHeaderValue(String headerName) {
            return ReqresEndpoint
                    .getResponder()
                    .getHeaderValue(UserAPI.response, headerName);
        }

        public static SimpleUser getCreatedUser() {
            return getResponder()
                    .getBodyAs(UserAPI.response, SimpleUser.class);
        }

        public static void logResponse() {
            ReqresEndpoint.logResponse(UserAPI.response);
        }
    }
}
