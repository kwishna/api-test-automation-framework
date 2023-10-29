package rest.cucumber.managers;

import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.CertificateAuthSettings;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.authentication.OAuthSignature;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.ProxySpecification;
import io.restassured.specification.RequestSpecification;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.config.MultiPartConfig.multiPartConfig;
import static rest.cucumber.base.APIBasePage.getReqSpecBuilder;
import static rest.cucumber.config.Configuration.configuration;

/**
 * Add all Request Specifications here: -
 */
@NoArgsConstructor
public class RequestSpecsManager {

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

    public RequestSpecsManager setBasicRequestSpecs(String url) {
        setBaseURI(url)
                .setContentType(ContentType.JSON)
                .setHeaders(getBasicHeaders())
                .setLog(LogDetail.ALL)
                .setUrlEncodingEnabled(true);
        return this;
    }

    public RequestSpecification getSpec() {
        return getReqSpecBuilder().build();
    }

    // -------------------------------- Authentication Starts --------------------------------

    public RequestSpecsManager setBasicAuth(String userName, String password) {
        getSpec().auth().basic(userName, password);
        return this;
    }

    public RequestSpecsManager setDigestAuth(String userName, String password) {
        getSpec().auth().digest(userName, password);
        return this;
    }

    public RequestSpecsManager setFormAuth(String userName, String password, FormAuthConfig formAuthConfig) {
        getSpec().auth().form(userName, password, formAuthConfig);
        return this;
    }

    public RequestSpecsManager setFormAuth(String userName, String password) {
        getSpec().auth().form(userName, password);
        return this;
    }

    public RequestSpecsManager setCertificateAuth(String certURL, String password, CertificateAuthSettings certificateAuthSettings) {
        getSpec().auth().certificate(certURL, password, certificateAuthSettings);
        return this;
    }

    public RequestSpecsManager setCertificateAuth(String certURL, String password) {
        getSpec().auth().certificate(certURL, password);
        return this;
    }

    public RequestSpecsManager setNoAuth() {
        getSpec().auth().none();
        return this;
    }

    public RequestSpecsManager setPreemptiveBasicAuth(String userName, String password) {
        getSpec().auth().preemptive().basic(userName, password);
        return this;
    }

    public RequestSpecsManager setOAuth2(String accessToken) {
        getSpec().auth().oauth2(accessToken);
        return this;
    }

    public RequestSpecsManager setOAuth2(String accessToken, OAuthSignature signature) {
        getSpec().auth().oauth2(accessToken, signature);
        return this;
    }

    public RequestSpecsManager setOAuth(String consumerKey, String consumerSecret, String accessToken, String secretToken, OAuthSignature signature) {
        getSpec().auth().oauth(consumerKey, consumerSecret, accessToken, secretToken, signature);
        return this;
    }

    public RequestSpecsManager setBasicRequestSpecs() {
        setBaseURI()
                .setContentType(ContentType.JSON)
                .setHeaders(getBasicHeaders())
                .setLog(LogDetail.ALL)
                .setUrlEncodingEnabled(true);
        return this;

    }

    // -------------------------------- Authentication Ends --------------------------------

    public RequestSpecsManager setReqSpec(RequestSpecification reqSpec) {
        getReqSpecBuilder().addRequestSpecification(reqSpec);
        return this;
    }

    public RequestSpecsManager setBaseURI() {
        getReqSpecBuilder()
                .setBaseUri(configuration().baseApiUrl());
        return this;
    }

    public RequestSpecsManager setBaseURI(String baseUri) {
        getReqSpecBuilder()
                .setBaseUri(baseUri);
        return this;
    }

    public RequestSpecsManager setBasePath(String basePath) {
        getReqSpecBuilder()
                .setBasePath(basePath);
        return this;
    }

    public RequestSpecsManager setHeader(String key, String val) {
        getReqSpecBuilder()
                .addHeader(key, val);
        return this;
    }

    public RequestSpecsManager setHeaders(Map<String, String> headers) {
        getReqSpecBuilder()
                .addHeaders(headers);
        return this;
    }

    public RequestSpecsManager setQueryParam(Map<String, String> queryParams) {
        getReqSpecBuilder()
                .addQueryParams(queryParams);
        return this;
    }

    public RequestSpecsManager setPathParam(String key, Object value) {
        getReqSpecBuilder()
                .addPathParam(key, value);
        return this;
    }

    public RequestSpecsManager setPathParams(Map<String, String> pathParams) {
        getReqSpecBuilder()
                .addPathParams(pathParams);
        return this;
    }

    public RequestSpecsManager disableCsrf() {
        getReqSpecBuilder()
                .disableCsrf();
        return this;
    }

    public RequestSpecsManager setLog(LogDetail logDetail) {
        getReqSpecBuilder()
                .log(logDetail);
        return this;
    }

    public RequestSpecsManager setContentType(ContentType contentType) {
        getReqSpecBuilder()
                .setAccept(contentType);
        return this;
    }

    // -------------------------------- Body Setter Starts --------------------------------

    public RequestSpecsManager setBody(Object objBody, ObjectMapper objMapper) {
        getReqSpecBuilder()
                .setBody(objBody, objMapper);
        return this;
    }

    public RequestSpecsManager setBody(Object objBody, ObjectMapperType objMapperType) {
        getReqSpecBuilder()
                .setBody(objBody, objMapperType);
        return this;
    }

    public RequestSpecsManager setBody(Object objBody) {
        getReqSpecBuilder()
                .setBody(objBody);
        return this;
    }

    public RequestSpecsManager setBody(String strBody) {
        getReqSpecBuilder()
                .setBody(strBody);
        return this;
    }

    public RequestSpecsManager setBody(byte[] bytes) {
        getReqSpecBuilder()
                .setBody(bytes);
        return this;
    }

    // -------------------------------- Body Setter Ends --------------------------------

    public RequestSpecsManager setConfig(RestAssuredConfig config) {
        getReqSpecBuilder()
                .setConfig(config);
        return this;
    }

    public RequestSpecsManager setProxySpec(ProxySpecification proxySpec) {
        getReqSpecBuilder()
                .setProxy(proxySpec);
        return this;
    }

    public RequestSpecsManager setProxySpec(String host, int port, String scheme) {
        getReqSpecBuilder()
                .setProxy(host, port, scheme);
        return this;
    }

    public RequestSpecsManager setRelaxedHTTPSValidation() {
        getReqSpecBuilder()
                .setRelaxedHTTPSValidation();
        return this;
    }

    public RequestSpecsManager setUrlEncodingEnabled(boolean enabled) {
        getReqSpecBuilder()
                .setUrlEncodingEnabled(enabled);
        return this;
    }

    public RequestSpecsManager setFilter(Filter filter) {
        getReqSpecBuilder()
                .addFilter(filter);
        return this;
    }

    public RequestSpecsManager setFilters(List<Filter> filters) {
        getReqSpecBuilder()
                .addFilters(filters);
        return this;
    }

    public RequestSpecsManager setFormParams(Map<String, ?> parametersMap) {
        getReqSpecBuilder()
                .addFormParams(parametersMap);
        return this;
    }

    public RequestSpecsManager setMultiPart(File file) {
        getReqSpecBuilder()
                .addMultiPart(file);
        return this;
    }

    public RequestSpecsManager setMultiPart(MultiPartSpecification spec) {
        getReqSpecBuilder()
                .addMultiPart(spec);
        return this;
    }

    public RequestSpecsManager addCookies(Map<String, String> cookieMap) {
        getReqSpecBuilder()
                .addCookies(cookieMap);
        return this;
    }

    public RequestSpecsManager addCookies(String key, Object value, Object... cookieNameValuePairs) {
        getReqSpecBuilder()
                .addCookie(key, value, cookieNameValuePairs);
        return this;
    }

    public RequestSpecsManager setSessionId(String key, String value) {
        getReqSpecBuilder()
                .setSessionId(key, value);
        return this;
    }

    public RequestSpecsManager setSessionId(String value) {
        getReqSpecBuilder()
                .setSessionId(value);
        return this;
    }

    public RequestSpecsManager setKeyStore(Path pathToJks, String password) {
        getReqSpecBuilder()
                .setKeyStore(pathToJks.toAbsolutePath().toString(), password);
        return this;
    }

    public RequestSpecsManager setAuthScheme(AuthenticationScheme scheme) {
//        // Create a PreemptiveBasicAuthScheme and set the username and password
//        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
//        authScheme.setUserName("yourUsername");
//        authScheme.setPassword("yourPassword");
//
//        // Use the authScheme with RestAssured
//        RestAssured.authentication = authScheme;

        getReqSpecBuilder()
                .setAuth(scheme);
        return this;
    }

    public void multiPartBuilder(byte[] bytes) throws IOException {
        new MultiPartSpecBuilder(bytes);

        new MultiPartSpecBuilder("jkbasdkjbsk").with().charset("UTF-8").and().with().controlName("other").
                and().with().mimeType("application/vnd.some+json").build();

        new MultiPartSpecBuilder("sdfdss").with().charset("UTF-8").and().with().controlName("other").
                and().with().mimeType("application/vnd.some+json").
                and().with().header("X-Header-1", "Value1").
                and().with().header("X-Header-2", "Value2").build();

        new MultiPartSpecBuilder(Files.newInputStream(Path.of(""), StandardOpenOption.READ)).with().and().with().controlName("file").
                and().with().mimeType("application/vnd.some+json").and().with().fileName("my-file").build();


        // given().
        //                multiPart(new MultiPartSpecBuilder(bytes).build()).
    }
}
