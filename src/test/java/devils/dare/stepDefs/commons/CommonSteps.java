package devils.dare.stepDefs.commons;

import devils.dare.apis.base.BaseEndpoint;
import devils.dare.commons.utils.TestContext;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import devils.dare.base.BaseSteps;

public class CommonSteps extends BaseSteps {

    private static final Logger LOGGER = LogManager.getLogger(CommonSteps.class);

    public CommonSteps(TestContext context) {
        super(context);
    }

    @Then("I clear rest-assured")
    public void clearRestAssured() {
        BaseEndpoint.clearAPI();
    }
}
