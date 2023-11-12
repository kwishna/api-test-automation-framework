package rest.cucumber;

import rest.cucumber.utils.Replacer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rough {

//    public static void main(String[] args) {
//        String input = "This is a ${project.name} located at ${project.dir}.";
//        String pattern = "\\{([^}]*)\\}";
//
//        Pattern regex = Pattern.compile(pattern);
//        Matcher matcher = regex.matcher(input);
//
//        while (matcher.find()) {
//            System.out.println("Match: " + matcher.group(1));
//        }
//    }

    public static void main(String[] args) throws IOException {

        String X = """
                {
                    "data": {
                        "id": 2,
                        "email": "janet.weaver@reqres.in",
                        "first_name": "${sun.stdout.encoding}",
                        "last_name": "${ file.encoding }",
                        "avatar": "https://reqres.in/img/faces/2-image.jpg"
                    },
                    "support": {
                        "url": "https://reqres.in/#support-heading",
                        "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
                    }
                }
                """;

        System.out.println("--------------------------------");
//        System.out.println(substituteSystemParameters(X));
        System.out.println("--------------------------------");

        Path path = Paths.get("test.json");

        String rawString = Replacer.substituteSystemParameters(Files.readString(path));

        Path targetDirectory = Paths.get("target", "subs");
        Files.createDirectories(targetDirectory);

        Path targetPath = targetDirectory.resolve(path.getFileName());
        System.out.println(targetPath);

        Files.writeString(targetPath, rawString, StandardOpenOption.CREATE);
    }

    public static String substituteParameters(String rawString, Properties prop) {
        Pattern find = Pattern.compile("\\$\\{[^{}]*\\}");
        Matcher matches = find.matcher(rawString);
        while (matches.find()) {
            String matchedValue = matches.group().trim();
            String paramKey = getKey(matchedValue);
            System.out.println("PARAM KEY '" + paramKey + "'");
            String paramValue = prop.getProperty(paramKey.trim());
            Objects.requireNonNull(paramValue, "Parameter Key '" + paramKey + "' Not Found In Provided Properties: " + prop);
            rawString = substituteParameters(rawString, paramKey, paramValue);
        }
        return rawString;
    }

    public static String substituteSystemParameters(String rawString) {
        return substituteParameters(rawString, System.getProperties());
    }

    private static String getKey(String matchedParam) {
        System.out.println("Matched Param :: " + matchedParam);
        Pattern find = Pattern.compile("\\{([^}]*)\\}");
        Matcher matches = find.matcher(matchedParam);
        if (matches.find()) {
            String grp = matches.group(1);
            System.out.println("Get Key group :: " + grp);
            return grp;
        }
        return "";
    }

    public static String substituteParameters(String rawString, String paramkey, String paramValue) {
        Pattern pattern = Pattern.compile("\\$\\{" + paramkey + "}");
        Matcher matches = pattern.matcher(rawString);
        while (matches.find()) {
            rawString = pattern.matcher(rawString).replaceAll(paramValue);
        }
        return rawString;
    }
}
