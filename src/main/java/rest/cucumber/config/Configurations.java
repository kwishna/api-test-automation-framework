package rest.cucumber.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.ConfigCache;

@LoadPolicy(LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:report.properties"
})
public interface Configurations extends Config {
    static Configurations configuration() {
        return ConfigCache.getOrCreate(Configurations.class);
    }

    @Key("user.dir")
    String userDir();

    @Key("project.name")
    String projectName();

    @Key("test.env")
    String testEnv();

    @DefaultValue("chrome")
    @Key("browser")
    String browser();

    @DefaultValue("true")
    @Key("headless")
    String headless();

    @DefaultValue("./")
    @Key("download.dir")
    String downloadDir();

    @DefaultValue("60")
    @Key("pageload.timeout")
    String pageLoadTimeout();

    @DefaultValue("30")
    @Key("implicit.timeout")
    String implicitTimeout();

    @DefaultValue("")
    @Key("driver.arguments")
    String driverArguments();

    @Key("target")
    String target();

    @Key("api.base")
    String baseApiUrl();

    @Key("base.url")
    String baseUrl();

    @Key("url.home")
    String userHome();

    @Key("client.id")
    String clientId();

    @Key("client.secret")
    String clientSecret();

    @Key("url.oauth")
    String oauthUrl();

    @Key("timeout")
    int timeout();

    @Key("faker.locale")
    String faker();

    @Key("action.delay")
    double slowMotion();

    @Key("pause.low")
    long pauseLow();

    @Key("pause.medium")
    long pauseMedium();

    @Key("pause.high")
    long pauseHigh();

    @Key("email.max.timeout")
    int maxEmailTimeout();

    @Key("max.retry")
    int maxRetry();

    @Key("app.username")
    String appUserName();

    @Key("app.password")
    String appPassword();

    @Key("report.title")
    String reportTitle();

    @Key("report.name")
    String reportName();

    @Key("report.theme")
    String reportTheme();

    @Key("driver.chromedriver.path")
    String chromeDriverPath();

    @Key("driver.geckodriver.path")
    String geckoDriverPath();

    @Key("driver.edge.path")
    String edgeDriverPath();

    @Key("driver.ie.path")
    String ieDriverPath();

    @Key("driver.safari.path")
    String safariDriverPath();
}
