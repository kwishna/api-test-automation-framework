package devils.dare.apis.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import static io.restassured.RestAssured.post;

public class CustomAuthFilter implements Filter {
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        final String loginResponse = post("/custom-auth/login").asString();
        final JsonPath jsonPath = new JsonPath(loginResponse);
        final int operandA = jsonPath.getInt("operandA");
        final int operandB = jsonPath.getInt("operandB");
        final String sessionId = jsonPath.getString("id");
        requestSpec.param("sum", operandA + operandB);
        requestSpec.param("id", sessionId);
        return ctx.next(requestSpec, responseSpec);
    }
}
