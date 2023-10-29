package rest.cucumber.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Utilities {

    public static String timeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_hh_mm_ss_SSS"));
    }

    public static String readFile(String filePath) {
        String data = "";
        try {
            data = java.nio.file.Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void saveDataToFile(String data) {
        try {
            Path path = Paths.get(System.getProperty("user.dir") + "/target/outputs");
            Files.createDirectories(path);
            Files.write(
                    Paths.get(path.toAbsolutePath() + "/Data_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".txt"),
                    data.getBytes(),
                    StandardOpenOption.CREATE_NEW
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDataToFile(String data, String fileNameWithFormat) {
        String[] s = fileNameWithFormat.split("[.]");
        String end = s[s.length - 1];
        StringBuilder fileName = new StringBuilder();

        for (int i = 0; i < s.length - 1; i++) {
            fileName.append(s[i]);
        }

        try {
            Path path = Paths.get(System.getProperty("user.dir") + "/target/outputs");
            Files.createDirectories(path);
            Files.write(
                    Paths.get(path.toAbsolutePath() + "/" + fileName + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".txt"),
                    data.getBytes(),
                    StandardOpenOption.CREATE_NEW
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDataIntoFile(String fileName, String data) throws IOException {

        Path path = Paths.get(System.getProperty("user.dir") + "/target/outputs");
        Files.createDirectories(path);

        if (!fileName.endsWith(".txt")) {
            fileName = fileName + ".txt";
        }
        FileWriter fwr = new FileWriter(path.toAbsolutePath() + "/" + fileName, true);
        fwr.write(data + "\n");
        fwr.flush();
        fwr.close();
    }

    public static String readFileAsString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }


}
