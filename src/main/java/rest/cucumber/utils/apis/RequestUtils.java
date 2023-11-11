package rest.cucumber.utils.apis;

import rest.cucumber.constants.APIConstants;

import java.util.Base64;
import java.util.HashMap;

public class RequestUtils {

    public static HashMap<String, String> getBasicAuthHeader(String username, String password) {
        String basicAuth = username + ":" + password;
        String encodeBasicAuth = Base64.getEncoder().encodeToString(basicAuth.getBytes());
        return new HashMap<>() {{
            put(APIConstants.HEADER_AUTHORIZATION, encodeBasicAuth);
        }};
    }

    public static String encodeToBasicAuth(String username, String password) {
        String basicAuth = username + ":" + password;
        return Base64.getEncoder().encodeToString(basicAuth.getBytes());
    }
}
