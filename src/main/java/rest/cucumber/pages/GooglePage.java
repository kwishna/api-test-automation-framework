package rest.cucumber.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import rest.cucumber.base.BasePage;
import rest.cucumber.driver.WebDriversManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class GooglePage extends BasePage {

    public void openUrl(String url) {
        open(url);
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
