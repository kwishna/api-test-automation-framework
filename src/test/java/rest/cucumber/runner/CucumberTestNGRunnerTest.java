package rest.cucumber.runner;

//import io.cucumber.picocontainer.PicoFactory;
//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;
//import org.testng.annotations.DataProvider;
//
//@CucumberOptions(
//        objectFactory = PicoFactory.class,
//        snippets = CucumberOptions.SnippetType.UNDERSCORE,
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
//public class CucumberTestNGRunnerTest /* extends AbstractTestNGCucumberTests */ {
//    @DataProvider(parallel = true)
//    @Override
//    public Object[][] scenarios() {
//        return super.scenarios();
//    }
//}
