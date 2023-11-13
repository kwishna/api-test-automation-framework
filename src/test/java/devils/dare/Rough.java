package devils.dare;

import devils.dare.commons.utils.Replacer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rough {

    private static ThreadLocal<Map<String, String>> _map = ThreadLocal.withInitial(HashMap::new);

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


        Rough r1 = new Rough();
        Rough r2 = new Rough();

        r1._map.get().put("Hi", "Hi...");
        System.out.println(r2._map.get().get("Hi"));

    }
}
