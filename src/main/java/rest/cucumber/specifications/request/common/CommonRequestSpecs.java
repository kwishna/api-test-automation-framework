package rest.cucumber.specifications.request.common;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import rest.cucumber.managers.RequestSpecsManager;

import java.util.HashMap;
import java.util.Map;

import static rest.cucumber.config.Configuration.configuration;

public class CommonRequestSpecs {

    private static final ThreadLocal<RequestSpecsManager> REQ_SPEC_MNGR = ThreadLocal.withInitial(RequestSpecsManager::new);

    private static Map getBasicHeaders() {
        // Create a HashMap to represent HTTP headers
        Map<String, String> headers = new HashMap<>();

        // Add common HTTP headers to the HashMap
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
//        headers.put("Authorization", "Bearer your-access-token");
//        headers.put("UserAPI-Agent", "YourApp/1.0");
//        headers.put("Accept-Encoding", "gzip, deflate");
//        headers.put("If-None-Match", "etag-value");
//        headers.put("If-Modified-Since", "Sat, 01 Jan 2023 00:00:00 GMT");
//        headers.put("Cache-Control", "no-cache");
//        headers.put("Content-Length", "1024");
        return headers;
    }

    public static void setBasicRequestSpecs() {
        reqSpecManager()
                .setBaseURI(configuration().baseApiUrl())
                .setContentType(ContentType.JSON)
                .setHeaders(getBasicHeaders())
                .setLog(LogDetail.ALL)
                .setUrlEncodingEnabled(true);

    }

    public static RequestSpecsManager reqSpecManager() {
        return REQ_SPEC_MNGR.get();
    }
}
