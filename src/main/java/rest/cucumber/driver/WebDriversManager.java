package rest.cucumber.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import rest.cucumber.config.Configurations;
import rest.cucumber.constants.Browsers;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

import static rest.cucumber.config.Configurations.configuration;

/**
 * Manages the browser driver for the project.
 * Opening a browser driver.
 * Closing a browser driver.
 */
public final class WebDriversManager {

    private static final Logger LOGGER = LogManager.getLogger(WebDriversManager.class);
    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final List<WebDriver> WD_LIST = new ArrayList<>();
    private static final String BROWSER_NAME = configuration().browser();
    private static final String DOWNLOAD_DIR = configuration().downloadDir();
    private static final String DRIVER_ARGUMENTS = configuration().driverArguments();

    static {
        // Close all drivers opened in current thread.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            WD_LIST.stream().filter(Objects::nonNull).forEach(WebDriver::quit);
            try {
                Runtime.getRuntime().exec("chromedriver.exe");
                Runtime.getRuntime().exec("chrome.exe");
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }));
    }

    private WebDriversManager() {
    }

    /**
     * Start a new driver or Get the currently running driver.
     *
     * @return {@link WebDriver} instance
     */
    public static WebDriver getDriver() {
        if (DRIVER_THREAD_LOCAL.get() == null) {
            WebDriver driver = DriverManager.startDriver();
            WD_LIST.add(driver);
            setDriver(driver);
        }
        return DRIVER_THREAD_LOCAL.get();
    }

    /**
     * Set driver instance.
     *
     * @param driver {@link WebDriver} instance
     */
    public static void setDriver(WebDriver driver) {
        DRIVER_THREAD_LOCAL.set(driver);
    }

    /**
     * Close the current driver.
     */
    public static void quitDriver() {
        WebDriver driver = DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            driver.quit();
            setDriver(null);
        }
    }

    // ----------------------------------------------------------------

    /**
     * This class starts the driver based on the capabilities.
     */
    private static class DriverManager {
        private static WebDriver startDriver() {
            Browsers browser = Arrays.stream(Browsers.values()).filter(browsr -> browsr.name().equalsIgnoreCase(BROWSER_NAME)).findFirst().orElse(Browsers.CHROME);
            return switch (browser) {
                case CHROME -> getChromeDriver();
                case FIREFOX, MOZILLA, GECKO -> getFirefoxDriver();
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

    // ----------------------------------------------------------------

    /**
     * class to manage capabilities for different browsers.
     */
    private static class CapabilitiesManager {
        /**
         * Get the capabilities for the specified driver.
         *
         * @param browser {@link Browsers}
         * @return {@link MutableCapabilities} instance
         */
        static public MutableCapabilities getBrowserCapabilities(Browsers browser) {
            return switch (browser) {
                case CHROME -> addCommonCapabilities(addChromiumCapabilities(getChromeOptions()));
                case FIREFOX, MOZILLA, GECKO -> addCommonCapabilities(getGeckoOptions());
                case EDGE -> addCommonCapabilities(addChromiumCapabilities(getEdgeOptions()));
                case IE -> addCommonCapabilities(getIeOptions());
                case SAFARI -> addCommonCapabilities(getSafariOptions());
                default -> throw new RuntimeException("No Capabilities Found For Provided Browser : " + browser);
            };
        }

        private static FirefoxOptions getGeckoOptions() {

            FirefoxOptions options = new FirefoxOptions();
            options.setLogLevel(FirefoxDriverLogLevel.DEBUG);
            options.setPlatformName(Platform.WINDOWS.name());

            final FirefoxProfile profile = getFirefoxProfile();
            options.setProfile(profile);

            if (!DRIVER_ARGUMENTS.isEmpty()) {
                options.addArguments(DRIVER_ARGUMENTS.split(";"));
            }

            return options;
        }

        private static FirefoxProfile getFirefoxProfile() {
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("browser.download.dir", DOWNLOAD_DIR);
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain,application/octet-stream,application/pdf,application/x-pdf,application/vnd.pdf,application/zip,application/x-rar-compressed,application/x-gzip,application/msword");
            profile.setPreference("browser.download.manager.showWhenStarting", false);
            profile.setPreference("browser.helperApps.alwaysAsk.force", false);
            return profile;
        }

        private static InternetExplorerOptions getIeOptions() {
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.setPlatformName(Platform.WINDOWS.name());
            options.setCapability("requireWindowFocus", true);
            return options;
        }

        private static SafariOptions getSafariOptions() {
            SafariOptions options = new SafariOptions();
            options.setUseTechnologyPreview(true);
            options.setPlatformName(Platform.MAC.name());
            return options;
        }

        private static MutableCapabilities addCommonCapabilities(AbstractDriverOptions<? extends MutableCapabilities> options) {
            options.setAcceptInsecureCerts(true);
            options.setPageLoadTimeout(Duration.ofSeconds(Integer.parseInt(configuration().pageLoadTimeout())));
            options.setScriptTimeout(Duration.ofSeconds(60));
            options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
            options.setImplicitWaitTimeout(Duration.ofSeconds(Integer.parseInt(configuration().implicitTimeout())));

            return options;
        }

        private static AbstractDriverOptions<? extends MutableCapabilities> addChromiumCapabilities(ChromiumOptions<? extends ChromiumOptions<?>> options) {

            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", DOWNLOAD_DIR);
            chromePrefs.put("plugins.always_open_pdf_externally", true);
            chromePrefs.put("credentials_enable_service", false);
            options.setExperimentalOption("prefs", chromePrefs);

            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            options.addArguments("disable-infobars");

            if (Boolean.parseBoolean(configuration().headless())) {
                options.addArguments("--headless=new");
            }

            if (!DRIVER_ARGUMENTS.isEmpty()) {
                options.addArguments(DRIVER_ARGUMENTS.split(";"));
            }

            return options;
        }

        private static ChromeOptions getChromeOptions() {
            ChromeOptions options = new ChromeOptions();
            options.setPlatformName(Platform.WINDOWS.name());
            return options;
        }

        private static EdgeOptions getEdgeOptions() {
            EdgeOptions options = new EdgeOptions();
            options.setPlatformName(Platform.WINDOWS.name());
            return options;
        }
    }
}
