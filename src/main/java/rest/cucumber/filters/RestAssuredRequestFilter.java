package rest.cucumber.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestAssuredRequestFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(RestAssuredRequestFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        LOGGER.info("-----------------------------------------------------------------------------------------");
        LOGGER.info(" Request Method => " + requestSpec.getMethod() +
                "\n Request URI => " + requestSpec.getURI() +
                "\n Request Header =>\n" + requestSpec.getHeaders() +
                "\n Request Body => " + requestSpec.getBody() +
                "\n\n Response Status => " + response.getStatusLine() +
                "\n Response Header =>\n" + response.getHeaders() +
                "\n Response Body => " + response.getBody().prettyPrint());
        LOGGER.info("-----------------------------------------------------------------------------------------");
        return response;
    }

//    public void removeHeader(String header) {
//        specBuilder.get().addFilter((filterableRequestSpecification, filterableResponseSpecification, filterContext) -> {
//            filterableRequestSpecification.removeHeader(header);
//            return filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
//        });
//    }
}