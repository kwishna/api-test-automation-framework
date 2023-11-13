package devils.dare.runner;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import io.cucumber.picocontainer.PicoFactory;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
//@RunWith(Cucumber.class)
@CucumberOptions(
        objectFactory = PicoFactory.class,
        snippets = SnippetType.UNDERSCORE,
        features = {"src/test/resources/features"},
        glue = {"devils.dare.stepDefs"},
//        glue = {"devils.dare.stepDefs.apis", "devils.dare.stepDefs.commons", "devils.dare.stepDefs.webs", "devils.dare.hooks"},
//        tags = "@ui_1",
        plugin = {
                "pretty",
                "usage:target/cucumber-reports/cucumber-usage.json",
                "html:target/cucumber-reports/cucumber-report.html",
                "json:target/cucumber-reports/cucumber.json",
                "pretty:target/cucumber-reports/cucumber-pretty.txt",
                "rerun:rerun/failed_scenarios.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
//                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        }
)
public class CucumberSerenityJunit4RunnerTest {
    private static final Logger LOGGER = LogManager.getLogger(CucumberSerenityJunit4RunnerTest.class);
}
