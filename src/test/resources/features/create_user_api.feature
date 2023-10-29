@all
Feature: As an user, I want to get started with Api challenge. So that, We can start the project.

  @smoke
  Scenario: Getting Started With The Api
    Given I prepare request specification
    Then I set the headers
      | Content-Type | application/json |
      | Accept       | application/json |
    Then I prepare the body for creating an user
	  """
	  	{
    		"name": "morpheus",
    		"job": "leader"
		}
	  """
    And I perform post request at '/api/users'
    Then I get the response code as 201
    And I extract 'Content-Type' Header