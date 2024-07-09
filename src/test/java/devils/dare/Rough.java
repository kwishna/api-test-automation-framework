package devils.dare;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;

import java.util.*;

/*
  Basic JSONPath Expressions:
    Root Object: $ represents the root object.
    Child Operator: . represents the child operator.
    Array Index: [n] represents the array index, where n is the index of the element.
    Wildcard: * represents the wildcard, matching any object at the current level.
    Recursive Descent: .. represents the recursive descent, allowing you to search through the entire JSON structure.
 */
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

    public static void main(String[] args) throws JsonProcessingException {

//        ExecutorService service = Executors.newFixedThreadPool(5);

        String res = """
                {
                    	"store":
                    	{
                    		"book": [
                                        {
                                            "category": "reference",
                                            "author": "Nigel Rees",
                                            "title": "Sayings of the Century",
                                            "price": 8.95
                                        },
                                        {
                                            "category": "fiction",
                                            "author": "Evelyn Waugh",
                                            "title": "Sword of Honour",
                                            "price": 12.99
                                        },
                                        {
                                            "category": "fiction",
                                            "author": "Herman Melville",
                                            "title": "Moby Dick",
                                            "isbn": "0-553-21311-3",
                                            "price": 8.99
                                        },
                                        {
                                            "category": "fiction",
                                            "author": "J. R. R. Tolkien",
                                            "title": "The Lord of the Rings",
                                            "isbn": "0-395-19395-8",
                                            "price": 22.99
                                        }
                    		        ],
                            "bicycle": {
                                    "color": "red",
                                    "price": 19.95
                                },
                            "nothing": 0
                    	},
                    	"name": "The Lord of the Rings"
                    }
                """;

//        List<String> keys = JsonPath.from(res).getList("$[0].keys()", String.class);
//        System.out.println("Keys: " + keys);

//        Set<Object> keys = JsonPath.from(res).getMap("[0]").keySet();
//        System.out.println("Keys: " + keys);

//        List<String> keys1 = JsonPath.from(res).get("[0].findAll { key, value -> true }.collect { it.key }");
//        System.out.println("Keys: " + keys1);

//        String X = JsonPath.from(res).get("[0]['access_code']");
//        System.out.println("access_code : " + X);

//        List<Float> allPrices = JsonPath.from(res).get("store.book.price");
//        System.out.println(allPrices.stream().reduce(Float::sum).orElseThrow());

//        Set<Object> actualSet = Set.of("A", "B", "C", "D");
//        String[] expected = new String[]{"B", "A", "D", "C"};
//        assertThat(actualSet, hasItems(expected));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(res);

        Map<Object, Object> map = JsonPath.from(res).getMap("$");
        System.out.println(getAllKeysFromJson(jsonNode));
    }

    public static Set<Object> getAllKeysFromJson(JsonNode jsonNode) {
        Set<Object> keys = new HashSet<>();

        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            keys.add(entry.getKey());

            if (entry.getValue().isObject()) {
                keys.addAll(getAllKeysFromJson(entry.getValue()));
            }
        }

        return keys;
    }

    public static Set<Object> getAllKeysFromJson(Map<Object, Object> map) {
        Set<Object> keys = new HashSet<>();

        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            keys.add(entry.getKey());

            if (entry.getValue() instanceof Map) {
                // Recursive call for inner map
                keys.addAll(getAllKeysFromJson((Map<Object, Object>) entry.getValue()));
            } else if (entry.getValue() instanceof List) {
                // Recursive call for each element in the list
                for (Object listItem : (List<?>) entry.getValue()) {
                    if (listItem instanceof Map) {
                        keys.addAll(getAllKeysFromJson((Map<Object, Object>) listItem));
                    } else {
                        // Handle other types in the list
                        keys.add(listItem);
                    }
                }
            } else {
                // Handle other types directly
                keys.add(entry.getKey());
            }
        }

        return keys;
    }
}
