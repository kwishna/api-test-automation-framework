package devils.dare.rough_files;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.Matchers.equalTo;

/**
 * rathr1
 **/
public class TestGetUsingMock {

    /**
     * 1. Create the Wiremock server and start
     * 2. Configure the server to intercept the request
     * 3. Stub for the request [get request]
     * - Create the mock response
     * 4. Shutdown the WireMock Server
     */
    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    private static final WireMockServer SERVER = new WireMockServer(PORT);

    @BeforeClass
    public static void setup() {
        SERVER.start();

        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
        mockResponse.withStatus(200)
                .withBody("[{\"BrandName\":\"Alienware\",\"Features\":{\"Feature\":[\"8th Generation Intel® Core™ i5-8300H\",\"Windows 10 Home 64-bit English\",\"NVIDIA® GeForce® GTX 1660 Ti 6GB GDDR6\",\"8GB, 2x4GB, DDR4, 2666MHz\"]},\"Id\":1,\"LaptopName\":\"Alienware M17\"}]")
                .withHeader("Content-Type", "application/json");

        WireMock.configureFor(HOST, PORT); // http://localhost:8080
        WireMock.stubFor(
                WireMock.get("/laptop-bag/webapi/api/all")
                        .willReturn(mockResponse)
        );
    }

    @AfterClass
    public static void teardown() {
        if (null != SERVER && SERVER.isRunning()) {
            SERVER.shutdownServer();
        }
    }

    @Test
    public void TestGetEndPoint() throws URISyntaxException {
        Response res = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get(new URI("http://localhost:8080/laptop-bag/webapi/api/all"))
                .thenReturn();

        res.then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("[0].BrandName", equalTo("Alienware"))
                .log()
                .all(true);


    }

}
