package rest.cucumber.runner;

//import io.cucumber.testng.CucumberOptions;
//import io.cucumber.testng.CucumberOptions.SnippetType;
//import io.cucumber.picocontainer.PicoFactory;
//
//@CucumberOptions(
//        objectFactory = PicoFactory.class,
//        snippets = SnippetType.UNDERSCORE,
//        features = {"src/test/resources/features"},
//        glue = {"rest.cucumber.stepDefs"},
////        tags = "@junit",
//        plugin = {
//                "pretty",
//                "usage:target/cucumber-reports/cucumber-usage.json",
//                "html:target/cucumber-reports/cucumber-report.html",
//                "json:target/cucumber-reports/cucumber.json",
//                "pretty:target/cucumber-reports/cucumber-pretty.txt",
////                "rerun:rerun/failed_scenarios.txt",
//                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
//                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
//        }
//)
//public class CucumberSerenityRunnerTestNGTest extends AbstractTestNGCucumberTests {
//  @DataProvider(parallel = true)
//  @Override
//  public Object[][] scenarios() {
//        return super.scenarios();
//        }
//}
