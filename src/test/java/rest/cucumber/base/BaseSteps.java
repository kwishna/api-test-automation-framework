package rest.cucumber.base;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rest.cucumber.specifications.request.common.CommonRequestSpecs;
import rest.cucumber.utils.data_sync.TestContext;

import java.io.IOException;

public class BaseSteps {

    Logger logger = LogManager.getLogger(APIBasePage.class);

    public TestContext ctx;

    public BaseSteps(TestContext context) {
        this.ctx = context;
    }

    private void startSevices() {
        try {
            Process process = Runtime.getRuntime().exec("java -jar apichallenges.jar");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (process.isAlive()) process.destroyForcibly();
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
