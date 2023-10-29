package rest.cucumber.pages;

import rest.cucumber.managers.RequestSpecsManager;
import rest.cucumber.managers.ResponseSpecsManager;

public class BaseAPIEndpoint {

    private static final ThreadLocal<RequestSpecsManager> reqSpecMngr = ThreadLocal.withInitial(RequestSpecsManager::new);
    private static final ThreadLocal<ResponseSpecsManager> respSpecMngr = ThreadLocal.withInitial(ResponseSpecsManager::new);

    public static RequestSpecsManager getReqSpecMngr() {
        return reqSpecMngr.get();
    }

    public static ResponseSpecsManager getRespSpecMngr() {
        return respSpecMngr.get();
    }
}
