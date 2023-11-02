package rest.cucumber.stepDefs;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.DocStringType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.pages.ReqresAPI;
import rest.cucumber.pojo.reqres.users.SimpleUser;
import rest.cucumber.utils.AssertUtils;
import rest.cucumber.utils.JsonUtils;
import rest.cucumber.utils.TestContext;

import java.util.Map;

public class UserSteps {

    private static final Logger LOGGER = LogManager.getLogger(UserSteps.class);
    private final TestContext ctx;
    RequestSpecification rSpec;

    String body;

    public UserSteps(TestContext context) {
        this.ctx = context;
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
        ReqresAPI.UserAPI.prepareBasicRequestSpecToCreateUser();


        // *** 2) Using wrapper class without Page Object Model. ***
//        ReqresAPI.getRequestSpecMngr().setBasicRequestSpecs();

        // *** 3) Using Rest Assured Library Directly. ***
//        rSpec = SerenityRest.given().baseUri("https://reqres.in/");
    }

    @Then("I set the headers")
    public void iSetTheHeaders(DataTable table) {

        // *** 1) Using wrapper class (Page Object Model) ***
        ReqresAPI.UserAPI.setHeaderToCreateUserRequest(table.asMap(String.class, String.class));

        // *** 2) Using wrapper class without Page Object Model. ***
//        ReqresAPI.getRequestSpecMngr().setHeaders(table.asMap(String.class, String.class));

        // *** 3) Using Rest Assured Library Directly. ***
//        rSpec.headers(table.asMap(String.class, String.class));
    }

    @Then("I prepare the body for creating an user")
    public void iPrepareTheBodyForChallenger(String userBody) {

        // *** 1) Using wrapper class (Page Object Model) ***
        SimpleUser user = JsonUtils.jsonStringToObject(userBody, SimpleUser.class);
        LOGGER.info("Request Body Into POJO :: " + user);
        ReqresAPI.UserAPI.setBodyToCreateUserRequest(user);

        // *** 2) Using wrapper class without Page Object Model. ***
//        SimpleUser user = JsonUtils.jsonStringToObject(userBody, SimpleUser.class);
//        LOGGER.info("Request Body Into POJO :: " + user);
//        ReqresAPI.getRequestSpecMngr().setBody(user);

        // *** 3) Using Rest Assured Library Directly. ***
//        body = userBody;
//        rSpec.log().all();
    }

    @Then("I perform post request at {string}")
    public void iPerformPostRequestAt(String path) {

        // *** 1) Using wrapper class (Page Object Model) ***
        ReqresAPI.UserAPI.postCreateUserRequest(path);

        // *** 2) Using wrapper class without Page Object Model. ***
//        this.ctx.response = ReqresAPI
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

        ReqresAPI.UserAPI.logResponse();

        // *** 1) Using wrapper class (Page Object Model) ***
        int statusCode = ReqresAPI.UserAPI.getResponseStatusCodeForCreateUser();

        // *** 2) Using wrapper class without Page Object Model. ***
//        int statusCode = ReqresAPI
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
        String headerValue = ReqresAPI.UserAPI.getResponseHeaderValue(headerName);
        LOGGER.info(String.format("Response Header Value For %s is %s", headerName, headerValue));
        LOGGER.info("Response Body Into POJO :: " + ReqresAPI.UserAPI.getCreatedUser());

        // *** 2) Using wrapper class without Page Object Model. ***
//        String headerValue = ReqresAPI
//                .getResponder()
//                .getHeaderValue(this.ctx.response, headerName);
//        LOGGER.info(String.format("Response Header Value For %s is %s", headerName, headerValue));
//        SimpleUser simpleUser = ReqresAPI
//                .getResponder()
//                .getBodyAs(this.ctx.response, SimpleUser.class);
//        LOGGER.info("Response Body Into POJO :: " + simpleUser);

        // *** 3) Using Rest Assured Library Directly. ***
//        LOGGER.info(String.format("Response Header Value For %s is %s", headerName, this.ctx.response.headers().getValue(headerName)));
//        LOGGER.info("Response Body Into POJO :: " + this.ctx.response.body().as(SimpleUser.class));

        // -------- Log response --------------

        // *** 1) Using wrapper class (Page Object Model) ***
        ReqresAPI.UserAPI.logResponse();

        // *** 2) Using wrapper class without Page Object Model. ***
//        ReqresAPI.getResponder().logResponse(this.ctx.response);

        // *** 3) Using Rest Assured Library Directly. ***
//        this.ctx.response.then().log().all(true);
    }
}
