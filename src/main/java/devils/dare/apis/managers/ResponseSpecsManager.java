package devils.dare.apis.managers;

import devils.dare.apis.constants.APIConstants;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Add all Response Specifications here.
 */
public class ResponseSpecsManager {

    private static final Logger LOGGER = LogManager.getLogger(ResponseSpecsManager.class);

    private final ResponseSpecBuilder requestSpecBuilder;

    public ResponseSpecsManager() {
        requestSpecBuilder = new ResponseSpecBuilder();
    }

    public ResponseSpecBuilder getResSpecBuilder() {
        return this.requestSpecBuilder;
    }

    private Map<String, Object> getBasicHeaders() {
        // Create a HashMap to represent HTTP headers
        Map<String, Object> headers = new HashMap<>();

        // Add common HTTP headers to the HashMap
        headers.put(APIConstants.HEADER_CONTENT_TYPE, APIConstants.APPLICATION_JSON);
        headers.put(APIConstants.HEADER_ACCEPT, APIConstants.APPLICATION_JSON);
//        headers.put("Authorization", "Bearer your-access-token");
//        headers.put("UserAPI-Agent", "YourApp/1.0");
//        headers.put("Accept-Encoding", "gzip, deflate");
//        headers.put("If-None-Match", "etag-value");
//        headers.put("If-Modified-Since", "Sat, 01 Jan 2023 00:00:00 GMT");
//        headers.put("Cache-Control", "no-cache");
//        headers.put("Content-Length", "1024");
        return headers;
    }

//    public static ResponseSpecsManager getInstance() {
//        return new ResponseSpecsManager();
//    }

    public ResponseSpecsManager setBasicResponseSpecs() {
        this
                .expectContentType(ContentType.JSON)
                .expectHeaders(getBasicHeaders())
                .expectBody("$", is(notNullValue()))
                .setLog(LogDetail.ALL);
        return this;
    }

    public ResponseSpecification getSpec() {
        return getResSpecBuilder().build();
    }

    public ResponseSpecsManager expectBody(Matcher<?> matcher) {
        getResSpecBuilder()
                .expectBody(matcher);
        return this;
    }

    public ResponseSpecsManager expectBody(String path, Matcher<?> matcher) {
        getResSpecBuilder()
                .expectBody(path, matcher);
        return this;
    }

    public ResponseSpecsManager expectContentType(ContentType contentType) {
        getResSpecBuilder()
                .expectContentType(contentType);
        return this;
    }

    public ResponseSpecsManager expectHeaders(Map<String, Object> headers) {
        getResSpecBuilder()
                .expectHeaders(headers);
        return this;
    }

    public ResponseSpecsManager expectHeader(String headerKey, String headerValue) {
        getResSpecBuilder()
                .expectHeader(headerKey, headerValue);
        return this;
    }

    public ResponseSpecsManager expectHeader(String headerKey, Matcher<String> matcher) {
        getResSpecBuilder()
                .expectHeader(headerKey, matcher);
        return this;
    }

    public ResponseSpecsManager expectStatusCode(int statuCode) {
        getResSpecBuilder()
                .expectStatusCode(statuCode);
        return this;
    }

    public ResponseSpecsManager expectStatusCode(Matcher<Integer> matcher) {
        getResSpecBuilder()
                .expectStatusCode(matcher);
        return this;
    }

    public ResponseSpecsManager expectStatusLine(String statusLine) {
        getResSpecBuilder()
                .expectStatusLine(statusLine);
        return this;
    }

    public ResponseSpecsManager expectStatusLine(Matcher<String> matcher) {
        getResSpecBuilder()
                .expectStatusLine(matcher);
        return this;
    }

    public ResponseSpecsManager expectResponseTime(Matcher<Long> matcher, TimeUnit timeUnit) {
        getResSpecBuilder()
                .expectResponseTime(matcher, timeUnit);
        return this;
    }

    public ResponseSpecsManager setLog(LogDetail logDetail) {
        getResSpecBuilder()
                .log(logDetail);
        return this;
    }
}
