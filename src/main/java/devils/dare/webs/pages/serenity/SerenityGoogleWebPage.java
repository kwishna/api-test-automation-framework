package devils.dare.webs.pages.serenity;

import devils.dare.webs.base.serenity.SerenityBaseWebPage;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class SerenityGoogleWebPage extends SerenityBaseWebPage {

    @FindBy(css = "a[href='slow-calculator.html']")
    WebElementFacade firstProductName;

    public void openTheUrl(String url) {
        openUrl(url);
    }

    public void clickSlowCalculator() {
        click(firstProductName);
    }

    public void quitTheBrowser() {
        quitDriver();
    }

    public void takeScreenshotOfCalculatorElement() {
        takeElementScreenshot(By.cssSelector("#calculator"));
        fullPageScreenshot();
    }

    public void openWindowOnTheBrowser(String url) {
        createAndSwitchToNewWindow();
        openUrl(url);
    }

    public void verifyTotalTabsOpened(int num) {
        assertThat(allWindows(), hasSize(num));
    }

    public void openTabOnTheBrowser(String url) {
        createAndSwitchToNewTab();
        openUrl(url);
    }

    public void printTheLocationOfTheObject() {
        WebElementFacade logo = getElement(By.cssSelector("a img[src='img/hands-on-icon.png']"));
        System.out.println("┌───────────────────┐");
        System.out.println("│ Height: " + logo.getRect().getDimension().getHeight());
        System.out.println("│ Height: " + logo.getRect().getDimension().getWidth());
        System.out.println("│ X-Location: " + logo.getRect().getX());
        System.out.println("│ Y-Location: " + logo.getRect().getY());
        System.out.println("└───────────────────┘");
    }
}
