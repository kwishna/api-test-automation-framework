package rest.cucumber.utils.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseUtils {

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

    public static XmlPath responseToXML(Response res) {
        String response = res.asString();
        return new XmlPath(response);

    }

    public static JsonPath responseToJson(Response res) {
        // Or, response.extract().response().jsonPath();
        String response = res.asString();
        return new JsonPath(response);
    }

    public static XmlPath responseToXML(ValidatableResponse res) {
        String response = res.extract().response().asString();
        return new XmlPath(response);

    }

    public static JsonPath responseToJson(ValidatableResponse res) {
        // Or, response.extract().response().jsonPath();
        String response = res.extract().response().asString();
        return new JsonPath(response);
    }

    public static void saveResponseIntoFile(ValidatableResponse response) {
        ResponseUtils.saveResponseIntoFile(response, "Response_");
    }

    public static void saveResponseIntoFile(ValidatableResponse response, String fileName) {

        if (response == null) {
            return;
        }

        String contentType = response.extract().response().contentType();
        Path path = Paths.get(System.getProperty("user.dir") + "/target/outputs");

        // If the Response body is JSON type.
        if (contentType.contains("json")) {
            try {
                Files.createDirectories(path);
                Files.write(
                        Paths.get(path.toAbsolutePath() + "/" + fileName + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".json"),
                        response.extract().response().asByteArray(),
                        StandardOpenOption.CREATE_NEW
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // If the Response body is XML type.
        if (contentType.contains("xml")) {
            try {
                Files.createDirectories(path);
                Files.write(
                        Paths.get(path.toAbsolutePath() + "/" + fileName + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".xml"),
                        response.extract().response().asByteArray(),
                        StandardOpenOption.CREATE_NEW
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        // If the Response body is neither JSON nor XML type.
        try {
            Files.createDirectories(path);
            Files.write(
                    Paths.get(path.toAbsolutePath() + "/" + fileName + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".txt"),
                    response.extract().response().asByteArray(),
                    StandardOpenOption.CREATE_NEW
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public static JsonSchemaValidator getJsonSchemaValidator(Response res, String schemaFilePath) {
        return JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaFilePath);
    }

    public static String prettyPrintJson(Response res) {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(
                        JsonParser.parseString(res.asString())
                );
    }
}
