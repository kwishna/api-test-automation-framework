package rest.cucumber.handles;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Responder {

    private static final Logger LOGGER = LogManager.getLogger(Responder.class);

    /**
     * Validate response specifications.
     *
     * @param response
     * @param respSpec
     * @return
     */
    public Responder validateResponse(Response response, ResponseSpecification respSpec) {
        response.then().spec(respSpec);
        return this;
    }

    /**
     * De-serialize Response to class
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserializeJsonResponseToObject(Response response, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.asString(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
