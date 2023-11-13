package devils.dare.commons.utils;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Test session manager.
 */
public class TestSession {

    private final Map<String, Object> metadata = new ConcurrentHashMap<>();

    public void shouldContainKey(String key) {
        Object result = metadata.get(key);
        if (result == null) {
            throw new AssertionError("Session variable " + key + " expected but not found.");
        }
    }

    public Map<String, Object> getEntireMetaData() {
        return new HashMap<>(metadata);
    }

    public void setMetaData(String key, Object value) {
        metadata.put(key, value);
    }

    public Object getMetaData(String key) {
        return metadata.get(key);
    }

    public void clearMetaData() {
        metadata.clear();
    }

    public Set<String> getMetadataKeys(Object val) {
        Set<String> allKeys = new LinkedHashSet<>();
        for (Map.Entry<String, Object> entry : metadata.entrySet()) {
            if (entry.getValue().equals(val)) {
                allKeys.add(entry.getKey());
            }
        }
        return allKeys;
    }
}

