package devils.dare.apis.base;

import com.google.common.util.concurrent.Uninterruptibles;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Base class for managing Rest API requests through rest-assured Library.
 */
public abstract class BaseApi {

    private BaseApi() {
    }

//    private static final ThreadLocal<RequestSpecBuilder> REQ_SPEC_BUILDER = ThreadLocal.withInitial(RequestSpecBuilder::new);
//    private static final ThreadLocal<ResponseSpecBuilder> RES_SPEC_BUILDER = ThreadLocal.withInitial(ResponseSpecBuilder::new);
    private static final Logger LOGGER = LogManager.getLogger(BaseApi.class);

//    public static RequestSpecBuilder getReqSpecBuilder() {
//        return REQ_SPEC_BUILDER.get();
//    }
//
//    public static ResponseSpecBuilder getResSpecBuilder() {
//        return RES_SPEC_BUILDER.get();
//    }

    public static void waitFor(long time) {
        Uninterruptibles.sleepUninterruptibly(time, TimeUnit.SECONDS);
    }

}
