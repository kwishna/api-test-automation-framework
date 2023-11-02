package rest.cucumber.filters;

import io.restassured.filter.cookie.CookieFilter;
import io.restassured.filter.session.SessionFilter;

public class RestAssuredFilters {

    public static SessionFilter getSessionFilter() {
        return new SessionFilter();
    }

    public static CookieFilter getCookieFilter() {
        return new CookieFilter();
    }
}
