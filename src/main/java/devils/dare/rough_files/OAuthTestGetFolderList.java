package devils.dare.rough_files;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
//import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class OAuthTestGetFolderList {

    private static String code = "";
    private static String token = "";

    private static String client_id = "<>";
    private static String client_secret = "<>";

    private static String username = "<>";
    private static String password = "<>";
    private static String redirect_uri = "https://www.google.com/";

    //	@BeforeClass
    public static void name() {
//        String username = "";
//        String password = "";
//        String client_id = "";
//        String client_secret = "";
//        String redirect_uri = "";

        code = new OAuthHelper().getOauthToken(username, password, client_id, client_secret, redirect_uri);

        token = given().contentType(ContentType.URLENC).formParam("code", code)
                .formParam("grant_type", "authorization_code")
                .formParam("redirect_uri", redirect_uri)
                .formParam("client_id", client_id)
                .formParam("client_secret", client_secret).when()
                .post("https://api.dropbox.com/oauth2/token")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath().get("access_token");
    }

//    @Test
    public void testGetFolderList() {
        String response = given().auth().oauth2(token).contentType(ContentType.JSON)
                .body("{\"path\": \"\",\"recursive\": false}").when()
                .post("https://api.dropboxapi.com/2/files/list_folder").then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
        System.out.println(response);

    }

}
