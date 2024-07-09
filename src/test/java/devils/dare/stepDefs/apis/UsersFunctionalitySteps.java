package devils.dare.stepDefs.apis;

import devils.dare.apis.endpoints.ReqresEndpoint;
import devils.dare.apis.pojo.reqres.users.User;
import devils.dare.apis.utils.ResponseUtils;
import devils.dare.base.BaseSteps;
import devils.dare.commons.utils.AssertUtils;
import devils.dare.commons.utils.JsonUtils;
import devils.dare.commons.utils.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class UsersFunctionalitySteps extends BaseSteps {

    private static final Logger LOGGER = LogManager.getLogger(UsersFunctionalitySteps.class);

    public UsersFunctionalitySteps(TestContext context) {
        super(context);
    }

    @Given("I set up the headers")
    public void iSetTheHeaders(DataTable table) {
        ReqresEndpoint.getRequestSpecMngr()
                .setBasicRequestSpecs()
                .setHeaders(table.asMap());
//        CommonRequestSpecs.reqSpecManager().setHeaders(table.asMap());
    }

    @Given("I setup GET user request for {string}")
    public void iSetupGETUserRequest(String apiEndPoint) {
        ReqresEndpoint.getRequestSpecMngr().setBasePath(apiEndPoint);
//        UsersRequestSpecs.userRequestSpec(ReqresAPIResources.of(apiEndPoint));
    }

    @When("I make GET user API calls to {string}")
    public void iMakeAPICallsTo(String userIndex) {
        this.ctx.response = ReqresEndpoint.UserAPI.getUser(userIndex);
    }

    @Then("I verify response code {int}")
    public void iVerifyResponseCode(int statusCode) {
        AssertUtils.matches(this.ctx.response.statusCode(), equalTo(statusCode));

    }

    @Then("I verify response body")
    public void iVerifyResponseBody(String docString) {
        Map<String, Object> _map = JsonUtils.jsonStringToType(docString);
//        System.out.println(_map);
        User user = ResponseUtils.deserializeJsonResponseToObject(this.ctx.response, User.class);
//        System.out.println(user);

        TestContext.getCurrentSession().setMetaData("USER", user);
    }

}
