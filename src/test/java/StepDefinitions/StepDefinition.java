package StepDefinitions;

import Resources.APIResources;
import Resources.TestDataBuild;
import Resources.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinition extends Utils {

    ResponseSpecification resSpec;
    RequestSpecification reqSpec;
    Response response;
    static String placeID;

    TestDataBuild data = new TestDataBuild();

    @Given("Add place Payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) {

        reqSpec = given().spec(requestSpecification())
                .body(data.addPlacePayload(name, language, address));
    }

    @When("User calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {

        //constructor will be called with value of resources which passed here
        APIResources resourceAPI = APIResources.valueOf(resource);
        String api = resourceAPI.getResource();

        resSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();

        if (method.equalsIgnoreCase("POST")) {
            response = reqSpec.when().post(api);
        } else if (method.equalsIgnoreCase("GET")) {
            response = reqSpec.when().get(api);
        }
    }

    @Then("The API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(int statusCode) {
        assertEquals(response.getStatusCode(), statusCode);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {
        assertEquals(getJsonPath(response, keyValue), expectedValue);
    }

    @And("verify place_id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) {
        placeID = getJsonPath(response, "place_id");

        reqSpec = given().spec(requestSpecification())
                .queryParam("place_id", placeID);

        user_calls_with_http_request(resource, "GET");

        String actualName = getJsonPath(response, "name");
        assertEquals(actualName, expectedName);
    }

    @Given("DeletePlace payload")
    public void delete_place_payload() {
        reqSpec = given().spec(requestSpecification())
                .body(data.deletePlacePayload(placeID));
    }
}