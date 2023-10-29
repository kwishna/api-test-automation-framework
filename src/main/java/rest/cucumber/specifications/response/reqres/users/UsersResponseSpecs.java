package rest.cucumber.specifications.response.reqres.users;

import io.restassured.specification.ResponseSpecification;
import rest.cucumber.specifications.response.common.CommonResponseSpecs;

/**
 * Spec bulder for '/user' endpoint.
 */
public class UsersResponseSpecs extends CommonResponseSpecs {
    public static ResponseSpecification addGetUserResponseSpec() {
        setBasicResponseSpecs();
        return respSpecManager().getSpec();
    }
}
