package rest.cucumber.resource.reqres;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReqresAPIResources {

    LIST_USERS("/api/users"),
    UNKNWON_USER("/api/unknown"),
    REGISTER_USER("/api/register"),
    LOGIN_USER("/api/login");

    private String resource;

    public static ReqresAPIResources of(String name) {
        ReqresAPIResources[] vals = ReqresAPIResources.values();
        for (int i = 0; i < vals.length; i++) {
            if (name.toLowerCase().equals(vals[i].resource.toLowerCase())) {
                return vals[i];
            }
        }
        throw new RuntimeException("API resource not found in class " + ReqresAPIResources.class.getName());
    }
}
