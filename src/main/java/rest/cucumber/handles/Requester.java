package rest.cucumber.handles;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.serenitybdd.rest.SerenityRest;
import io.restassured.http.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.pages.BaseAPI;

import java.util.Map;

public class Requester {

    private static final Logger LOGGER = LogManager.getLogger(Requester.class);

    private Response makeGetRequest(String path) {
        LOGGER.debug("Making 'GET' request");
        return SerenityRest.given(BaseAPI.getRequestSpecMngr().getSpec()).get(path).thenReturn();
    }

    private Response makePostRequest(String path) {
        LOGGER.debug("Making 'POST' request");
        return SerenityRest.given(BaseAPI.getRequestSpecMngr().getSpec()).post(path).thenReturn();
    }

    private Response makePutRequest(String path) {
        LOGGER.debug("Making 'PUT' request");
        return SerenityRest.given(BaseAPI.getRequestSpecMngr().getSpec()).put(path).thenReturn();
    }

    private Response makePatchRequest(String path) {
        LOGGER.debug("Making 'PATCH' request");
        return SerenityRest.given(BaseAPI.getRequestSpecMngr().getSpec()).patch(path).thenReturn();
    }

    private Response makeHeadRequest(String path) {
        LOGGER.debug("Making 'HEAD' request");
        return SerenityRest.given(BaseAPI.getRequestSpecMngr().getSpec()).head(path).thenReturn();
    }

    private Response makeOptionsRequest(String path) {
        LOGGER.debug("Making 'OPTIONS' request");
        return SerenityRest.given(BaseAPI.getRequestSpecMngr().getSpec()).options(path).thenReturn();
    }

    private Response makeDeleteRequest(String path) {
        LOGGER.debug("Making 'DELETE' request");
        return SerenityRest.given(BaseAPI.getRequestSpecMngr().getSpec()).delete(path).thenReturn();
    }

    public Response makeRequest(String path, Method method) {
        return switch (method) {
            case GET -> makeGetRequest(path);
            case POST -> makePostRequest(path);
            case PUT -> makePutRequest(path);
            case PATCH -> makePatchRequest(path);
            case HEAD -> makeHeadRequest(path);
            case OPTIONS -> makeOptionsRequest(path);
            case DELETE -> makeDeleteRequest(path);
            default -> throw new RuntimeException("No such request method found - " + method);
        };
    }

    private Response makeGetRequest(RequestSpecification reqSpec) {
        LOGGER.debug("Making 'GET' request");
        return SerenityRest.given(reqSpec).get().thenReturn();
    }

    private Response makePostRequest(RequestSpecification reqSpec) {
        LOGGER.debug("Making 'POST' request");
        return SerenityRest.given(reqSpec).post().thenReturn();
    }

    private Response makePutRequest(RequestSpecification reqSpec) {
        LOGGER.debug("Making 'PUT' request");
        return SerenityRest.given(reqSpec).put().thenReturn();
    }

    private Response makePatchRequest(RequestSpecification reqSpec) {
        LOGGER.debug("Making 'PATCH' request");
        return SerenityRest.given(reqSpec).patch().thenReturn();
    }

    private Response makeHeadRequest(RequestSpecification reqSpec) {
        LOGGER.debug("Making 'HEAD' request");
        return SerenityRest.given(reqSpec).head().thenReturn();
    }

    private Response makeOptionsRequest(RequestSpecification reqSpec) {
        LOGGER.debug("Making 'OPTIONS' request");
        return SerenityRest.given(reqSpec).options().thenReturn();
    }

    private Response makeDeleteRequest(RequestSpecification reqSpec) {
        LOGGER.debug("Making 'DELETE' request");
        return SerenityRest.given(reqSpec).delete().thenReturn();
    }

    public Response makeRequest(RequestSpecification reqSpec, Method method) {
        return switch (method) {
            case GET -> makeGetRequest(reqSpec);
            case POST -> makePostRequest(reqSpec);
            case PUT -> makePutRequest(reqSpec);
            case PATCH -> makePatchRequest(reqSpec);
            case HEAD -> makeHeadRequest(reqSpec);
            case OPTIONS -> makeOptionsRequest(reqSpec);
            case DELETE -> makeDeleteRequest(reqSpec);
            default -> throw new RuntimeException("No such request method found - " + method);
        };
    }

    // ----

    private Response makeGetRequest(RequestSpecification reqSpec, String path) {
        LOGGER.debug("Making 'GET' request");
        return SerenityRest.given(reqSpec).get(path).thenReturn();
    }

    private Response makePostRequest(RequestSpecification reqSpec, String path) {
        LOGGER.debug("Making 'POST' request");
        return SerenityRest.given(reqSpec).post(path).thenReturn();
    }

    private Response makePutRequest(RequestSpecification reqSpec, String path) {
        LOGGER.debug("Making 'PUT' request");
        return SerenityRest.given(reqSpec).put(path).thenReturn();
    }

    private Response makePatchRequest(RequestSpecification reqSpec, String path) {
        LOGGER.debug("Making 'PATCH' request");
        return SerenityRest.given(reqSpec).patch(path).thenReturn();
    }

    private Response makeHeadRequest(RequestSpecification reqSpec, String path) {
        LOGGER.debug("Making 'HEAD' request");
        return SerenityRest.given(reqSpec).head(path).thenReturn();
    }

    private Response makeOptionsRequest(RequestSpecification reqSpec, String path) {
        LOGGER.debug("Making 'OPTIONS' request");
        return SerenityRest.given(reqSpec).options(path).thenReturn();
    }

    private Response makeDeleteRequest(RequestSpecification reqSpec, String path) {
        LOGGER.debug("Making 'DELETE' request");
        return SerenityRest.given(reqSpec).delete(path).thenReturn();
    }

    public Response makeRequest(RequestSpecification reqSpec, String path, Method method) {
        return switch (method) {
            case GET -> makeGetRequest(reqSpec, path);
            case POST -> makePostRequest(reqSpec, path);
            case PUT -> makePutRequest(reqSpec, path);
            case PATCH -> makePatchRequest(reqSpec, path);
            case HEAD -> makeHeadRequest(reqSpec, path);
            case OPTIONS -> makeOptionsRequest(reqSpec, path);
            case DELETE -> makeDeleteRequest(reqSpec, path);
            default -> throw new RuntimeException("No such request method found - " + method);
        };
    }

    // ----

    private Response makeGetRequest(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        LOGGER.debug("Making 'GET' request");
        return SerenityRest.given(reqSpec, resSpec).get().thenReturn();
    }

    private Response makePostRequest(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        LOGGER.debug("Making 'POST' request");
        return SerenityRest.given(reqSpec, resSpec).post().thenReturn();
    }

    private Response makePutRequest(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        LOGGER.debug("Making 'PUT' request");
        return SerenityRest.given(reqSpec, resSpec).put().thenReturn();
    }

    private Response makePatchRequest(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        LOGGER.debug("Making 'PATCH' request");
        return SerenityRest.given(reqSpec, resSpec).patch().thenReturn();
    }

    private Response makeHeadRequest(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        LOGGER.debug("Making 'HEAD' request");
        return SerenityRest.given(reqSpec, resSpec).head().thenReturn();
    }

    private Response makeOptionsRequest(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        LOGGER.debug("Making 'OPTIONS' request");
        return SerenityRest.given(reqSpec, resSpec).options().thenReturn();
    }

    private Response makeDeleteRequest(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        LOGGER.debug("Making 'DELETE' request");
        return SerenityRest.given(reqSpec, resSpec).options().thenReturn();
    }

    public Response makeRequest(RequestSpecification reqSpec, ResponseSpecification resSpec, Method method) {
        return switch (method) {
            case GET -> makeGetRequest(reqSpec, resSpec);
            case POST -> makePostRequest(reqSpec, resSpec);
            case PUT -> makePutRequest(reqSpec, resSpec);
            case PATCH -> makePatchRequest(reqSpec, resSpec);
            case HEAD -> makeHeadRequest(reqSpec, resSpec);
            case OPTIONS -> makeOptionsRequest(reqSpec, resSpec);
            case DELETE -> makeDeleteRequest(reqSpec, resSpec);
            default -> throw new RuntimeException("No such request method found - " + method);
        };
    }

    // ----

    private Response makeGetRequest(RequestSpecification reqSpec, ResponseSpecification resSpec, String path) {
        LOGGER.debug("Making 'GET' request");
        return SerenityRest.given(reqSpec, resSpec).get(path).thenReturn();
    }

    private Response makePostRequest(RequestSpecification reqSpec, ResponseSpecification resSpec, String path) {
        LOGGER.debug("Making 'POST' request");
        return SerenityRest.given(reqSpec, resSpec).post(path).thenReturn();
    }

    private Response makePutRequest(RequestSpecification reqSpec, ResponseSpecification resSpec, String path) {
        LOGGER.debug("Making 'PUT' request");
        return SerenityRest.given(reqSpec, resSpec).put(path).thenReturn();
    }

    private Response makePatchRequest(RequestSpecification reqSpec, ResponseSpecification resSpec, String path) {
        LOGGER.debug("Making 'PATCH' request");
        return SerenityRest.given(reqSpec, resSpec).patch(path).thenReturn();
    }

    private Response makeHeadRequest(RequestSpecification reqSpec, ResponseSpecification resSpec, String path) {
        LOGGER.debug("Making 'HEAD' request");
        return SerenityRest.given(reqSpec, resSpec).head(path).thenReturn();
    }

    private Response makeOptionsRequest(RequestSpecification reqSpec, ResponseSpecification resSpec, String path) {
        LOGGER.debug("Making 'OPTIONS' request");
        return SerenityRest.given(reqSpec, resSpec).options(path).thenReturn();
    }

    private Response makeDeleteRequest(RequestSpecification reqSpec, ResponseSpecification resSpec, String path) {
        LOGGER.debug("Making 'DELETE' request");
        return SerenityRest.given(reqSpec, resSpec).options(path).thenReturn();
    }

    public Response makeRequest(RequestSpecification reqSpec, ResponseSpecification resSpec, String path, Method method) {
        return switch (method) {
            case GET -> makeGetRequest(reqSpec, resSpec, path);
            case POST -> makePostRequest(reqSpec, resSpec, path);
            case PUT -> makePutRequest(reqSpec, resSpec, path);
            case PATCH -> makePatchRequest(reqSpec, resSpec, path);
            case HEAD -> makeHeadRequest(reqSpec, resSpec, path);
            case OPTIONS -> makeOptionsRequest(reqSpec, resSpec, path);
            case DELETE -> makeDeleteRequest(reqSpec, resSpec, path);
            default -> throw new RuntimeException("No such request method found - " + method);
        };
    }

    // ----

    private Response makeGetRequest(RequestSpecification reqSpec, String path, Object... obj) {
        LOGGER.debug("Making 'GET' request");
        return SerenityRest.given(reqSpec).get(path, obj).thenReturn();
    }

    private Response makePostRequest(RequestSpecification reqSpec, String path, Object... obj) {
        LOGGER.debug("Making 'POST' request");
        return SerenityRest.given(reqSpec).post(path, obj).thenReturn();
    }

    private Response makePutRequest(RequestSpecification reqSpec, String path, Object... obj) {
        LOGGER.debug("Making 'PUT' request");
        return SerenityRest.given(reqSpec).put(path, obj).thenReturn();
    }

    private Response makePatchRequest(RequestSpecification reqSpec, String path, Object... obj) {
        LOGGER.debug("Making 'PATCH' request");
        return SerenityRest.given(reqSpec).patch(path, obj).thenReturn();
    }

    private Response makeHeadRequest(RequestSpecification reqSpec, String path, Object... obj) {
        LOGGER.debug("Making 'HEAD' request");
        return SerenityRest.given(reqSpec).head(path, obj).thenReturn();
    }

    private Response makeOptionsRequest(RequestSpecification reqSpec, String path, Object... obj) {
        LOGGER.debug("Making 'OPTIONS' request");
        return SerenityRest.given(reqSpec).options(path, obj).thenReturn();
    }

    private Response makeDeleteRequest(RequestSpecification reqSpec, String path, Object... obj) {
        LOGGER.debug("Making 'DELETE' request");
        return SerenityRest.given(reqSpec).options(path, obj).thenReturn();
    }

    public Response   makeRequest(RequestSpecification reqSpec, Method method, String path, Object... obj) {
        return switch (method) {
            case GET -> makeGetRequest(reqSpec, path, obj);
            case POST -> makePostRequest(reqSpec, path, obj);
            case PUT -> makePutRequest(reqSpec, path, obj);
            case PATCH -> makePatchRequest(reqSpec, path, obj);
            case HEAD -> makeHeadRequest(reqSpec, path, obj);
            case OPTIONS -> makeOptionsRequest(reqSpec, path, obj);
            case DELETE -> makeDeleteRequest(reqSpec, path, obj);
            default -> throw new RuntimeException("No such request method found - " + method);
        };
    }

    // ----

    private Response makeGetRequest(RequestSpecification reqSpec, String path, Map<String, ?> pathParams) {
        LOGGER.debug("Making 'GET' request");
        return SerenityRest.given(reqSpec).get(path, pathParams).thenReturn();
    }

    private Response makePostRequest(RequestSpecification reqSpec, String path, Map<String, ?> pathParams) {
        LOGGER.debug("Making 'POST' request");
        return SerenityRest.given(reqSpec).post(path, pathParams).thenReturn();
    }

    private Response makePutRequest(RequestSpecification reqSpec, String path, Map<String, ?> pathParams) {
        LOGGER.debug("Making 'PUT' request");
        return SerenityRest.given(reqSpec).put(path, pathParams).thenReturn();
    }

    private Response makePatchRequest(RequestSpecification reqSpec, String path, Map<String, ?> pathParams) {
        LOGGER.debug("Making 'PATCH' request");
        return SerenityRest.given(reqSpec).patch(path, pathParams).thenReturn();
    }

    private Response makeHeadRequest(RequestSpecification reqSpec, String path, Map<String, ?> pathParams) {
        LOGGER.debug("Making 'HEAD' request");
        return SerenityRest.given(reqSpec).head(path, pathParams).thenReturn();
    }

    private Response makeOptionsRequest(RequestSpecification reqSpec, String path, Map<String, ?> pathParams) {
        LOGGER.debug("Making 'OPTIONS' request");
        return SerenityRest.given(reqSpec).options(path, pathParams).thenReturn();
    }

    private Response makeDeleteRequest(RequestSpecification reqSpec, String path, Map<String, ?> pathParams) {
        LOGGER.debug("Making 'DELETE' request");
        return SerenityRest.given(reqSpec).options(path, pathParams).thenReturn();
    }

    public Response makeRequest(RequestSpecification reqSpec, Method method, String path, Map<String, ?> pathParams) {
        return switch (method) {
            case GET -> makeGetRequest(reqSpec, path, pathParams);
            case POST -> makePostRequest(reqSpec, path, pathParams);
            case PUT -> makePutRequest(reqSpec, path, pathParams);
            case PATCH -> makePatchRequest(reqSpec, path, pathParams);
            case HEAD -> makeHeadRequest
                    (reqSpec, path, pathParams);
            case OPTIONS -> makeOptionsRequest(reqSpec, path, pathParams);
            case DELETE -> makeDeleteRequest(reqSpec, path, pathParams);
            default -> throw new RuntimeException("No such request method found - " + method);
        };
    }
}
