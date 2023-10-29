package rest.cucumber.examples;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import rest.cucumber.utils.PropertiesUtils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class RestFiveParamterization {

	static PropertiesUtils prop;

//	@BeforeTest
	public static void loadProperties() throws IOException {

		prop = PropertiesUtils.getPropertyFileReader("resources");
	}
	
//	@Test(dataProvider="dp")
	public void addBookPost(String isbn, String isle) {
		
		RestAssured.baseURI = prop.getValue("UDEMY_HOST");
	
		Response res =	given()
			.header("Content-Type", "application/json")
			.body(BooksHandler.getABook(isbn, isle))
			.when()
			.post("Library/Addbook.php")
			.then()
			.log().body(true) // Remember
			.assertThat().statusCode(200)
			.and()
			.extract()
			.response();
		
		JsonPath jp = res.jsonPath();
		System.out.println("ID From Response :: "+jp.get("ID"));

	}
	
//	@DataProvider
	public Object[][] dp() {
		
//		return new Object[][] {{"",""}, {"",""}, {"",""}};
		
		Object[][] obj = {{"isbn1","aisle1"}, {"isbn2","isle2"}, {"isbn3","isle3"}};
		return obj;
	}

}

class BooksHandler{
	
	public static String getABook(String isbn, String aisle) {
		
		return "{ \"name\":\"Learn Appium Automation with Java\", \"isbn\":\""+isbn+"\", \"aisle\":\""+aisle+"\", \"author\":\"John foe\" }";
	}
}
