package devils.dare.apis.utils;

import java.util.Map;
import java.util.HashMap;

public class ScenarioContext {
    private static final ThreadLocal<Map<String, Object>> contextMap = new ThreadLocal<>();

    public static void init() {
        contextMap.set(new HashMap<>());
    }

    public static void setContext(String key, Object value) {
        contextMap.get().put(key, value);
    }

    public static Object getContext(String key) {
        return contextMap.get().get(key);
    }

    public static void clear() {
        contextMap.remove();
    }
}