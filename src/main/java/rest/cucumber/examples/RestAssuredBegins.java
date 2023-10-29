package rest.cucumber.examples;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HeaderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestAssuredBegins {

	/**
	 * Main method for logging data
	 */
	public Response filter(FilterableRequestSpecification requestSpec,
						   FilterableResponseSpecification responseSpec, FilterContext ctx) {
		// Invoke the request by delegating to the next filter in the filter chain.
		final Response response = ctx.next(requestSpec, responseSpec);

		if (responseSpec.getStatusCode() != null) {
			if (responseSpec.getStatusCode().matches(response.statusCode())) {
//				Reporter.log("Status code matched what was expected");
			} else {
				String req = "REQUEST: " + System.lineSeparator() + "Request method: "
						+ requestSpec.getMethod() + System.lineSeparator() + "Request Path: "
						+ requestSpec.getURI() + System.lineSeparator() + "Request Params: "
						+ requestSpec.getRequestParams() + System.lineSeparator()
						+ "Query Params: " + requestSpec.getQueryParams()
						+ System.lineSeparator() + "Form Params: "
						+ requestSpec.getFormParams() + System.lineSeparator()
						+ "Path Params: " + requestSpec.getPathParams()
						+ System.lineSeparator() + "Headers: " + requestSpec.getHeaders()
						+ System.lineSeparator() + "Body: " + System.lineSeparator()
						+ requestSpec.getBody()+ System.lineSeparator()
						+ System.lineSeparator() + System.lineSeparator() + "RESPONSE: "
						+ System.lineSeparator() + " " + response.getStatusLine() + System.lineSeparator()
						+ response.getHeaders() + System.lineSeparator()
						+ response.getBody() + System.lineSeparator()
						+ "SPLUNK SEARCH: " + System.lineSeparator()
						+ System.lineSeparator() + System.lineSeparator()
						+ System.lineSeparator();

//				Reporter.log(req);
			}
		} else {
//			 Reporter.log("Status code was null");
		}
		return response;
	}

	public void headers() {	
		
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		
		ValidatableResponse res = given()
										.when()
										.get("http://api.zippopotam.us/IN/110001")
										.then();
		
//		Response reponse = res.extract().response(); // Converting Validatable Response To Response Object
//		String prettyJson = reponse.prettyPrint();	// Printing Pretty JSON
//		Object result = reponse.as(Object.class); // Converting Response To Object
		
//		String resul = res.extract().path("user_id"); // Extracting Value From JSON
//		String userId = response.path("user_id");
//		extract().jsonPath().getLong("user_id");
//		
//		JsonPath jsonPathEvaluator = response.jsonPath();
//		return jsonPathEvaluator.get("user_id").toString();
		
		System.out.println(res.extract().headers());
		
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	}
	
	/**
	 * Parameterization :
	 * 	  2 Types :-
	 * a) Query Parameters : something.com/?text=apple, something.com/?text=mango
	 * b) Path Parameters : something.com/qa/test, something.com/dev/deploy
	 */

	public void parameterization() {	// Pass Parameters With DataProviders
		
		String country = "IN";
		String pinCode = "110001";
		
								given()
									.pathParam("country", country)
										.pathParam("zip", pinCode)
									.when()
									.log().all()
									.get("http://api.zippopotam.us/{country}/{zip}")
									.then()
									.log().body();
		
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");	
	}

	public void requestSpecification() {

		RequestSpecification reqBuilder = new RequestSpecBuilder() // For Re-usability Again & Again In Every Request
				.addFilter(RequestLoggingFilter.logRequestTo(System.out))
				.addFilter(ResponseLoggingFilter.logResponseTo(System.out))
				.addFilter(ErrorLoggingFilter.logErrorsTo(System.out))
				.addFilter(new Filter() {
					@Override
					public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
						MatcherAssert.assertThat(requestSpec.getHeaders().getList("Host").size(), CoreMatchers.equalTo(1));
						MatcherAssert.assertThat(requestSpec.getHeaders().get("Host").getValue(), CoreMatchers.equalTo("host url"));
						return ctx.next(requestSpec, responseSpec);
					}})
				/**
				 * Filter the incoming request and response specifications and outgoing response.
				 * You need to call FilterContext.next(FilterableRequestSpecification, FilterableResponseSpecification)
				 * when you're done otherwise the request will not be delivered.
				 * It's of course possible to abort the filter chain execution by returning a Response directly.
				 */
				//CookieFilter, ErrorLoggingFilter, FormAuthFilter, RequestLoggingFilter, ResponseLoggingFilter, SendRequestFilter, SessionFilter, TimingFilter
							.log(LogDetail.ALL)
													.setBaseUri("http://api.zippopotam.us")
													.setContentType(ContentType.JSON)
													.build();
		
								given()
									.spec(reqBuilder)
									.when()
									.get("/IN/110001")
									.then()
									.assertThat()
									.statusCode(200);
		
		
	}

	public void responseSpecification() {

		ResponseSpecification resp = new ResponseSpecBuilder()	// For Re-usability Again & Again In Every Request. Put In @Before Methods
											.expectContentType(ContentType.JSON)
											.expectStatusCode(200)
											.log(LogDetail.COOKIES)
											.build();
		
								given()
									.when()
									.get("http://api.zippopotam.us/IN/110001")
									.then()
									.spec(resp)
									.and()
									.assertThat()
									.body("places.'place name'", not(hasItem("Desh")));
	
	}

	public void responseReuse() {

		ResponseSpecification resp = new ResponseSpecBuilder()	// For Re-usability Again & Again In Every Request. Put In @Before Methods
											.expectContentType(ContentType.JSON)
											.expectStatusCode(200)
											.log(LogDetail.COOKIES)
											.build();
		
			List<String> places =	given() // We Can Use List In Another Tests
									.when()
									.get("http://api.zippopotam.us/IN/110001")
									.then()
									.spec(resp)
									.and()
									.log().all(true)
									.and()
									.extract()
									.path("places.'place name'");
			
//			assertEquals(places.size(), 19);
			
			System.out.println(places);
	}

	// Use : http://www.jsonschema2pojo.org/
//	@Test(enabled = false)
	public void deSerialization() {
	
//			Location loc =	given()
//					.expect()
//					.parser("application/json; charset=utf-8", Parser.JSON)
//					.when()
//					.get("http://api.zippopotam.us/IN/110001")
//					.as(Location.class);
//
//		System.out.println(loc);
	}
	
//	@Test(enabled = false)
	public void serialization() {

//		Location loc = new Location();
//							given()
//							.contentType(ContentType.JSON)
//							.body(loc)
//							.log().body()
//							.when()
//							.post("http://api.zippopotam.us/IN/110001")
//							.then()
//							.log().all(true)
//							.assertThat()
//							.statusCode(201);

	}

//	@Test(enabled = false)
	public static void serialize() {

//		AnotherLocation anotherLocation = new AnotherLocation();
//		anotherLocation.setLat(50.0);
//		anotherLocation.setLng(150.0);
//
//		AnyLocation anyLocation = new AnyLocation();
//		anyLocation.setAccuracy(5);
//		anyLocation.setAddress("Address Serialize");
//		anyLocation.setLanguage("Hi");
//		anyLocation.setLocation(anotherLocation);
//		anyLocation.setName("Taj");
//		anyLocation.setPhoneNumber("9771451456");
//		anyLocation.setTypes("Tourism, Old");
//		anyLocation.setWebsite("https://gjghgj.com");

		// BaseUrl Or Host
//		RestAssured.baseURI = "https://rahulshettyacademy.com/"; // For Post Request
//
//		ValidatableResponse vres =	given()
//				.queryParam("key", "qaclick123")
//				.body(anyLocation)
//				.when()
//				.post("/maps/api/place/add/json")
//				.then()
//				.log().all(true)
//				.assertThat().statusCode(200)
//				.and().contentType(ContentType.JSON)
//				.and().statusLine("HTTP/1.1 200 OK");

//		System.out.println("Post Request ::: "+vres.extract().response().prettyPrint());
	}


//	@Test
	public void getReqAndDeSerialize() {

		baseURI = "https://rahulshettyacademy.com/";

		ValidatableResponse response =	given()
				.param("place_id", "18c6c82b4942bcec93964b8304956bbe")
				.param("key", "qaclick123")
				.when()
				.log().everything()
				.get("/maps/api/place/get/json")
				.then()
				.log().all(true);

//		AnyLocation loc = response.extract().response().as(AnyLocation.class);
//		System.out.println(loc);
	}

	public void getPlaceAPI()
	{
		// RestAssured.authentication = basic("username", "password");
		//
		//BaseURL or Host
		RestAssured.baseURI="https://maps.googleapis.com";

		given().
				param("location","-33.8670522,151.1957362").
				param("radius","500").
				param("key","AIzaSyDIQgAh0B4p0SdyYkyW8tlG-y0yJMfss5Y").
				config(RestAssuredConfig.config().headerConfig(HeaderConfig.headerConfig().mergeHeadersWithName("Accept"))).
				when().
				get("/maps/api/place/nearbysearch/json").
				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
				body("results[0].name",equalTo("Sydney")).and().
				body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and().
				header("Server","pablo");


		       /*header("dfd","fsdfds").
		       cookie("dsfs","csder").
		       body()*/
		//Status code of the response
		//Content type
		//Body
		//Header responses

	}

}
