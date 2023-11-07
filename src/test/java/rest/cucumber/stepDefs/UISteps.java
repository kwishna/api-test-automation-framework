package rest.cucumber.stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import rest.cucumber.pages.GooglePage;

public class UISteps {

    @Given("User Navigates To URL: {string}")
    public void user_navigates_to_url(String url) {
        new GooglePage().openUrl(url);
    }

    @And("User clicks on 'Slow calculator'")
    public void userClicksOnSlowCalculator() {
        new GooglePage().clickSlowCalculator();
    }

    @Then("User quits The Browser")
    public void userQuitsTheBrowser() {
        new GooglePage().userQuitsTheBrowser();
    }

    @And("User takes screenshot of 'Calculator' element")
    public void userTakesScreenshotOfCalculatorElement() {
        new GooglePage().userTakesScreenshotOfCalculatorElement();
    }

    @And("User opens {string} in a new window")
    public void userOpensANewWindowOnTheBrowser(String url) {
        new GooglePage().userOpensWindowOnTheBrowser(url);
    }

    @And("User opens {string} in a new tab")
    public void userOpensANewTabOnTheBrowser(String url) {
        new GooglePage().userOpensTabOnTheBrowser(url);
    }

    @And("User verify total tabs opened are: {int}")
    public void userVerifyTotalTabsOpenedAre(int num) {
        new GooglePage().userVerifyTotalTabsOpenedAre(num);
    }

    @And("User print the location of the object")
    public void userPrintTheLocationOfTheObject() {
        new GooglePage().userPrintTheLocationOfTheObject();
    }

}
