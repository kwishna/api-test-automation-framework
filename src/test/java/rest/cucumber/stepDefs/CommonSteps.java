package rest.cucumber.stepDefs;

import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.base.BaseSteps;
import rest.cucumber.pages.BaseAPI;
import rest.cucumber.utils.TestContext;

public class CommonSteps extends BaseSteps {

    private static final Logger LOGGER = LogManager.getLogger(CommonSteps.class);

    public TestContext ctx;

    public CommonSteps(TestContext context) {
        super(context);
    }

    @Then("I clear rest-assured")
    public void clearRestAssured() {
        BaseAPI.clearAPI();
    }
}
