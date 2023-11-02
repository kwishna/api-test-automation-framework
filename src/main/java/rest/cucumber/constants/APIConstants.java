package rest.cucumber.constants;

public interface APIConstants {
    String HEADER_USER_AGENT = "User-Agent";
    String HEADER_CONTENT_TYPE = "Content-Type";
    String HEADER_ACCEPT = "Accept";
    String CACHE_CONTROL = "Cache-Control";
    String ACCEPT_ENCODING = "Accept-Encoding";
    String CONTENT_LENGTH = "Content-Length";
    String HEADER_AUTHORIZATION = "Authorization";
    String APPLICATION_XML = "application/xml";
    String APPLICATION_JSON = "application/json";
    String TEXT_HTML_CHARSET_UTF_8 = "text/html; charset=utf-8";
    String KEEP_ALIVE = "Keep-Alive";
    String CLOSE = "CLOSE";

    // Additional commonly used headers with example values
    String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    String HEADER_COOKIE = "Cookie";
    String HEADER_CONNECTION = "Connection";
    String HEADER_HOST = "Host";
    String HEADER_REFERER = "Referer";
    String HEADER_IF_MATCH = "If-Match";
    String HEADER_IF_NONE_MATCH = "If-None-Match";
    String HEADER_IF_MODIFIED_SINCE = "If-Modified-Since";
    String HEADER_IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    String HEADER_ETAG = "ETag";

    // Security-related headers with example values
    String HEADER_X_FRAME_OPTIONS = "X-Frame-Options";
    String HEADER_X_XSS_PROTECTION = "X-XSS-Protection";
    String HEADER_CONTENT_SECURITY_POLICY = "Content-Security-Policy";
    String HEADER_STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
    String HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    String HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    String HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    // Caching and validation headers with example values
    String EXPIRES = "Expires";
    String LAST_MODIFIED = "Last-Modified";
    String PRAGMA = "Pragma";

    // Compression and encoding headers with example values
    String CONTENT_ENCODING = "Content-Encoding";
    String ACCEPT_CHARSET = "Accept-Charset";
    String ACCEPT_LANGUAGE = "Accept-Language";
    String VARY = "Vary";

    String MAX_AGE = "max-age=0";
    String TRANSFER_ENCODING_CHUNKED = "Transfer-Encoding: chunked";
    String SET_COOKIE = "Set-Cookie";
}

