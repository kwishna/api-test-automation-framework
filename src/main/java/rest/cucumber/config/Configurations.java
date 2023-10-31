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

    @Key("target")
    String target();

    @Key("API_BASE")
    String baseApiUrl();

    @Key("url.base")
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
}
