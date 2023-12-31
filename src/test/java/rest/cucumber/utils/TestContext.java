package rest.cucumber.utils;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * class to sync data for sharing between steps.
 */
public final class TestContext {
    private static final Logger LOGGER = LogManager.getLogger(TestContext.class);
    private static final ThreadLocal<Map<String, Object>> LOCAL_CONTEXT = ThreadLocal.withInitial(HashMap::new);
    public Response response;

    /**
     * Get data from sync storage.
     *
     * @param key
     * @return
     */
    public static Object getSyncVal(String key) {
        return LOCAL_CONTEXT.get().get(key);
    }

    /**
     * Set data to sync storage.
     *
     * @param key
     * @param value
     */
    public static void setSyncVal(String key, Object value) {
        LOCAL_CONTEXT.get().put(key, value);
    }

    /**
     * Get keys from sync storage.
     *
     * @param val
     * @return
     */
    public static Set<String> getSyncKeys(Object val) {
        Set<String> allKeys = new LinkedHashSet<>();
        for (Map.Entry<String, Object> entry : LOCAL_CONTEXT.get().entrySet()) {
            if (entry.getValue().equals(val)) {
                allKeys.add(entry.getKey());
            }
        }
        return allKeys;
    }
}

