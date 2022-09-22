Feature: Validating Place API

  @AddPlace @Regression
  Scenario Outline: Verify if place is being Successfully added using AddPlaceAPI
    Given Add place Payload with "<name>" "<language>" "<address>"
    When User calls "addPlaceAPI" with "post" http request
    Then The API call got success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_id created maps to "<name>" using "getPlaceAPI"

    Examples:
     | name       | language | address      |
     | My house   | English  | Liverpool St |
#    | Your house | Spanish  | Archer Ave   |

  @DeletePlace @Regression
  Scenario: Verify if delete place functionality is working
    Given DeletePlace payload
    When User calls "deletePlaceAPI" with "post" http request
    Then The API call got success with status code 200
    And "status" in response body is "OK"