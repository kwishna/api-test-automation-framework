package devils.dare.commons.utils;

import devils.dare.commons.config.Configurations;
import io.cucumber.datatable.DataTable;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public final class Utilities {

    private static final Configurations CONFIGS = Configurations.configuration();
    private static final Path OUTPUT_FOLDER = Paths.get(CONFIGS.userDir() + "/target/outputs");

    public static String getTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_hh_mm_ss_SSS"));
    }

    public static String readFile(String filePath) {
        String data = "";
        try {
            data = Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void saveDataToFile(String data) {
        try {
            Files.createDirectories(OUTPUT_FOLDER);
            Files.write(
                    Paths.get(OUTPUT_FOLDER.toAbsolutePath() + "/Data_" + getTimeStamp() + ".txt"),
                    data.getBytes(),
                    StandardOpenOption.CREATE
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDataToFile(String data, String fileNameWithFormat) {
        String[] s = fileNameWithFormat.split("[.]");
        StringBuilder fileName = new StringBuilder();

        for (int i = 0; i < s.length - 1; i++) {
            fileName.append(s[i]);
        }

        try {
            Files.createDirectories(OUTPUT_FOLDER);
            Files.write(
                    Paths.get(OUTPUT_FOLDER.toAbsolutePath() + "/" + fileName + getTimeStamp() + ".txt"),
                    data.getBytes(),
                    StandardOpenOption.CREATE
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDataIntoFile(String fileName, String data) throws IOException {
        Files.createDirectories(OUTPUT_FOLDER);

        if (!fileName.endsWith(".txt")) {
            fileName = fileName + ".txt";
        }
        FileWriter fwr = new FileWriter(OUTPUT_FOLDER.toAbsolutePath() + "/" + fileName, true);
        fwr.write(data + "\n");
        fwr.flush();
        fwr.close();
    }

    public static String readFileAsString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }


    public static Map<String, String> substituteSysProperty(Map<String, String> row) {

        final Map<String, String> finalMap = new HashMap<>();

        for (Map.Entry<String, String> entry : row.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // If the value contains "${VALUE}", then
            if (value.startsWith("${") && value.endsWith("}")) {

                String cleanedValue = value.substring(2, value.length() - 1).trim();
                String systemPropertyValue = System.getProperty(cleanedValue);

                if (systemPropertyValue != null) {
                    finalMap.put(key, systemPropertyValue);
                } else {
                    System.out.println("NO PROPERTY FOUND TO INJECT FOR :: " + cleanedValue);
                }
            }
//            else if (value.startsWith("$${") && value.endsWith("}")) {
//
//                String cleanedValue = value.substring(2, value.length() - 1).trim();
//                String systemPropertyValue = TestContext.getSyncVal(cleanedValue).toString();
//
//                if (systemPropertyValue != null) {
//                    finalMap.put(key, systemPropertyValue);
//                } else {
//                    System.out.println("NO PROPERTY FOUND TO INJECT FOR :: " + cleanedValue);
//                }
//            }
            else {
                finalMap.put(key, value);
            }
        }
        return finalMap;
    }

    public static Map<String, String> substituteSysProperty(DataTable table) {

        final Map<String, String> finalMap = new HashMap<>();

        for (Map.Entry<String, String> entry : table.asMap().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // If the value contains "${VALUE}", then
            if (value.startsWith("${") && value.endsWith("}")) {

                String cleanedValue = value.substring(2, value.length() - 1).trim();
                String systemPropertyValue = System.getProperty(cleanedValue);

                if (systemPropertyValue != null) {
                    finalMap.put(key, systemPropertyValue);
                } else {
                    System.out.println("NO PROPERTY FOUND TO INJECT FOR :: " + cleanedValue);
                }
            }
//            else if (value.startsWith("$${") && value.endsWith("}")) {
//
//                String cleanedValue = value.substring(2, value.length() - 1).trim();
//                String systemPropertyValue = TestContext.getSyncVal(cleanedValue).toString();
//
//                if (systemPropertyValue != null) {
//                    finalMap.put(key, systemPropertyValue);
//                } else {
//                    System.out.println("NO PROPERTY FOUND TO INJECT FOR :: " + cleanedValue);
//                }
//            }
            else {
                finalMap.put(key, value);
            }
        }
        return finalMap;
    }


}
