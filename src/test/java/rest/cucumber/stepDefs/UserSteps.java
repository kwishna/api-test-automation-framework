package rest.cucumber.stepDefs;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.utils.data_sync.TestContext;

public class UserSteps {

    private TestContext ctx;

    private static final Logger LOGGER = LogManager.getLogger(UserSteps.class);

    RequestSpecification rSpec;
    String body;

    public UserSteps(TestContext context) {
        this.ctx = context;
    }

    @Given("I prepare request specification")
    public void iPrepareRequestSpec() {
        rSpec = SerenityRest.given().baseUri("https://reqres.in/");
    }

    @Then("I set the headers")
    public void iSetTheHeaders(DataTable table) {
        rSpec.headers(table.asMap(String.class, String.class));
    }

    @Then("I prepare the body for creating an user")
    public void iPrepareTheBodyForChallenger(String userBody) {
        body = userBody;
        rSpec.log().all();
    }

    @Then("I perform post request at {string}")
    public void iPerformPostRequestAt(String string) {
        this.ctx.response = rSpec.post(string);

        ExtentCucumberAdapter.addTestStepLog(this.ctx.response.asString());
        LOGGER.info("Response is: - "+this.ctx.response.asString());
    }

    @Then("I get the response code as {int}")
    public void iGetTheResponseCodeAs(int responseCode) {
        this.ctx.response.then().statusCode(responseCode);
    }

    @Then("I extract {string} Header")
    public void iExtractHeader(String string) {
        LOGGER.info(this.ctx.response.getHeader(string));
        this.ctx.response.then().log().all();
    }
}
