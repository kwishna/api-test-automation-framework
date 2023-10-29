package rest.cucumber.specifications.request.reqres.users;

import rest.cucumber.resource.reqres.ReqresAPIResources;
import rest.cucumber.specifications.request.common.CommonRequestSpecs;

/**
 * Spec bulder for '/user' endpoint.
 */
public class UsersRequestSpecs extends CommonRequestSpecs {
    public static void userRequestSpec(ReqresAPIResources apiResources) {
        setBasicRequestSpecs();
        reqSpecManager()
                .setBasePath(apiResources.getResource());
    }
}
