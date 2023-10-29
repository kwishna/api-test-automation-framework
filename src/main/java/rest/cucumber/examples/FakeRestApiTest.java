package rest.cucumber.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HeaderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.listener.ResponseValidationFailureListener;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static io.restassured.RestAssured.*;
import static io.restassured.config.CsrfConfig.csrfConfig;
import static io.restassured.config.FailureConfig.failureConfig;
import static java.nio.charset.StandardCharsets.UTF_8;
import static net.serenitybdd.rest.RestRequests.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static rest.cucumber.listeners.ExtentLogger.fail;

public class FakeRestApiTest {
    RequestSpecification reqSpec;
    ResponseSpecification getRespSpec;
    ObjectMapper mapper = new ObjectMapper();

    public void beforeTest() {

        RestAssured.baseURI = "https://fakerestapi.azurewebsites.net";
        RestAssured.basePath = "/api/v1";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Accept", "*/*")
                .log(LogDetail.URI)
                .build();

        getRespSpec = new ResponseSpecBuilder()
                .expectHeader("Content-Type", "application/json; charset=utf-8; v=1.0")
                .expectHeader("Server", "Kestrel")
                .expectHeader("Transfer-Encoding", "chunked")
                .expectHeader("api-supported-versions", "1.0")
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectStatusCode(200)
                .expectResponseTime(lessThan(10000L))
                .build();
    }

    public void tearDown() {
        RestAssured.reset();
    }

    public void serialize() throws JsonProcessingException {
        ValidatableResponse response = given()
                .spec(reqSpec)
                .when()
                .get("/Activities")
                .then()
                .spec(getRespSpec);

        Response resp = response.extract().response();
        String respStr = resp.asPrettyString();

//        Activity[] activities = mapper.readValue(respStr, Activity[].class);
//        assertThat(activities, arrayWithSize(30));
//        assertThat(activities[0].isCompleted(), equalTo(false));
    }

//    @Test
//    public void deSerialize() throws JsonProcessingException {
//        String ldt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH));
//        Activity activity = new Activity().withId(System.currentTimeMillis()).withTitle("Krish").withCompleted(false).withDueDate(ldt);
//        String reqStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(activity);
//
//        ValidatableResponse response = given()
//                .header("Content-Type", "application/json; v=1.0", "accept", "text/plain; v=1.0")
////                .with()
//                .body(reqStr)
//                .when()
//                .post("/Activities")
//                .then().log().all();
//
//        Response resp = response.extract().response();
//        String respStr = resp.asPrettyString();
//
////        System.out.println(respStr);
//    }

//    public RequestSpecification requestSetup() {
//        RestAssured.reset();
//        Options options = Options.builder().logStacktrace().build();
//        RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(options);
//        RestAssured.baseURI = PropertiesFile.getProperty("baseURL");
//        return RestAssured.given()
//                .config(config)
//                .filter(new RestAssuredRequestFilter())
//                .contentType(CONTENT_TYPE)
//                .accept(CONTENT_TYPE);
//    }

    /**
     * response.then().assertThat().body(
     * JsonSchemaValidator.matchesJsonSchema(((Map<String,String>) context.session.get("excelDataMap")).get("responseSchema"))
     * );
     *
     * response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/"+schemaFileName));
     *
     * .setContentType(ContentType.JSON)
     *     .setAccept(ContentType.JSON)
     *     .setConfig(newConfig().encoderConfig(encoderConfig().defaultContentCharset(StandardCharsets.UTF_8)))
     *
     *   RestAssuredConfig config = newConfig().jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL))
     *       .redirect(redirectConfig().followRedirects(world.getBoolean(CukesOptions.FOLLOW_REDIRECTS, true)));
     *
     *       config.sslConfig(new SSLConfig().allowAllHostnames());
     *       .config(RestAssuredConfig.newConfig().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
     *
     *       LogConfig logConfig = LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(logDetail);
     *   config = RestAssured.config().logConfig(logConfig);
     *
     *   given().relaxedHTTPSValidation()
     *
     *   public static RequestSpecBuilder buildRequestSpecification(WebAdminServer webAdminServer) {
     *         return buildRequestSpecification(webAdminServer.getPort());
     *     }
     *
     *     public static RequestSpecBuilder buildRequestSpecification(Port port) {
     *         return new RequestSpecBuilder()
     *             .setContentType(ContentType.JSON)
     *             .setAccept(ContentType.JSON)
     *             .setConfig(defaultConfig())
     *             .setPort(port.getValue())
     *             .setBasePath("/");
     *     }
     *
     *     public static RestAssuredConfig defaultConfig() {
     *         return newConfig().encoderConfig(encoderConfig().defaultContentCharset(StandardCharsets.UTF_8));
     *     }
     *
     *     public static RequestSpecification spec(Port port) {
     *         return given().spec(buildRequestSpecification(port).build())
     *             .urlEncodingEnabled(false); // no further automatically encoding by Rest Assured client. rf: https://issues.apache.org/jira/projects/JAMES/issues/JAMES-3936
     *     }
     *
     *         public void assertEmailReceived(Consumer<ValidatableResponse> expectations) {
     *         Awaitility.await().atMost(Duration.ofMinutes(2))
     *             .untilAsserted(() -> expectations.accept(
     *                 given(requestSpecification(), RESPONSE_SPECIFICATION)
     *                     .get("/api/email")
     *                     .then()
     *                     .statusCode(200)));
     *     }
     *
     */

    /**
     * // URL Abstrcation
     * PlaceAPIResources enum
     *
     * // Authentication Abstraction
     * UserAPI aUSer = new UserAPI("admin", "password");
     * new RestListicatorApi(server, aUser).lists(Verb.OPTIONS);
     *
     * // Assertion Abstraction
     *  Assert.assertEquals(200, response.getStatusCode());
     *
     *  // API Abstraction
     *  UserDAO user = new UserDAO();
     *  UserAPI credential = new UserAPI("admin", "password");
     *  RestApi api = new ResApi(server);
     *  Response response = api.createUser(user, credential);
     */

    /**
     * --> "[0].bookingid"
     */

    @Test
    public void groceriesContainsChocolateAndCoffee() throws Exception {
        expect().
                body("shopping.category.find { it.@type == 'groceries' }", hasItems("Chocolate", "Coffee")).
                when().
                get("/shopping");
    }

    @Test
    public void groceriesContainsChocolateAndCoffeeUsingDoubleStarNotation() throws Exception {
        expect().
                body("**.find { it.@type == 'groceries' }", hasItems("Chocolate", "Coffee")).
                when().
                get("/shopping");
    }

    @Test
    public void advancedJsonValidation() throws Exception {
        expect().
                statusCode(allOf(greaterThanOrEqualTo(200), lessThanOrEqualTo(300))).
                rootPath("store.book").
                body("findAll { book -> book.price < 10 }.title", hasItems("Sayings of the Century", "Moby Dick")).
                body("author.collect { it.length() }.sum()", equalTo(53)).
                when().
                get("/jsonStore");
    }

    @Test
    public void advancedJsonValidation2() throws Exception {
        expect().
                statusCode(allOf(greaterThanOrEqualTo(200), lessThanOrEqualTo(300))).
                rootPath("store.book").
                body("findAll { book -> book.price < 10 }.title", hasItems("Sayings of the Century", "Moby Dick")).
                body("price.min()", equalTo(8.95f)).
                body("price.max()", equalTo(22.99f)).
                body("min { it.price }.title", equalTo("Sayings of the Century")).
                body("author*.length().sum()", equalTo(53)).
                body("author*.length().sum(2, { it * 2 })", is(108)).
                when().
                get("/jsonStore");
    }

    @Test
    public void products() throws Exception {
        when().
                get("/products").
                then().
                body("price.sum()", is(38.0d)).
                body("dimensions.width.min()", is(1.0f)).
                body("name.collect { it.length() }.max()", is(16)).
                body("dimensions.multiply(2).height.sum()", is(21.0));
    }

    @Test
    public void
    accept_headers_are_merged_from_request_spec_and_request_when_configured_to() {
        RequestSpecification spec = new RequestSpecBuilder().setAccept("text/jux").build();

        final MutableObject<List<String>> headers = new MutableObject<List<String>>();

        RestAssured.given().
                config(RestAssuredConfig.config().headerConfig(HeaderConfig.headerConfig().mergeHeadersWithName("Accept"))).
                accept(ContentType.JSON).
                spec(spec).
                body("{ \"message\" : \"hello world\"}").
                filter(new Filter() {
                    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
                        headers.setValue(requestSpec.getHeaders().getValues("Accept"));
                        return ctx.next(requestSpec, responseSpec);
                    }
                }).
                when().
                post("/jsonBodyAcceptHeader").
                then().
                body(equalTo("hello world"));

        assertThat(headers.getValue(), contains("application/json, application/javascript, text/javascript, text/json", "text/jux"));
    }

    @Test
    public void formAuthenticationWithDefinedCsrfFieldDefinedInRequestConfig() {
        given().
                config(config().csrfConfig(csrfConfig().csrfTokenPath("/formAuthCsrf").csrfInputFieldName("_csrf"))).
                auth().form("John", "Doe", new FormAuthConfig("j_spring_security_check_with_csrf", "j_username", "j_password")).
                when().
                get("/formAuthCsrf").
                then().
                statusCode(200).
                body(equalTo("OK"));
    }

    @Test
    public void configurationsDefinedGloballyAreAppliedWhenUsingResponseSpecBuilders() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ResponseValidationFailureListener failureListener = (reqSpec, respSpec, resp) -> atomicBoolean.set(true);

        try {
            given().config(RestAssuredConfig.config().failureConfig(failureConfig().failureListeners(failureListener)))
                    .get("http://jsonplaceholder.typicode.com/todos/1").then()
                    .spec(new ResponseSpecBuilder().expectStatusCode(400).build());
            fail("Should throw exception");
        } catch (Error ignored) {
        }

        assertThat(atomicBoolean.get(), is(true));
    }

    @Test
    public void
    ignores_spacing_between_content_type_and_charset_when_server_returns_single_space_between_content_type_and_charset() {
        given().
                filter((requestSpec, responseSpec, ctx) -> new ResponseBuilder()
                        .setStatusCode(200)
                        .setHeader("Content-Type", "application/json; charset=UTF-8")
                        .setHeader("Some", "Value")
                        .setBody("Test").build()).
                when().
                get("/something").
                then().
                contentType(ContentType.JSON.withCharset(UTF_8));
    }

}
