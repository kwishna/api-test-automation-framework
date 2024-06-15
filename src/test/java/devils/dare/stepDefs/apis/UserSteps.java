package devils.dare.stepDefs.apis;

import devils.dare.apis.endpoints.ReqresEndpoint;
import devils.dare.apis.pojo.reqres.users.SimpleUser;
import devils.dare.base.BaseSteps;
import devils.dare.commons.utils.AssertUtils;
import devils.dare.commons.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import devils.dare.commons.utils.JsonUtils;

public class UserSteps extends BaseSteps {

    private static final Logger LOGGER = LogManager.getLogger(UserSteps.class);
    RequestSpecification rSpec;

    String body;

    public UserSteps(TestContext context) {
       super(context);
    }

    // ----------------------------------------------------------------------

//    @DataTableType
//    public Author authorEntry(Map<String, String> entry) {
//        return new Author(
//                entry.get("firstName"),
//                entry.get("lastName"),
//                entry.get("famousBook"));
//    }
//
//    @Given("There are my favorite authors")
//    public void these_are_my_favourite_authors(List<Author> authors) {
//        // step implementation
//    }

// ----------------------------------------------------------------------

//    @ParameterType(".*")
//    public Book book(String bookName) {
//        return new Book(bookName);
//    }
//
//    @Given("{book} is my favorite book")
//    public void this_is_my_favorite_book(Book book) {
//        // step implementation
//    }

//    @DocStringType
//    public SimpleUser simpleUser(String docString) {
//        return JsonUtils.jsonStringToObject(docString, SimpleUser.class);
//    }

    @Given("I prepare request specification")
    public void iPrepareRequestSpec() {

        // *** 1) Using wrapper class (Page Object Model) ***
        ReqresEndpoint.UserAPI.prepareBasicRequestSpecToCreateUser();


        // *** 2) Using wrapper class without Page Object Model. ***
//        ReqresEndpoint.getRequestSpecMngr().setBasicRequestSpecs();

        // *** 3) Using Rest Assured Library Directly. ***
//        rSpec = SerenityRest.given().baseUri("https://reqres.in/");
    }

    @Then("I set the headers")
    public void iSetTheHeaders(DataTable table) {

        // *** 1) Using wrapper class (Page Object Model) ***
        ReqresEndpoint.UserAPI.setHeaderToCreateUserRequest(table.asMap(String.class, String.class));

        // *** 2) Using wrapper class without Page Object Model. ***
//        ReqresEndpoint.getRequestSpecMngr().setHeaders(table.asMap(String.class, String.class));

        // *** 3) Using Rest Assured Library Directly. ***
//        rSpec.headers(table.asMap(String.class, String.class));
    }

    @Then("I prepare the body for creating an user")
    public void iPrepareTheBodyForChallenger(String userBody) {

        // *** 1) Using wrapper class (Page Object Model) ***
        SimpleUser user = JsonUtils.jsonStringToObject(userBody, SimpleUser.class);
        LOGGER.info("Request Body Into POJO :: " + user);
        ReqresEndpoint.UserAPI.setBodyToCreateUserRequest(user);

        // *** 2) Using wrapper class without Page Object Model. ***
//        SimpleUser user = JsonUtils.jsonStringToObject(userBody, SimpleUser.class);
//        LOGGER.info("Request Body Into POJO :: " + user);
//        ReqresEndpoint.getRequestSpecMngr().setBody(user);

        // *** 3) Using Rest Assured Library Directly. ***
//        body = userBody;
//        rSpec.log().all();
    }

    @Then("I perform post request at {string}")
    public void iPerformPostRequestAt(String path) {

        // *** 1) Using wrapper class (Page Object Model) ***
        ReqresEndpoint.UserAPI.postCreateUserRequest(path);

        // *** 2) Using wrapper class without Page Object Model. ***
//        this.ctx.response = ReqresEndpoint
//                .getRequester()
//                .makeRequest(
//                        path,
//                        Method.POST
//                );

        // *** 3) Using Rest Assured Library Directly. ***
//        this.ctx.response = rSpec.post(string);
    }

    @Then("I get the response code as {int}")
    public void iGetTheResponseCodeAs(int responseCode) {

        ReqresEndpoint.UserAPI.logResponse();

        // *** 1) Using wrapper class (Page Object Model) ***
        int statusCode = ReqresEndpoint.UserAPI.getResponseStatusCodeForCreateUser();

        // *** 2) Using wrapper class without Page Object Model. ***
//        int statusCode = ReqresEndpoint
//                .getResponder()
//                .getStatusCode(this.ctx.response);

        // *** 3) Using Rest Assured Library Directly. ***
//        this.ctx.response.then().statusCode(responseCode);

        AssertUtils.assertEquals(statusCode, responseCode, "API response code mismatch");
    }

    @Then("I extract {string} Header")
    public void iExtractHeader(String headerName) {

        // -------- Header and POJO --------------
        // *** 1) Using wrapper class (Page Object Model) ***
        String headerValue = ReqresEndpoint.UserAPI.getResponseHeaderValue(headerName);
        LOGGER.info(String.format("Response Header Value For %s is %s", headerName, headerValue));
        LOGGER.info("Response Body Into POJO :: " + ReqresEndpoint.UserAPI.getCreatedUser());

        // *** 2) Using wrapper class without Page Object Model. ***
//        String headerValue = ReqresEndpoint
//                .getResponder()
//                .getHeaderValue(this.ctx.response, headerName);
//        LOGGER.info(String.format("Response Header Value For %s is %s", headerName, headerValue));
//        SimpleUser simpleUser = ReqresEndpoint
//                .getResponder()
//                .getBodyAs(this.ctx.response, SimpleUser.class);
//        LOGGER.info("Response Body Into POJO :: " + simpleUser);

        // *** 3) Using Rest Assured Library Directly. ***
//        LOGGER.info(String.format("Response Header Value For %s is %s", headerName, this.ctx.response.headers().getValue(headerName)));
//        LOGGER.info("Response Body Into POJO :: " + this.ctx.response.body().as(SimpleUser.class));

        // -------- Log response --------------

        // *** 1) Using wrapper class (Page Object Model) ***
        ReqresEndpoint.UserAPI.logResponse();

        // *** 2) Using wrapper class without Page Object Model. ***
//        ReqresEndpoint.getResponder().logResponse(this.ctx.response);

        // *** 3) Using Rest Assured Library Directly. ***
//        this.ctx.response.then().log().all(true);
    }
}
