package rest.cucumber.examples;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import rest.cucumber.utils.properties.PropertyReader;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredOneJson {
	
	static PropertyReader prop;
	
//	@BeforeTest
	public static void loadProperties() throws IOException {
		
		prop = PropertyReader.getPropertyFileReader("resources");		
	}
	
//	@Test
	public static void getReq() {
		
		// BaseUrl Or Host
		RestAssured.baseURI = prop.getValue("GOOGLE_HOST");
		
// 		https://maps.googleapis.com/maps/api/place/nearbysearch/json?
//		location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=cruise&key=YOUR_API_KEY
		
		ValidatableResponse vres =	given()
										.param("location", "-33.8670522,151.1957362")
										.param("radius", 500)
										.param("key", prop.getValue("GOOGLE_KEY"))
										.when()
										.get(prop.getValue("GET_PLACE_JSON"))
										.then()
										.assertThat().statusCode(200)
										.and().contentType(ContentType.JSON)
										.and().body("results[0].name", equalTo("Sydney"));
			
		System.out.println("Get Request ::: "+vres.extract().response().prettyPrint());
		
	}
	
//	@Test
	public static void postReq() {
		
		// BaseUrl Or Host
		RestAssured.baseURI = prop.getValue("UDEMY_HOST"); // For Post Request 
		
		ValidatableResponse vres =	given()
										.queryParam("key", prop.getValue("UDEMY_KEY"))
										.body("{\r\n" + 
												"	 \"location\": {\r\n" + 
												"                    \"lat\": 27.173113,\r\n" + 
												"                    \"lng\": 78.040637\r\n" + 
												"                },\r\n" + 
												"      \"accuracy\" : 50,\r\n" + 
												"      \"name\"	 : \"Taj Mahal!\",\r\n" + 
												"      \"phone_number\" : \"(02) 9374 4000\",\r\n" + 
												"      \"address\"	 : \"Taj Museum, Agra, Uttar Pradesh, India\",\r\n" + 
												"      \"types\"	 : [\"Historical Place\"],\r\n" + 
												"      \"website\"	 : \"https://www.google.co.in\",\r\n" + 
												"      \"language\" : \"en-IN\"\r\n" + 
												"}")
										.when()
										.post(prop.getValue("POST_PLACE_JSON"))
										.then()
										.assertThat().statusCode(200)
										.and().contentType(ContentType.JSON)
										.and().statusLine("HTTP/1.1 200 OK");
			
		System.out.println("Post Request Response::: "+vres.extract().response().prettyPrint());
	}
	
//	@Test
	public static void postAndDeleteReq() {
		
		// BaseUrl Or Host
		RestAssured.baseURI = prop.getValue("UDEMY_HOST"); // For Post Request 
						
					// POST Request To Create
					String place_id =	given()
										.queryParam("key", prop.getValue("UDEMY_KEY"))
										.body(new File(System.getProperty("user.dir")+"/src/main/java/dataFiles"+"/body.json")) // Using File
										.when()
										.post(prop.getValue("POST_PLACE_JSON"))
										.then()
										.assertThat().statusCode(200)
										.and().contentType(ContentType.JSON)
										.and().statusLine("HTTP/1.1 200 OK")
										.extract()
										.path("place_id");  // Or, .extract().response(); returns Response res; Now, res.asString();
													  // Then, JsonPath jp = new JsonPath(res.asString()); String pId = jp.get("place_id");						
										
					System.out.println("Post Response Place Id Is :: "+place_id);
		
					// Delete The Data Using Value Acquired From The Above Request
										given()
										.accept(ContentType.JSON)
										.queryParam("key", prop.getValue("UDEMY_KEY"))
										.body("{\r\n" + 
												"	\"place_id\" : \""+place_id+"\"\r\n" + 
												"}")
										.when()
										.post(prop.getValue("DELETE_PLACE_JSON"))
										.then()
										.assertThat().statusCode(200)
										.and().contentType(ContentType.JSON)
										.and().statusLine("HTTP/1.1 200 OK")
										.and().body("status", equalTo("OK"));
	}
}
