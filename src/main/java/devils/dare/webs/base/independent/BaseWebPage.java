package devils.dare.webs.base.independent;

import com.google.common.util.concurrent.Uninterruptibles;
import devils.dare.commons.config.Configurations;
import devils.dare.commons.constants.Constants;
import devils.dare.commons.utils.Utilities;
import devils.dare.webs.base.serenity.SerenityBaseWebPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static devils.dare.webs.driver.WebDriversManager.getDriver;

/**
 * This Base class should be used only with Selenium WebDriver.
 * Remove this when you're using Serenity WebDriver.
 */
public abstract class BaseWebPage {

    private static final Logger LOGGER = LogManager.getLogger(SerenityBaseWebPage.class);

    private static final long TIMEOUT = Long.parseLong(Configurations.configuration().implicitTimeout());

    protected <WebDriver, V> void fluentWait(org.openqa.selenium.WebDriver driver, long timeOutInSeconds, String message, Function<? super org.openqa.selenium.WebDriver, V> isTrue) {
        try {
            new FluentWait<>(driver)
                    .ignoreAll(Collections.singleton(WebDriverException.class))
                    .pollingEvery(Duration.of(1, ChronoUnit.SECONDS))
                    .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                    .withMessage(message).until(isTrue);
        } catch (Exception e) {
            LOGGER.error("Explicit wait failed with message : " + message, e);
        }
    }

    protected <WebDriver, R> void explicitWait(org.openqa.selenium.WebDriver driver, long timeOutInSeconds, String message, Function<org.openqa.selenium.WebDriver, R> fun) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))
                    .withMessage(message)
                    .until(fun);
        } catch (Exception e) {
            LOGGER.error("Explicit wait failed with message : " + message, e);
        }
    }

    protected <WebDriver, R> void explicitWait(long timeOutInSeconds, String message, Function<org.openqa.selenium.WebDriver, R> fun) {
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(timeOutInSeconds))
                    .withMessage(message)
                    .until(fun);
        } catch (Exception e) {
            LOGGER.error("Explicit wait failed with message : " + message, e);
        }
    }

    protected <WebDriver, R> void explicitWait(String message, Function<org.openqa.selenium.WebDriver, R> fun) {
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(TIMEOUT))
                    .withMessage(message)
                    .until(fun);
        } catch (Exception e) {
            LOGGER.error("Explicit wait failed with message : " + message, e);
        }
    }

    protected <T, V> V fluentWaitGeneric(T input, Function<? super T, V> isTrue, String errMessage) {
        return new FluentWait<>(input)
                .ignoreAll(Collections.singleton(Exception.class))
                .pollingEvery(Duration.of(2, ChronoUnit.SECONDS))
                .withTimeout(Duration.ofSeconds(10))
                .withMessage(errMessage)
                .until(isTrue);
    }

    protected void waitForPresence(By byObj, long timeout) {
        explicitWait(timeout, "Element Not Present : " + byObj.toString(), ExpectedConditions.presenceOfElementLocated(byObj));
    }

    protected void waitForVisible(By byObj, long timeout) {
        explicitWait(timeout, "Element Not Visible : " + byObj.toString(), ExpectedConditions.visibilityOfElementLocated(byObj));
    }

    protected void waitForVisible(WebElement element, long timeout) {
        explicitWait(timeout, "Element Not Visible : " + element, ExpectedConditions.visibilityOf(element));
    }

    protected void waitForInvisible(By byObj, long timeout) {
        explicitWait(timeout, "Element Not Invisible : " + byObj.toString(), ExpectedConditions.invisibilityOfElementLocated(byObj));
    }

    protected void waitForInvisible(WebElement element, long timeout) {
        explicitWait(timeout, "Element Not Invisible : " + element, ExpectedConditions.stalenessOf(element));
    }

    protected void waitForClickable(By byObj, long timeout) {
        explicitWait(timeout, "Element Not Clickable : " + byObj.toString(), ExpectedConditions.elementToBeClickable(byObj));
    }

    protected void waitForClickable(WebElement element, long timeout) {
        explicitWait(timeout, "Element Not Clickable : " + element, ExpectedConditions.elementToBeClickable(element));
    }

    protected boolean isVisible(By byObj) {
        waitForVisible(byObj, TIMEOUT);
        WebElement el = getElement(byObj);
        return el != null && el.isDisplayed();
    }

    protected boolean isVisible(WebElement element) {
        waitForVisible(element, TIMEOUT);
        return element != null && element.isDisplayed();
    }

    protected boolean isEnabled(By byObj) {
        waitForVisible(byObj, TIMEOUT);
        WebElement el = getElement(byObj);
        return el != null && el.isEnabled();
    }

    protected boolean isEnabled(WebElement element) {
        waitForVisible(element, TIMEOUT);
        return element != null && element.isEnabled();
    }

    protected boolean isSelected(By byObj) {
        waitForVisible(byObj, TIMEOUT);
        WebElement el = getElement(byObj);
        return el != null && el.isSelected();
    }

    protected boolean isSelected(WebElement element) {
        waitForVisible(element, TIMEOUT);
        return element != null && element.isSelected();
    }

    protected boolean isNotVisible(By byObj) {
        waitForInvisible(byObj, TIMEOUT);
        return !getElement(byObj).isDisplayed();
    }

    protected boolean isNotVisible(WebElement element) {
        waitForInvisible(element, TIMEOUT);
        return !element.isDisplayed();
    }

    protected void sendKeys(By byObj, CharSequence... string) {
        waitForVisible(byObj, TIMEOUT);
        getElement(byObj).sendKeys(string);
    }

    protected void sendKeys(WebElement element, CharSequence... string) {
        waitForVisible(element, TIMEOUT);
        element.sendKeys(string);
    }

    protected void clear(By byObj) {
        WebElement element = getElement(byObj);
        element.clear();
    }

    protected void clear(WebElement element) {
        waitForVisible(element, TIMEOUT);
        element.clear();
    }

    protected void click(By byObj) {
        waitForClickable(byObj, TIMEOUT);
        getElement(byObj).click();
    }

    protected void click(WebElement element) {
        waitForClickable(element, TIMEOUT);
        element.click();
    }

    protected void submit(By byObj) {
        waitForClickable(byObj, TIMEOUT);
        getElement(byObj).submit();
    }

    protected void submit(WebElement element) {
        waitForClickable(element, TIMEOUT);
        element.submit();
    }

    protected WebElement getElement(By byObj) {
        waitForClickable(byObj, TIMEOUT);
        return getDriver().findElement(byObj);
    }

    protected List<WebElement> getElements(By byObj) {
        return getDriver().findElements(byObj);
    }

    protected WebElement getElement(SearchContext ctx, By byObj) {
        return ctx.findElement(byObj);
    }

    protected List<WebElement> getElements(SearchContext ctx, By byObj) {
        return ctx.findElements(byObj);
    }

    protected String text(By byObj) {
        waitForClickable(byObj, TIMEOUT);
        return getElement(byObj).getText();
    }

    protected String text(WebElement element) {
        waitForClickable(element, TIMEOUT);
        return element.getText();
    }

    protected String texts(By byObj) {
        waitForPresence(byObj, TIMEOUT);
        return getElement(byObj).getText();
    }

    protected List<String> texts(List<WebElement> element) {
        return element.stream().map(WebElement::getText).toList();
    }

    protected String cssValue(By byObj, String cssKey) {
        waitForVisible(byObj, TIMEOUT);
        return getElement(byObj).getCssValue(cssKey);
    }

    protected String cssValue(WebElement element, String cssKey) {
        waitForVisible(element, TIMEOUT);
        return element.getCssValue(cssKey);
    }

    protected String attribute(By byObj, String attr) {
        waitForVisible(byObj, TIMEOUT);
        return getElement(byObj).getAttribute(attr);
    }

    protected String attribute(WebElement element, String attr) {
        waitForVisible(element, TIMEOUT);
        return element.getAttribute(attr);
    }

    protected String className(By byObj) {
        waitForVisible(byObj, TIMEOUT);
        return getElement(byObj).getAttribute("class");
    }

    protected String className(WebElement element) {
        waitForVisible(element, TIMEOUT);
        return element.getAttribute("class");
    }

    protected String title() {
        return getDriver().getTitle();
    }

    protected String url() {
        return getDriver().getCurrentUrl();
    }

    protected Set<String> allWindows() {
        return getDriver().getWindowHandles();
    }

    protected String currentWindow() {
        return getDriver().getWindowHandle();
    }

    protected void navigateTo(String url) {
        getDriver().navigate().to(url);
    }

    protected void reload() {
        getDriver().navigate().refresh();
    }

    protected void forward() {
        getDriver().navigate().forward();
    }

    protected void back() {
        getDriver().navigate().back();
    }

    protected void openTheUrl(String url) {
        getDriver().get(url);
    }

    protected void clearCookies() {
        getDriver().manage().deleteAllCookies();
    }

    protected void addCookies(Cookie cookie) {
        getDriver().manage().addCookie(cookie);
    }

    protected Set<Cookie> cookies() {
        return getDriver().manage().getCookies();
    }

    protected void implicitlyWait(Duration timeout) {
        getDriver().manage().timeouts().implicitlyWait(timeout);
    }

    protected void pageLoadTimeout(Duration timeout) {
        getDriver().manage().timeouts().pageLoadTimeout(timeout);
    }

    protected void scriptTimeout(Duration timeout) {
        getDriver().manage().timeouts().scriptTimeout(timeout);
    }

    protected void takeElementScreenshot(By byObj) {
        byte[] file = getDriver().findElement(byObj).getScreenshotAs(OutputType.BYTES);
        try {
            Files.write(Paths.get(Constants.TARGET_PATH, Utilities.getTimeStamp() + ".PNG"), file, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void takeElementScreenshot(By byObj, String fileName) {
        byte[] file = getDriver().findElement(byObj).getScreenshotAs(OutputType.BYTES);
        try {
            Files.write(Paths.get(Constants.TARGET_PATH, fileName + ".PNG"), file, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void fullPageScreenshot(String fileName) {
        byte[] file = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        try {
            Files.write(Paths.get(Constants.TARGET_PATH, fileName + ".PNG"), file, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void fullPageScreenshot() {
        byte[] file = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        try {
            Files.write(Paths.get(Constants.TARGET_PATH, Utilities.getTimeStamp() + ".PNG"), file, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void closeWindow() {
        getDriver().close();
    }

    protected void createAndSwitchToNewTab() {
        getDriver().switchTo().newWindow(WindowType.TAB);
    }

    protected void createAndSwitchToNewWindow() {
        getDriver().switchTo().newWindow(WindowType.WINDOW);
    }

    protected Alert switchToAlert() {
        return getDriver().switchTo().alert();
    }

    protected void sleep(long timeInMillis) {
        Uninterruptibles.sleepUninterruptibly(timeInMillis, TimeUnit.MILLISECONDS);
    }

    protected void quitDriver() {
        getDriver().quit();
    }
}
