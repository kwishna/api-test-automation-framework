Parameter	        Purpose
parallel	        Level of parallelism (methods, classes, suites)
threadCount	Total   number of threads for parallel execution
threadCountSuites	Threads dedicated to suites
threadCountClasses	Threads dedicated to classes
threadCountMethods	Threads dedicated to methods
useUnlimitedThreads	Allow unlimited threads (boolean)
forkCount	        Number of JVM processes spawned concurrently
reuseForks	        Whether to reuse JVM forks for multiple tests

### JUnit5
JUnit 5 is highly modular and supports extensive configuration through annotations, system properties, and build tool plugins.
@Test: Marks a method as a test.
@ParameterizedTest: Allows running a test with multiple data sets.
@ValueSource, @CsvSource, @MethodSource, @EnumSource, @ArgumentsSource: Provide data for parameterized tests.
@Tag: Categorize tests for selective execution.
@DisplayName: Custom name for test methods.

Lifecycle and Hooks
@BeforeAll, @BeforeEach, @AfterEach, @AfterAll: Setup and teardown methods.
@TestInstance: Controls test instance lifecycle (per class or per method).

### Cucumber
cucumber.ansi-colors.disabled=  # true or false. default: false
cucumber.execution.dry-run=     # true or false. default: false
cucumber.execution.limit=       # number of scenarios to execute (CLI only).
cucumber.execution.order=       # lexical, reverse, random or random:[seed] (CLI only). default: lexical
cucumber.execution.wip=         # true or false. default: false.
cucumber.features=              # comma separated paths to feature files. example: path/to/example.feature, path/to/other.feature
cucumber.filter.name=           # regex. example: .*Hello.*
cucumber.filter.tags=           # tag expression. example: @smoke and not @slow
cucumber.glue=                  # comma separated package names. example: com.example.glue
cucumber.plugin=                # comma separated plugin strings. example: pretty, json:path/to/report.json
cucumber.object-factory=        # object factory class name. example: com.example.MyObjectFactory
cucumber.snippet-type=          # underscore or camelcase. default: underscore


cucumber.glue=                  # comma separated package names. 
                                # example: com.example.glue  
  
cucumber.plugin=                # comma separated plugin strings. 
                                # example: pretty, json:path/to/report.json

cucumber.object-factory=        # object factory class name.
                                # example: com.example.MyObjectFactory

cucumber.uuid-generator         # uuid generator class name of a registered service provider.
                                # default: io.cucumber.core.eventbus.RandomUuidGenerator
                                # example: com.example.MyUuidGenerator

cucumber.publish.enabled        # true or false. default: false
                                # enable publishing of test results 

cucumber.publish.quiet          # true or false. default: false
                                # supress publish banner after test exeuction  

cucumber.publish.token          # any string value.
                                # publish authenticated test results

cucumber.publish.url            # a valid url
                                # location to publish test reports to

cucumber.snippet-type=          # underscore or camelcase. 
                                # default: underscore

@CucumberOptions Annotation: Placed on the test runner class to control execution:
features: Path(s) to feature files.
glue:     Package(s) containing step definitions.
tags:     Filter which scenarios to run.
plugin:   Output/reporting formats (e.g., HTML, JSON).
dryRun:   Checks step definitions without executing tests.
strict:   Fails tests if there are undefined steps.
name:     Regex to select scenarios by name.
snippets: Code style for generated step definitions (camelCase or underscore).

JUnit5
cucumber.execution.parallel.enabled=true
cucumber.execution.parallel.config.strategy=fixed               # dynamic, fixed or custom.
cucumber.execution.parallel.config.fixed.parallelism=2          # positive integer.
cucumber.execution.parallel.config.fixed.max-pool-size=2        # positive integer.

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/qa/selenium/cucumber/features/")
@ConfigurationParameter(
    key = GLUE_PROPERTY_NAME,
    value = "com.qa.selenium.cucumber.steps, com.qa.selenium.cucumber.hooks"
    )
public class CucumberRunnerTest {}

mvn test -Dsurefire.includeJUnit5Engines=cucumber -Dcucumber.plugin=pretty -Dcucumber.features=path/to/example.feature:10
https://github.com/cucumber/cucumber-jvm/blob/main/cucumber-junit-platform-engine/README.md

### Retry

```java
package cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/cucumber/feature/Login.feature",
        glue = {"cucumber.stepdefinitions"},
        tags = "@LoginTest",
        plugin = {"rerun:target/failedrerun.txt"}
)
public class LoginRunner extends AbstractTestNGCucumberTests {
}
```

```java
package cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "@target/failedrerun.txt",
        glue = {"cucumber.stepdefinitions"}
)
public class FailedTestRerun extends AbstractTestNGCucumberTests {
}
```

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd" >

<suite name="Our Suite" verbose="1" >
    <test name="YouTube test on Chrome">
        <classes>
            <class name="cucumber.runner.LoginRunner" />
        </classes>
    </test>
    <test name="Rerun failed tests">
        <classes>
            <class name="cucumber.runner.FailedTestRerun" />
        </classes>
    </test>
</suite>
```

Jenkinsfile
# e.g: 
gradle cleanTest test or mvn test

filename="/var/lib/jenkins/workspace/your-workspace-name/failedrerun.txt"
if [ -e "$filename" ]; then
    # Check if the file size is greater than 0 bytes
    if [ -s "$filename" ]; then
        echo "The file '$filename' exists and its size is greater than 0 bytes."
        # put your maven/gradle test command here to rerun failed tests
        # e.g: 
        gradle cleanTest test -P reruntest or mvn test -Dtest=FailedTestRerun
    else
        echo "The file '$filename' exists, but its size is 0 bytes."
    fi
else
    echo "The file '$filename' does not exist."
fi