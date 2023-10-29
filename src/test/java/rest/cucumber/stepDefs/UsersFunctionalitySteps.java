package rest.cucumber.stepDefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.base.BaseSteps;
import rest.cucumber.pages.ReqresAPIEndpoint;
import rest.cucumber.pojo.reqres.users.User;
import rest.cucumber.resource.reqres.ReqresAPIResources;
import rest.cucumber.specifications.request.reqres.users.UsersRequestSpecs;
import rest.cucumber.utils.Assertions;
import rest.cucumber.utils.Jsonutils;
import rest.cucumber.utils.apis.ResponseUtils;
import rest.cucumber.utils.data_sync.TestContext;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class UsersFunctionalitySteps extends BaseSteps {

    Logger logger = LogManager.getLogger(UserSteps.class);

    public UsersFunctionalitySteps(TestContext context) {
        super(context);
    }

    @Given("I set up the headers")
    public void iSetTheHeaders(DataTable table) {
        ReqresAPIEndpoint.getReqSpecMngr().setHeaders(table.asMap());
//        CommonRequestSpecs.reqSpecManager().setHeaders(table.asMap());
    }

    @Given("I setup GET user request for {string}")
    public void iSetupGETUserRequest(String apiEndPoint) {
        ReqresAPIEndpoint.getReqSpecMngr().setBasePath(apiEndPoint);
//        UsersRequestSpecs.userRequestSpec(ReqresAPIResources.of(apiEndPoint));
    }

    @When("I make GET user API calls to {string}")
    public void iMakeAPICallsTo(String userIndex) {
        this.ctx.response = ReqresAPIEndpoint.UserAPI.getUserRequest(userIndex);
    }

    @Then("I verify response code {int}")
    public void iVerifyResponseCode(int statusCode) {
        Assertions.matches(this.ctx.response.statusCode(), equalTo(statusCode));

    }

    @Then("I verify reponse body")
    public void iVerifyReponseBody(String docString) {
        Map<String, Object> _map = Jsonutils.jsonStringToMap(docString);
        System.out.println(_map);
        User user = ResponseUtils.deserializeJsonResponseToObject(this.ctx.response, User.class);
        System.out.println(user);
    }


}
