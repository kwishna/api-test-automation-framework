package devils.dare.hooks;

import devils.dare.apis.utils.ScenarioContext;
import io.cucumber.java.*;
import net.serenitybdd.core.Serenity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import devils.dare.commons.config.Configurations;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public class CucumberHooks {

    private static final Logger LOGGER = LogManager.getLogger(CucumberHooks.class);
    private static final String[] TO_BE_SKIPPED = new String[] {"@skip", "@ignore", "@disable", "@pending"};

    @Before
    public void beforeScenario(Scenario scenario) {
        // Be
        //		Dotenv
        //				.configure()
        //				.systemProperties()
        //				.load();
        Collection<String> tags = scenario.getSourceTagNames();
        LOGGER.info("Starting :: " + scenario.getName());
        ScenarioContext.init();
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        // Before step
        LOGGER.info("Before Step :: " + scenario.getName());
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        // After step
        LOGGER.info("After Step :: " + scenario.getName());
    }


    @After
    public void afterScenario(Scenario scenario) {
        // After Scenario
        LOGGER.info("Completed :: " + scenario.getName());
        try {
            Serenity.recordReportData().withTitle("Log").fromFile(Path.of(Configurations.configuration().userDir() + "/target/logs/app.log"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        ScenarioContext.clear();
    }
}
