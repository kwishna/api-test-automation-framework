package devils.dare.stepDefs.webs;

import devils.dare.base.BaseSteps;
import devils.dare.commons.utils.TestContext;
import devils.dare.webs.pages.serenity.SerenityGoogleWebPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class UISteps extends BaseSteps {

    //    GoogleWebPage googlePg = new GoogleWebPage();
    SerenityGoogleWebPage googlePg = new SerenityGoogleWebPage();

    public UISteps(TestContext context) {
        super(context);
    }

    @Given("User navigates To URL: {string}")
    public void user_navigates_to_url(String url) {
        googlePg.openTheUrl(url);
    }

    @And("User clicks on 'Slow calculator'")
    public void userClicksOnSlowCalculator() {
        googlePg.clickSlowCalculator();
    }

    @Then("User quits The Browser")
    public void userQuitsTheBrowser() {
        googlePg.quitTheBrowser();
    }

    @And("User takes screenshot of 'Calculator' element")
    public void userTakesScreenshotOfCalculatorElement() {
        googlePg.takeScreenshotOfCalculatorElement();
    }

    @And("User opens {string} in a new window")
    public void userOpensANewWindowOnTheBrowser(String url) {
        googlePg.openWindowOnTheBrowser(url);
    }

    @And("User opens {string} in a new tab")
    public void userOpensANewTabOnTheBrowser(String url) {
        googlePg.openTabOnTheBrowser(url);
    }

    @And("User verify total tabs opened are: {int}")
    public void userVerifyTotalTabsOpenedAre(int num) {
        googlePg.verifyTotalTabsOpened(num);
    }

    @And("User print the location of the object")
    public void userPrintTheLocationOfTheObject() {
        googlePg.printTheLocationOfTheObject();
    }

}
