:: mvn clean verify -P dev -Dcucumber.filter.tags="@all" -Dcucumber.plugin="html:target/cucumber-reports/cucumber-report.html, json:target/cucumber-reports/cucumber.json, com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
mvn clean verify -P dev -Dcucumber.filter.tags="(@all_ui) and (not @skip)" -Dfailsafe.rerunFailingTestsCount=0