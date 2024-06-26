@all
Feature: As an user I want to make API calls to /user. So that I can verify that the user API is working fine.

  @getUser
  Scenario: Get user details - 1
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

  @getUser
  Scenario: Get user details - 2
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
