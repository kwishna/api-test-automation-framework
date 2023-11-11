package rest.cucumber.stepDefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;
import rest.cucumber.pojo.reqres.users.User;
import rest.cucumber.utils.TestContext;
import rest.cucumber.utils.Utilities;

import java.util.HashMap;
import java.util.Map;

public class TestSteps {

    @Given("I want to verify a scenario")
    public void iWantToVerifyScenario(@Transpose DataTable table) {

        System.out.println("--------------------------------");
        System.out.println(SerenityRest.lastResponse().body().prettyPrint());
        System.out.println("--------------------------------");

        System.out.println("--------------------------------");
        User user = (User) TestContext.getSyncVal("USER");
        System.out.println(user);
        System.out.println("--------------------------------");

        System.out.println("--------------------------------");
        System.out.println("DataTable To List :: " + table.asList(String.class));
        System.out.println("--------------------------------");
    }

    @When("I pass some headers")
    public void iPassSomeHeaders(@Transpose DataTable table) {
        System.out.println("--------------------------------");
        System.out.println("Table To Map :: " + Utilities.injectSysProperty(table.asMap()));
        System.out.println("--------------------------------");
    }
}
