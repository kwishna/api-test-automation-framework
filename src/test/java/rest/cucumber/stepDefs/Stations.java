package openWeather.stepDef;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Method;
import rest.cucumber.pages.PlaceAPIEndpoint;
import rest.cucumber.utils.data_sync.TestContext;

public class Stations {

    private TestContext ctx;
    private PlaceAPIEndpoint placeAPIEndpoint;

    public Stations(PlaceAPIEndpoint placeAPIEndpoint, TestContext ctx) {
        this.placeAPIEndpoint = placeAPIEndpoint;
        this.ctx = ctx;
    }

    @When("I call stations with {string}")
    public void iCallStationsWith(String operation) {
        this.ctx.response = this.placeAPIEndpoint.getAddPlaceResponse(Method.GET);
    }

    @Then("I verify status code {int}")
    public void iVerifyStatusCode(int statusCode) {
        this.ctx.response.then().assertThat().statusCode(statusCode);
    }
}
