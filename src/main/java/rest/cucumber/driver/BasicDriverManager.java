package rest.cucumber.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import rest.cucumber.config.Configurations;
import rest.cucumber.constants.Browsers;

import java.util.Arrays;

public class BasicDriverManager {

    private static final String BROWSER_NAME = Configurations.configuration().browser();

    public static WebDriver getDriver() {
        Browsers browser = Arrays.stream(Browsers.values()).filter(browsr -> browsr.name().equalsIgnoreCase(BROWSER_NAME)).findFirst().orElse(Browsers.CHROME);
        return switch (browser) {
            case CHROME -> getChromeDriver();
            case FIREFOX , MOZILLA, GECKO -> getFirefoxDriver();
            case EDGE -> getEdgeDriver();
            case IE -> getIEDriver();
            case SAFARI -> getSafariDriver();
            default -> throw new RuntimeException("No Match Found For The Provided Browser : " + BROWSER_NAME);
        };
    }

    private static ChromeDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver", Configurations.configuration().chromeDriverPath());
        return new ChromeDriver((ChromeOptions) CapabilitiesManager.getBrowserCapabilities(Browsers.CHROME));
    }

    private static FirefoxDriver getFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver", Configurations.configuration().geckoDriverPath());
        return new FirefoxDriver((FirefoxOptions) CapabilitiesManager.getBrowserCapabilities(Browsers.FIREFOX));
    }

    private static EdgeDriver getEdgeDriver() {
        System.setProperty("webdriver.edge.driver", Configurations.configuration().edgeDriverPath());
        return new EdgeDriver((EdgeOptions) CapabilitiesManager.getBrowserCapabilities(Browsers.EDGE));
    }

    private static InternetExplorerDriver getIEDriver() {
        System.setProperty("webdriver.ie.driver", Configurations.configuration().ieDriverPath());
        return new InternetExplorerDriver((InternetExplorerOptions) CapabilitiesManager.getBrowserCapabilities(Browsers.EDGE));
    }

    private static SafariDriver getSafariDriver() {
        System.setProperty("webdriver.ie.driver", Configurations.configuration().safariDriverPath());
        return new SafariDriver((SafariOptions) CapabilitiesManager.getBrowserCapabilities(Browsers.SAFARI));
    }
}
