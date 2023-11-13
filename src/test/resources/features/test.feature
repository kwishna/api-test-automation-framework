Feature: As an user I want to verify a scenario so that I can validate complete functionality

  @debug
  Scenario: Test
    Given I want to verify a scenario
      | Apple | Mango | Cat | Rat |
    When I pass some headers
      | fruit           | animal | city | age |
      | ${project.name} | cat    | Agra | 21  |
    Then I send the body from "test.json"