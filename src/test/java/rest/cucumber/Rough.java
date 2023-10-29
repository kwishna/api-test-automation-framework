package rest.cucumber;

import rest.cucumber.managers.RequestSpecsManager;
import rest.cucumber.specifications.request.common.CommonRequestSpecs;
import rest.cucumber.specifications.request.places.PlaceRequestSpecs;

class ThreadChecker {
    private static ThreadLocal<Integer> INT = ThreadLocal.withInitial(() -> 1);

    public static Integer getLocal() {
        return INT.get();
    }

    public static void setLocal(Integer local) {
        INT.set(local);
    }
}

public class Rough {

    public static void main(String[] args) {
        CommonRequestSpecs.setBasicRequestSpecs();
        PlaceRequestSpecs.prepareAddPlaceSpecs();


    }
}
