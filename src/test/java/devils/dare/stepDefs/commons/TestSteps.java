package devils.dare.stepDefs.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import devils.dare.base.BaseSteps;
import devils.dare.commons.utils.Replacer;
import devils.dare.commons.utils.TestContext;
import devils.dare.commons.utils.Utilities;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DocStringType;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.nio.file.Paths;

public class TestSteps extends BaseSteps {

    public TestSteps(TestContext context) {
        super(context);
    }

    @Given("I want to verify a scenario")
    public void iWantToVerifyScenario(@Transpose DataTable table) {

        System.out.println("--------------------------------");
//        System.out.println(SerenityRest.lastResponse().body().prettyPrint());
        System.out.println("--------------------------------");

        System.out.println("--------------------------------");
//        User user = (User) TestContext.getSyncVal("USER");
//        System.out.println(user);
        System.out.println("--------------------------------");

        System.out.println("--------------------------------");
        System.out.println("DataTable To List :: " + table.asList(String.class));
        System.out.println("--------------------------------");
    }

    @When("I pass some headers")
    public void iPassSomeHeaders(@Transpose DataTable table) {
        System.out.println("--------------------------------");
        System.out.println("Table To Map :: " + Utilities.substituteSysProperty(table.asMap()));
        System.out.println("--------------------------------");
    }

    @DocStringType
    public JsonNode json(String docString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String substiuted = Replacer.substituteSystemParameters(docString);
        return objectMapper.readValue(substiuted, JsonNode.class);
    }

    @Then("I send the body")
    public void iVerifyResponseBody(JsonNode node) {
        System.err.println(node);
    }

    @Then("I send the body from {string}")
    public void iVerifyResponseBody(String file) {
        Replacer.substituteSystemParameters(Paths.get(file));
    }
}
