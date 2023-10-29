package rest.cucumber.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Jsonutils {

    // Serialize a Java object to a JSON string
    public static String serializeObjectToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception as needed
            return null;
        }
    }

    // Serialize a Java object to a pretty JSON string
    public static String serializeObjectToPrettyJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception as needed
            return null;
        }
    }

    public static <T> T deserializeJsonResponseToObject(Response response, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.asString(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T deserializeJsonResponseToObject(ValidatableResponse response, Class<T> clazz) {
        try {
            return response.extract().as(clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map jsonStringToMap(String jsonString) {
        try {
            return new ObjectMapper().readValue(jsonString, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String mapToJson(Map<String, Object> map) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }

    public static List<Object> jsonArrayToList(String jsonArrayString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonArrayString, new TypeReference<List<Object>>() {});
    }

    public static String listToJsonArray(List<Object> list) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(list);
    }

    public static String prettyPrintJson(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(objectMapper.readTree(jsonString));
    }

    public static String mergeJson(String json1, String json2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node1 = objectMapper.readTree(json1);
        JsonNode node2 = objectMapper.readTree(json2);
        // Merge JSON objects, with values from the second JSON overwriting the first
        ((ObjectNode) node1).setAll((ObjectNode) node2);
        return objectMapper.writeValueAsString(node1);
    }

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

    public static String jsonToXml(String json) throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = jsonMapper.readTree(json);
        return xmlMapper.writeValueAsString(jsonNode);
    }

    public static Object getJsonValue(String jsonResponse, String jsonPath) {
        JsonPath jsonPathEvaluator = new JsonPath(jsonResponse);
        return jsonPathEvaluator.get(jsonPath);
    }

    public static List<Object> validateArraySize(String jsonResponse, String jsonArrayPath, int expectedSize) {
        JsonPath jsonPathEvaluator = new JsonPath(jsonResponse);
        return jsonPathEvaluator.getList(jsonArrayPath);
    }

    public static boolean isJsonKeyPresent(String jsonResponse, String key) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        return rootNode.has(key);
    }

//    public static void assertJsonEquals(String expectedJson, String actualJson) {
//        JSONAssert.assertEquals(expectedJson, actualJson, false);
//    }

    public static String getJsonElementType(String jsonResponse, String elementPath) {
        JsonPath jsonPathEvaluator = new JsonPath(jsonResponse);
        return jsonPathEvaluator.get(elementPath).getClass().getSimpleName();
    }

}
