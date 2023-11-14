package devils.dare.rough_files;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

/**
 * Client ID And Client Secret Must Be Disabled Before Pushing To Any Repository.
 *
 * Useful Links :-
 * https://developer.paypal.com/developer/application/create
 * https://developer.paypal.com/developer/dashboard/sandbox/
 * https://developer.paypal.com/docs/api/overview/
 */
public class PaypalApiTest {

	SessionFilter filter;
	private static String access_token;
	// DELETED
	private static final String CLIENT_ID = "ARI6GGXwpfEjCFyDnMltGQX9C_z4Av2yAPBPm0FwdMvrHaG7EKuQtCn0J1YubdtL-aoxQVmvI-7SNkNB";
	private static final String CLIENT_SECRET = "EFfSsiK7BxgtGuKbrilYpP9PD2All9lKUx7n0wp9dNMiFIjo1AX7e-dnB9PhUG5g649g9o3D-ocKvfzr";

//	@BeforeTest
	public void setUp(){
		baseURI = "https://api.sandbox.paypal.com";
	}

//	@Test(groups = "")
	public void getToken() throws IOException {

		RestAssured.basePath = "/v1/oauth2/token/";

		ValidatableResponse response =
		given()
				.param("grant_type", "client_credentials")
				.header("Accept", "application/json")
//				.header("Accept", "application/x-www-form-urlencoded") // Media type Not Acceptable Error May Come.
				.header("Accept-Language", "en_US")
				.auth().preemptive().basic(CLIENT_ID, CLIENT_SECRET)
				.when()
				.post()
				.then()
				.log().all();

		String accessToken = response.extract().jsonPath().getString("access_token");
		response.extract().response().getBody().asInputStream().transferTo(Files.newOutputStream(Paths.get("xyz1s.json"), StandardOpenOption.CREATE_NEW));
		access_token = accessToken;

	}

//	@Test
	public void createAPayment() throws IOException {

//		access_token = "-tbEj_w";
		basePath = "/v1/payments/payment";

		ValidatableResponse response =
		given()
				.contentType(ContentType.JSON)
				.auth().oauth2(access_token)
				.body(new File(System.getProperty("user.dir")+"/src/test/java/paypal/create_payment.json"))
				.post()
				.then()
				.log().all()
				.assertThat()
				.statusCode(201)
				.statusLine("HTTP/1.1 201 Created");

		String paymentId = response.extract().response().jsonPath().getString("id");
		System.out.println(paymentId); // "PAYID-LZJKE5I55155213HT935604K"
		response.extract().response().getBody().asInputStream().transferTo(Files.newOutputStream(Paths.get("xyz.json"), StandardOpenOption.CREATE_NEW));

	}

//	@Test
	public void getPaymentDetails(){

//		access_token = "-tbEj_w";
		String paymentId= "PAYID-LZJKE5I55155213HT935604K";
		basePath = "/v1/payments/payment";

		given()
				.auth().oauth2(access_token)
				.get(paymentId)
				.then()
				.log().all()
				.assertThat()
				.statusCode(200)
				.statusLine("HTTP/1.1 200 OK")
				.body(             "id", equalTo(paymentId),
				"state", equalTo("created"),
						                 "transactions[0].amount.total", equalTo("93.00"),
						"transactions[0].amount.currency", equalTo("BRL"));
	}

	public void formAuthentication(){

		filter = new SessionFilter();
		baseURI = "https://localhost:8080";
		given().
				auth()
		//		.form("username", "password", FormAuthConfig.springSecurity())
				.form("username", "password", new FormAuthConfig("/actionNameOfForm", "user_fieldName", "pass_fieldName"))
				.filter(filter)
				.get();

		System.out.println(filter.getSessionId());
	}

//	@Test(dependsOnMethods = {"formAuthentication"})
	public void getAllDataAfterLoginUsingSession(){

		given()
				.sessionId(filter.getSessionId())
				.get("/students/list")
				.then()
				.log().all();
	}

//	@Test(dependsOnMethods = {"formAuthentication"})
	public void loginUsingSessionAndCSRFToken(){
		/*
		formAuthConfig().withAutoDetectionOfCsrf();
		springSecurity().withCsrfFieldName("")
		*/
		// Everytime when user tries to login CSRF Token Is Generated And Found Hidden In The Form.
		// We Need Both Cookies & CSRF Token To Login.
		filter = new SessionFilter();
//		baseURI = "https://localhost:8080";
//		given().
//				auth()
//				.form("username", "password",
//						new FormAuthConfig("/actionNameOfForm", "user_fieldName", "pass_fieldName")
//								.withCsrfFieldName("_csrf")
//				.filter(filter)
//				.get();

		System.out.println(filter.getSessionId());
	}
}
