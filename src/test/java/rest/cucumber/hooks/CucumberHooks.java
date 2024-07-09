package rest.cucumber.hooks;

import io.cucumber.java.*;
import net.serenitybdd.core.Serenity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.ScenarioContext;

public class CucumberHooks {

    private static final Logger LOGGER = LogManager.getLogger(CucumberHooks.class);
    private static final String[] TO_BE_SKIPPED = new String[] {"@skip", "@ignore", "@disable", "@pending"};

    @Before
    public void beforeScenario(Scenario scenario) {
        ScenarioContext.init();
        // Be
        //		Dotenv
        //				.configure()
        //				.systemProperties()
        //				.load();
        Collection<String> tags = scenario.getSourceTagNames();
        LOGGER.info("Starting :: " + scenario.getName());
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
        ScenarioContext.clear();
        // After Scenario
        LOGGER.info("Completed :: " + scenario.getName());
        try {
            Serenity.recordReportData().withTitle("Log").fromFile(Path.of(System.getProperty("base.dir") + "/target/logs/app.log"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
