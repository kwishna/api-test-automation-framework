package rest.cucumber.handles;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Responder {

    private static final Logger LOGGER = LogManager.getLogger(Responder.class);

    /**
     * Validate response specifications.
     *
     * @param response
     * @param respSpec
     * @return
     */
    public Responder validateResponse(Response response, ResponseSpecification respSpec) {
        response.then().spec(respSpec);
        return this;
    }

    /**
     * Get JsonPath from Response.
     *
     * @param response
     * @return
     */
    public JsonPath getBodyAsJsonPath(Response response) {
        return response.body().jsonPath();
    }

    public <T> T getBody(Response response, Class<T> clazz) {
        return response.body().as(clazz);
    }

    public <T> T getBody(Response response, Type cls) {
        return response.body().as(cls);
    }

    public String getContentType(Response response) {
        return response.contentType();
    }

    public Headers getHeaders(Response response) {
        return response.headers();
    }

    public <T> T extractFromBody(Response response, String path, Class<T> objectType) {
        return response.body().jsonPath().getObject(path, objectType);
    }

    public String extractStringFromBody(Response response, String path) {
        return response.body().jsonPath().getString(path);
    }

    public <T> List<T> extractListFromBody(Response response, String path, Class<T> clazz) {
        return response.body().jsonPath().getList(path, clazz);
    }

    public <T> T extractFromBody(Response response, String path) {
        return response.body().path(path);
    }

    public Map<Object, Object> extractMapFromBody(Response response, String path) {
        return response.body().jsonPath().getMap(path);
    }

    public int getStatusCode(Response response) {
        return response.statusCode();
    }

    public String getStatusList(Response response) {
        return response.statusLine();
    }

    public long getResponseTime(Response response) {
        return response.time();
    }

    public long getResponseTime(Response response, TimeUnit timeUnit) {
        return response.getTimeIn(timeUnit);
    }

    public String getResponseAsString(Response response) {
        return response.getBody().asString();
    }

    /**
     * De-serialize Response to class
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserializeJsonResponseToObject(Response response, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.asString(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
