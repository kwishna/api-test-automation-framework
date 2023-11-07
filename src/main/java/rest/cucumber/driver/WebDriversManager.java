package rest.cucumber.driver;

import org.openqa.selenium.WebDriver;
import rest.cucumber.config.Configurations;
import rest.cucumber.constants.Browsers;

import java.util.Arrays;

public final class WebDriversManager {
    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final String BROWSER_NAME = Configurations.configuration().browser();

//    private static final List<WebDriver> wdList = new ArrayList<>();
//    static {
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            wdList.stream().filter(Objects::nonNull).forEach(WebDriver::quit);
//        }));
//    }

    private WebDriversManager() {
    }

    private static Browsers getBrowser() {
        return Arrays.stream(Browsers.values()).filter(browsr -> browsr.name().equalsIgnoreCase(BROWSER_NAME)).findFirst().orElse(Browsers.CHROME);
    }

    public static WebDriver getDriver() {
        if (DRIVER_THREAD_LOCAL.get() == null) {
            WebDriver driver = BasicDriverManager.getDriver();
            setDriver(driver);
        }
        return DRIVER_THREAD_LOCAL.get();
    }

    public static void setDriver(WebDriver driver) {
        DRIVER_THREAD_LOCAL.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            driver.quit();
            setDriver(null);
        }
    }
}
