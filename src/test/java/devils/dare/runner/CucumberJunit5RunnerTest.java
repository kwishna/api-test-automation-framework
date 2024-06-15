//package devils.dare.runner;
//
//import org.junit.platform.suite.api.*;
//import static io.cucumber.junit.platform.engine.Constants.*;
//import io.cucumber.core.plugin.SerenityReporterParallel;
//
//@Suite(failIfNoTests = true)
//@SuiteDisplayName("Cucumber JUnit5")
//@IncludeEngines("cucumber")
// // @SelectDirectories("src/test/java/features")
// // @SelectClasspathResource("io/github/bonigarcia")
//@ConfigurationParameters({
//        @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "devils.dare.hooks"),
//        @ConfigurationParameter(key = OBJECT_FACTORY_PROPERTY_NAME, value = "io.cucumber.picocontainer.PicoFactory"),
//        @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "devils.dare.stepDefs"),
//        @ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features"),
//        @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "io.cucumber.core.plugin.SerenityReporterParallel,pretty,timeline:build/cucumber-results/timeline,html:build/cucumber-results/cucumber-report.html,com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:")
//})
// // @ExcludeClassNamePatterns({"*Disabled*"})
// // @ExcludePackages({"devils.dare.disabled"})
// // @ExcludeTags({"skip"})
// // @IncludeTags("all")
// // @ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@ui")
//public class CucumberJunit5RunnerTest {
//}
///*
//Alternate Ways To Pass Cucumber Parameters:-
//1) mvn -Dtest="devils.dare.runner.CucumberJunit5Test" test
//2) mvn clean install -Dcucumber.glue="features.step_definitions" -Dcucumber.features="src/test/java/features"
//3) junit-platform.properties
//*/
