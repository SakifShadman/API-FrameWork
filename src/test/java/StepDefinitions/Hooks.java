package StepDefinitions;

import io.cucumber.java.Before;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() {

        StepDefinition stepDef = new StepDefinition();

        if (StepDefinition.placeID == null) {
            stepDef.add_place_payload_with("Sakif", "Bangla", "Bangladesh");
            stepDef.user_calls_with_http_request("addPlaceAPI", "Post");
            stepDef.verify_place_id_created_maps_to_using("Sakif", "getPlaceAPI");
        }
    }
}
