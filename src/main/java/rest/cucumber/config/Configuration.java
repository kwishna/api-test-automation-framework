package rest.cucumber.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.ConfigCache;

@LoadPolicy(LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:local.properties",
        "classpath:app.properties",
        "classpath:grid.properties",
        "classpath:report.properties"
})
public interface Configuration extends Config {
    static Configuration configuration() {
        return ConfigCache.getOrCreate(Configuration.class);
    }

    @Key("user.dir")
    String userDir();

    @Key("target")
    String target();

    @Key("browser")
    String browser();

    @Key("headless")
    Boolean headless();

    @Key("url.api")
    String baseApiUrl();

    @Key("url.base")
    String baseUrl();

    @Key("client.id")
    String clientId();

    @Key("client.secret")
    String clientSecret();

    @Key("url.oauth")
    String oauthUrl();

    @Key("timeout")
    int timeout();

    @Key("grid.url")
    String gridUrl();

    @Key("grid.port")
    String gridPort();

    @Key("faker.locale")
    String faker();

    @Key("auto.login")
    boolean autoLogin();

    @Key("enable.tracing")
    boolean enableTracing();

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
}
