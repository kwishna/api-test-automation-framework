package rest.cucumber.utils.apis;

import rest.cucumber.constants.APIContants;

import java.util.Base64;
import java.util.HashMap;

public class RequestUtils {

    public HashMap<String, String> getBasicAuthHeader(String username, String password) {
        String basicAuth = username + ":" + password;
        String encodeBasicAuth = Base64.getEncoder().encodeToString(basicAuth.getBytes());
        return new HashMap<String, String>() {{
            put(APIContants.HEADER_AUTHORIZATION, encodeBasicAuth);
        }};
    }
}
