package rest.cucumber.handles;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.serenitybdd.rest.SerenityRest;
import io.restassured.http.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class Requester {

    private static final Logger LOGGER = LogManager.getLogger(Requester.class);

    private static Response getGetResponse(RequestSpecification reqSpec) {
        return SerenityRest.given(reqSpec).get().thenReturn();
    }

    private static Response getPostResponse(RequestSpecification reqSpec) {
        return SerenityRest.given(reqSpec).post().thenReturn();
    }

    private static Response getPutResponse(RequestSpecification reqSpec) {
        return SerenityRest.given(reqSpec).put().thenReturn();
    }

    private static Response getPatchResponse(RequestSpecification reqSpec) {
        return SerenityRest.given(reqSpec).patch().thenReturn();
    }

    private static Response getHeadResponse(RequestSpecification reqSpec) {
        return SerenityRest.given(reqSpec).head().thenReturn();
    }

    private static Response getOptionsResponse(RequestSpecification reqSpec) {
        return SerenityRest.given(reqSpec).options().thenReturn();
    }

    private static Response getDeleteResponse(RequestSpecification reqSpec) {
        return SerenityRest.given(reqSpec).delete().thenReturn();
    }

    public static Response getResponse(RequestSpecification reqSpec, Method method) {
        switch (method) {
            case GET:
                return getGetResponse(reqSpec);
            case POST:
                return getPostResponse(reqSpec);
            case PUT:
                return getPutResponse(reqSpec);
            case PATCH:
                return getPatchResponse(reqSpec);
            case HEAD:
                return getHeadResponse(reqSpec);
            case OPTIONS:
                return getOptionsResponse(reqSpec);
            case DELETE:
                return getDeleteResponse(reqSpec);
            default:
                throw new RuntimeException("No such request method found - " + method);
        }
    }

    // ----

    private static Response getGetResponse(RequestSpecification reqSpec, String url) {
        return SerenityRest.given(reqSpec).get(url).thenReturn();
    }

    private static Response getPostResponse(RequestSpecification reqSpec, String url) {
        return SerenityRest.given(reqSpec).post(url).thenReturn();
    }

    private static Response getPutResponse(RequestSpecification reqSpec, String url) {
        return SerenityRest.given(reqSpec).put(url).thenReturn();
    }

    private static Response getPatchResponse(RequestSpecification reqSpec, String url) {
        return SerenityRest.given(reqSpec).patch(url).thenReturn();
    }

    private static Response getHeadResponse(RequestSpecification reqSpec, String url) {
        return SerenityRest.given(reqSpec).head(url).thenReturn();
    }

    private static Response getOptionsResponse(RequestSpecification reqSpec, String url) {
        return SerenityRest.given(reqSpec).options(url).thenReturn();
    }

    private static Response getDeleteResponse(RequestSpecification reqSpec, String url) {
        return SerenityRest.given(reqSpec).delete(url).thenReturn();
    }

    public static Response getResponse(RequestSpecification reqSpec, String url, Method method) {
        switch (method) {
            case GET:
                return getGetResponse(reqSpec, url);
            case POST:
                return getPostResponse(reqSpec, url);
            case PUT:
                return getPutResponse(reqSpec, url);
            case PATCH:
                return getPatchResponse(reqSpec, url);
            case HEAD:
                return getHeadResponse(reqSpec, url);
            case OPTIONS:
                return getOptionsResponse(reqSpec, url);
            case DELETE:
                return getDeleteResponse(reqSpec, url);
            default:
                throw new RuntimeException("No such request method found - " + method);
        }
    }

    // ----

    private static Response getGetResponse(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        return SerenityRest.given(reqSpec, resSpec).get().thenReturn();
    }

    private static Response getPostResponse(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        return SerenityRest.given(reqSpec, resSpec).post().thenReturn();
    }

    private static Response getPutResponse(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        return SerenityRest.given(reqSpec, resSpec).put().thenReturn();
    }

    private static Response getPatchResponse(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        return SerenityRest.given(reqSpec, resSpec).patch().thenReturn();
    }

    private static Response getHeadResponse(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        return SerenityRest.given(reqSpec, resSpec).head().thenReturn();
    }

    private static Response getOptionsResponse(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        return SerenityRest.given(reqSpec, resSpec).options().thenReturn();
    }

    private static Response getDeleteResponse(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        return SerenityRest.given(reqSpec, resSpec).options().thenReturn();
    }

    public static Response getResponse(RequestSpecification reqSpec, ResponseSpecification resSpec, Method method) {
        switch (method) {
            case GET:
                return getGetResponse(reqSpec, resSpec);
            case POST:
                return getPostResponse(reqSpec, resSpec);
            case PUT:
                return getPutResponse(reqSpec, resSpec);
            case PATCH:
                return getPatchResponse(reqSpec, resSpec);
            case HEAD:
                return getHeadResponse(reqSpec, resSpec);
            case OPTIONS:
                return getOptionsResponse(reqSpec, resSpec);
            case DELETE:
                return getDeleteResponse(reqSpec, resSpec);
            default:
                throw new RuntimeException("No such request method found - " + method);
        }
    }

    // ----

    private static Response getGetResponse(RequestSpecification reqSpec, ResponseSpecification resSpec, String url) {
        return SerenityRest.given(reqSpec, resSpec).get(url).thenReturn();
    }

    private static Response getPostResponse(RequestSpecification reqSpec, ResponseSpecification resSpec, String url) {
        return SerenityRest.given(reqSpec, resSpec).post(url).thenReturn();
    }

    private static Response getPutResponse(RequestSpecification reqSpec, ResponseSpecification resSpec, String url) {
        return SerenityRest.given(reqSpec, resSpec).put(url).thenReturn();
    }

    private static Response getPatchResponse(RequestSpecification reqSpec, ResponseSpecification resSpec, String url) {
        return SerenityRest.given(reqSpec, resSpec).patch(url).thenReturn();
    }

    private static Response getHeadResponse(RequestSpecification reqSpec, ResponseSpecification resSpec, String url) {
        return SerenityRest.given(reqSpec, resSpec).head(url).thenReturn();
    }

    private static Response getOptionsResponse(RequestSpecification reqSpec, ResponseSpecification resSpec, String url) {
        return SerenityRest.given(reqSpec, resSpec).options(url).thenReturn();
    }

    private static Response getDeleteResponse(RequestSpecification reqSpec, ResponseSpecification resSpec, String url) {
        return SerenityRest.given(reqSpec, resSpec).options(url).thenReturn();
    }

    public static Response getResponse(RequestSpecification reqSpec, ResponseSpecification resSpec, String url, Method method) {
        switch (method) {
            case GET:
                return getGetResponse(reqSpec, resSpec, url);
            case POST:
                return getPostResponse(reqSpec, resSpec, url);
            case PUT:
                return getPutResponse(reqSpec, resSpec, url);
            case PATCH:
                return getPatchResponse(reqSpec, resSpec, url);
            case HEAD:
                return getHeadResponse(reqSpec, resSpec, url);
            case OPTIONS:
                return getOptionsResponse(reqSpec, resSpec, url);
            case DELETE:
                return getDeleteResponse(reqSpec, resSpec, url);
            default:
                throw new RuntimeException("No such request method found - " + method);
        }
    }

    // ----

    private static Response getGetResponse(RequestSpecification reqSpec, String url, Object... obj) {
        return SerenityRest.given(reqSpec).get(url, obj).thenReturn();
    }

    private static Response getPostResponse(RequestSpecification reqSpec, String url, Object... obj) {
        return SerenityRest.given(reqSpec).post(url, obj).thenReturn();
    }

    private static Response getPutResponse(RequestSpecification reqSpec, String url, Object... obj) {
        return SerenityRest.given(reqSpec).put(url, obj).thenReturn();
    }

    private static Response getPatchResponse(RequestSpecification reqSpec, String url, Object... obj) {
        return SerenityRest.given(reqSpec).patch(url, obj).thenReturn();
    }

    private static Response getHeadResponse(RequestSpecification reqSpec, String url, Object... obj) {
        return SerenityRest.given(reqSpec).head(url, obj).thenReturn();
    }

    private static Response getOptionsResponse(RequestSpecification reqSpec, String url, Object... obj) {
        return SerenityRest.given(reqSpec).options(url, obj).thenReturn();
    }

    private static Response getDeleteResponse(RequestSpecification reqSpec, String url, Object... obj) {
        return SerenityRest.given(reqSpec).options(url, obj).thenReturn();
    }

    public static Response getResponse(RequestSpecification reqSpec, Method method, String url, Object... obj) {
        switch (method) {
            case GET:
                return getGetResponse(reqSpec, url, obj);
            case POST:
                return getPostResponse(reqSpec, url, obj);
            case PUT:
                return getPutResponse(reqSpec, url, obj);
            case PATCH:
                return getPatchResponse(reqSpec, url, obj);
            case HEAD:
                return getHeadResponse(reqSpec, url, obj);
            case OPTIONS:
                return getOptionsResponse(reqSpec, url, obj);
            case DELETE:
                return getDeleteResponse(reqSpec, url, obj);
            default:
                throw new RuntimeException("No such request method found - " + method);
        }
    }

    // ----

    private static Response getGetResponse(RequestSpecification reqSpec, String url, Map<String, ?> map) {
        return SerenityRest.given(reqSpec).get(url, map).thenReturn();
    }

    private static Response getPostResponse(RequestSpecification reqSpec, String url, Map<String, ?> map) {
        return SerenityRest.given(reqSpec).post(url, map).thenReturn();
    }

    private static Response getPutResponse(RequestSpecification reqSpec, String url, Map<String, ?> map) {
        return SerenityRest.given(reqSpec).put(url, map).thenReturn();
    }

    private static Response getPatchResponse(RequestSpecification reqSpec, String url, Map<String, ?> map) {
        return SerenityRest.given(reqSpec).patch(url, map).thenReturn();
    }

    private static Response getHeadResponse(RequestSpecification reqSpec, String url, Map<String, ?> map) {
        return SerenityRest.given(reqSpec).head(url, map).thenReturn();
    }

    private static Response getOptionsResponse(RequestSpecification reqSpec, String url, Map<String, ?> map) {
        return SerenityRest.given(reqSpec).options(url, map).thenReturn();
    }

    private static Response getDeleteResponse(RequestSpecification reqSpec, String url, Map<String, ?> map) {
        return SerenityRest.given(reqSpec).options(url, map).thenReturn();
    }

    public static Response getResponse(RequestSpecification reqSpec, Method method, String url, Map<String, ?> map) {
        switch (method) {
            case GET:
                return getGetResponse(reqSpec, url, map);
            case POST:
                return getPostResponse(reqSpec, url, map);
            case PUT:
                return getPutResponse(reqSpec, url, map);
            case PATCH:
                return getPatchResponse(reqSpec, url, map);
            case HEAD:
                return getHeadResponse(reqSpec, url, map);
            case OPTIONS:
                return getOptionsResponse(reqSpec, url, map);
            case DELETE:
                return getDeleteResponse(reqSpec, url, map);
            default:
                throw new RuntimeException("No such request method found - " + method);
        }
    }
}
