package rest.cucumber.stepDefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.base.BaseSteps;
import rest.cucumber.pages.ReqresAPI;
import rest.cucumber.pojo.reqres.users.User;
import rest.cucumber.utils.AssertUtils;
import rest.cucumber.utils.JsonUtils;
import rest.cucumber.utils.apis.ResponseUtils;
import rest.cucumber.utils.TestContext;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class UsersFunctionalitySteps extends BaseSteps {

    private static final Logger LOGGER = LogManager.getLogger(UsersFunctionalitySteps.class);

    public UsersFunctionalitySteps(TestContext context) {
        super(context);
    }

    @Given("I set up the headers")
    public void iSetTheHeaders(DataTable table) {
        ReqresAPI.getRequestSpecMngr()
                .setBasicRequestSpecs()
                .setHeaders(table.asMap());
//        CommonRequestSpecs.reqSpecManager().setHeaders(table.asMap());
    }

    @Given("I setup GET user request for {string}")
    public void iSetupGETUserRequest(String apiEndPoint) {
        ReqresAPI.getRequestSpecMngr().setBasePath(apiEndPoint);
//        UsersRequestSpecs.userRequestSpec(ReqresAPIResources.of(apiEndPoint));
    }

    @When("I make GET user API calls to {string}")
    public void iMakeAPICallsTo(String userIndex) {
        this.ctx.response = ReqresAPI.UserAPI.getUser(userIndex);
    }

    @Then("I verify response code {int}")
    public void iVerifyResponseCode(int statusCode) {
        AssertUtils.matches(this.ctx.response.statusCode(), equalTo(statusCode));

    }

    @Then("I verify response body")
    public void iVerifyResponseBody(String docString) {
        Map<String, Object> _map = JsonUtils.jsonStringToType(docString);
        System.out.println(_map);
        User user = ResponseUtils.deserializeJsonResponseToObject(this.ctx.response, User.class);
        System.out.println(user);
    }


}
