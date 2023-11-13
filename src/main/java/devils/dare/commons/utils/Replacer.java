package devils.dare.commons.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Induces parameters inside a text.
 */
public class Replacer {

    /**
     * Getting Key From Matched Regex.
     *
     * @param matchedParam
     * @return
     */
//    private static String getKey(String matchedParam) {
//        Pattern find = Pattern.compile("\\w+");
//        Matcher matches = find.matcher(matchedParam);
//        if (matches.find()) {
//            return matches.group();
//        }
//        return "";
//    }
    private static String getKey(String matchedParam) {
        Pattern find = Pattern.compile("\\{([^}]*)\\}");
        Matcher matches = find.matcher(matchedParam);
        if (matches.find()) {
            return matches.group(1);
        }
        return "";
    }

    // ----------------------------------------------------------------

    /**
     * @param rawString
     * @param paramkey
     * @param paramValue
     * @return
     */
    public static String substituteParameters(String rawString, String paramkey, String paramValue) {
        Pattern pattern = Pattern.compile("\\$\\{" + paramkey + "}");
        Matcher matches = pattern.matcher(rawString);
        while (matches.find()) {
            rawString = pattern.matcher(rawString).replaceAll(paramValue);
        }
        return rawString;
    }

    /**
     * @param rawString
     * @param map
     * @return
     */
    public static String substituteParameters(String rawString, Map<String, String> map) {
        Pattern find = Pattern.compile("\\$\\{[^{}]*\\}");
//        Pattern find = Pattern.compile("\\$\\{.*?}");
        Matcher matches = find.matcher(rawString);
        while (matches.find()) {
            String matchedValue = matches.group().trim();
            String paramKey = getKey(matchedValue);
            String paramValue = map.get(paramKey.trim());
            Objects.requireNonNull(paramValue, "Parameter Key '" + paramKey + "' Not Found In Provided Map: " + map);
            rawString = substituteParameters(rawString, paramKey, paramValue);
        }
        return rawString;
    }


    /**
     * @param rawString
     * @param prop
     * @return
     */
    public static String substituteParameters(String rawString, Properties prop) {
        Pattern find = Pattern.compile("\\$\\{[^{}]*\\}");
        Matcher matches = find.matcher(rawString);
        while (matches.find()) {
            String matchedValue = matches.group().trim();
            String paramKey = getKey(matchedValue);
            String paramValue = prop.getProperty(paramKey.trim());
            Objects.requireNonNull(paramValue, "Parameter Key '" + paramKey + "' Not Found In Provided Properties: " + prop);
            rawString = substituteParameters(rawString, paramKey, paramValue);
        }
        return rawString;
    }

    // ----------------------------------------------------------------

    /**
     * @param rawString
     * @return
     */
    public static String substituteSystemParameters(String rawString) {
        return substituteParameters(rawString, System.getProperties());
    }

    /**
     * @param path
     * @return
     * @throws IOException
     */
    public static Path substituteSystemParameters(Path path) {
        String rawString = fileContentAsString(path);
        rawString = substituteSystemParameters(rawString);
        return saveToNewFile(path, rawString);
    }

    /**
     * @param path
     * @param map
     * @return
     * @throws IOException
     */
    public static Path substituteParameters(Path path, Map<String, String> map) {
        String rawString = fileContentAsString(path);
        rawString = substituteParameters(rawString, map);
        return saveToNewFile(path, rawString);
    }

    /**
     * @param path
     * @param prop
     * @return
     */
    public static Path substituteParameters(Path path, Properties prop) {
        String rawString = fileContentAsString(path);
        rawString = substituteParameters(rawString, prop);
        return saveToNewFile(path, rawString);

    }

    private static Path saveToNewFile(Path path, String rawString) {
        try {
            Path targetDirectory = Paths.get("target", "subs");
            Files.createDirectories(targetDirectory);
            Path targetPath = targetDirectory.resolve(path.getFileName());
            return Files.writeString(targetPath, rawString, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store substituted string in new file :" + e);
        }
    }

    private static String fileContentAsString(Path path) {
        try {
            return Files.readString(path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read the file '" + path.getFileName() + "' :" + e);
        }
    }
}
