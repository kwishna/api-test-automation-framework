package devils.dare.rough_files;

import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static devils.dare.rough_files.RestAssuredOneJson.JPaths.queryPath;
import static io.restassured.RestAssured.when;
import static net.serenitybdd.rest.RestRequests.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/*
    1. Collection Operations: ----------------------------------------------------------------
    def prices = jsonPath.get("store.book*.price") // Extract all book prices
    def totalPrice = prices.sum() // Calculate the sum of prices
    def averagePrice = prices.sum() / prices.size() // Calculate the average price

    2. Closure and Filtering: -----------------------------------------------------------------
    def expensiveBooks = jsonPath.get("store.book.findAll { it.price > 15 }")

    3. Transformation: ------------------------------------------------------------------------
    def bookTitles = jsonPath.get("store.book.title").collect { it.toUpperCase() }

    4. Dynamic Features: -----------------------------------------------------------------------
    def title = jsonPath.get("store.book[0].title")
    def price = jsonPath.get("store.book[0].price")

    5. String Interpolation: --------------------------------------------------------------------
    def bookIndex = 0
    def title = jsonPath.get("store.book[${bookIndex}].title")

    6. Integration with Java: -------------------------------------------------------------------

    def json = """
    {
      "store": {
        "book": [
          {
            "title": "Book 1",
            "price": 10.99
          },
          {
            "title": "Book 2",
            "price": 19.99
          }
        ],
        "bicycle": {
          "color": "red",
          "price": 129.99
        }
      }
    }
    """

    def jsonPath = JsonPath.from(json)

    def expensiveBooks = jsonPath.get("store.book.findAll { it.price > 15 }")
    def totalPrice = jsonPath.get("store.book*.price.sum()")

    println "Expensive Books: $expensiveBooks"
    println "Total Price: $totalPrice"

    1. Safe Navigation Operator (?.):
    def title = jsonPath.get("store.book[5]?.title")

    2. Dynamic Method Invocation:
    def method = "toUpperCase"
    def title = jsonPath.get("store.book[0].title").invokeMethod(method)

    3. JsonSlurper:
    def jsonSlurper = new groovy.json.JsonSlurper()
    def jsonData = jsonSlurper.parseText(json)
    def bookTitles = jsonData.store.book.collect { it.title }

    4. Destructuring Assignment:
    def (firstTitle, secondTitle) = jsonPath.get("store.book*.title")

    5. Spread Operator (*.):
    def allTitles = jsonPath.get("store.book*.title.flatten()")

    6. JsonBuilder:
    def jsonBuilder = new groovy.json.JsonBuilder()

    def book = [
        title: "New Book",
        price: 29.99
    ]

    def jsonString = jsonBuilder {
        store {
            book book
        }
    }

    println jsonString

    7. Metaprogramming:
    String.metaClass.reverse = { -> delegate.reverse() }
    def reversedTitle = jsonPath.get("store.book[0].title".reverse())

// ----------------------------------------------------------------------------------------
    XPath	JSONPath	    Description
    /	    $	            the root object/element
    .	    @	            the current object/element
    /	    . or []	        child operator
    ..	    n/a	            parent operator
    //	    ..	            recursive descent. JSONPath borrows this syntax from E4X.
    *	    * wildcard.     All objects/elements regardless their names.
    @	    n/a	attribute access. JSON structures don't have attributes.
    []	    []	subscript operator. XPath uses it to iterate over element collections and for predicates. In Javascript and JSON it is the native array operator.
    |	    [,]	Union operator in XPath results in a combination of node sets. JSONPath allows alternate names or array indices as a set.
    n/a	    [start:end:step]	array slice operator borrowed from ES4.
    []	    ?()	            applies a filter (script) expression.
    n/a	    ()	            script expression, using the underlying script engine.
    ()	    n/a	            grouping in Xpath

// ----------------------------------------------------------------------------------------

    Function	    Description	                                        Output type
    min()	        Provides the min value of an array of numbers	    Double
    max()	        Provides the max value of an array of numbers	    Double
    avg()	        Provides the average value of an array of numbers	Double
    stddev()	    Provides the standard deviation value of an array of numbers	Double
    length()	    Provides the length of an array	Integer
    sum()	        Provides the sum value of an array of numbers	Double
    keys()	        Provides the property keys (An alternative for terminal tilde ~)	Set<E>
    concat(X)	    Provides a concatinated version of the path output with a new item	like input
    append(X)	    add an item to the json path output array	like input
    first()	        Provides the first item of an array	Depends on the array
    last()	        Provides the last item of an array	Depends on the array
    index(X)	Provides the item of an array of index: X, if the X is negative, take from backwards	Depends on the array

// ----------------------------------------------------------------------------------------
 */
public class JsonPathTest {

    private static final String LOTTO = """
            {
                "lotto":{
                    "lottoId":5,
                    "winning-numbers":[2, 45, 34, 23, 7, 5, 3],
                    "winners":[
                        {
                              "winnerId":23,
                              "numbers":[2, 45, 34, 23, 3, 5]
                        },{
                              "winnerId":54,
                              "numbers":[52, 3, 12, 11, 18, 22]
                        }
                    ]
                }
            }
            """;
    private final String JSON = """
            { "store": {
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
                      "price": 12
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
                "bicycle":
                    {
                        "color": "red",
                        "price": 19.95,
                        "atoms": %d                    }
                  }
            }""".formatted(Long.MAX_VALUE);
    private final String JSON2 = """
            [
                {
                    "email" : "name1@mail.com",
                    "alias" : "name one",
                    "phone" : "3456789"
                },
                {
                    "email" : "name2@mail.com",
                    "alias" : "name two",
                    "phone" : "1234567"
                },
                {
                    "email" : "name3@mail.com",
                    "alias" : "name three",
                    "phone" : "2345678"
                }
            ]
            """;
    private final String JSON_MAP = """
            {
                "price1" : 12.3,
                "price2": 15.0
            }
            """;
    private final String JSON_PATH_STARTING_WITH_NUMBER = """
            {
                "0" : 12.3,
                "1": 15.0
            }
            """;
    private final String JSON_PATH_WITH_BOOLEAN = """
            { "map" :
                {
                    "true" : 12.3,
                    "false": 15.0
                }
            }
            """;

    @Test
    public void getList() {
        final List<String> categories = new JsonPath(JSON).get("store.book.category");
        assertThat(categories.size(), equalTo(4));
        assertThat(categories, hasItems("reference", "fiction"));
    }

    @Test
    public void firstBookCategory() {
        final String category = JsonPath.with(JSON).get("store.book[0].category");
        assertThat(category, equalTo("reference"));
    }

    @Test
    public void lastBookTitle() {
        final String title = JsonPath.with(JSON).get("store.book[-1].title");
        assertThat(title, equalTo("The Lord of the Rings"));
    }

    @Test
    public void booksWithArgAuthor() {
        String author = "Herman Melville";
        final List<Map<String, ?>> books = JsonPath.with(JSON).param("author", author).get("store.book.findAll { book -> book.author == author }");
        assertThat(books.size(), equalTo(1));

        final String authorActual = (String) books.get(0).get("author");
        assertThat(authorActual, equalTo(author));
    }

    @Test
    public void booksBetween5And15() {
        final List<Map<String, ?>> books = JsonPath.with(JSON).get("store.book.findAll { book -> book.price >= 5 && book.price <= 15 }");
        assertThat(books.size(), equalTo(3));

        final String author = (String) books.get(0).get("author");
        assertThat(author, equalTo("Nigel Rees"));

        final int price = (Integer) books.get(1).get("price");
        assertThat(price, equalTo(12));
    }

    @Test
    public void sizeInPath() {
        final Integer size = JsonPath.with(JSON).get("store.book.size()");
        assertThat(size, equalTo(4));
    }

    @Test
    public void getRootObjectAsMap() {
        final Map<String, Map<String, Object>> store = JsonPath.given(JSON).get("store");
        assertThat(store.size(), equalTo(2));

        final Map<String, Object> bicycle = store.get("bicycle");
        final String color = (String) bicycle.get("color");
        final float price = (Float) bicycle.get("price");
        assertThat(color, equalTo("red"));
        assertThat(price, equalTo(19.95f));
    }

    @Test
    public void getFloatAndDoublesAsBigDecimal() {
        final JsonPath using = JsonPath.with(JSON).using(new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
        assertThat(using.<Map<String, Map<String, Object>>>get("store").size(), equalTo(2));

        final Map<String, Object> bicycle = using.<Map<String, Map<String, Object>>>get("store").get("bicycle");
        final String color = (String) bicycle.get("color");
        final BigDecimal price = (BigDecimal) bicycle.get("price");
        assertThat(color, equalTo("red"));
        assertThat(price, equalTo(new BigDecimal("19.95")));
    }

    @Test
    public void getFloatAndDoublesAsBigDecimalUsingStaticConfiguration() {
        JsonPath.config = new JsonPathConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
        try {
            final Map<String, Map<String, Object>> store = JsonPath.with(JSON).get("store");
            assertThat(store.size(), equalTo(2));

            final Map<String, Object> bicycle = store.get("bicycle");
            final String color = (String) bicycle.get("color");
            final BigDecimal price = (BigDecimal) bicycle.get("price");
            assertThat(color, equalTo("red"));
            assertThat(price, equalTo(new BigDecimal("19.95")));
        } finally {
            JsonPath.config = null;
        }
    }

    @Test
    public void nonStaticJsonPathConfigHasPrecedenceOverStaticConfiguration() {
        JsonPath.config = new JsonPathConfig().numberReturnType(JsonPathConfig.NumberReturnType.FLOAT_AND_DOUBLE);
        try {
            final Map<String, Map<String, Object>> store = JsonPath.with(JSON).using(new JsonPathConfig(JsonPathConfig.NumberReturnType.BIG_DECIMAL)).get("store");
            assertThat(store.size(), equalTo(2));

            final Map<String, Object> bicycle = store.get("bicycle");
            final String color = (String) bicycle.get("color");
            final BigDecimal price = (BigDecimal) bicycle.get("price");
            assertThat(color, equalTo("red"));
            assertThat(price, equalTo(new BigDecimal("19.95")));
        } finally {
            JsonPath.config = null;
        }
    }

    @Test
    public void getRootObjectAsMap2() {
        final Map<String, Object> store = JsonPath.from(JSON).get("store.book[0]");
        for (Map.Entry<String, Object> stringObjectEntry : store.entrySet()) {
            System.out.println(stringObjectEntry.getKey() + stringObjectEntry.getValue());
        }
    }

    @Test
    public void rootPath() {
        final JsonPath jsonPath = new JsonPath(JSON).setRootPath("store.book");
        assertThat(jsonPath.getInt("size()"), equalTo(4));
        assertThat(jsonPath.getList("author", String.class), hasItem("J. R. R. Tolkien"));
    }

    @Test
    public void rootPathFollowedByArrayIndexing() {
        final JsonPath jsonPath = new JsonPath(JSON).setRootPath("store.book");
        assertThat(jsonPath.getString("[0].author"), equalTo("Nigel Rees"));
    }

    @Test
    public void supportsGettingEntireObjectGraphUsingEmptyString() {
        final List<Map<String, String>> object = JsonPath.from(JSON2).get("");
        assertThat(object.get(0).get("email"), equalTo("name1@mail.com"));
    }

    @Test
    public void supportsGettingEntireObjectGraphUsing$() {
        final List<Map<String, String>> object = JsonPath.from(JSON2).get("$");
        assertThat(object.get(0).get("email"), equalTo("name1@mail.com"));
    }

    @Test
    public void supportsGettingEntireObjectGraphUsingNoArgumentGet() {
        final List<Map<String, String>> object = JsonPath.from(JSON2).get();
        assertThat(object.get(0).get("email"), equalTo("name1@mail.com"));
    }

    @Test
    public void getValueFromUnnamedRootObject() {
        final Map<String, String> object = JsonPath.from(JSON2).get("get(0)");
        assertThat(object.get("email"), equalTo("name1@mail.com"));
    }

    @Test
    public void getValueFromUnnamedRootObjectUsingBrackets() {
        final Map<String, String> object = JsonPath.from(JSON2).get("[0]");
        assertThat(object.get("email"), equalTo("name1@mail.com"));
    }

    @Test
    public void getSubValueFromUnnamedRootObjectUsingBrackets() {
        final String object = JsonPath.from(JSON2).getString("[0].email");
        assertThat(object, equalTo("name1@mail.com"));
    }

    @Test
    public void getNumericalValues() {
        assertThat(JsonPath.with(JSON).getDouble("store.book[0].price"), equalTo(8.95D));
        assertThat(JsonPath.with(JSON).getFloat("store.book[0].price"), equalTo(8.95F));

        // The price is stored as an integer
        assertThat(JsonPath.with(JSON).getByte("store.book[1].price"), equalTo((byte) 12));
        assertThat(JsonPath.with(JSON).getShort("store.book[1].price"), equalTo((short) 12));
        assertThat(JsonPath.with(JSON).getInt("store.book[1].price"), equalTo(12));
        assertThat(JsonPath.with(JSON).getLong("store.book[1].price"), equalTo(12L));

        // The atoms are stored as a long
        assertThat(JsonPath.with(JSON).getByte("store.bicycle.atoms"), equalTo((byte) Long.MAX_VALUE));
        assertThat(JsonPath.with(JSON).getShort("store.bicycle.atoms"), equalTo((short) Long.MAX_VALUE));
        assertThat(JsonPath.with(JSON).getInt("store.bicycle.atoms"), equalTo((int) Long.MAX_VALUE));
        assertThat(JsonPath.with(JSON).getLong("store.bicycle.atoms"), equalTo(Long.MAX_VALUE));
    }

    @Test
    public void convertsValueToStringWhenExplicitlyRequested() {
        String phoneNumber = JsonPath.from(JSON2).getString("phone[0]");
        assertThat(phoneNumber, equalTo("3456789"));
    }

    @Test
    public void convertsValueToIntWhenExplicitlyRequested() {
        int phoneNumber = JsonPath.from(JSON2).getInt("phone[0]");
        assertThat(phoneNumber, equalTo(3456789));
    }

    @Test
    public void convertsValueToDoubleWhenExplicitlyRequested() {
        double phoneNumber = JsonPath.from(JSON2).getDouble("phone[0]");
        assertThat(phoneNumber, equalTo(3456789d));
    }

    @Test
    public void convertsValueToFloatWhenExplicitlyRequested() {
        float phoneNumber = JsonPath.from(JSON2).getFloat("phone[0]");
        assertThat(phoneNumber, equalTo(3456789f));
    }

    @Test
    public void convertsValueToUUIDWhenExplicitlyRequested() {
        String JSON3 = "{\"id\":\"db24eeeb-7fe5-41d3-8f06-986b793ecc91\"}";
        UUID uuid = JsonPath.from(JSON3).getUUID("id");
        assertThat(uuid, equalTo(UUID.fromString("db24eeeb-7fe5-41d3-8f06-986b793ecc91")));
    }

    @Test
    public void convertsListMembersToDefinedTypeIfPossible() {
        final List<Integer> phoneNumbers = JsonPath.with(JSON2).getList("phone", int.class);
        assertThat(phoneNumbers, hasItems(3456789, 1234567, 2345678));
    }

    @Test
    public void getMapWithGenericType() {
        final Map<String, String> map = JsonPath.with(JSON_MAP).getMap("$", String.class, String.class);
        assertThat(map, allOf(hasEntry("price1", "12.3"), hasEntry("price2", "15.0")));
    }

    @Test
    public void getMapWithAnotherGenericType() {
        final Map<String, Float> map = JsonPath.with(JSON_MAP).getMap("$", String.class, float.class);
        assertThat(map, allOf(hasEntry("price1", 12.3f), hasEntry("price2", 15.0f)));
    }

    @Test
    public void getStringConvertsTheResultToAString() {
        final String priceAsString = JsonPath.with(JSON).getString("store.book.price[0]");
        assertThat(priceAsString, is("8.95"));
    }

    //    @Test(expectedExceptions = JsonPathException.class)
    @Test
    public void malformedJson() {
        String MALFORMED_JSON = """
                {
                    "a": 123456
                    "b":"string"
                }""";
        JsonPath.from(MALFORMED_JSON).get("a");
    }

    @Test
    public void getObjectWorksWhenPathPointsToAJsonObject() {
        final Book book = JsonPath.from(JSON).getObject("store.book[2]", Book.class);

        assertThat(book, equalTo(new Book("fiction", "Herman Melville", "Moby Dick", "0-553-21311-3", 8.99f)));
    }

    @Test
    public void getObjectWorksWhenPathPointsToATypeRefMap() {
        final Map<String, Object> book = JsonPath.from(JSON).getObject("store.book[2]", new TypeRef<>() {
        });
        assertThat(book.get("category"), Matchers.equalTo("fiction"));
        assertThat(book.get("author"), Matchers.equalTo("Herman Melville"));
        assertThat(book.get("price"), Matchers.equalTo(8.99));
    }

    @Test
    public void getObjectWorksWhenPathPointsToATypeRefList() {
        final List<Float> prices = JsonPath.from(JSON).getObject("store.book.price", new TypeRef<>() {
        });
        assertThat(prices, containsInAnyOrder(8.95, 12, 8.99, 22.99));
    }

    @Test
    public void getObjectWorksWhenPathPointsToAJsonObject2() {
        final List<Book> books = JsonPath.from(JSON).getList("store.book", Book.class);
        assertThat(books, hasSize(4));
        assertThat(books.get(0).getAuthor(), equalTo("Nigel Rees"));
    }

    @Test
    public void getObjectAsMapWorksWhenPathPointsToAJsonObject() {
        final Map<String, String> book = JsonPath.from(JSON).getObject("store.book[2]", Map.class);
        assertThat(book, hasEntry("category", "fiction"));
        assertThat(book, hasEntry("author", "Herman Melville"));
    }

    @Test
    public void getObjectWorksWhenPathPointsToAList() {
        final List<String> categories = JsonPath.from(JSON).getObject("store.book.category", List.class);
        assertThat(categories, hasItems("reference", "fiction"));
    }

    @Test
    public void getObjectAsFloatWorksWhenPathPointsToAFloat() {
        final Float price = JsonPath.from(JSON).getObject("store.book.price[0]", Float.class);
        assertThat(price, equalTo(8.95f));
    }

    @Test
    public void getObjectAsStringWorksWhenPathPointsToAString() {
        final String category = JsonPath.from(JSON).getObject("store.book.category[0]", String.class);
        assertThat(category, equalTo("reference"));
    }

    @Test
    public void jsonPathSupportsPrettyPeekingJson() {
        final String phone = JsonPath.with(JSON2).prettyPeek().getString("phone[0]");
        assertThat(phone, equalTo("3456789"));
    }

    @Test
    public void jsonPathSupportsPeekingAtTheJson() {
        final String phone = JsonPath.with(JSON2).peek().getString("phone[0]");
        assertThat(phone, equalTo("3456789"));
    }

    @Test
    public void canParseJsonDocumentWhenFirstKeyIsIntegerUsingManualEscaping() {
        final float number = JsonPath.from(JSON_PATH_STARTING_WITH_NUMBER).getFloat("'0'");
        assertThat(number, equalTo(12.3f));
    }

    @Test
    public void canParseJsonDocumentWhenFirstKeyThatIsAIntegerUsingNoEscaping() {
        final float number = JsonPath.from(JSON_PATH_STARTING_WITH_NUMBER).getFloat("0");
        assertThat(number, equalTo(12.3f));
    }

    @Test
    public void canParseJsonDocumentWhenPathIncludesKeyThatIsAIntegerUsingNoEscaping() {
        String JSON_PATH_WITH_NUMBER = """
                {
                    "map" :
                        {
                            "0" : 12.3,
                            "1": 15.0
                        }
                }
                """;
        final float number = JsonPath.from(JSON_PATH_WITH_NUMBER).getFloat("map.0");
        assertThat(number, equalTo(12.3f));
    }

    @Test
    public void canParseJsonDocumentWhenPathIncludesKeyThatIsABooleanUsingEscaping() {
        final float number = JsonPath.from(JSON_PATH_WITH_BOOLEAN).getFloat("map.'false'");
        assertThat(number, equalTo(15.0f));
    }

    @Test
    public void canParseJsonDocumentWhenPathIncludesKeyThatIsABooleanUsingNoEscaping() {
        final float number = JsonPath.from(JSON_PATH_WITH_BOOLEAN).getFloat("map.true");
        assertThat(number, equalTo(12.3f));
    }

    @Test
    public void canParseJsonDocumentWhenPathIncludesMinusInsideEscaped() {
        JsonPath path = new JsonPath("""
                {
                    "a-b" : "minus",
                    "a.b" : "dot",
                    "a.b-c" : "both"
                }
                """);

        assertThat(path.getString("'a.b-c'"), equalTo("both"));
    }

    @Test
    public void canParseJsonDocumentWithMultipleConsecutiveIntegersInsidePath() {
        String json = """
                {
                    "foo.bar.baz":
                        {
                            "0.2.0": "test"
                        }
                }""";

        final String string = JsonPath.from(json).getString("'foo.bar.baz'.'0.2.0'");

        assertThat(string, equalTo("test"));
    }

    @Test
    public void can_parse_multiple_values() {
        final JsonPath jsonPath = new JsonPath(JSON);

        final String category1 = jsonPath.getString("store.book.category[0]");
        final String category2 = jsonPath.getString("store.book.category[1]");

        assertThat(category1, equalTo("reference"));
        assertThat(category2, equalTo("fiction"));
    }

    @Test
    public void pretty_printing_works() {
        String json = """
                {
                    "data":
                        [
                            {
                                 "uid": 10,
                                 "name": "abc"
                            }
                       ]
                }""";
        final JsonPath jsonPath = new JsonPath(json);

        final String string = jsonPath.prettyPrint();
        assertThat(string, equalTo("""
                {
                    "data": [
                        {
                            "uid": 10,
                            "name": "abc"
                        }
                    ]
                }"""));
    }

    @Test
    public void parses_json_document_with_attribute_name_equal_to_properties() {
        final String jsonWithPropertyAttribute = """
                [
                    {
                        "properties": "test"
                    }
                ]
                """;
        final String value = new JsonPath(jsonWithPropertyAttribute).getString("[0].properties");
        assertThat(value, equalTo("test"));
    }

    @Test
    public void parses_json_document_with_attribute_name_equal_to_size() {
        String JSON_PATH_WITH_SIZE = """
                { "map" :
                    {
                        "size" : 12.3,
                        "1": 15.0
                    }
                }
                """;
        final float anInt = new JsonPath(JSON_PATH_WITH_SIZE).getFloat("map.size");
        assertThat(anInt, is(12.3f));
    }

    @Test
    public void can_find_if_a_key_exists_in_json_object() {
        String json = """
                {
                "status": "success",
                "fund_code":"00200",
                "fund_name":"My Fund Name",
                "models": [
                  {
                    "model_id": 639506,
                    "model_name": "New Validated Model",
                    "model_type": null,
                    "portfolios": null,
                    "created_date": 1390978800000,
                    "display_create_date": "01/29/2014",
                    "updated_date": 1392274800000,
                    "display_update_date": "02/13/2014",
                    "number_of_clients": 1,
                    "risk": "N/A",
                    "time_frame": "N/A"
                  },
                  {
                    "model_id": 639507,
                    "model_name": "My Validated Model",
                    "model_type": null,
                    "portfolios": null,
                    "created_date": 1390978800000,
                    "display_create_date": "01/29/2014",
                    "updated_date": 1392274800000,
                    "display_update_date": "02/13/2014",
                    "number_of_clients": 1,
                    "risk": "N/A",
                    "time_frame": "N/A"
                  }
                ]
                }""";
        JsonPath jsonPath = new JsonPath(json);
        assertThat(jsonPath.getBoolean("any { it.key == 'fund_code' }"), is(true));
        assertThat(jsonPath.getBoolean("models.any { it.containsKey('number_of_clients') }"), is(true));
    }

    @Test
    public void can_parse_json_attributes_starting_with_a_number() {
        String json = """
                {
                   "6269f15a0bb9b1b7d86ae718e84cddcd" : {
                            "attr1":"val1",
                            "attr2":"val2",
                            "attrx":"valx"
                   }
                }""";
        JsonPath jsonPath = new JsonPath(json);
        assertThat(jsonPath.getString("6269f15a0bb9b1b7d86ae718e84cddcd.attr1"), equalTo("val1"));
    }

    @Test
    public void automatically_escapes_json_attributes_whose_name_equals_properties() {
        String json = """
                {
                   "features":[
                      {
                         "type":"Feature",
                         "geometry":{
                            "type":"GeometryCollection",
                            "geometries":[
                               {
                                  "type":"Point",
                                  "coordinates":[
                                     19.883992823270653,
                                     50.02026203045478
                                  ]
                               }
                            ]
                         },
                         "properties":{
                            "gridId":6
                         }
                      },
                      {
                         "type":"Feature",
                         "geometry":{
                            "type":"GeometryCollection",
                            "geometries":[
                               {
                                  "type":"Point",
                                  "coordinates":[
                                     19.901266347582094,
                                     50.07074684071764
                                  ]
                               }
                            ]
                         },
                         "properties":{
                            "gridId":7
                         }
                      }
                   ]
                }""";
        JsonPath jsonPath = new JsonPath(json);
        assertThat(jsonPath.getList("features.properties.gridId", Integer.class), hasItems(7));
    }

    @Test
    public void can_manually_escape_class_property() {
        String json = """
                {
                  "semester": "Fall 2015",
                  "groups": [
                    {
                      "siteUrl": "http://cphbusinessjb.cloudapp.net/CA2/",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca2-ebski.rhcloud.com/CA2New/",
                      "authors": "Ebbe, Kasper, Christoffer",
                      "class": "A klassen",
                      "group": "Gruppe: Johns Llama Herders A/S"
                    },
                    {
                      "siteUrl": "http://ca2-chrislind.rhcloud.com/CA2Final/",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca2-pernille.rhcloud.com/NYCA2/",
                      "authors": "Marta, Jeanette, Pernille",
                      "class": "DAT A",
                      "group": "Group: MJP"
                    },
                    {
                      "siteUrl": "https://ca2-afn.rhcloud.com:8443/company.jsp",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca-smcphbusiness.rhcloud.com/ca2/index.jsp",
                      "authors": "Mikkel, Steffen, B Andersen",
                      "class": "A Class Computer Science",
                      "group": "1"
                    }
                  ]
                }""";
        JsonPath jsonPath = new JsonPath(json);
        assertThat(jsonPath.getList("groups.getAt('class')", String.class), hasItems("A klassen"));
    }

    @Test
    public void automatically_escapes_class_property() {
        String json = """
                {
                  "semester": "Fall 2015",
                  "groups": [
                    {
                      "siteUrl": "http://cphbusinessjb.cloudapp.net/CA2/",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca2-ebski.rhcloud.com/CA2New/",
                      "authors": "Ebbe, Kasper, Christoffer",
                      "class": "A klassen",
                      "group": "Gruppe: Johns Llama Herders A/S"
                    },
                    {
                      "siteUrl": "http://ca2-chrislind.rhcloud.com/CA2Final/",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca2-pernille.rhcloud.com/NYCA2/",
                      "authors": "Marta, Jeanette, Pernille",
                      "class": "DAT A",
                      "group": "Group: MJP"
                    },
                    {
                      "siteUrl": "https://ca2-afn.rhcloud.com:8443/company.jsp",
                      "error": "NO AUTHOR/CLASS-INFO"
                    },
                    {
                      "siteUrl": "http://ca-smcphbusiness.rhcloud.com/ca2/index.jsp",
                      "authors": "Mikkel, Steffen, B Andersen",
                      "class": "A Class Computer Science",
                      "group": "1"
                    }
                  ]
                }""";
        JsonPath jsonPath = new JsonPath(json);
        assertThat(jsonPath.getList("groups.class", String.class), hasItems("A klassen"));
    }

    @Test
    public void need_to_escape_lists_with_hyphen_and_brackets() {
        String json = """
                {
                    "some-list[0]" : [ "one", "two" ]
                 }
                """;
        JsonPath jsonPath = JsonPath.from(json);
        assertThat(jsonPath.getString("'some-list[0]'[0]"), equalTo("one"));
    }

    @Test
    public void doesnt_need_to_escape_lists_with_hyphen_without_brackets() {
        String json = """
                { "some-list" : [ "one", "two" ] }
                """;
        JsonPath jsonPath = JsonPath.from(json);
        assertThat(jsonPath.getString("some-list[0]"), equalTo("one"));
    }

    @Test
    public void does_not_fail_on_absent_lists() {
        String json = """
                {
                    "root" : { }
                }
                """;
        JsonPath jsonPath = JsonPath.from(json);
        assertThat(jsonPath.getString("root.items[0]"), is(nullValue()));
    }

    @Test
    public void groceriesContainsChocolateAndCoffee() {
        expect().
                body("shopping.category.find { it.@type == 'groceries' }", hasItems("Chocolate", "Coffee")).
                when().
                get("/shopping");
    }

    @Test
    public void groceriesContainsChocolateAndCoffeeUsingDoubleStarNotation() {
        expect().
                body("**.find { it.@type == 'groceries' }", hasItems("Chocolate", "Coffee")).
                when().
                get("/shopping");
    }

    @Test
    public void advancedJsonValidation() {
        expect().
                statusCode(allOf(greaterThanOrEqualTo(200), lessThanOrEqualTo(300))).
                rootPath("store.book").
                body("findAll { book -> book.price < 10 }.title", hasItems("Sayings of the Century", "Moby Dick")).
                body("author.collect { it.length() }.sum()", equalTo(53)).
                when().
                get("/jsonStore");
    }

    @Test
    public void advancedJsonValidation2() {
        expect().
                statusCode(allOf(greaterThanOrEqualTo(200), lessThanOrEqualTo(300))).
                rootPath("store.book").
                body("findAll { book -> book.price < 10 }.title", hasItems("Sayings of the Century", "Moby Dick")).
                body("price.min()", equalTo(8.95f)).
                body("price.max()", equalTo(22.99f)).
                body("min { it.price }.title", equalTo("Sayings of the Century")).
                body("author*.length().sum()", equalTo(53)).
                body("author*.length().sum(2, { it * 2 })", is(108)).
                when().
                get("/jsonStore");
    }

    @Test
    public void products() {
        when().
                get("/products").
                then().
                body("price.sum()", is(38.0d)).
                body("dimensions.width.min()", is(1.0f)).
                body("name.collect { it.length() }.max()", is(16)).
                body("dimensions.multiply(2).height.sum()", is(21.0));
    }

    @Test
    public void queryPathTest() {
        String books = """
                <response version-api="2.0">
                        <value>
                            <books>
                                <book available="20" id="1">
                                    <title>Don Quixote</title>
                                    <author id="1">Miguel de Cervantes</author>
                                </book>
                                <book available="14" id="2">
                                    <title>Catcher in the Rye</title>
                                   <author id="2">JD Salinger</author>
                               </book>
                               <book available="13" id="3">
                                   <title>Alice in Wonderland</title>
                                   <author id="3">Lewis Carroll</author>
                               </book>
                               <book available="5" id="4">
                                   <title>Don Quixote</title>
                                   <author id="4">Miguel de Cervantes</author>
                               </book>
                           </books>
                       </value>
                    </response>
                                """;
        queryPath("$", books);
        queryPath("response", books);
        queryPath("response.value.books.book[0].author", books);
        queryPath("response.value.books.book[0]", books);
        queryPath("response.value.books.book[0]['@id']", books);
        // Example shows a simple use of *, which only iterates over the direct children of the node.
        // Look for any node with a tag name equal to 'book' having an id with a value of '2' directly under the 'books' node.
        queryPath("""
                response.value.books.'*'.find { node ->
                    node.name() == 'book' && node.@id == '2'
                }
                """, books);
        // ** is the same as looking for something everywhere in the tree from this point down.
        queryPath("""
                response.'**'.find { book ->
                     book.author.text() == 'Lewis Carroll'
                 }.@id
                        """, books);
        queryPath("""
                response.value.books.book.findAll { book ->
                          book.@id.toInteger() > 2
                      }*.title.size()
                                """, books);
        queryPath("""
                response.value.books.book.findAll { book ->
                            book.@id.toInteger() > 2 }*.title
                """, books);
    }

    @Test
    public void jp() {
        String books = """
                  {
                  "response": {
                    "value": {
                      "books": {
                        "book": [
                          {
                            "title": "Don Quixote",
                            "author": "Miguel de Cervantes"
                          },
                          {
                            "title": "Catcher in the Rye",
                            "author": "JD Salinger"
                          },
                          {
                            "title": "Alice in Wonderland",
                            "author": "Lewis Carroll"
                          },
                          {
                            "title": "Don Quixote",
                            "author": "Miguel de Cervantes"
                          }
                        ]
                      }
                    }
                  }
                }
                """;
        String user = """
                {
                    "firstName": "John",
                    "lastName": "doe",
                    "age": 26,
                    "address": {
                        "streetAddress": "naist street",
                        "city": "Nara",
                        "postalCode": "630-0192"
                    },
                    "phoneNumbers": [
                        {
                            "type": "iPhone",
                            "number": "0123-4567-8888"
                        },
                        {
                            "type": "home",
                            "number": "0123-4567-8910"
                        }
                    ]
                }
                """;
        String store = """
                {
                   "store": {
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
                                 "price": 9.99
                             },
                             {
                                 "category": "fiction",
                                 "author": "J. R. R. Tolkien",
                                 "title": "The Lord of the Rings",
                                 "isbn": "0-395-19395-8",
                                 "price": 22.99
                             },
                             {
                                 "category": "horror",
                                 "author": "Krishna Singh",
                                 "title": "Snakes In The Ganga",
                                 "isbn": "0-235-14536-6",
                                 "price": 33
                             }
                       ],
                       "bicycle": {
                             "color": "red",
                             "price": 19.95
                       }
                     },
                     "city":"Bangalore"
                  }
                   """;
        String persons = """
                [
                    {
                        "id": 1,
                        "name": "John Smith",
                        "description": null,
                        "shortDescription": "No recorded interests.",
                        "alias": "JS",
                        "preferences": [
                            {
                                "id": 1,
                                "description": "likes candy and papaya",
                                "image": "papaya.jpg",
                                "name": "Papaya",
                                "dependentPreferences": [
                                    {
                                        "id": 1,
                                        "description": "Fruit must be ripe",
                                        "image": "ripe-papaya.jpg",
                                        "name": "pap"
                                    }
                                ]
                            }
                         ]
                    },
                    {
                        "id": 2,
                        "name": "Jane Smith",
                        "description": null,
                        "shortDescription": "No recorded interests.",
                        "alias": "JS",
                        "preferences": [
                            {
                                "id": 1,
                                "description": "likes candy and papaya",
                                "image": "papaya.jpg",
                                "name": "Papaya",
                                "dependentPreferences": [
                                    {
                                        "id": 1,
                                        "description": "Candy must be Skittles",
                                        "image": "Skittles.jpg",
                                        "name": "skt"
                                    }
                                ]
                            }
                         ]
                    }
                ]
                """;
        String football = """
                {
                  "count": 20,
                  "teams": [
                    {
                      "id": 322,
                      "name": "Hull City FC",
                      "shortName": "Hull",
                      "squadMarketValue": null,
                      "crestUrl": "http://upload.wikimedia.org/wikipedia/de/a/a9/Hull_City_AFC.svg"
                    },
                    {
                      "id": 338,
                      "name": "Leicester City FC",
                      "shortName": "Foxes",
                      "squadMarketValue": null,
                      "crestUrl": "http://upload.wikimedia.org/wikipedia/en/6/63/Leicester02.png"
                    },
                    {
                      "id": 340,
                      "name": "Southampton FC",
                      "shortName": "Southampton",
                      "squadMarketValue": null,
                      "crestUrl": "http://upload.wikimedia.org/wikipedia/de/c/c9/FC_Southampton.svg"
                    }
                  ]
                }
                """;
        String players = """
                {
                  "count": 24,
                  "players": [
                    {
                      "id": 439,
                      "name": "David de Gea",
                      "position": "Keeper",
                      "jerseyNumber": 1,
                      "dateOfBirth": "1990-11-07",
                      "nationality": "Spain",
                      "contractUntil": "2019-06-30",
                      "marketValue": null
                    },
                    {
                      "id": 440,
                      "name": "Sergio Romero",
                      "position": "Keeper",
                      "jerseyNumber": 20,
                      "dateOfBirth": "1987-02-22",
                      "nationality": "Argentina",
                      "contractUntil": "2018-06-30",
                      "marketValue": null
                    },
                    {
                      "id": 441,
                      "name": "Eric Bailly",
                      "position": "Centre-Back",
                      "jerseyNumber": 3,
                      "dateOfBirth": "1994-04-12",
                      "nationality": "Cote d'Ivoire",
                      "contractUntil": "2020-06-30",
                      "marketValue": null
                    }
                  ]
                }
                """;
        //        queryPath("$", books);
        //        queryPath("response", books);
        //        queryPath("response.value.books.book[0].author", books);
        //        queryPath("response.value.books.book[0]", books);
        //        queryPath("phoneNumbers[1].number", user);
        //
        //        queryPath("find { it.name == 'John Smith' }.preferences.find { it.image == 'papaya.jpg' }.dependentPreferences.description", persons);
        //        queryPath("response.value.books.book.find { it.author == 'Lewis Carroll' }.title", books);
        //        queryPath("teams.name[-1]", football);
        //        queryPath("teams.name[0,1]", football);
        //
        //        queryPath("players.findAll { it.jerseyNumber > 1 }.name", players);
        //        queryPath("players.max { it.jerseyNumber }.name", players);
        //        queryPath("players.min { it.jerseyNumber }.name", players);
        //        queryPath("players.collect { it.jerseyNumber }.sum()", players);
        //        queryPath("players.findAll { it.position == 'Keeper' }.find { it.nationality == 'Argentina' }.name", players);
        //
        //        queryPath("store.book[*].title", store); // Doesn't Work
        //        queryPath("store.book[*]", store); // Doesn't Work
        //
        //        queryPath("store.bicycle.color", store);
        //        queryPath("store.book[1].title", store);
        //        queryPath("store.book[-1].title", store);
        //        queryPath("store.book.findAll { it.category == 'reference' }", store);
        //        queryPath("store.book.findAll { it.category in ['reference','fiction'] }", store);
        //        queryPath("store.book.findAll { it.category=='reference'}.size()", store);
        //        queryPath("store.book.findAll { it.price < 12.0 }", store);
        //        queryPath("store.book.findAll { it.category != 'reference' }", store);
        //        queryPath("store.book.findAll { it.category == 'reference' || it.category == 'fiction' }", store);
        //        queryPath("store.book.findAll { it.category == 'reference' && it.category ==  'fiction' }", store);
        //        queryPath("store.book.findAll { it.category == 'reference' && it.author ==  'Herman Melville' }", store);
        //        queryPath("store.book.findAll { it.category =~/ref.*/ || it.category=~/icti.*/ }", store);
        //
        //        queryPath("store.bicycle.price.toInteger();", store);
        //        queryPath("store.bicycle.price.any();", store);
        //        queryPath("store.book.get(1);", store);
        //        queryPath("store.book.every { it.category == 'reference' };", store);
        //        queryPath("store.book.any { it.category == 'reference' };", store);
        //        queryPath("store.book.any { it.category.contains('reference') };", store);
        //        queryPath("store.book.author.get(0).toUpperCase();", store);
        //
        //        queryPath("store.book[1..3]", store);
        //        queryPath("store.book[1,3]", store);
        //        queryPath("store.book[-1,-2,-3].asReversed()", store);
        //        queryPath("store.book.price.average()", store);
        //        queryPath("store.book.price", store);
        //        queryPath("store.book.price*.plus(3)", store);
        //        queryPath("store.book.price*.div(3)", store);
        //        queryPath("store.book.price*.minus(15)", store);
        //        queryPath("store.book.price*.multiply(3)", store);
        //        queryPath("store.book.price*.minus(15)*.abs()", store);
        //        queryPath("store.book.price*.toInteger()", store);
        //        queryPath("store.book.price.findAll { Math.toIntExact(it.toLong()) % 2 == 0 }", store);
        //        queryPath("store.book.price.grep { Math.toIntExact(it.toLong()) % 2 == 0 }", store);
        //        queryPath("store.book.category", store);
        //        queryPath("store.book.category.unique()", store);
        //        queryPath("store.book.unique()", store);
        //        queryPath("store.book.toUnique { it.category == 'horror' }", store);
        //        queryPath("store.book.price.sort()", store);
        //        queryPath("store.book.price.sort( { (a, b) -> (a == b) ? 0 : (a < b) ? 1 : -1 } )", store);
        //        queryPath("store.book.price.max()", store);
        //        queryPath("store.book.price.min()", store);
        //        queryPath("store.book.collect { it.author }", store);
        //        queryPath("store.book.price.join('-')", store);
        //
        //        queryPath("store.book[0].minus(['category':'reference']);", store);
        //        queryPath("store.book.removeAll { it.key == 'reference' }", store);
        //        queryPath("store.book.retainAll { it -> it.price % 2 == 0 }", store);
        //        queryPath("store.book*.findAll { it.key == 'price' }", store);
        //        queryPath("store.book*.findAll { it.key == 'author' }*.author", store);
        //        queryPath("store.book.collect { entry -> entry.category }", store);
        //
        //        queryPath("store.book*.each { println(it) }", store);
        //        queryPath("store.book.each { println(it) }", store);
        //
        //        queryPath("store.book.each { it.price > 30 }", store);

        /*
        assert nums.min() == 1
                assert nums.max() == 3
                assert nums.sum() == 6
                assert nums.indices == [0, 1, 2]
                assert nums.swap(0, 2) == [3, 2, 1] as int[]
         */

    }

    @Test
    public void
    extracting_first_lotto_winner_to_java_object() {
        final Winner winner = JsonPath.from(LOTTO).getObject("lotto.winners[0]", Winner.class);
        assertThat(winner.getNumbers(), hasItems(2, 45, 34, 23, 3, 5));
        assertThat(winner.getWinnerId(), equalTo(23));
    }

    @Test
    public void
    getting_numbers_greater_than_ten_for_lotto_winner_with_id_equal_to_23() {
        List<Integer> numbers = JsonPath.from(LOTTO)
                .getList("lotto.winners.find { it.winnerId == 23 }.numbers.findAll { it > 10 }", Integer.class);
        assertThat(numbers, hasItems(45, 34, 23));
        assertThat(numbers, hasSize(3));
    }

    // https://docs.groovy-lang.org/latest/html/api/org/codehaus/groovy/runtime/DefaultGroovyMethods.html

}
