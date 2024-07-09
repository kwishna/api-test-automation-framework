package devils.dare.commons.utils;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * class to sync data for sharing between steps.
 */
public final class TestContext {

    private static final ThreadLocal<TestSession> TEST_SESSION_THREAD_LOCAL = ThreadLocal.withInitial(TestSession::new);
    private static final Logger LOGGER = LogManager.getLogger(TestContext.class);

    public static TestSession getCurrentSession() {
        return TEST_SESSION_THREAD_LOCAL.get();
    }

    public Response response;
}

