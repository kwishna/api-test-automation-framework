package devils.dare.rough_files;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 *
 * https://developer.nytimes.com/apis
 *
 * APP ID : 7a5da757-1c5e-4599-97ef-9ba3a0520039
 * KEY : FJ2dtgGXWrNxaWiIy935VkJ3fsbJGZy7
 */
public class NewYorkTimes {

	static PrintStream stream;

	static {
		try {
			stream = new PrintStream(new File("logger.log"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	RequestSpecification reqSpec;
	ResponseSpecification respSpec;

	final String subject = "World";

//	@BeforeTest
	public void beforeTest() {

		RestAssured.baseURI = "https://api.nytimes.com/";
		RestAssured.basePath = "/svc/topstories/v2/";
//		RestAssured.urlEncodingEnabled = true; //Means URL to be provided is not encoded by user.
//		RestAssured.rootPath = "results[0]"; // Root Node From Where JSONPATH Will Be Start Applying

		reqSpec = new RequestSpecBuilder()
				.addHeader("Accept", "*/*")
				.log(LogDetail.URI)
				.addFilter(RequestLoggingFilter.logRequestTo(stream))
				.addFilter(ResponseLoggingFilter.logResponseTo(stream))
				.addFilter(ErrorLoggingFilter.logErrorsTo(stream))
				.addPathParam("section", subject)
				.addQueryParam("api-key", "FJ2dtgGXWrNxaWiIy935VkJ3fsbJGZy7")
				.build();

		respSpec = new ResponseSpecBuilder()
				.expectHeader("Content-Type", "application/json")
				.expectStatusLine("HTTP/1.1 200 OK")
				.expectStatusCode(200)
				.expectResponseTime(lessThan(10000L))
				.build();
		/*
		arts, automobiles, books, business, fashion, food, health, home, insider, magazine, movies, nyregion, obituaries, opinion,
		politics, realestate, science, sports, sundayreview, technology, theater, t-magazine, travel, upshot, us, world
		 */
	}

//	@AfterTest
	public void tearDown(){
		RestAssured.reset();
	}

//	@Test
	public void testOne() {

		ValidatableResponse response = given()
												.spec(reqSpec)
												.when()
												.get("/{section}.json")
												.then()
												.spec(respSpec);

		response.assertThat()
				.statusLine(equalToIgnoringCase("HTTP/1.1 200 OK"))
				.and()
				.body("section", containsString(subject))
				.body("results.subsection", hasItem("asia"));

		System.out.println(response.extract().jsonPath().getString("num_results"));
		System.out.println("***************************************");

		System.out.println(response.extract().jsonPath().getString("results.section"));
		// If results is an array. All data will be collected in a list and printed.
		System.out.println("***************************************");

		System.out.println(response.extract().jsonPath().getString("results.des_facet.findAll{ it.contains 'International Relations'}"));
		System.out.println("***************************************");

		System.out.println(response.extract().jsonPath().getString("results.findAll{ it.des_facet.findAll { it.contains 'International Relations'}}"));
		System.out.println("***************************************");

		response.assertThat().body("results.findAll{ it.subsection == 'asia'}", hasItem(hasEntry("url", "https://www.nytimes.com/2020/02/23/world/asia/china-coronavirus.html")));
		System.out.println("***************************************");

		/**
		 * direct notation with @nameoftheattribute
		 * map notation using ['@nameoftheattribute']
		 * // node.@id == 2 could be expressed as node['@id'] == 2
		 *
		 * response.value.books.'*'.find { node -> node.name() == 'book' && node.@id == '2' }
		 *
		 * bookId = response.'**'.find { book -> book.author.text() == 'Lewis Carroll' }.@id
		 *
		 * response.value.books.book.findAll { book -> book.@id.toInteger() > 2 }
		 *     // You can use toInteger() over the GPathResult object
		 *
		 * items.findAll{it.name ==~/Ref.* /} // Name Starts With 'Ref'
		 * items.findAll{it.name ==~/.*ed/} // Name Ends With 'ed'
		 */
	}

//	@Test
	public void learnRoot(){

		ValidatableResponse response = given()
				.spec(reqSpec)
				.when()
				.get("/{section}.json")
				.then()
				.spec(respSpec)
				.rootPath("results[0].multimedia[0]")
				.assertThat()
				.body("format", containsString("superJumbo"), // 1st Matcher
						"height", equalTo(1415), // 2nd Matcher
						"width", equalTo(2048), // 3rd Matcher
						"type", containsString("images")); // 4th Matcher
	}

//	@Test
	public void downloadFile(){

		byte[] array = given()
							.when()
							.get("https://file-examples.com/wp-content/uploads/2017/02/file_example_XLS_10.xls")
							.then()
							.extract()
							.asByteArray();
		System.out.println(array.length);
	}
	//https://developers.zamzar.com/docs // For File Upload & Download.


//	@Test
	public void getTime(){

		long time = given()
				.when()
				.get("https://file-examples.com/wp-content/uploads/2017/02/file_example_XLS_10.xls")
				// .time()
				.timeIn(TimeUnit.SECONDS);

		System.out.println(time);
	}

//	@Test
	public void proxyServer(){

//		ProxySpecification proxy = new ProxySpecification("localhost", 5555, "http");
//		RestAssured.proxy(proxy);
//		RestAssured.proxy(5555);
//		RestAssured.proxy(new RequestSpecBuilder().setProxy(5555);

		 given()
				.proxy(5555) // Need Some Server To Capture. Example : Postman Proxy Server.
				.get("https://file-examples.com/wp-content/uploads/2017/02/file_example_XLS_10.xls");
	}

}
