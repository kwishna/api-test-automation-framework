package devils.dare.hooks;

//import io.cucumber.java.DataTableType;
//
//import java.util.HashMap;
//import java.util.Map;

public class TypeRegistries {

//    @DataTableType(replaceWithEmptyString = "")
//    public Map<String, String> injectSysProperty(Map<String, String> row) {
//
//        final Map<String, String> finalMap = new HashMap<>();
//
//        for (Map.Entry<String, String> entry : row.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            if (value.startsWith("${") && value.endsWith("}")) {
//                String cleanedValue = value.substring(2, value.length() - 1);
//                String systemPropertyValue = System.getProperty(cleanedValue);
//                if (systemPropertyValue != null) {
//                    entry.setValue(systemPropertyValue);
//                    finalMap.put(key, systemPropertyValue);
//                } else {
//                    System.out.println("NO PROPERTY FOUND TO INJECT FOR :: " + cleanedValue);
//                }
//            } else {
//                finalMap.put(key, value);
//            }
//        }
//        return finalMap;
//    }

//    @DataTableType(replaceWithEmptyString = "")
//    public Grocery defineGrocery(Map<String, String> entry) {
//        return new Grocery(entry.get("name"), Price.fromString(entry.get("price")));
//    }
//
//    @ParameterType(name = "amount", value = "\\d+\\.\\d+\\s[a-zA-Z]+")
//    public Amount defineAmount(String value) {
//        String[] arr = value.split("\\s");
//        return new Amount(new BigDecimal(arr[0]), Currency.getInstance(arr[1]));
//    }
//
//    @DocStringType(contentType = "shopping_list")
//    public List<Grocery> defineShoppingList(String docstring) {
//        return Stream.of(docstring.split("\\s")).map(Grocery::new).collect(Collectors.toList());
//    }
//
//    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
//    public LocalDate iso8601Date(String year, String month, String day) {
//        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
//    }
//
//    @ParameterType("red|blue|yellow")  // regexp
//    public Color color(String color) {  // type, name (from method)
//        return new Color(color);       // transformer function
//    }
}
