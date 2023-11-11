@debug
Feature: As an user I want to verify a scenario so that I can validate complete functionality

  Scenario: Test - 1
    Given I set up the headers
      | Content-Type | application/json |
      | Accept       | application/json |
    And I setup GET user request for "/api/users"
    When I make GET user API calls to "/2"
    Then I verify response code 200
    Then I verify response body
      """
      {
          "data": {
              "id": 2,
              "email": "janet.weaver@reqres.in",
              "first_name": "Janet",
              "last_name": "Weaver",
              "avatar": "https://reqres.in/img/faces/2-image.jpg"
          },
          "support": {
              "url": "https://reqres.in/#support-heading",
              "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
          }
      }
      """
    Then I clear rest-assured

  Scenario: Test - 2
    Given I want to verify a scenario
      | Apple | Mango | Cat | Rat |
    When I pass some headers
      | fruit           | animal | city | age |
      | ${project.name} | cat    | Agra | 21  |