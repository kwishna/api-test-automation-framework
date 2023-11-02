package rest.cucumber.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.xml.XmlMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    private static final Logger LOGGER = LogManager.getLogger(JsonUtils.class);

    /**
     * Serialize a Java object to a JSON string
     *
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception as needed
            return null;
        }
    }

    /**
     * Method that can be used to serialize any Java value as JSON output, written to File provided
     *
     * @param file
     * @param obj
     * @return
     */
    public static void objectIntoFile(File file, Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(file, obj);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    /**
     * Method to deserialize JSON content from given file into given Java type.
     *
     * @param file
     * @param clazz
     * @return
     */
    public static <T> T fileToClass(File file, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(file, clazz);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception as needed
            return null;
        }
    }

    /**
     * Serialize a Java object to a pretty JSON string
     *
     * @param object
     * @return
     */
    public static String objectToPrettyJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception as needed
            return null;
        }
    }

    /**
     * De-serialize Response to class
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonResponseToObject(Response response, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.asString(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * De-serialize Response to class
     *
     * @param response
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonResponseToObject(ValidatableResponse response, Class<T> clazz) {
        return jsonResponseToObject(response.extract().response(), clazz);
    }

    /**
     * Json String To Map
     *
     * @param jsonString
     * @return
     */
    public static Map<String, Object> jsonStringToType(String jsonString) {
        try {
            return new ObjectMapper().readValue(jsonString, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts an Object to Map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) {
        try {
            // Convert the object to a Map
            return new ObjectMapper().convertValue(obj, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Json String to a Class.
     *
     * @param jsonString
     * @param T
     * @param <T>
     * @return
     */
    public static <T> T jsonStringToType(String jsonString, Class<?> T) {
        try {
            return new ObjectMapper().readValue(jsonString, new TypeReference<T>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

public static <T> T jsonStringToObject(String jsonString, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts Map to a Class.
     *
     * @param map
     * @param T
     * @param <T>
     * @return
     */
    public static <T> T mapToType(Map<String, Object> map, Class<?> T) {
        try {
            return new ObjectMapper().readValue(mapToJson(map), new TypeReference<T>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts Map to a Class.
     *
     * @param map
     * @param T
     * @param <T>
     * @return
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(mapToJson(map), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts Map to Json
     *
     * @param map
     * @return
     * @throws JsonProcessingException
     */
    public static String mapToJson(Map<String, Object> map) {
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Json Array To List of Object.
     *
     * @param jsonArrayString
     * @return
     * @throws IOException
     */
    public static List<Object> jsonArrayToList(String jsonArrayString) {
        try {
            return new ObjectMapper().readValue(jsonArrayString, new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * List of Object to Json array.
     *
     * @param list
     * @return
     * @throws JsonProcessingException
     */
    public static String listToJsonArray(List<Object> list) {
        try {
            return new ObjectMapper().writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Print Json in a pretty format.
     *
     * @param jsonString
     * @return
     * @throws IOException
     */
    public static String prettyPrintJson(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(objectMapper.readTree(jsonString));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Merge 2 Json String.
     *
     * @param json1
     * @param json2
     * @return
     * @throws IOException
     */
    public static String mergeJson(String json1, String json2) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node1 = objectMapper.readTree(json1);
            JsonNode node2 = objectMapper.readTree(json2);
            // Merge JSON objects, with values from the second JSON overwriting the first
            ((ObjectNode) node1).setAll((ObjectNode) node2);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Compare 2 Json Strings.
     *
     * @param json1
     * @param json2
     * @return
     */
    public static boolean compareJson(String json1, String json2) {
        try {
            // Create ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse JSON strings into JsonNode objects
            JsonNode node1 = objectMapper.readTree(json1);
            JsonNode node2 = objectMapper.readTree(json2);

            // Compare the JsonNode objects
            return node1.equals(node2);
        } catch (Exception e) {
            // Handle exceptions as needed
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Json to XML.
     *
     * @param json
     * @return
     * @throws IOException
     */
    public static String jsonToXml(String json) throws IOException {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = jsonMapper.readTree(json);
            return xmlMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Query a JsonPath.
     *
     * @param jsonResponse
     * @param jsonPath
     * @return
     */
    public static Object queryValueFromJson(String jsonResponse, String jsonPath) {
        return new JsonPath(jsonResponse).get(jsonPath);
    }

    /**
     * @param jsonResponse
     * @param jsonArrayPath
     * @return
     */
    public static List<Object> queryListFromJson(String jsonResponse, String jsonArrayPath) {
        return new JsonPath(jsonResponse).getList(jsonArrayPath);
    }

    public static boolean isJsonKeyPresent(String jsonResponse, String key) throws Exception {
        return new ObjectMapper().readTree(jsonResponse).has(key);
    }

//    public static void assertJsonEquals(String expectedJson, String actualJson) {
//        JSONAssert.assertEquals(expectedJson, actualJson, false);
//    }

    public static String getJsonElementType(String jsonResponse, String elementPath) {
        return new JsonPath(jsonResponse).get(elementPath).getClass().getSimpleName();
    }

}
