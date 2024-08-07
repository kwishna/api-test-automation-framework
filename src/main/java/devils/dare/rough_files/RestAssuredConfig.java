package devils.dare.rough_files;

import io.restassured.config.*;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.config.JsonPathConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * All Rest Assured Configs.
 */
public class RestAssuredConfig {

    private static final Logger LOGGER = LogManager.getLogger(RestAssuredConfig.class);

    public static io.restassured.config.RestAssuredConfig getSslConfig(String sessionName, String sessionValue) {
        // Configure ssl settings
        SSLConfig sslConfig = SSLConfig
                .sslConfig()
                .allowAllHostnames();

        // Create a RestAssuredConfig with the ssl settings
        return io.restassured.config.RestAssuredConfig.config().sslConfig(sslConfig);
    }

    public static io.restassured.config.RestAssuredConfig getSessionConfig(String sessionName, String sessionValue) {
        // Configure session settings
        SessionConfig sessionConfig = SessionConfig
                .sessionConfig()
                .sessionIdName(sessionName)
                .sessionIdValue(sessionValue);

        // Create a RestAssuredConfig with the configured session settings
        return io.restassured.config.RestAssuredConfig.config().sessionConfig(sessionConfig);
    }

    public static io.restassured.config.RestAssuredConfig getMatcherConfig() {
        // Configure matcher settings
        MatcherConfig matcherConfig = MatcherConfig
                .matcherConfig()
                .errorDescriptionType(MatcherConfig.ErrorDescriptionType.REST_ASSURED);

        // Create a RestAssuredConfig with the configured matcher settings
        return io.restassured.config.RestAssuredConfig.config().matcherConfig(matcherConfig);
    }

    public static io.restassured.config.RestAssuredConfig getLogConfig() {
        // Configure log settings
        LogConfig logConfig = LogConfig
                .logConfig()
                .blacklistDefaultSensitiveHeaders()
                .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
                .enablePrettyPrinting(true);

        // Create a RestAssuredConfig with the configured logConfig settings
        return io.restassured.config.RestAssuredConfig.config().logConfig(logConfig);
    }

//    public static RestAssuredConfig getEncoderConfig() {
//        /// Configure encoder settings
//        EncoderConfig encoderConfig = EncoderConfig
//                .encoderConfig()
//                .appendDefaultContentCharsetToContentTypeIfUndefined(true);
//
//        // Create a RestAssuredConfig with the configured encoder settings
//        return RestAssuredConfig.config().encoderConfig(encoderConfig);
//    }


//    public static RestAssuredConfig getDecoderConfig() {
//        // Configure decoder settings
//        DecoderConfig decoderConfig = DecoderConfig
//                .decoderConfig()
//                .defaultContentCharset("UTF-8"); // Set the default content charset
//
//        // Create a RestAssuredConfig with the configured decoder settings
//        return RestAssuredConfig.config().decoderConfig(decoderConfig);
//    }

    public static io.restassured.config.RestAssuredConfig getRedirectConfig() {
        // Configure redirect settings
        RedirectConfig redirectConfig = RedirectConfig.redirectConfig()
                .followRedirects(true)
                .and()
                .maxRedirects(5);

        // Create a RestAssuredConfig with the configured redirect settings
        return io.restassured.config.RestAssuredConfig.config().redirect(redirectConfig);
    }

    public static io.restassured.config.RestAssuredConfig getCsrfConfig(URL url) {
        // Configure CSRF settings
        CsrfConfig csrfConfig = CsrfConfig
                .csrfConfig()
                .csrfTokenPath(url);

        // Create a RestAssuredConfig with the configured CSRF settings
        return io.restassured.config.RestAssuredConfig.config().csrfConfig(csrfConfig);
    }


    public static io.restassured.config.RestAssuredConfig getConnectionConfig(long timeOut, TimeUnit timeUnit) {
        // Configure connection settings
        ConnectionConfig connectionConfig = ConnectionConfig
                .connectionConfig()
                .closeIdleConnectionsAfterEachResponseAfter(timeOut, timeUnit);
        // Create a RestAssuredConfig with the configured connection settings
        return io.restassured.config.RestAssuredConfig.config().connectionConfig(connectionConfig);

    }

    public static io.restassured.config.RestAssuredConfig getJsonConfig(JsonPathConfig.NumberReturnType returnType) {
        // Configure connection settings
        JsonConfig jsonConfig = JsonConfig
                .jsonConfig()
                .numberReturnType(returnType);
        // Create a RestAssuredConfig with the configured connection settings
        return io.restassured.config.RestAssuredConfig.config().jsonConfig(jsonConfig);

    }

}
