:: mvn clean verify -Dcucumber.filter.tags="@all" -Dcucumber.plugin="html:target/cucumber-reports/cucumber-report.html, json:target/cucumber-reports/cucumber.json, com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
mvn clean verify -Dcucumber.filter.tags="@createUser" -Dfailsafe.rerunFailingTestsCount=1