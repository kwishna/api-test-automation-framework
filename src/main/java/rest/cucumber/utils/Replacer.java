package rest.cucumber.utils;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replacer {

    /**
     * Getting Key From Matched Regex.
     *
     * @param matchedParam
     * @return
     */
    private static String getKey(String matchedParam) {
        Pattern find = Pattern.compile("\\w+");
        Matcher matches = find.matcher(matchedParam);
        if (matches.find()) {
            return matches.group();
        }
        return "";
    }

    /**
     * @param rawString
     * @param paramkey
     * @param paramValue
     * @return
     */
    public static String induceParameters(String rawString, String paramkey, String paramValue) {
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
    public static String induceParameters(String rawString, Map<String, String> map) {
        Pattern find = Pattern.compile("\\$\\{.*?}");
        Matcher matches = find.matcher(rawString);
        while (matches.find()) {
            String matchedValue = matches.group().trim();
            String paramKey = getKey(matchedValue);
            String paramValue = map.get(paramKey);
            Objects.requireNonNull(paramValue, "Parameter Key '" + paramValue + "' Not Found In Provided Map: " + map);
            rawString = induceParameters(rawString, paramKey, paramValue);
        }
        return rawString;
    }

    /**
     * @param rawString
     * @param prop
     * @return
     */
    public static String induceParameters(String rawString, Properties prop) {
        Pattern find = Pattern.compile("\\$\\{.*?}");
        Matcher matches = find.matcher(rawString);
        while (matches.find()) {
            String matchedValue = matches.group().trim();
            String paramKey = getKey(matchedValue);
            String paramValue = prop.getProperty(paramKey);
            Objects.requireNonNull(paramValue, "Parameter Key '" + paramValue + "' Not Found In Provided Properties: " + prop);
            rawString = induceParameters(rawString, paramKey, paramValue);
        }
        return rawString;
    }


}
