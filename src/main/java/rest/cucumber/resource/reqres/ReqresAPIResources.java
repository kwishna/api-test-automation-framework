package rest.cucumber.resource.reqres;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReqresAPIResources {

    LIST_USERS("/api/users"),
    UNKNOWN_USER("/api/unknown"),
    REGISTER_USER("/api/register"),
    LOGIN_USER("/api/login");

    private final String resource;

    public static ReqresAPIResources of(String name) {
        ReqresAPIResources[] vals = ReqresAPIResources.values();
        for (ReqresAPIResources val : vals) {
            if (name.equalsIgnoreCase(val.resource)) {
                return val;
            }
        }
        throw new RuntimeException("API resource not found in class " + ReqresAPIResources.class.getName());
    }
}
