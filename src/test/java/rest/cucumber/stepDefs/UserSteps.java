package rest.cucumber.stepDefs;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.pages.ReqresAPI;
import rest.cucumber.pojo.reqres.users.SimpleUser;
import rest.cucumber.pojo.reqres.users.User;
import rest.cucumber.utils.AssertUtils;
import rest.cucumber.utils.JsonUtils;
import rest.cucumber.utils.TestContext;
import rest.cucumber.utils.apis.RequestUtils;

public class UserSteps {

    private static final Logger LOGGER = LogManager.getLogger(UserSteps.class);
    private final TestContext ctx;
    RequestSpecification rSpec;
    String body;

    public UserSteps(TestContext context) {
        this.ctx = context;
    }

    @Given("I prepare request specification")
    public void iPrepareRequestSpec() {
        ReqresAPI.getRequestSpecMngr().setBasicRequestSpecs();
//        rSpec = SerenityRest.given().baseUri("https://reqres.in/");
    }

    @Then("I set the headers")
    public void iSetTheHeaders(DataTable table) {
        ReqresAPI.getRequestSpecMngr().setHeaders(table.asMap(String.class, String.class));
//        rSpec.headers(table.asMap(String.class, String.class));
    }

    @Then("I prepare the body for creating an user")
    public void iPrepareTheBodyForChallenger(String userBody) {
        SimpleUser user = JsonUtils.jsonStringToClass(userBody, SimpleUser.class);
        ReqresAPI.getRequestSpecMngr().setBody(user).setLog(LogDetail.ALL);
//        body = userBody;
//        rSpec.log().all();
    }

    @Then("I perform post request at {string}")
    public void iPerformPostRequestAt(String path) {

        this.ctx.response = ReqresAPI
                .getRequester()
                .makeRequest(
                        path,
                        Method.POST
                );

//        this.ctx.response = rSpec.post(string);

        ExtentCucumberAdapter.addTestStepLog(this.ctx.response.asString());
        LOGGER.info("Response is: - " + this.ctx.response.asString());
    }

    @Then("I get the response code as {int}")
    public void iGetTheResponseCodeAs(int responseCode) {
        int statusCode = ReqresAPI
                            .getResponder()
                            .getStatusCode(this.ctx.response);
        AssertUtils.assertEquals(statusCode, responseCode, "API response code mismatch");
//        this.ctx.response.then().statusCode(responseCode);
    }

    @Then("I extract {string} Header")
    public void iExtractHeader(String headerName) {
        String headerValue = ReqresAPI
                                .getResponder()
                                .getHeaderValue(this.ctx.response, headerName);
        LOGGER.info(headerValue);

//        LOGGER.info(this.ctx.response.getHeader(headerName));
        this.ctx.response.then().log().all();
    }
}
