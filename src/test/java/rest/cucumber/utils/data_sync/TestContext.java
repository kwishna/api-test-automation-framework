package rest.cucumber.utils.data_sync;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseOptions;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * class to sync data for sharing between steps.
 */
public final class TestContext {

    private static final ThreadLocal<Map<String, Object>> LOCAL_CONTEXT = ThreadLocal.withInitial(HashMap::new);
    // Use this variable cautiously.
    public final ThreadLocal<Map<String, Object>> session = ThreadLocal.withInitial(HashMap::new);
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

