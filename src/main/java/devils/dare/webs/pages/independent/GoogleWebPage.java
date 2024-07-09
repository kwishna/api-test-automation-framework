package devils.dare.webs.pages.independent;

import devils.dare.webs.driver.WebDriversManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import devils.dare.webs.base.independent.BaseWebPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class GoogleWebPage extends BaseWebPage {

    public void openUrl(String url) {
        openTheUrl(url);
    }

    public void clickSlowCalculator() {
        click(By.cssSelector("a[href='slow-calculator.html']"));
    }

    public void userQuitsTheBrowser() {
        WebDriversManager.quitDriver();
    }

    public void userTakesScreenshotOfCalculatorElement() {
        takeElementScreenshot(By.cssSelector("#calculator"));
        fullPageScreenshot();
    }

    public void userOpensWindowOnTheBrowser(String url) {
        WebDriversManager.getDriver().switchTo().newWindow(WindowType.WINDOW);
        navigateTo(url);
    }

    public void userVerifyTotalTabsOpenedAre(int num) {
        assertThat(allWindows(), hasSize(num));
    }

    public void userOpensTabOnTheBrowser(String url) {
        WebDriversManager.getDriver().switchTo().newWindow(WindowType.TAB);
        navigateTo(url);
    }

    public void userPrintTheLocationOfTheObject() {
        WebElement logo = getElement(By.cssSelector("a img[src='img/hands-on-icon.png']"));
        System.out.println("┌───────────────────┐");
        System.out.println("│ Height: " + logo.getRect().getDimension().getHeight());
        System.out.println("│ Height: " + logo.getRect().getDimension().getWidth());
        System.out.println("│ X-Location: " + logo.getRect().getX());
        System.out.println("│ Y-Location: " + logo.getRect().getY());
        System.out.println("└───────────────────┘");
    }
}
