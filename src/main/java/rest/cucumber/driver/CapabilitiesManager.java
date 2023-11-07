package rest.cucumber.driver;


import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariOptions;
import rest.cucumber.constants.Browsers;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;

import static rest.cucumber.config.Configurations.configuration;

public class CapabilitiesManager {
    private static final boolean HEADLESS = Boolean.parseBoolean(configuration().headless());
    private static final String DOWNLOAD_DIR = configuration().downloadDir();
    private static final int PAGE_LOAD_TIMEOUT = Integer.parseInt(configuration().pageLoadTimeout());
    private static final int IMPLICIT_WAIT_TIME = Integer.parseInt(configuration().implicitTimeout());
    private static final String DRIVER_ARGUMENTS = configuration().driverArguments();

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

    static FirefoxOptions getGeckoOptions() {

        FirefoxOptions options = new FirefoxOptions();
        options.setLogLevel(FirefoxDriverLogLevel.DEBUG);
        options.setPlatformName(Platform.WINDOWS.name());

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.dir", DOWNLOAD_DIR);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain,application/octet-stream,application/pdf,application/x-pdf,application/vnd.pdf,application/zip,application/x-rar-compressed,application/x-gzip,application/msword");
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);

        options.setProfile(profile);

        if (!DRIVER_ARGUMENTS.isEmpty()) {
            options.addArguments(DRIVER_ARGUMENTS.split(";"));
        }

        return options;
    }

    static InternetExplorerOptions getIeOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setPlatformName(Platform.WINDOWS.name());
        options.setCapability("requireWindowFocus", true);
        return options;
    }

    static SafariOptions getSafariOptions() {
        SafariOptions options = new SafariOptions();
        options.setUseTechnologyPreview(true);
        options.setPlatformName(Platform.MAC.name());
        return options;
    }

    static MutableCapabilities addCommonCapabilities(AbstractDriverOptions<? extends MutableCapabilities> options) {
        options.setAcceptInsecureCerts(true);
        options.setPageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        options.setScriptTimeout(Duration.ofSeconds(60));
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
        options.setImplicitWaitTimeout(Duration.ofSeconds(IMPLICIT_WAIT_TIME));

        return options;
    }

    static AbstractDriverOptions<? extends MutableCapabilities> addChromiumCapabilities(ChromiumOptions<? extends ChromiumOptions<?>> options) {

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", DOWNLOAD_DIR);
        chromePrefs.put("plugins.always_open_pdf_externally", true);
        chromePrefs.put("credentials_enable_service", false);
        options.setExperimentalOption("prefs", chromePrefs);

        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("disable-infobars");

        if (HEADLESS) {
            options.addArguments("--headless=new");
        }

        if (!DRIVER_ARGUMENTS.isEmpty()) {
            options.addArguments(DRIVER_ARGUMENTS.split(";"));
        }

        return options;
    }

    static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setPlatformName(Platform.WINDOWS.name());

        return options;
    }

    static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.setPlatformName(Platform.WINDOWS.name());
        return options;
    }
}
